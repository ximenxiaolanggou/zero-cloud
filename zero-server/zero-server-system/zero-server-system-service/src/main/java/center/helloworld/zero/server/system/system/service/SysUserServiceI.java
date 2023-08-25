package center.helloworld.zero.server.system.system.service;

import center.helloworld.zero.server.system.api.model.system.entity.SysUser;
import center.helloworld.zero.server.system.system.mapper.SysUserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysUserServiceI extends ServiceImpl<SysUserMapper, SysUser>{


}
