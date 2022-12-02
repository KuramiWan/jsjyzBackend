package ga.jsjyz.contrller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import ga.jsjyz.pojo.User;
import ga.jsjyz.service.PermissionService;
import ga.jsjyz.util.ErrorCode;
import ga.jsjyz.util.JwtUtils;
import ga.jsjyz.util.Response;
import ga.jsjyz.vo.LoginVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PermissionController {

    private PermissionService permissionService;

    @PostMapping("/login")
    public Response Login(@RequestBody LoginVo loginVo){
        String username = loginVo.getUsername();
        String password = loginVo.getPassword();
        if (StringUtils.isBlank(username)||StringUtils.isBlank(password)){
            return new Response(ErrorCode.ERROR_PARAMETER);
        }else {
            return permissionService.login(username,password);

        }
    }
    @PostMapping("/register")
    public Response Register(@RequestBody LoginVo loginVo){
        String username = loginVo.getUsername();
        String password = loginVo.getPassword();
        if (StringUtils.isBlank(username)||StringUtils.isBlank(password)){
            return new Response(ErrorCode.ERROR_PARAMETER);
        }
        return permissionService.register(username,password);
    }
}


