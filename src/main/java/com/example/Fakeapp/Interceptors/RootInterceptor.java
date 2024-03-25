package com.example.Fakeapp.Interceptors;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.example.Fakeapp.Statics.Statics.UNPRIVILEGED_USERS_STARTING_ID;

@Component
public class RootInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest req,
                             HttpServletResponse resp,
                             Object handler) throws Exception {
        if (req.getSession().getAttribute("userId") != null) {
            Long id = (Long) req.getSession().getAttribute("userId");
            if (id < UNPRIVILEGED_USERS_STARTING_ID){
                return true;
            }
        }

        resp.sendRedirect("/root/login");
        return false;
    }

}