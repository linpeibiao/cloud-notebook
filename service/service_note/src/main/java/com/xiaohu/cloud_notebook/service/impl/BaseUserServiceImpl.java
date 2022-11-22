package com.xiaohu.cloud_notebook.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.xiaohu.cloud_notebook.mapper.BaseUserMapper;
import com.xiaohu.cloud_notebook.model.domain.BaseUser;
import com.xiaohu.cloud_notebook.service.BaseUserService;
import org.springframework.stereotype.Service;

/**
* @author xiaohu
* @description 针对表【t_base_user(用户---知识库关系表)】的数据库操作Service实现
* @createDate 2022-11-14 23:37:18
*/
@Service
public class BaseUserServiceImpl extends ServiceImpl<BaseUserMapper, BaseUser>
    implements BaseUserService {

    @Override
    public boolean addRecord(Long noteBaseId, Long userId) {
        BaseUser baseUser = new BaseUser();
        baseUser.setUserId(userId);
        baseUser.setNoteBaseId(noteBaseId);
        return save(baseUser);
    }
}




