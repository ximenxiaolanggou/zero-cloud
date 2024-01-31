package center.helloworld.zero.server.chat.api.model.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
    @TableId
    private Long user_id;

    /**
     * 关联朋友ID集合
     */
    private Long friend_id;

    /**
     * 状态 1. 待确认 2. 已拒绝
     */
    @TableField("`status`")
    private Integer status;

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
        return this.user_id;
    }
}
