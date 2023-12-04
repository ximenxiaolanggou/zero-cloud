package center.helloworld.zero.server.chat.api.model.entity.message.send;

import lombok.Data;

/**
 * @author zhishun.cai
 * @create 2023/12/4
 * @note 发送消息类型
 */
public enum SendMsgType {
    // 客户端直接关闭连接
    CLOSE(1),
    // 展示完消息后关闭连接
    CLOSE_AFTER_SHOW_MSG(2);

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
