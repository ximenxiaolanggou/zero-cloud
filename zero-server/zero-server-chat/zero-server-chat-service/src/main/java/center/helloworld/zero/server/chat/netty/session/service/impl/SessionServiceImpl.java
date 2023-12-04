package center.helloworld.zero.server.chat.netty.session.service.impl;

import center.helloworld.zero.server.chat.api.model.entity.Session;
import center.helloworld.zero.server.chat.netty.session.dao.MemorySessionDao;
import center.helloworld.zero.server.chat.netty.session.dao.SessionDao;
import center.helloworld.zero.server.chat.netty.session.service.SessionService;
import com.alibaba.nacos.common.util.UuidUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Slf4j
@Service
public class SessionServiceImpl implements SessionService {

    @Resource
    private SessionDao sessionDao;

    /**
     * 移除会话
     * @param sessionId
     */
    @Override
    public void remove(String sessionId) {
        sessionDao.remove(sessionId);
    }

    /**
     * 根据用户ID获取会话
     * @param userId
     * @return
     */
    @Override
    public Session getSessionByUserId(Long userId) {
        Map<String, Session> sessionMap = sessionDao.all();
        for (Session session : sessionMap.values()) {
            if(session.getClientId().equals(userId))
                return session;
        }
        return null;
    }

    /**
     * 创建会话
     * @param id
     * @param token
     * @param properties
     * @return
     */
    @Override
    public Session createSession(Long id, String token, Map<String, Object> properties) {
        // TODO这边可以将会话ID生成策略抽取出来
        String sessionId = UuidUtils.generateUuid();
        Session session = new Session();
        session.setSessionId(sessionId);
        session.setToken(token);
        session.setClientId(id);
        session.setAttrs(properties);
        sessionDao.save(session);
        return session;
    }

    /**
     * 创建会话
     * @param id
     * @param properties
     * @return
     */
    @Override
    public Session createSession(Long id, Map<String, Object> properties) {
        // TODO这边可以将会话ID生成策略抽取出来
        String sessionId = UuidUtils.generateUuid();
        Session session = new Session();
        session.setSessionId(sessionId);
        session.setClientId(id);
        session.setAttrs(properties);
        sessionDao.save(session);
        return session;
    }
}
