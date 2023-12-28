package center.helloworld.zero.server.system.api.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author zhishun.cai
 * @create 2023/12/4
 * @note 微信 用户信息
 */
@Data
public class WxUser extends Model<WxUser> {

    /**
     * 唯一标识
     */
    @TableId
    private String unionid;

    /**
     * open id
     */
    private String openid;

    /**
     * 用户名
     */
    private String nickname;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 语言
     */
    private String language;

    /**
     * 城市
     */
    private String city;

    /**
     * 省
     */
    private String province;

    /**
     * 国家
     */
    private String country;

    /**
     * 头像图片地址
     */
    private String headimgurl;

    /**
     * 权限
     */
    @TableField(exist = false)
    private String[] privilege;

    /**
     * 系统用户ID
     */
    private Long sysUserId;

    /**
     * 创建时间
     */
    private LocalDateTime createtime;

    /**
     * 修改时间
     */
    private LocalDateTime updatetime;
}
