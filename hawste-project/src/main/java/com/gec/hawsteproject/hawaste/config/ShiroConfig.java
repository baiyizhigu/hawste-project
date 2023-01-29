package com.gec.hawsteproject.hawaste.config;

import com.gec.hawsteproject.hawaste.realm.MyRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfig {

    /**
     * 自定义加密器
     * @return
     */
    @Bean
    public HashedCredentialsMatcher getHashedCredentialsMatcher(){
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");//设置加密算法
        matcher.setHashIterations(3);//设置散列次数
        return matcher;
    }

    /**
     * 自定义  realm
     * @param matcher
     * @return
     */
    @Bean
    public MyRealm getRealm(HashedCredentialsMatcher matcher){
        MyRealm myRealm = new MyRealm();
        myRealm.setCredentialsMatcher(matcher);//设置加密匹配器
        return myRealm;
    }

    /**
     * 自定义 WebSecurityManager   注意指定bean的名字为 securityManager
     * @param myRealm
     * @return
     */
   /* @Bean("securityManager")
    public DefaultWebSecurityManager getWebSecurityManager(MyRealm myRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myRealm);
        return  securityManager;
    }*/


    /**
     * 配置shiro的过滤器规则
     */
    @Bean
    public ShiroFilterChainDefinition getShiroFilterChainDefinition(){
        //默认过滤器链对象
        DefaultShiroFilterChainDefinition chainDefinition =
                new DefaultShiroFilterChainDefinition();
        //添加过滤器规则 过滤器规则从上往下执行

        //认证+授权规则
        //访问 /dept下请求  的鉴定规则：  必须有登录且是角色中的dept-admin,dept-01,dept-02才能访问
//        chainDefinition.addPathDefinition("/dept/**","authc,roles[dept-admin]");
//        chainDefinition.addPathDefinition("/emp/**","authc,roles[emp-admin]");
        //放行规则
        chainDefinition.addPathDefinition("/ace/**","anon");
        chainDefinition.addPathDefinition("/bootstrap/**","anon");
        chainDefinition.addPathDefinition("/chosen/**","anon");
        chainDefinition.addPathDefinition("/css/**","anon");
        chainDefinition.addPathDefinition("/fonts/**","anon");
        chainDefinition.addPathDefinition("/gritter/**","anon");
        chainDefinition.addPathDefinition("/images/**","anon");
        chainDefinition.addPathDefinition("/img/**","anon");
        chainDefinition.addPathDefinition("/jquery/**","anon");
        chainDefinition.addPathDefinition("/js/**","anon");
        chainDefinition.addPathDefinition("/layer/**","anon");
        chainDefinition.addPathDefinition("/switch/**","anon");
        chainDefinition.addPathDefinition("/ueditor/**","anon");
        chainDefinition.addPathDefinition("/uploads/**","anon");
        chainDefinition.addPathDefinition("/validate/**","anon");
        chainDefinition.addPathDefinition("/vue/**","anon");
        chainDefinition.addPathDefinition("/ztree/**","anon");
//        chainDefinition.addPathDefinition("/login/**","anon");
        chainDefinition.addPathDefinition("/doLogin/**","anon");
        chainDefinition.addPathDefinition("/login.html","anon");
        chainDefinition.addPathDefinition("/notlogin.html","anon");
        //对验证码接口放行
        chainDefinition.addPathDefinition("/checkKaptcha","anon");
        chainDefinition.addPathDefinition("/captcha/**","anon");

        //认证规则
        chainDefinition.addPathDefinition("/**","authc");

        return chainDefinition;
    }
}
