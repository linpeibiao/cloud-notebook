package com.xiaohu.cloud_notebook.service.impl;
import java.util.Date;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohu.cloud_notebook.mapper.NoteBaseMapper;
import com.xiaohu.cloud_notebook.model.domain.BaseUser;
import com.xiaohu.cloud_notebook.model.domain.NoteBase;
import com.xiaohu.cloud_notebook.model.dto.JoinNoteBaseDto;
import com.xiaohu.cloud_notebook.model.dto.NoteBaseDto;
import com.xiaohu.cloud_notebook.model.dto.NoteBaseInfoDto;
import com.xiaohu.cloud_notebook.service.BaseUserService;
import com.xiaohu.cloud_notebook.service.NoteBaseService;
import com.xiaohu.cloud_notebook_common.exception.BusinessException;
import com.xiaohu.cloud_notebook_common.model.domain.User;
import com.xiaohu.cloud_notebook_common.result.ResultCode;
import com.xiaohu.cloud_notebook_common.util.UserHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @author xiaohu
* @description 针对表【t_note_base(知识库表)】的数据库操作Service实现
* @createDate 2022-11-14 23:37:18
*/
@Service
public class NoteBaseServiceImpl extends ServiceImpl<NoteBaseMapper, NoteBase>
    implements NoteBaseService {
    @Autowired
    private NoteBaseMapper noteBaseMapper;
    @Autowired
    private BaseUserService baseUserService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addNoteBase(NoteBaseDto noteBaseDto) {
        // 判空
        if (noteBaseDto == null){
            throw new BusinessException(ResultCode.PARAMS_ERROR);
        }
        String noteBaseName = noteBaseDto.getNoteBaseName();
        // TODO 自动设置创建者id 和创建者昵称
        if (StringUtils.isEmpty(noteBaseName)){
            throw new BusinessException(ResultCode.PARAMS_ERROR, "知识库名不能为空");
        }
        int status = noteBaseDto.getStatus();
        // 判断是否加密
        if (status == 2){
            // 密码是否为空
            if (StringUtils.isEmpty(noteBaseDto.getPassword())){
                throw new BusinessException(ResultCode.PARAMS_ERROR, "请设置加密密码或重新设置状态");
            }
        }
        // 将 noteBaseDto 转换成 实体类对象
        NoteBase noteBase = BeanUtil.copyProperties(noteBaseDto, NoteBase.class, "id");
        save(noteBase);
        // 关系表添加记录
        baseUserService.addRecord(noteBase.getId(), noteBase.getUserId());
        return noteBase.getId();
    }

    @Override
    public boolean infoUpdate(NoteBaseInfoDto noteBaseInfoDto) {
        // 判空
        if (noteBaseInfoDto == null){
            throw new BusinessException(ResultCode.PARAMS_ERROR, "参数不能为空");
        }
        Long id = noteBaseInfoDto.getId();
        if (id == null || id < 1){
            throw new BusinessException(ResultCode.PARAMS_ERROR, "知识库 id 不合法");
        }
        // TODO 获取当前登录用户，判断修改的知识库是否属于当前登录用户
//        User user = UserHolder.get();
//        if (!user.getId().equals(id)){
//            throw new BusinessException(ResultCode.NO_AUTH, "无权限");
//        }

        // 状态修改判断，如果设置为加密，需要设置密码
        int status = noteBaseInfoDto.getStatus();
        if (status == 2){
            // 密码是否为空
            if (StringUtils.isEmpty(noteBaseInfoDto.getPassword())){
                throw new BusinessException(ResultCode.PARAMS_ERROR, "请设置加密密码或重新设置状态");
            }
        }
        // 将 noteBaseDto 转换成 实体类对象
        NoteBase noteBase = BeanUtil.copyProperties(noteBaseInfoDto, NoteBase.class);
        return updateById(noteBase);
    }

    @Override
    public boolean transferTo(NoteBaseDto noteBaseDto, Long noteBaseId) {
        // 判空
        if (noteBaseDto == null || noteBaseId < 0){
            throw new BusinessException(ResultCode.PARAMS_ERROR);
        }

        // 判权
        NoteBase noteBase = getById(noteBaseId);
        // 判断知识库是否存在
        if (noteBase == null){
            throw new BusinessException(ResultCode.NOT_FOUND, "该用户不在知识库中");
        }
        // TODO 判断该知识库的创建者是否为当前登录用户
//        User user = UserHolder.get();
//        if (!noteBase.getUserId().equals(user.getId())){
//            throw new BusinessException(ResultCode.NO_AUTH, "无权限，只能创建者可以转让知识库");
//        }

        // 查看转让的用户是否在此知识库
        Long transferTo = noteBaseDto.getUserId();
        if (!isJoin(transferTo, noteBaseId)){
            throw new BusinessException(ResultCode.NOT_FOUND, "该用户不在知识库中");
        }

        // 更新
        noteBase.setUserId(transferTo);
        noteBase.setUserNackname(noteBaseDto.getUserNackName());
        return updateById(noteBase);
    }

    @Override
    public boolean joinNoteBase(JoinNoteBaseDto joinNoteBaseDto) {
        // 判空
        if (joinNoteBaseDto == null){
            throw new BusinessException(ResultCode.PARAMS_ERROR);
        }
        // TODO userId 可以从当前登录用户拿
        long userId = joinNoteBaseDto.getUserId();
        long noteBaseId = joinNoteBaseDto.getNoteBaseId();
        if (userId < 1 || noteBaseId < 1){
            throw new BusinessException(ResultCode.PARAMS_ERROR, "请求参数不合法");
        }

        // 判断知识库是否存在
        NoteBase noteBase = getById(noteBaseId);
        if (noteBase == null){
            throw new BusinessException(ResultCode.NOT_FOUND, "该知识库不存在");
        }
        // 判断知识库状态
        int status = noteBase.getStatus();
        //   私密 无法加入
        if (1 == status){
            throw new BusinessException(ResultCode.NO_AUTH, "私密知识库无法加入");
        }
        //   加密 需要验证密码
        if (2 == status){
            if (!noteBase.getPassword().equals(joinNoteBaseDto.getPassword())){
                throw new BusinessException(ResultCode.NO_AUTH, "密码输入不正确");
            }
        }

        // 判断是否已经加入（不能重复加入）
        if (isJoin(userId, noteBaseId)){
            throw new BusinessException(ResultCode.PARAMS_ERROR, "已经加入");
        }

        BaseUser baseUser = new BaseUser();
        baseUser.setNoteBaseId(noteBaseId);
        baseUser.setUserId(userId);
        return baseUserService.save(baseUser);
    }

    /**
     * 判断用户是否已经加入知识库
     * @param userId
     * @param noteBaseId
     * @return
     */
    private boolean isJoin(Long userId, Long noteBaseId){

        long count = baseUserService.count(new QueryWrapper<BaseUser>()
                .eq("user_id", userId)
                .eq("note_base_id", noteBaseId));
        return count > 0;
    }
}




