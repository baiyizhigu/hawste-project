package com.gec.hawsteproject.hawaste.controller;

import com.anji.captcha.model.common.ResponseModel;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import com.gec.hawsteproject.hawaste.entity.ResponseStatus;
import com.gec.hawsteproject.hawaste.entity.ResultBean;
import com.gec.hawsteproject.hawaste.entity.SysUser;
import com.gec.hawsteproject.hawaste.service.ISysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    ISysUserService userService;
    @Autowired
    private CaptchaService captchaService;

    /**
     *
     * 登录
     * 1.校验传入的验证码是否正确
     * 2.匹配账户密码是否正确,正确则跳转到index页,并且将用户绑定到session
     *   错误则显示错误提示
     *
     * @return
     */
    @RequestMapping("doLogin")
    @ResponseBody
    public ResultBean doLogin(@RequestParam Map<String,Object> params, HttpSession session){
        if(params.containsKey("username")&&!ObjectUtils.isEmpty(params.get("username"))&&
                params.containsKey("password")&&!ObjectUtils.isEmpty(params.get("password"))){
            /*SysUser sysUser = new SysUser();*/
            String username = (String) params.get("username");
            /*sysUser.setUsername(username);*/
            //密码匹配逻辑:  将用户传入的密码进行加密后与数据库加密的密码进行比较
            String password = (String) params.get("password");
            /*sysUser.setPassword(EncryptUtils.MD5_HEX(EncryptUtils.MD5_HEX(password)+username));*/
            //返回对象是null则失败  否则是成功登录
           /* sysUser = userService.selectByNameAndPwd(sysUser);

            if(sysUser!=null){
                sysUser.setPassword(null);//不对密码进行状态管理
                //放入session
                session.setAttribute("loginUser",sysUser);
                //存储用户信息
                HashMap<String, Object> map = new HashMap<>();
                map.put("loginUser",sysUser);
                return ResultBean.ok(map);//登录成功后将用户信息返回到客户端
            }*/
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);//登录认证

            if(subject.isAuthenticated()){
                SysUser sysUser = (SysUser) subject.getPrincipal();
                sysUser.setPassword(null);//不对密码进行状态管理
                //放入session
                session.setAttribute("loginUser",sysUser);
                //存储用户信息
                HashMap<String, Object> map = new HashMap<>();
                map.put("loginUser",sysUser);
                return ResultBean.ok(map);//登录成功后将用户信息返回到客户端
            }
        }
        return ResultBean.fail(ResponseStatus.USERNAME_PASS_ERROR);
    }




    /**
     * 登出
     */
    @RequestMapping("logout")
    public String logout(HttpSession session){
        session.invalidate();   //  spring session  退出
        SecurityUtils.getSubject().logout();//shiro 退出用户登录状态
        return  "redirect:/login.html";
    }


    /**
     * 服务端的验证码二次校验
     * @param captchaVO
     * @return
     */
    @PostMapping("/checkKaptcha")
    @ResponseBody
    public ResponseModel get(@RequestBody CaptchaVO captchaVO) {
        //必传参数：captchaVO.captchaVerification
        ResponseModel response = captchaService.verification(captchaVO);
        return response;
    }
}
