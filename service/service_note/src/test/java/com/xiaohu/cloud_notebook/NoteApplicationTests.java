package com.xiaohu.cloud_notebook;

import com.xiaohu.cloud_notebook.model.domain.BaseUser;
import com.xiaohu.cloud_notebook.service.BaseUserService;
import com.xiaohu.cloud_notebook_common.model.domain.User;
import com.xiaohu.cloud_notebook_common.util.UserHolder;
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
    private BaseUserService baseUserService;

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

    @Test
    public void loginTest(){
        User user = new User();
        user.setId(67L);
        UserHolder.save(user);
    }
}
