package center.helloworld.zero.server.chat.api.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Long id;

    /**
     * 关联朋友ID集合
     */
    private String friend_id;

    /**
     * 状态 1. 待确认 2. 已拒绝
     */
    private Integer status = 1;

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