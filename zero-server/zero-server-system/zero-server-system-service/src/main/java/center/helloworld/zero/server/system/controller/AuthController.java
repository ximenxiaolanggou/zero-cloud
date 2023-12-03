package center.helloworld.zero.server.system.controller;

import center.helloworld.zero.server.system.api.model.entity.SysUser;
import center.helloworld.zero.server.system.api.model.vo.LoginVO;
import center.helloworld.zero.server.system.service.SysUserService;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.digest.BCrypt;
import center.helloworld.zero.common.base.Result;
import center.helloworld.zero.common.code.ResCode;
import center.helloworld.zero.common.exception.ApiException;
import center.helloworld.zero.common.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private SysUserService userService;

    @Value("${zero.jwt.secret}")
    private String secret;

    /**
     * 登录
     * @param loginVO
     * @return
     */
    @PostMapping("login")
    public Result login(@RequestBody LoginVO loginVO) {
        String username = loginVO.getUsername();
        SysUser user = userService.findUserByUsername(username);
        // 用户不存在 | 密码错误
        if (user == null || !BCrypt.checkpw(loginVO.getPassword(), user.getPassword())) {
            throw new ApiException(ResCode.ERROR_USERNAME_OR_PWD);
        }


        Map<String, String> map = new HashMap();
        map.put("username", user.getUsername());
        map.put("mobile", user.getMobile());
        map.put("mail", user.getMail());
        String token = JwtUtil.createToken(secret, String.valueOf(user.getId()),map, null);
        return Result.ok(token);
    }

    /**
     * 用户信息
     * @return
     */
    @GetMapping("userInfo")
    public Result userInfo(HttpServletRequest request) {
        Map<String,Object> map = new HashMap();
        map.put("id", request.getHeader("id"));
        map.put("username", Base64.decodeStr(request.getHeader("username"),  CharsetUtil.UTF_8));
        map.put("mail", Base64.decodeStr(request.getHeader("mail"),  CharsetUtil.UTF_8));
        map.put("mobile", Base64.decodeStr(request.getHeader("mobile"),  CharsetUtil.UTF_8));
        return Result.ok(map);
    }


}
