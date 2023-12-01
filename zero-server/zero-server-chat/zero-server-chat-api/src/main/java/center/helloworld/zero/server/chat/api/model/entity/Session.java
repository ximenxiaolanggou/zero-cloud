package center.helloworld.zero.server.chat.api.model.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 会话
 */
@Data
public class Session {

    /**
     * 会话ID
     */
    private String sessionId;

    /**
     * 客户端ID（用户ID）
     */
    private Long clientId;

    /**
     * 额外属性
     */
    private Map<String, Object> attrs;

    /**
     * 会话创建时间
     */
    private LocalDateTime createtime = LocalDateTime.now();
}
