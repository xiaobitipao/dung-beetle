package com.xiaobitipao.dungbeetle.core.interceptors;

import com.auth0.jwt.interfaces.Claim;
import com.xiaobitipao.dungbeetle.core.LocalUser;
import com.xiaobitipao.dungbeetle.exception.http.ForbiddenException;
import com.xiaobitipao.dungbeetle.exception.http.UnAuthenticatedException;
import com.xiaobitipao.dungbeetle.model.User;
import com.xiaobitipao.dungbeetle.service.UserService;
import com.xiaobitipao.dungbeetle.util.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;

public class PermissionInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private UserService userService;

    public PermissionInterceptor() {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Optional<ScopeLevel> scopeLevel = this.getScopeLevel(handler);
        if (!scopeLevel.isPresent()) {
            // 如果没有获取到 Optional 的话，说明访问的 API 是一个公开的接口，直接通过
            return true;
        }

        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.isEmpty(bearerToken)) {
            // 没有获取到 token 的话说明令牌不合法
            throw new UnAuthenticatedException(10004);
        }

        if (!bearerToken.startsWith("Bearer ")) {
            // 令牌以 Bearer 为前缀是 JWT 的标准，这里也遵循这个标准，所以要求令牌以 Bearer 开头，否则令牌不合法
            throw new UnAuthenticatedException(10004);
        }

        // 校验令牌是否过期或无效
        String token = bearerToken.split(" ")[1];
        Optional<Map<String, Claim>> optionalMap = JwtToken.getClaims(token);
        Map<String, Claim> map = optionalMap.orElseThrow(() -> new UnAuthenticatedException(10004));

        // 到此，令牌没有过期并且合法，接下来对 scope 进行比对
        if (!this.hasPermission(scopeLevel.get(), map)) {
            throw new ForbiddenException(10005);
        }

        // 保存用户信息保存至 ThreadLocal
        this.setToThreadLocal(map);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LocalUser.clear();
        super.afterCompletion(request, response, handler, ex);
    }

    /**
     * 获取 Controller 类或者 Controller 的方法方法上标记的 @ScopeLevel 注解
     * TODO: 获取 Controller 类上标记的注解
     */
    private Optional<ScopeLevel> getScopeLevel(Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            ScopeLevel scopeLevel = handlerMethod.getMethod().getAnnotation(ScopeLevel.class);
            if (scopeLevel != null) {
                return Optional.of(scopeLevel);
            }
        }
        return Optional.empty();
    }

    /**
     * 根据 token 中的 scope 和 API 上的注解 @ScopeLevel 中的 level 值以确认用户是否有访问当前 API 的权限
     */
    private boolean hasPermission(ScopeLevel scopeLevel, Map<String, Claim> map) {
        // 获取注解中的 level
        Integer level = scopeLevel.value();
        // 获取 token 中的 scope
        Integer scope = map.get("scope").asInt();
        if (level > scope) {
            // 用户未被授权
            return false;
        }
        return true;
    }

    /**
     * 根据 token 中自定义信息的 uid 获取 user 信息并写入 LocalUser
     */
    private void setToThreadLocal(Map<String, Claim> map) {
        Long uid = map.get("uid").asLong();
        Integer scope = map.get("scope").asInt();
        User user = this.userService.getUserById(uid);
        LocalUser.set(user, scope);
    }
}
