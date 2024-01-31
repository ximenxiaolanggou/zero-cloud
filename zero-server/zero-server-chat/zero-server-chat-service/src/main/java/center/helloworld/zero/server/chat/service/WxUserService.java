package center.helloworld.zero.server.chat.service;

import center.helloworld.zero.server.chat.api.model.model.wx.WxUser;
import center.helloworld.zero.server.chat.mapper.WxUserMapper;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    /**
     * 列表
     * @param searchKey 查询关键字
     * @param sysUserIds 系统用户ID集合，如果不传递则为查询所有
     * @return
     */
    public List<WxUser> list(String searchKey, List<Long> sysUserIds) {
        LambdaQueryWrapper<WxUser> condition = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(searchKey)) {
            condition.like(WxUser::getNickname, searchKey);
        }
        if(CollectionUtil.isNotEmpty(sysUserIds)) {
            condition.in(WxUser::getSysUserId, sysUserIds);
        }
        condition.orderByAsc(WxUser::getNickname);
        return wxUserMapper.selectList(condition);
    }

    /**
     * 根据unionid判断微信用户是否存在
     * @param unionid 用户微信唯一标识
     * @return
     */
    public boolean existByUnionid(String unionid) {
        return wxUserMapper.exists(new LambdaQueryWrapper<WxUser>().eq(WxUser::getUnionid, unionid));
    }

    /**
     * 根据微信昵称查询用户
     * @param nickname
     * @return
     */
    public List<WxUser> findByNickname(String nickname) {
        return wxUserMapper.selectList(new LambdaQueryWrapper<WxUser>().eq(WxUser::getNickname, nickname));
    }
}
