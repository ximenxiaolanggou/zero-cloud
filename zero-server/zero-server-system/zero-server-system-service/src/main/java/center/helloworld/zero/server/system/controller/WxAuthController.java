package center.helloworld.zero.server.system.controller;

import center.helloworld.zero.server.system.properties.WxAuthProperty;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthToken;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.request.AuthWeChatOpenRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("wxAuth")
public class WxAuthController {

    @Autowired
    private WxAuthProperty wxAuthProperty;

    @RequestMapping("/login")
    public void renderAuth(String code,HttpServletRequest request, HttpServletResponse response) throws IOException {
        String url="https://api.weixin.qq.com/sns/oauth2/access_token?appid="+wxAuthProperty.getAppid()+"&secret="+wxAuthProperty.getSecret()+"&code="+code+"&grant_type=authorization_code";
        String msg= HttpUtil.get(url);//用临时票据code向微信服务器换取用户信息
        JSONObject obj = JSONUtil.parseObj(msg);
        Object access_token = obj.get("access_token");
        String userinfo = "https://api.weixin.qq.com/sns/userinfo?access_token="+access_token+"&openid=" + wxAuthProperty.getAppid();
        msg= HttpUtil.get(userinfo);
    }

}
