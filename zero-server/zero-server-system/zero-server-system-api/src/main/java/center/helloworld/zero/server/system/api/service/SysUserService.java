package center.helloworld.zero.server.system.api.service;

import center.helloworld.zero.common.base.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
}
