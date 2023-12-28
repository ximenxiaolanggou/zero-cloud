package center.helloworld.zero.server.chat.controller;

import center.helloworld.zero.common.base.Result;
import center.helloworld.zero.common.code.ResCode;
import center.helloworld.zero.common.exception.ApiException;
import center.helloworld.zero.server.chat.api.model.entity.FriendApply;
import center.helloworld.zero.server.chat.api.model.entity.FriendRelation;
import center.helloworld.zero.server.chat.api.model.entity.Session;
import center.helloworld.zero.server.chat.netty.channel.ChannelManager;
import center.helloworld.zero.server.chat.netty.session.service.SessionService;
import center.helloworld.zero.server.chat.service.FriendApplyService;
import center.helloworld.zero.server.chat.service.FriendRelationService;
import center.helloworld.zero.server.system.api.model.entity.WxUser;
import center.helloworld.zero.server.system.api.service.SysUserService;
import center.helloworld.zero.server.system.api.service.WxUserService;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.json.JSONUtil;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhishun.cai
 * @create 2023/12/28
 * @note
 */

@RestController
@RequestMapping("friends")
public class FriendsController {

    @Autowired
    private WxUserService wxUserService;


    @Autowired
    private SysUserService userService;

    @Autowired
    private FriendApplyService friendApplyService;

    @Autowired
    private FriendRelationService friendRelationService;

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
        Long currentUserId = Long.valueOf(request.getHeader("id"));
        assertFriendApply(currentUserId, friendId);

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

    /**
     * 好友添加断言
     * @param currentUserId
     * @param friendId
     */
    private void assertFriendApply(Long currentUserId, Long friendId) {
        FriendRelation friendRelation = friendRelationService.getById(currentUserId);
        if(friendRelation != null && StringUtils.isNotBlank(friendRelation.getFriendIds())) {
            // 已是好友
            ArrayUtil.indexOf( friendRelation.getFriendIds().split(","), friendId);
            throw new ApiException(ResCode.ERROR_FRIEND_EXIST);
        }
    }

    /**
     * 好有列表
     * @param searchKey
     * @return
     */
    @GetMapping("list")
    public Result list(String searchKey, HttpServletRequest request) {
        Long currentUserId = Long.valueOf(request.getHeader("id"));
        FriendRelation friendRelation = friendRelationService.getById(currentUserId);
        if(friendRelation == null || StringUtils.isBlank(friendRelation.getFriendIds())) {
            return Result.ok(new ArrayList(0));
        }
        List<Long> friendIds = Arrays.stream(friendRelation.getFriendIds().split(",")).map(friendId -> Long.valueOf(friendId)).collect(Collectors.toList());
        List<WxUser> wxUsers = wxUserService.list(searchKey, friendIds);
        return Result.ok(wxUsers);
    }
}
