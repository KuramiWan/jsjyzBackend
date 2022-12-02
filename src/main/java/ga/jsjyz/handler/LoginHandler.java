package ga.jsjyz.handler;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.fastjson2.JSON;
import ga.jsjyz.Bo.PermissionsBo;
import ga.jsjyz.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class LoginHandler implements HandlerInterceptor {
    @Autowired
    private JwtUtils jwtUtils;
    private RedisUtils redisUtils;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)){
            return true;
        }
        String token = request.getHeader("Authorization");
        if ( StringUtils.isBlank(token)){
            Response result = new Response(ErrorCode.NO_LOGIN);
            response.setContentType("application/json;charset = utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        Map<String, Object> parseToken = jwtUtils.parse(token);
        if (parseToken==null){
            Response result = new Response(ErrorCode.NO_LOGIN);
            response.setContentType("application/json;charset = utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        String userId = String.valueOf(parseToken.get("userId"));
        String permissions = String.valueOf(parseToken.get("permissions"));
        if (StringUtils.isBlank(permissions)||StringUtils.isBlank(userId)){
            Response result = new Response(ErrorCode.NO_LOGIN);
            response.setContentType("application/json;charset = utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        if (permissions.equals("user")){
            Response result = new Response(ErrorCode.NO_PERMISSIONS);
            response.setContentType("application/json;charset = utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        PermissionsBo permissionsBo = new PermissionsBo();
        permissionsBo.setId(Long.parseLong(userId));
        permissionsBo.setPermissions(permissions);
        PermissionsThreadLocal.put(permissionsBo);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        PermissionsThreadLocal.remove();
    }
}
