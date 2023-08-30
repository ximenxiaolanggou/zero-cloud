package center.helloworld.zero.server.system.controller;

import center.helloworld.zero.server.system.api.model.entity.SysUser;
import center.helloworld.zero.server.system.service.SysUserService;
import center.helloworld.zero.common.base.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhishun.cai
 * @create 2023/8/25
 * @note
 */
@RestController
@RequestMapping("sysUser")
public class SysUserController {

    @Autowired
    private SysUserService userService;

    /**
     * 用户列表
     * @return
     */
    @GetMapping("userList")
    public Result userList() {
        List<SysUser> users = userService.list();
        return Result.ok(users);
    }

    /**
     * 根据用户名查询
     * @param username
     * @return
     */
    @GetMapping("findUserByUsername")
    public Result findUserByUsername(@RequestParam("username") String username) {
        SysUser user = userService.findUserByUsername(username);
        return Result.ok(user);
    }
}
