package center.helloworld.zero.server.chat.api.model.model.message.send;

/**
 * @author zhishun.cai
 * @create 2023/12/4
 * @note 发送消息类型
 */
public enum SendMsgType {
    // 客户端直接关闭连接
    CLOSE(1),
    // 展示完消息后关闭连接(不同客户端（浏览器连接）)
    CLOSE_AFTER_SHOW_MSG_OTHER_CLIENT(2),

    // 展示完消息后关闭连接(相同同客户端（浏览器连接）)
    CLOSE_AFTER_SHOW_MSG_SAME_CLIENT(3),
    ;


    private Integer type;

    SendMsgType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
