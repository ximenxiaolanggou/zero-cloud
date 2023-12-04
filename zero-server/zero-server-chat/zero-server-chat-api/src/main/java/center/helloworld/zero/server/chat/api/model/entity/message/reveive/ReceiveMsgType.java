package center.helloworld.zero.server.chat.api.model.entity.message.reveive;

/**
 * @author zhishun.cai
 * @create 2023/12/4
 * @note 发送消息类型
 */
public enum ReceiveMsgType {
    // 客户端直接关闭连接
    CLOSE(1),
    // 展示完消息后关闭连接
    CLOSE_AFTER_SHOW_MSG(2)
    ;

    private Integer type;

    ReceiveMsgType(Integer type) {
        this.type = type;
    }
}
