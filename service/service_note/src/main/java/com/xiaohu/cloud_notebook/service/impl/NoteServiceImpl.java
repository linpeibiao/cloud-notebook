package com.xiaohu.cloud_notebook.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohu.cloud_notebook.mapper.NoteMapper;
import com.xiaohu.cloud_notebook.model.domain.Note;
import com.xiaohu.cloud_notebook.service.NoteService;
import org.springframework.stereotype.Service;

/**
* @author xiaohu
* @description 针对表【t_note(笔记文档表)】的数据库操作Service实现
* @createDate 2022-11-14 23:37:18
*/
@Service
public class NoteServiceImpl extends ServiceImpl<NoteMapper, Note>
    implements NoteService {

}




