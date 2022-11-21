package com.xiaohu.cloud_notebook.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohu.cloud_notebook.mapper.NoteMapper;
import com.xiaohu.cloud_notebook.model.domain.Note;
import com.xiaohu.cloud_notebook.model.dto.AddNoteDto;
import com.xiaohu.cloud_notebook.service.NoteBaseService;
import com.xiaohu.cloud_notebook.service.NoteService;
import com.xiaohu.cloud_notebook_common.exception.BusinessException;
import com.xiaohu.cloud_notebook_common.model.domain.User;
import com.xiaohu.cloud_notebook_common.result.ResultCode;
import com.xiaohu.cloud_notebook_common.util.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public Long addNote(AddNoteDto addNoteDto) {
        log.info(Thread.currentThread().getName() + "用户添加笔记");
        if (addNoteDto == null){
            throw new BusinessException(ResultCode.PARAMS_ERROR);
        }
        // title 不能为空
        if (StringUtils.isBlank(addNoteDto.getTitle())){
            throw new BusinessException(ResultCode.PARAMS_ERROR, "标题不能为空");
        }
        // 判断知识库 id
        Long noteBaseId = addNoteDto.getNoteBaseId();
        if (noteBaseId == null || noteBaseId <= 0){
            throw new BusinessException(ResultCode.PARAMS_ERROR, "知识库id 不合法");
        }
        // 不存在
        if (!noteBaseService.isNoteBaseExist(noteBaseId)){
            throw new BusinessException(ResultCode.NOT_FOUND, "该知识库不存在");
        }

        // dto 转换成实体对象
        Note note = BeanUtil.copyProperties(addNoteDto, Note.class);

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
        save(note);
        return note.getId();
    }
}




