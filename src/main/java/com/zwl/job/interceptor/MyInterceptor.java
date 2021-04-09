package com.zwl.job.interceptor;

import com.zwl.job.entity.Result;
import com.zwl.job.entity.User;
import com.zwl.job.utils.JwtUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // System.out.println("===preHandle===");
        String token = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0){
            for (Cookie cookie : cookies){
                if (cookie.getName().equals("token")) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
//        System.out.println("token = " + token);
        if (token == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        } else {
            String userId = JwtUtil.verify(token);
            if (!userId.equals("0")) {
                return true;
            } else {
                response.sendRedirect(request.getContextPath() + "/login");
                return false;
            }
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // System.out.println("===postHandle===");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // System.out.println("===afterCompletion===");
    }
}
