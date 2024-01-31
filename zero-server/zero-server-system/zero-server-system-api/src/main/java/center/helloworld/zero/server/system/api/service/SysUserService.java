package center.helloworld.zero.server.system.api.service;

import center.helloworld.zero.common.base.Result;
import center.helloworld.zero.server.system.api.model.entity.SysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Service
@FeignClient("Zero-Server-System")
@RequestMapping("/system/sysUser")
public interface SysUserService {

    /**
     * 用户列表
     * @return
     */
    @GetMapping("userList")
    public Result userList();

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    @GetMapping("findUserByUsername")
    public Result findUserByUsername(@RequestParam("username") String username);

    /**
     * 创建用户
     * @param sysUser
     * @return
     */
    @PostMapping
    public Result addUser(@RequestBody SysUser sysUser);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    @GetMapping("findById/{id}")
    public SysUser findById(@PathVariable("id") Long id);
}
