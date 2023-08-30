package center.helloworld.zero.server.chat.api.model.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 好友申请
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FriendApply extends Model<FriendApply> {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 关联朋友ID集合
     */
    private String friend_ids;

    /**
     * 创建时间
     */
    private LocalDateTime createtime;

    /**
     * 修改时间
     */
    private LocalDateTime updatetime;

    @Override
    public Serializable pkVal() {
        return this.id;
    }
}
