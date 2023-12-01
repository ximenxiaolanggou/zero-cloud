package center.helloworld.zero.server.chat.netty.channel;

import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 通道管理
 */

@Component
public class ChannelManager {

    private Map<String, Channel> channelRegister = new ConcurrentHashMap();

    /**
     * 保存通道
     * @param id
     * @param channel
     */
    public void save(String id, Channel channel) {
        Channel oChannel = channelRegister.get(id);
        if(oChannel != null)
            oChannel.close();

        channelRegister.put(id, channel);
    }

    /**
     * 删除通道
     * @param id
     */
    public void remove(String id) {
        Channel oChannel = channelRegister.get(id);
        if(oChannel != null) {
            oChannel.close();
            channelRegister.remove(id);
        }
    }

    /**
     * 获取通道
     * @param id
     * @return
     */
    public Channel getChannel(String id) {
        return channelRegister.get(id);
    }
}
