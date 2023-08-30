package center.helloworld.zero.server.chat.controller;

import center.helloworld.zero.server.system.api.service.SysUserService;
import center.helloworld.zero.common.base.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 用户列表
     * @return
     */
    @GetMapping("test")
    public Result test() {
        return userService.userList();
    }

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
    public Result friendApply(@PathVariable("friendId") Long friendId) {
        // TODO 当前用户ID
        Long currentUserId = 0L;

        // 1. 添加申请记录信息

        // 2. 如果用户在线发送好友添加请求

        return Result.ok();
    }
}
