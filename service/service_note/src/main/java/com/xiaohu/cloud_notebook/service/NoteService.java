package com.xiaohu.cloud_notebook.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaohu.cloud_notebook.model.domain.Note;
import com.xiaohu.cloud_notebook.model.dto.AddNoteDto;

/**
* @author xiaohu
* @description 针对表【t_note(笔记文档表)】的数据库操作Service
* @createDate 2022-11-14 23:37:18
*/
public interface NoteService extends IService<Note> {

    /**
     * 添加笔记
     * @param addNoteDto
     * @return
     */
    Long addNote(AddNoteDto addNoteDto);

}
