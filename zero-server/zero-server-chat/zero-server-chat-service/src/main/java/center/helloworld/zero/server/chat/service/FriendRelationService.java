package center.helloworld.zero.server.chat.service;

import center.helloworld.zero.server.chat.api.model.model.FriendRelation;
import center.helloworld.zero.server.chat.mapper.FriendRelationMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class FriendRelationService extends ServiceImpl<FriendRelationMapper, FriendRelation> {
}
