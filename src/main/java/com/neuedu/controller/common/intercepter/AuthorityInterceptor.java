package com.neuedu.controller.common.intercepter;

import com.google.gson.Gson;
import com.neuedu.common.Const;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.UserInfo;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * 权限拦截器
 * */
public class AuthorityInterceptor   implements HandlerInterceptor{
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse response, Object handler) throws Exception {

        System.out.println("========preHandle============");

        HandlerMethod handlerMethod=(HandlerMethod)handler;
       String className= handlerMethod.getBean().getClass().getSimpleName();
       String methodName=handlerMethod.getMethod().getName();

       if(className.equals("ProductManageController")&&methodName.equals("set_sale_status")){

       }
        HttpSession session= httpServletRequest.getSession();
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        //重构HttpServerletResponse

         if(userInfo==null||userInfo.getRole()!=Const.RoleEnum.ROLE_ADMIN.getCode()){

             response.reset();
             response.setCharacterEncoding("UTF-8");
             response.setContentType("application/json;charset=UTF-8");
             PrintWriter printWriter=response.getWriter();
             if(userInfo==null){
                 //未登录
                 ServerResponse serverResponse=ServerResponse.serverResponseByError("用户未登录");
                 Gson gson=new Gson();
                 String json=gson.toJson(serverResponse);
                 printWriter.write(json);


             }else{
                 //无权限操作
                 ServerResponse serverResponse=ServerResponse.serverResponseByError("无权限操作");
                 Gson gson=new Gson();
                 String json=gson.toJson(serverResponse);
                 printWriter.write(json);

             }
             printWriter.flush();
             printWriter.close();
             return false;
         }



        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

        System.out.println("========postHandle============");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        System.out.println("========afterCompletion============");
    }
}
