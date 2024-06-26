package asia.lhweb.IntelligentCard.Interceptor;

import asia.lhweb.IntelligentCard.common.Result;
import asia.lhweb.IntelligentCard.constant.LhIntelligentCardConstant;
import asia.lhweb.IntelligentCard.utils.JwtUtil;
import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author :罗汉
 * @date : 2024/4/24
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        String token = request.getHeader(LhIntelligentCardConstant.TOKEN_NAME);
        if (token == null||"".equals(token)){
            response.getWriter().write(JSON.toJSONString(Result.error("token为空")));
            return false;
        }
        try {
            Claims claims = JwtUtil.parseJWT(token);
            System.err.println(claims);
            String adminAccount = claims.get("adminAccount", String.class);
            System.out.println(adminAccount);
            String tokenInRedis = redisTemplate.opsForValue().get(adminAccount);
            if (tokenInRedis == null||"".equals(tokenInRedis)){
                response.getWriter().write(JSON.toJSONString(Result.error(403,"Token验证失败或已过期，请重新登录！")));
                return false;
            }
            if (JwtUtil.parseJWT(tokenInRedis)!=null&&JwtUtil.parseJWT(token)!=null&&token.equals(tokenInRedis)){
                return true;
            }
        } catch (Exception e) {//说明解密失败 客户端返回信息
            e.printStackTrace();
            response.getWriter().write(JSON.toJSONString(Result.error(403,"Token验证失败或已过期，请重新登录！")));
        }
        return false;
    }
}
