package center.helloworld.zero.server.chat.controller;

import center.helloworld.zero.server.chat.api.model.model.wx.WxUser;
import center.helloworld.zero.server.chat.service.WxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * 列表
     * @param searchKey
     * @return
     */
    @GetMapping("list")
    public List<WxUser> list(@RequestParam(name = "searchKey", required = false) String searchKey, @RequestParam(name = "friendIds", required = true) List<Long> friendIds){
        return wxUserService.list(searchKey, friendIds);
    }

    /**
     * 根据微信昵称查询用户
     * @param nickname
     * @return
     */
    @GetMapping("findByNickname/{nickname}")
    public List<WxUser> findByNickname(@PathVariable("nickname") String nickname) {
        return wxUserService.findByNickname(nickname);
    }
}
