package center.helloworld.zero.server.system.mapper;

import center.helloworld.zero.server.system.api.model.entity.SysUser;
import center.helloworld.zero.server.system.api.model.entity.WxUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zhishun.cai
 * @create 2023/7/25
 * @note
 */
@Mapper
public interface WxUserMapper extends BaseMapper<WxUser> {


}
