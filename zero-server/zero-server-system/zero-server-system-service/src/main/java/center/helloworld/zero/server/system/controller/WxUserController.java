package center.helloworld.zero.server.system.controller;

import center.helloworld.zero.server.system.api.model.entity.SysUser;
import center.helloworld.zero.server.system.api.model.entity.WxUser;
import center.helloworld.zero.server.system.service.SysUserService;
import center.helloworld.zero.server.system.service.WxUserService;
import cn.hutool.core.collection.CollectionUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhishun.cai
 * @create 2023/12/28
 * @note
 */
@RestController
@RequestMapping("wxUser")
public class WxUserController {

    @Autowired
    private WxUserService wxUserService;

    @Autowired
    private SysUserService sysUserService;

    /**
     * 列表
     * @param searchKey
     * @return
     */
    @GetMapping("list")
    public List<WxUser> list(@RequestParam(name = "searchKey", required = false) String searchKey, @RequestParam(name = "friendIds", required = true) List<Long> friendIds){
        return wxUserService.list(searchKey, friendIds);
    }
}
