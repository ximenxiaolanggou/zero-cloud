package center.helloworld.zero.server.chat.netty.session.dao;

import center.helloworld.zero.server.chat.api.model.model.Session;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 内存会话管理
 */
@Repository
public class MemorySessionDao implements SessionDao{

    Map<String, Session> sessionMap = new ConcurrentHashMap();

    /**
     * 获取所有会话
     * @return
     */
    @Override
    public Map<String, Session> all() {
        return sessionMap;
    }

    /**
     * 保存会话
     * @param session
     */
    public void save(Session session) {
        sessionMap.put(session.getSessionId(), session);
    }

    /**
     * 移除会话
     * @param sessionId
     */
    public void remove(String sessionId) {
        sessionMap.remove(sessionId);
    }
}
