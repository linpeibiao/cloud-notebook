package com.xiaohu.cloud_notebook.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaohu.cloud_notebook.model.domain.NoteBase;
import com.xiaohu.cloud_notebook.model.dto.JoinNoteBaseDto;
import com.xiaohu.cloud_notebook.model.dto.NoteBaseDto;
import com.xiaohu.cloud_notebook.model.dto.NoteBaseInfoDto;

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

    /**
     * 修改知识库基本信息
     * @param noteBaseInfoDto
     * @return
     */
    boolean infoUpdate(NoteBaseInfoDto noteBaseInfoDto);

    /**
     * 转让知识库
     * @param noteBaseDto
     * @return
     */
    boolean transferTo(NoteBaseDto noteBaseDto, Long noteBaseId);

    /**
     * 加入知识库
     * @param joinNoteBaseDto
     * @return
     */
    boolean joinNoteBase(JoinNoteBaseDto joinNoteBaseDto);

    /**
     * 退出知识库
     * @param noteBaseId
     * @return
     */
    boolean exitNoteBase(Long noteBaseId);

    /**
     *
     * @param noteBaseId
     * @return
     */
    NoteBase getNoteBaseById(Long noteBaseId);

    /**
     * 分页获取某用户的所有知识库
     * @param pageNum
     * @param pageSize
     * @param userId
     * @return
     */
    IPage<NoteBase> getNoteBasePageByUserId(int pageNum, int pageSize, Long userId);

    /**
     * 分页模糊查询
     * @param pageNum
     * @param pageSize
     * @param noteBaseName
     * @return
     */
    IPage<NoteBase> getNoteBasePageByNoteBaseName(int pageNum, int pageSize, String noteBaseName);
}
