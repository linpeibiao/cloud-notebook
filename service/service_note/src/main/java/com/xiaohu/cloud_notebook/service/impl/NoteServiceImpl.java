package com.xiaohu.cloud_notebook.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohu.cloud_notebook.mapper.NoteMapper;
import com.xiaohu.cloud_notebook.model.domain.Note;
import com.xiaohu.cloud_notebook.model.domain.NoteBase;
import com.xiaohu.cloud_notebook.model.domain.NoteContent;
import com.xiaohu.cloud_notebook.model.domain.NoteContentHistory;
import com.xiaohu.cloud_notebook.model.dto.NoteDto;
import com.xiaohu.cloud_notebook.service.NoteBaseService;
import com.xiaohu.cloud_notebook.service.NoteContentService;
import com.xiaohu.cloud_notebook.service.NoteService;
import com.xiaohu.cloud_notebook.util.MqProvider;
import com.xiaohu.cloud_notebook_common.exception.BusinessException;
import com.xiaohu.cloud_notebook_common.model.domain.User;
import com.xiaohu.cloud_notebook_common.result.ResultCode;
import com.xiaohu.cloud_notebook_common.util.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.xiaohu.cloud_notebook_common.constant.RedisConstant.DISTRIBUTED_LOCK_KEY;

/**
* @author xiaohu
* @description 针对表【t_note(笔记文档表)】的数据库操作Service实现
* @createDate 2022-11-14 23:37:18
*/
@Slf4j
@Service
public class NoteServiceImpl extends ServiceImpl<NoteMapper, Note>
    implements NoteService {

    @Autowired
    private NoteBaseService noteBaseService;
    @Autowired
    private NoteContentService noteContentService;
    @Autowired
    private MqProvider mqProvider;

    /**
     * 引入 redisson 用于获取分布式
     */
    @Autowired
    private RedissonClient redissonClient;

    @Override
    // TODO 优化事务解决方案
    @Transactional(rollbackFor = Exception.class)
    public Long addNote(NoteDto noteDto) {
        log.info(Thread.currentThread().getName() + "用户添加笔记");
        if (noteDto == null){
            throw new BusinessException(ResultCode.PARAMS_ERROR);
        }
        // title 不能为空
        if (StringUtils.isBlank(noteDto.getTitle())){
            throw new BusinessException(ResultCode.PARAMS_ERROR, "标题不能为空");
        }
        // 判断知识库 id
        Long noteBaseId = noteDto.getNoteBaseId();
        if (noteBaseId == null || noteBaseId <= 0){
            throw new BusinessException(ResultCode.PARAMS_ERROR, "知识库id 不合法");
        }
        // 不存在
        if (!noteBaseService.isNoteBaseExist(noteBaseId)){
            throw new BusinessException(ResultCode.NOT_FOUND, "该知识库不存在");
        }

        // dto 转换成实体对象
        Note note = BeanUtil.copyProperties(noteDto, Note.class);

        // 需要指定创建者和所属知识库
        // 获取登录用户 设置创建者信息
        User user = UserHolder.get();
        if (user == null){
            throw new BusinessException(ResultCode.NOT_LOGIN, "未登录");
        }
        Long createdUserId = user.getId();
        String userNackName = user.getNackname();
        note.setUserId(createdUserId);
        note.setUserNackname(userNackName);
        // 笔记主要信息和正文信息分开保存
        NoteContent noteContent = new NoteContent();
        noteContent.setNoteId(note.getId());
        noteContent.setContent(noteDto.getContent());
        // 保存在笔记正文表
        noteContentService.save(noteContent);
        // 保存在笔记表
        save(note);
        saveToHistory(note);
        return note.getId();
    }

    /**
     * 调用中间件保存在历史笔记正文表
     * @param note
     */
    private void saveToHistory(Note note) {
        // 需要指定创建者和所属知识库
        // 获取登录用户 设置创建者信息
        User user = UserHolder.get();
        if (user == null){
            throw new BusinessException(ResultCode.NOT_LOGIN, "未登录");
        }
        NoteContentHistory history = new NoteContentHistory();
        history.setUserId(user.getId());
        history.setNoteId(note.getId());
        history.setContent(note.getContent());
        mqProvider.sendMessage(history);
    }

    @Override
    public boolean deleteNote(Long noteId) {
        if (noteId == null || noteId < 1){
            throw new BusinessException(ResultCode.PARAMS_ERROR);
        }
        // 获取当前用户
        User user = UserHolder.get();
        if (user == null){
            throw new BusinessException(ResultCode.NOT_LOGIN, "未登录");
        }
        Note note = getById(noteId);
        if (note == null){
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        // 判断笔记的创建者是否为当前登录用户
        if (!user.getId().equals(note.getUserId())){
            throw new BusinessException(ResultCode.NO_AUTH, "不能删除自己之外的笔记");
        }
        return removeById(noteId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean editNode(NoteDto noteDto, Long noteId) {
        // 判空
        if (noteDto == null || noteId == null || noteId < 1){
            throw new BusinessException(ResultCode.PARAMS_ERROR);
        }
        // 获取分布式锁
        String lockKey = DISTRIBUTED_LOCK_KEY + noteId;
        RLock lock = redissonClient.getLock(lockKey);
        try{
            // 等待时间，存活时间，时间单位（等待时间即抢不到锁继续等待抢锁的最大时间，存活时间为-1时会自动开启开门狗机制）
            boolean isLock = lock.tryLock(3L, -1, TimeUnit.SECONDS);
            if (!isLock){
                return false;
            }
            // 成功拿到分布式锁，保存编辑后笔记
            // 将 dto 转换成 实体类对象
            Note note = BeanUtil.copyProperties(noteDto, Note.class);
            note.setId(noteId);
            // 只需要更新正文（自动更新 版本号）
            noteContentService.update(new UpdateWrapper<NoteContent>()
                    .eq("note_id", noteId)
                    .set("content", note.getContent()));
            // 再将正本保存在历史版本
            saveToHistory(note);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            // 释放锁
            lock.unlock();
        }
        return false;
    }

    @Override
    public List<Note> getNotesByNoteBaseId(Long noteBaseId) {
        if (noteBaseId == null || noteBaseId < 1){
            throw new BusinessException(ResultCode.PARAMS_ERROR);
        }

        // 获取当前登录用户
        User user = UserHolder.get();
        if (user == null){
            throw new BusinessException(ResultCode.NOT_LOGIN);
        }
        // 获取知识库
        NoteBase noteBase = noteBaseService.getById(noteBaseId);
        // 判断知识库是否存在 是否属于当前登录用户
        if (noteBase == null || !user.getId().equals(noteBase.getUserId())){
            throw new BusinessException(ResultCode.NO_AUTH, "无权限");
        }
        // 查询
        return list(new QueryWrapper<Note>().eq("note_base_id", noteBaseId));
    }
}




