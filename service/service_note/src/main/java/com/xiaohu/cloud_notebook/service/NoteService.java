package com.xiaohu.cloud_notebook.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaohu.cloud_notebook.model.domain.Note;
import com.xiaohu.cloud_notebook.model.dto.NoteDto;

import java.util.List;

/**
* @author xiaohu
* @description 针对表【t_note(笔记文档表)】的数据库操作Service
* @createDate 2022-11-14 23:37:18
*/
public interface NoteService extends IService<Note> {

    /**
     * 添加笔记
     * @param noteDto
     * @return
     */
    Long addNote(NoteDto noteDto);

    /**
     * 删除
     * @param noteId
     * @return
     */
    boolean deleteNote(Long noteId);

    /**
     * 编辑笔记
     * @param noteDto
     * @return
     */
    boolean editNode(NoteDto noteDto, Long noteId);

    /**
     * 获取用户某知识库中的所有笔记
     * @param noteBaseId
     * @return
     */
    List<Note> getNotesByNoteBaseId(Long noteBaseId);
}
