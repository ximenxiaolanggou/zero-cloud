package center.helloworld.zero.server.chat.netty.session.service;

import center.helloworld.zero.server.chat.api.model.entity.Session;

import java.util.Map;

public interface SessionService {

    /**
     * 创建会话
     * @param id
     * @param properties
     * @return
     */
    Session createSession(Long id, Map<String,Object> properties);

    /**
     * 创建会话
     * @param id
     * @param token
     * @param properties
     * @return
     */
    Session createSession(Long id,String token, Map<String,Object> properties);

    /**
     * 根据用户ID获取会话
     * @param userId
     * @return
     */
    Session getSessionByUserId(Long userId);

    /**
     * 移除会话
     * @param sessionId
     */
    void remove(String sessionId);
}
