package com.xiaohu.cloud_notebook;
import com.xiaohu.cloud_notebook.constant.NoteEditingStatusEnum;
import java.util.Date;

import com.xiaohu.cloud_notebook.controller.NoteController;
import com.xiaohu.cloud_notebook.model.domain.BaseUser;
import com.xiaohu.cloud_notebook.model.domain.Note;
import com.xiaohu.cloud_notebook.service.BaseUserService;
import com.xiaohu.cloud_notebook.service.NoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author xiaohu
 * @date 2022/11/16/ 16:26
 * @description
 */
@SpringBootTest
public class NoteApplicationTests {
    @Autowired
    private NoteController noteController;
    @Autowired
    private BaseUserService baseUserService;
    @Autowired
    private NoteService noteService;

    @Test
    public void saveEnumTest(){
        Note note = new Note();
        note.setTitle("");
        note.setUserId(67L);
        note.setUserNackname("收割稻草的假面骑士");
        note.setContent("and I will be a knight!");
        note.setStatus(0);
        note.setNoteBaseId(4L);
        note.setIsEditing(NoteEditingStatusEnum.NOTEDITING);
        noteService.save(note);
    }

    @Test
    public void saveTest(){
        BaseUser baseUser = new BaseUser();
        baseUser.setNoteBaseId(1L);
        baseUser.setUserId(67L);
        baseUserService.save(baseUser);
    }

    @Test
    public void updateTest(){
        BaseUser baseUser = baseUserService.getById(2L);
        baseUser.setNoteBaseId(12L);
        baseUser.setUserId(67L);
        baseUserService.updateById(baseUser);
    }

}
