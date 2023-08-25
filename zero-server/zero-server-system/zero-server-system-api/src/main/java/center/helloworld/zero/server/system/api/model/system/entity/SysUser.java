package center.helloworld.zero.server.system.api.model.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author zhishun.cai
 * @create 2023/7/25
 * @note
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysUser extends Model<SysUser> {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 盐
     */
    private String salt;

    /**
     * 联系方式
     */
    private String mobile;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 电子邮件
     */
    private String mail;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 初始化用户
     */
    private Integer initial;

    /**
     * 0 启用 1 停用
     */
    private Integer stop;

    /**
     * 企业ID
     */
    private Long businessId;

    /**
     * 微信openid
     */
    private String openid;

    /**
     * 微信昵称
     */
    private String nickName;

    /**
     * 是否是服务车用户
     */
    private Integer carUser;

    /**
     * 创建时间
     */
    private LocalDateTime createtime;

    /**
     * 修改时间
     */
    private LocalDateTime updatetime;

    /**
     * 组织名称
     */
    @TableField(exist = false)
    private String orgs;

    /**
     * 组织ID
     */
    @TableField(exist = false)
    private String orgIds;

    /**
     * 权限名称
     */
    @TableField(exist = false)
    private Set<String> permissions;

    /**
     * 角色名称
     */
    @TableField(exist = false)
    private String roles;

    /**
     * 角色ID
     */
    @TableField(exist = false)
    private String roleIds;

    @Override
    public Serializable pkVal() {
        return this.id;
    }
}
