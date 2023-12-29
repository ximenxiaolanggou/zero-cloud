package center.helloworld.zero.server.system.service;

import center.helloworld.zero.server.system.api.model.entity.SysUser;
import center.helloworld.zero.server.system.mapper.SysUserMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserService extends ServiceImpl<SysUserMapper, SysUser> {

    @Autowired
    private SysUserMapper userMapper;

    /**
     * 根据用户名查询
     * @param username
     * @return
     */
    public SysUser findUserByUsername(String username) {
        return userMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, username));
    }

}
