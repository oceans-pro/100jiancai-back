package oceans.model;

import lombok.Data;

import javax.persistence.*;

/**
 * 存储关于用户的基本的信息
 * 将认证的信息和基本的信息分开，有利于后期的优化
 * 这样认证时只要最根本的字段即可
 * id和username都是唯一的
 */
@Data
@Entity
@Table(name = "表_用户信息")
public class UserInfo {
    /**
     * TABLE：使用一个特定的数据库表格来保存主键。
     * SEQUENCE：根据底层数据库的序列来生成主键，条件是数据库支持序列。
     * IDENTITY：主键由数据库自动生成（主要是自动增长型）
     * AUTO：主键由程序控制：不同的数据库策略不同
     */
    @Id
    private Integer id;
    private String username;
    private String nickname;
    private String email;
    private String phone;
    private String avatar;
    private String introduction;
}
