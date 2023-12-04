package center.helloworld.zero.server.chat.api.model.entity.message.send;

import center.helloworld.zero.server.chat.api.model.entity.message.send.SendMsgType;
import lombok.Data;

/**
 * @author zhishun.cai
 * @create 2023/12/4
 * @note 发送的基本消息
 */
@Data
public class SendBaseMsg<T> {

    /**
     * 发送消息类型
     */
    private int msgType;

    private T payload;

    public SendBaseMsg() {

    }

    public SendBaseMsg(SendMsgType msgType, T data) {
        this.msgType = msgType.getType();
        this.payload = data;
    }
}
