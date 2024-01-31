package center.helloworld.zero.server.chat.netty.session.dao;

import center.helloworld.zero.server.chat.api.model.model.Session;

import java.util.Map;

/**
 * session 存储管理
 */
public interface SessionDao {

    /**
     * 保存会话
     * @param session
     */
    void save(Session session);

    /**
     * 获取所有会话
     * @return
     */
    Map<String, Session> all();

    /**
     * 移除会话
     * @param sessionId
     */
    void remove(String sessionId);
}
