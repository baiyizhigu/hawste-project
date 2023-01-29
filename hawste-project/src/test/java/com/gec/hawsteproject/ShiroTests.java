package com.gec.hawsteproject;

import com.gec.hawsteproject.hawaste.entity.SysUser;
import com.gec.hawsteproject.hawaste.service.ISysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ShiroTests {


    @Autowired
    ISysUserService userService;

    @Autowired
    DefaultWebSecurityManager  securityManager;
    /**
     * 将所有用户密码重置为shiro  加密 密码
     * 先将所有重置为123456
     * 再将admin的密码重置为admin
     */
    @Test
    void contextLoads() {
        List<SysUser> list = userService.list();
        list.forEach(user ->{
            String pwd = "123456";
            Md5Hash hash = new Md5Hash(pwd, user.getUsername(), 3);
            System.out.println(hash);
            user.setPassword(hash.toString());
        });
        userService.updateBatchById(list);
    }

    @Test
    void selectOne(){
        SysUser user = userService.getById(2);
        String pwd = "admin";
        Md5Hash hash = new Md5Hash(pwd, user.getUsername(), 3);
        System.out.println(hash);
        user.setPassword(hash.toString());
        userService.updateById(user);
    }

    @Test
    public void login(){
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("admin", "admin");
        subject.login(token);
        System.out.println(subject.isAuthenticated());
        //CachePrincipalNotImplementsAssignedException: Principal class com.gec.hawsteproject.hawaste.entity.SysUser must implements com.github.fartherp.shiro.ShiroFieldAccess
        System.out.println(subject.isPermitted("menu:select"));
    }
}
