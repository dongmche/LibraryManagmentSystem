//package com.example.Fakeapp.Interceptors;
//
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@Component
//public class AuthInterceptor implements HandlerInterceptor {
//
//    @Override
//    public boolean preHandle(HttpServletRequest req,
//                             HttpServletResponse resp,
//                             Object handler) throws Exception {
//        if (req.getSession().getAttribute("userId") == null) {
//            resp.sendRedirect("/login");
//            return false;
//        }
//        return true;
//    }
//}
