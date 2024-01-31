package center.helloworld.zero.server.chat.controller;

import center.helloworld.zero.common.base.Result;
import center.helloworld.zero.common.utils.JwtUtil;
import center.helloworld.zero.server.chat.api.model.model.wx.WxUser;
import center.helloworld.zero.server.chat.properties.WxAuthProperty;
import center.helloworld.zero.server.chat.service.WxUserService;
import center.helloworld.zero.server.system.api.model.entity.SysUser;
import center.helloworld.zero.server.system.api.model.vo.WxAuthTokenVO;
import center.helloworld.zero.server.system.api.service.SysUserService;
import cn.hutool.crypto.digest.BCrypt;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

        SysUser sysUser = null;
        // 第一次登录，创建用户信息保存到 sys_user中
        WxUser oldWxuser = wxUserService.findByUnionid(wxUser.getUnionid());
        if(oldWxuser == null) {
            sysUser = new SysUser();
            sysUser.setUsername(wxUser.getUnionid());
            sysUser.setNickname(wxUser.getNickname());
            sysUser.setAvatar(wxUser.getHeadimgurl());
            Result result = sysUserService.addUser(sysUser);
            sysUser.setId(Long.valueOf(String.valueOf(result.getData())));
        }else {
            sysUser = sysUserService.findById(oldWxuser.getSysUserId());
        }
        // 更新或保存微信用户信息
        wxUser.setSysUserId(sysUser.getId());
        wxUserService.saveOrUpdate(wxUser);

        // 创建令牌
        Map<String, String> map = new HashMap();
        map.put("username", wxUser.getNickname());
        map.put("mobile", null);
        map.put("mail", null);
        String token = JwtUtil.createToken(secret, String.valueOf(sysUser.getId()),map, null);
        return Result.ok(token);
    }
}
