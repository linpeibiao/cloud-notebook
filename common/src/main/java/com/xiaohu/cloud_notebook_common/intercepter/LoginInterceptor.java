package com.xiaohu.cloud_notebook_common.intercepter;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xiaohu.cloud_notebook_common.exception.BusinessException;
import com.xiaohu.cloud_notebook_common.model.domain.User;
import com.xiaohu.cloud_notebook_common.result.ResultCode;
import com.xiaohu.cloud_notebook_common.util.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Map;

import static com.xiaohu.cloud_notebook_common.constant.RedisConstant.LOGIN_USER_KEY;

/**
 * @author xiaohu
 * @date 2022/11/19/ 16:55
 * @description
 */
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("登录拦截器被执行");
        // 1.判断是否需要拦截（ThreadLocal中是否有用户）
        if (UserHolder.get() == null) {
            log.info("不存在登录用户");
            HttpSession httpSession = request.getSession();
            // 从 session 获取 token
            String token = (String) httpSession.getAttribute("token");
            // 判断 token 是否存在
            if (StringUtils.isBlank(token)){
                return false;
            }
            String tokenKey = LOGIN_USER_KEY + token;
            // 先从 redis 中拿到登录信息，若数据为空，返回false
            Map<Object, Object> userMap = stringRedisTemplate.opsForHash().entries(tokenKey);
            // 判断 userMap
            if (userMap.isEmpty()){
                return false;
            }
            // 将 map 转换成 User 实体
            User user = BeanUtil.fillBeanWithMap(userMap, new User(), false);
            // 将 USer 保存在 UserHolder
            UserHolder.save(user);
            return true;
        }
        // 有用户，则放行
        log.info("存在登录用户");
        return true;
    }

}
