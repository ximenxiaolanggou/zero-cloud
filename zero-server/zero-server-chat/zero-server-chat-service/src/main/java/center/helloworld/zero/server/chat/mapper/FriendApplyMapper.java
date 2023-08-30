package center.helloworld.zero.server.chat.mapper;

import center.helloworld.zero.server.chat.api.model.entity.FriendApply;
import center.helloworld.zero.server.chat.api.model.entity.FriendRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FriendApplyMapper extends BaseMapper<FriendApply> {
}
