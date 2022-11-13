package com.xiaohu.cloud_notebook.controller;

import com.xiaohu.cloud_notebook.model.domain.User;
import com.xiaohu.cloud_notebook.model.dto.UserDto;
import com.xiaohu.cloud_notebook.model.vo.LoginUser;
import com.xiaohu.cloud_notebook.service.UserService;
import com.xiaohu.cloud_notebook_common.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author xiaohu
 * @date 2022/11/09/ 0:12
 * @description
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation("通过账号设置密码")
    @PostMapping("set/password")
    public Result<String> setPassword(@RequestBody LoginUser loginUser){
        boolean isSuccess = userService.setPassword(loginUser);
        return isSuccess ? Result.success("修改成功") : Result.fail("");
    }

    @ApiOperation("用户更改手机号")
    @GetMapping("change/phone/{phone}")
    public Result<String> changePhone(@PathVariable("phone") String phone){
        // TODO
        return null;
    }

    @PostMapping("/register")
    @ApiOperation("用户注册")
    public Result<User> register(@RequestBody LoginUser loginUser){
        boolean isSuccess = userService.register(loginUser);
        if (!isSuccess){
            return Result.fail();
        }
        return Result.success(loginUser.getAccount());
    }

    /**
     *
     * @param userDto
     * @return
     */
    @ApiOperation("用户更新信息")
    @PostMapping("/update")
    public Result<String> update(@RequestBody UserDto userDto){
        boolean isSuccess = userService.updateInfo(userDto);
        return isSuccess ? Result.success("修改成功") : Result.fail("");
    }

    @ApiOperation("用户账号登陆")
    @PostMapping("/login/by/account")
    public Result<String> loginByAccount(@RequestBody LoginUser loginUser){
        String token = userService.loginByAccount(loginUser);
        return Result.success(token);
    }

    /**
     * 用户登录注册
     * @return
     */
    @ApiOperation("用户手机登录")
    @PostMapping("/login")
    public Result<String> login(@RequestBody LoginUser loginUser){
        String token = userService.login(loginUser);
        return Result.success(token);
    }

    @ApiOperation("发送手机验证码")
    @GetMapping("/sendCode/{phone}")
    public Result<String> sendCode(@PathVariable("phone") String phone){
        userService.sendCode(phone);
        return Result.success("发送成功");
    }
}
