package center.helloworld.zero.server.chat.api.model.model.chatsession;

import center.helloworld.zero.server.chat.api.model.model.wx.WxUser;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author zhishun.cai
 * @create 2024/1/19
 * @note
 */
@Data
public class ChatSession extends Model<ChatSession> {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 会话创建者
     */
    private Long author;

    /**
     * 会话聊天对象，当type为2 群聊时 为空
     */
    private Long toUser;

    /**
     * 会话类型 1 单聊 2 群聊
     */
    private Integer type;

    /**
     * 创建时间
     */
    private LocalDateTime createtime;

    /**
     * 修改时间
     */
    private LocalDateTime updatetime;

}
