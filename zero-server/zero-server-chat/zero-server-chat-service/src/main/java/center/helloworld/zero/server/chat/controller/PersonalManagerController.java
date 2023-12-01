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

    @Autowired
    private SysUserService userService;

    @Autowired
    private FriendApplyService friendApplyService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private ChannelManager channelManager;

    /**
     * 查询用户
     * @param username
     * @return
     */
    @GetMapping("searchUser")
    public Result searchUser(@RequestParam("username") String username) {
        return userService.findUserByUsername(username);
    }

    /**
     * 好友申请
     * @return
     */
    @PostMapping("friendApply/{friendId}")
    public Result friendApply(@PathVariable("friendId") Long friendId, HttpServletRequest request) {
        // TODO 当前用户ID
        Long currentUserId = 0L;

        // 1. 添加申请记录信息
        FriendApply friendApply = new FriendApply();
        friendApply.setUser_id(currentUserId);
        friendApply.setFriend_id(friendId);
        friendApply.setStatus(1);
        friendApplyService.save(friendApply);
        // 2. 如果用户在线发送好友添加请求
        Session friendSession = sessionService.getSessionByUserId(friendId);
        if(friendSession != null) {
            Map<String,Object> map = new HashMap();
            map.put("id", request.getHeader("id"));
            map.put("username", Base64.decodeStr(request.getHeader("username"),  CharsetUtil.UTF_8));
            String sessionId = friendSession.getSessionId();
            Channel channel = channelManager.getChannel(sessionId);
            channel.write(new TextWebSocketFrame(JSONUtil.toJsonStr(map)));
            channel.flush();
        }
        return Result.ok();
    }
}
