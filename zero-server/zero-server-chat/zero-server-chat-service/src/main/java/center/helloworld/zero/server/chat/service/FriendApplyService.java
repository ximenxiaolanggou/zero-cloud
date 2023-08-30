package center.helloworld.zero.server.chat.service;

import center.helloworld.zero.server.chat.api.model.entity.FriendApply;
import center.helloworld.zero.server.chat.api.model.entity.FriendRelation;
import center.helloworld.zero.server.chat.mapper.FriendApplyMapper;
import center.helloworld.zero.server.chat.mapper.FriendRelationMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class FriendApplyService extends ServiceImpl<FriendApplyMapper, FriendApply> {
}
