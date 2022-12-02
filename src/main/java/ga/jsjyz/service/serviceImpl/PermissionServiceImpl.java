package ga.jsjyz.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import ga.jsjyz.mapper.UserMapper;
import ga.jsjyz.pojo.User;
import ga.jsjyz.service.PermissionService;
import ga.jsjyz.util.ErrorCode;
import ga.jsjyz.util.JwtUtils;
import ga.jsjyz.util.RedisUtils;
import ga.jsjyz.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;

public class PermissionServiceImpl implements PermissionService {
    private String salt ="jsjyz";
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private RedisUtils redisUtils;

    @Override
    public Response login(String username, String password) {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        String md5Password = DigestUtils.md5DigestAsHex((password + salt).getBytes());
        userLambdaQueryWrapper.eq(User::getUsername,username);
        User user = userMapper.selectOne(userLambdaQueryWrapper);
        Long id = user.getId();
        if (user.getId() == null) {
            return new Response(10001,"无用户",null);
        }
        if (user.getPassword().equals(md5Password)){
            return new Response(10001,"密码错误",null);
        }else {
            String token = jwtUtils.create(id, "admin");
            redisUtils.add("PERMISSIONS_"+id,"admin");
            return new Response().ok(token);
        }
    }

    @Override
    public Response register(String username, String password) {
        User user = new User(null, username, password);
        int insert = userMapper.insert(user);
        if (insert != 0){
            return new Response().ok(null);
        }
        return new Response(ErrorCode.FAILED);
    }
}
