package com.usc.lzh.doms.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    /**
     * 请求处理前调用
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 判断session中的属性是否为空
        Object uid = request.getSession().getAttribute("uid");
        Object uname = request.getSession().getAttribute("uname");
        // 如果是空说明还没登录，去登录界面
        if (uid == null || uname == null){
            response.sendRedirect(request.getContextPath()+"/login.html");
            return false;
        }
        return true;
    }
}
