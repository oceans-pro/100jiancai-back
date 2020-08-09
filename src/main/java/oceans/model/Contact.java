package oceans.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "表_联系方式")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String telephone;
    private String mobilePhone;
    private String wechat;
    private String qq;
    private String mail;
    // @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    // private Date datetime;
    private String datetime;
    private String address;
}
