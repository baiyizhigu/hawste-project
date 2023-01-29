package com.gec.hawsteproject.hawaste.interceptor;

/**
 * 拦截器开发流程：
 * 1.编写拦截器类，实现HandlerInterceptor的三个方法
 * preHandle:请求到达后先执行，返回true则放行请求，进入controller执行接口方法  false阻止
 * postHandle：接口方法执行完毕后执行
 * afterCompletion：处理结束返回前端前调用
 * 2.配置拦截器拦截逻辑
 *
 * 登录拦截：
 * 请求到达，查看是否在session中存在，如果不存在则未登录，返回登录页，如果存在则放行
 */
/*@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("请求到达后先执行，返回true则放行请求");
        HttpSession session = request.getSession();
        Object loginUser = session.getAttribute("loginUser");
        if (!ObjectUtils.isEmpty(loginUser)){
            //登录用户放行
            return true;
        }
        //未登录用户
        response.sendRedirect("/notlogin.html");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        log.info("接口方法执行完毕后执行");
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        log.info("处理结束返回前端前调用");
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}

 */
