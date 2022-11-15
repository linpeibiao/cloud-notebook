package com.xiaohu.cloud_notebook.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaohu.cloud_notebook.model.domain.NoteBase;
import com.xiaohu.cloud_notebook.model.dto.NoteBaseDto;

/**
* @author xiaohu
* @description 针对表【t_note_base(知识库表)】的数据库操作Service
* @createDate 2022-11-14 23:37:18
*/
public interface NoteBaseService extends IService<NoteBase> {

    /**
     * 添加知识库
     * @param noteBaseDto
     * @return
     */
    Long addNoteBase(NoteBaseDto noteBaseDto);
}
