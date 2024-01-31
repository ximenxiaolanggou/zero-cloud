package center.helloworld.zero.server.system.controller;

import center.helloworld.zero.server.system.api.model.entity.SysUser;
import center.helloworld.zero.server.system.service.SysUserService;
import center.helloworld.zero.common.base.Result;
import cn.hutool.crypto.digest.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 创建用户
     * @param sysUser
     * @return
     */
    @PostMapping
    public Result addUser(@RequestBody SysUser sysUser) {
        sysUser.setPassword(BCrypt.hashpw("wogua", BCrypt.gensalt()));
        userService.save(sysUser);
        return Result.ok(sysUser.getId());
    }

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    @GetMapping("findById/{id}")
    public SysUser findById(@PathVariable("id") Long id) {
        return userService.getById(id);
    }
}
