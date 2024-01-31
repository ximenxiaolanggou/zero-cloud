package center.helloworld.zero.server.chat.api.model.model.chatsession;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author zhishun.cai
 * @create 2024/1/19
 * @note
 */

@Data
public class ChatSessionGroup {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 群聊会话ID
     */
    private Long chatSessionId;

    /**
     * 群聊成员ID
     */
    private Long chatGroupMemberId;

    /**
     * 创建时间
     */
    private LocalDateTime createtime;

    /**
     * 修改时间
     */
    private LocalDateTime updatetime;
}
