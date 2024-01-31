package center.helloworld.zero.server.chat.api.model.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 好友关系
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FriendRelation extends Model<FriendRelation> {
    /**
     * 主键ID
     */
    @TableId
    private Long userId;

    /**
     * 关联朋友ID集合
     */
    private String friendIds;

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
        return this.userId;
    }
}
