package com.neuedu.controller.backend;


import com.neuedu.common.Const;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IUserService;
import com.neuedu.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * 后台用户控制器类
 * */
@RestController
@RequestMapping(value = "/manage/user")
public class UserManageController {

    @Autowired
    IUserService userService;
    /**
     * 管理员登录
     *
     * */
    @RequestMapping(value = "/login.do")
    public ServerResponse login(HttpServletRequest request, HttpServletResponse response,HttpSession session, String username, String password){
        ServerResponse serverResponse=userService.login(username,password);
        if(serverResponse.isSuccess()){//登录成功
            UserInfo userInfo=(UserInfo) serverResponse.getData();
            if(userInfo.getRole()==Const.RoleEnum.ROLE_CUSTOMER.getCode()){
                return ServerResponse.serverResponseByError("无权限登录");
            }
        //    session.setAttribute(Const.CURRENTUSER,userInfo);
            String userJson= JsonUtils.obj2String(userInfo);
            RedisPoolUtil.set(session.getId(),userJson);
            CookieUtils.writeCookie(response,Const.JESSESSIONID_COOKIE,session.getId());
            //生成autoLogintoken
           String ip= IpUtils.getRemoteAddress(request);
            try {
                String mac=IpUtils.getMACAddress(ip);
                String token= MD5Utils.getMD5Code(mac);
                //token保存到数据库
                userService.updateTokenByUserId(userInfo.getId(),token);
                //token作为cookie相应到客户端
               /* Cookie autoLoginTokenCookie=new Cookie(Const.AUTOLOGINTOKEN,token);
                autoLoginTokenCookie.setPath("/");
                autoLoginTokenCookie.setMaxAge(60*60*24*7);//7天
                response.addCookie(autoLoginTokenCookie);*/
                CookieUtils.writeCookie(response,Const.AUTOLOGINTOKEN,token);


            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (SocketException e) {
                e.printStackTrace();
            }

        }
        return  serverResponse;
    }


}
