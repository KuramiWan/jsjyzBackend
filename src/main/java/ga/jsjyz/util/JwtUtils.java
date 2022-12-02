package ga.jsjyz.util;

import io.jsonwebtoken.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JwtUtils {
    //定义加密盐
    private String slat = "jsjyz";
    //封装创建token方法
    //<token> create(Long,String)
    public String create(Long userId, String permissions){
        JwtBuilder builder = Jwts.builder();
        Map<String, Object> stringStringHashMap = new HashMap<>();
        //放入想保存的内容
        stringStringHashMap.put("userId",userId);
        stringStringHashMap.put("permissions",permissions);
        long month = 1000 * 60 * 60 * 24 * 30L;
        String token = builder
                //header
                //配置token类型,加密类型
                .setHeaderParam("typ","JWT")
                .setHeaderParam("alg","HS256")
                //payload
                //配置token内容
                //setClaims(Map(String,Object))
                .setClaims(stringStringHashMap)
                //comment
                .setSubject("create token when login")
                //设置cookie过期时间
                //不能使用Date或SimpleDateFormat jdk8 时间类已被遗弃,使用Instance类
                .setExpiration(Date.from(Instant.now().plus(30, ChronoUnit.DAYS)))
                .setId(UUID.randomUUID().toString())
                //设置开始时间
                .setIssuedAt(Date.from(Instant.now()))
                //加密
                .signWith(SignatureAlgorithm.HS256, slat)
                //打包
                .compact();
        return token;
    }

    //jwt解析方法
    //Map<String,Object> parse(<token>)
    public Map<String,Object> parse(String token){
        try {
            //解析token
            Jwt parse = Jwts.parser().setSigningKey(slat).parse(token);
            return (Map<String, Object>) parse.getBody();
        } catch (ExpiredJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }
}
