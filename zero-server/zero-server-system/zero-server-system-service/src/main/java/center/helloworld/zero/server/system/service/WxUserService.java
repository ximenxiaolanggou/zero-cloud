package center.helloworld.zero.server.system.service;

import center.helloworld.zero.server.system.api.model.entity.WxUser;
import center.helloworld.zero.server.system.mapper.WxUserMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WxUserService extends ServiceImpl<WxUserMapper, WxUser> {

    @Autowired
    private WxUserMapper wxUserMapper;

    /**
     * 根据unionid查询用户
     * @param unionid
     * @return
     */
    public WxUser findByUnionid(String unionid) {
        return wxUserMapper.selectOne(new LambdaQueryWrapper<WxUser>().eq(WxUser::getUnionid, unionid));
    }
}
