package com.xiaohu.cloud_notebook;
import cn.hutool.http.HttpUtil;
import com.xiaohu.cloud_notebook.constant.NoteEditingStatusEnum;
import java.util.Date;
import java.util.HashMap;

import com.xiaohu.cloud_notebook.controller.NoteController;
import com.xiaohu.cloud_notebook.model.domain.BaseUser;
import com.xiaohu.cloud_notebook.model.domain.Note;
import com.xiaohu.cloud_notebook.service.BaseUserService;
import com.xiaohu.cloud_notebook.service.NoteService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xiaohu
 * @date 2022/11/16/ 16:26
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class NoteApplicationTests {
    @Autowired
    private NoteController noteController;
    @Autowired
    private BaseUserService baseUserService;
    @Autowired
    private NoteService noteService;

    @Test
    public void hutoolsTest(){
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("account", "linxiaohu");
        paramMap.put("password", "linxiaohu");
        Object result = HttpUtil.get("http://43.139.60.130:9000/user/login/by/account", paramMap);
        System.out.println(result);
    }

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
