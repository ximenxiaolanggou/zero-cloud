package center.helloworld.zero.server.system.controller;

import center.helloworld.zero.common.base.Result;
import center.helloworld.zero.common.utils.JwtUtil;
import center.helloworld.zero.server.system.api.model.entity.SysUser;
import center.helloworld.zero.server.system.api.model.vo.WxAuthTokenVO;
import center.helloworld.zero.server.system.api.model.entity.WxUser;
import center.helloworld.zero.server.system.properties.WxAuthProperty;
import center.helloworld.zero.server.system.service.SysUserService;
import center.helloworld.zero.server.system.service.WxUserService;
import cn.hutool.crypto.digest.BCrypt;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("wxAuth")
public class WxAuthController {

    @Autowired
    private WxAuthProperty wxAuthProperty;

    @Autowired
    private WxUserService wxUserService;

    @Autowired
    private SysUserService sysUserService;

    @Value("${zero.jwt.secret}")
    private String secret;

    @RequestMapping("/login")
    public Result renderAuth(String code) throws IOException {

        //用临时票据code向微信服务器换取用户信息
        WxAuthTokenVO authTokenVO = JSONUtil.toBean(HttpUtil.get(wxAuthProperty.getAuthUrl(code)), WxAuthTokenVO.class);
        // 获取用户信息
        WxUser wxUser = JSONUtil.toBean(HttpUtil.get(wxAuthProperty.getUserInfoUrl(authTokenVO.getAccess_token())), WxUser.class);

        SysUser newUser = null;
        // 第一次登录，创建用户信息保存到 sys_user中
        if((newUser = sysUserService.findByUnionid(wxUser.getUnionid())) == null) {
            newUser = new SysUser();
            newUser.setUsername(wxUser.getUnionid());
            newUser.setPassword(BCrypt.hashpw("wogua", BCrypt.gensalt()));
            newUser.setGender(wxUser.getSex());
            newUser.setAvatar(wxUser.getHeadimgurl());
            newUser.insert();
        }
        // 更新或保存微信用户信息
        wxUser.setSysUserId(newUser.getId());
        wxUserService.saveOrUpdate(wxUser);

        // 创建令牌
        Map<String, String> map = new HashMap();
        map.put("username", wxUser.getNickname());
        map.put("mobile", null);
        map.put("mail", null);
        String token = JwtUtil.createToken(secret, String.valueOf(newUser.getId()),map, null);
        return Result.ok(token);
    }
}
