package com.example.testproj.handler;

import com.example.testproj.Clazz.User.SessionUser;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TeamLeaderInterceptor extends HandlerInterceptorAdapter {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        HttpSession httpSession = request.getSession();
        if (httpSession.getAttribute("user") != null) {
            SessionUser user = (SessionUser) httpSession.getAttribute("user");
            if (modelAndView != null) {
                // Model에 사용자 객체 추가
                modelAndView.addObject("user", user);
                if (user.getPOSITION().equals("팀장")) {
                    modelAndView.addObject("isTeamleader", true);
                }else {
                    modelAndView.addObject("isTeammember", true);
                }
            }
        }
    }
}