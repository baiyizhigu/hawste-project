package com.gec.hawsteproject.hawaste.realm;

import com.gec.hawsteproject.hawaste.entity.SysResource;
import com.gec.hawsteproject.hawaste.entity.SysRole;
import com.gec.hawsteproject.hawaste.entity.SysUser;
import com.gec.hawsteproject.hawaste.service.ISysResourceService;
import com.gec.hawsteproject.hawaste.service.ISysRoleService;
import com.gec.hawsteproject.hawaste.service.ISysUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义realm：
 * 1.自定义认证
 * 2.自定义授权
 */
public class MyRealm extends AuthorizingRealm {

    @Autowired
    ISysUserService userService;

    @Autowired
    ISysRoleService roleService;

    @Autowired
    ISysResourceService permissionService;

    @Override
    public String getName() {
        return "HawasteRealm";
    }

    /**
     * 授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //1.获取用户对象
        SysUser user = (SysUser) principals.getPrimaryPrincipal();
        //2.获取角色集合   name-> 监管方
        List<SysRole> sysRoles = roleService.selectByUid(user.getId());
        List<String> roles = sysRoles
                .stream()
                .map(SysRole::getName)
                .collect(Collectors.toList());
        //3.获取权限集合   resource->area:delete
        //从SysResource列表中找出不重复的权限，且只需要返回premissionStr属性组成的List
        List<Long> rids = sysRoles.stream().map(SysRole::getId).collect(Collectors.toList());
        List<String> permissions = permissionService.selectByRids(rids)
                .stream()
                .distinct()
                .map(SysResource::getPermissionStr)
                .collect(Collectors.toList());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRoles(roles);
        info.addStringPermissions(permissions);
        return info;
    }

    /**
     * 认证
     * 1.用户提交用户名和密码
     * 2.shiro 封装令牌对象
     * 3.realm 判断令牌用户是否存在 存在则设置用户AuthenticationInfo对象
     * 4.shiro 通过匹配器匹配用户输入的密码和用户AuthenticationInfo对象中的密码进行判断
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        //根据用户名查询数据库
        SysUser user = userService.selectByName(username);
        if(user==null){
            return null;
        }
        return new SimpleAuthenticationInfo(
                user,
                user.getPassword(),
                ByteSource.Util.bytes(user.getUsername()),
                getName());
    }
}
