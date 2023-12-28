package center.helloworld.zero.server.system.api.service;

import center.helloworld.zero.server.system.api.model.entity.WxUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author zhishun.cai
 * @create 2023/12/28
 * @note
 */
@Service
@FeignClient("Zero-Server-System")
@RequestMapping("/system/wxUser")
public interface WxUserService {

    @GetMapping("list")
    public List<WxUser> list(@RequestParam(name = "searchKey", required = false) String searchKey,@RequestParam(name = "friendIds", required = true) List<Long> friendIds);
}
