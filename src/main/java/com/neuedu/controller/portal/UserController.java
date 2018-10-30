package com.neuedu.controller.portal;

import com.neuedu.common.Const;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value ="/user" )
public class UserController {

    @Autowired
    IUserService userService;

   /**
    * 登录
    * */
   @RequestMapping(value = "/login.do")
   public ServerResponse login(HttpSession session, String username, String password){
      ServerResponse serverResponse=userService.login(username,password);
      if(serverResponse.isSuccess()){//登录成功
          UserInfo userInfo=(UserInfo) serverResponse.getData();
          session.setAttribute(Const.CURRENTUSER,userInfo);
      }
       return  serverResponse;
   }

    /**
     * 注册
     * */
    @RequestMapping(value = "/register.do")
    public ServerResponse register(HttpSession session,UserInfo userInfo){
        ServerResponse serverResponse=userService.register(userInfo);

        return  serverResponse;
    }

    /**
     * 根据用户名查询密保问题
     * */
    @RequestMapping(value = "/forget_get_question.do")
    public ServerResponse forget_get_question(String username){
        ServerResponse serverResponse=userService.forget_get_question(username);

        return  serverResponse;
    }

   /**
    * 提交问题答案
    * */
   @RequestMapping(value = "/forget_check_answer.do")
   public ServerResponse forget_check_answer(String username,String question,String answer){
       ServerResponse serverResponse=userService.forget_check_answer(username,question,answer);

       return  serverResponse;
   }

   /**
    * 忘记密码的重置密码
    * */

    @RequestMapping(value = "/forget_reset_password.do")
    public ServerResponse forget_reset_password(String username,String passwordNew,String forgetToken){
        ServerResponse serverResponse=userService.forget_reset_password(username,passwordNew,forgetToken);

        return  serverResponse;
    }




}
