package com.xiaohu.cloud_notebook.service;

import com.xiaohu.cloud_notebook_common.model.domain.User;
import com.xiaohu.cloud_notebook_common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author xiaohu
 * @date 2022/12/01/ 16:39
 * @description
 */
@FeignClient("cloud-notebook-user")
@Component
public interface UserFeignService {
    /**
     * 获取用户信息
     * @param ids
     * @return
     */
    @RequestMapping("/user/list/by/ids")
    Result<List<User>> getUserByIds(@RequestBody List<Long> ids);
}
