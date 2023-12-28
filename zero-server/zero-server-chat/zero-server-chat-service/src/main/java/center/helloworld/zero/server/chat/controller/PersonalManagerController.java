package center.helloworld.zero.server.chat.controller;

import center.helloworld.zero.server.chat.api.model.entity.FriendApply;
import center.helloworld.zero.server.chat.api.model.entity.Session;
import center.helloworld.zero.server.chat.netty.channel.ChannelManager;
import center.helloworld.zero.server.chat.netty.session.service.SessionService;
import center.helloworld.zero.server.chat.service.FriendApplyService;
import center.helloworld.zero.server.system.api.service.SysUserService;
import center.helloworld.zero.common.base.Result;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.json.JSONUtil;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhishun.cai
 * @create 2023/8/25
 * @note 个人管理控制层
 */
@RestController
@RequestMapping("personal")
public class PersonalManagerController {

}
