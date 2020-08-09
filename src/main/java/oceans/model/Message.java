package oceans.model;

import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Data
@Entity
@Table(name = "表_顾客留言")
@SQLDelete(sql = "update 表_顾客留言 set active = 0 where id = ?")
@Where(clause = "active = 1")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Boolean active;
    private Boolean hasRead;
    private String title;
    private String content;
    private String datetime;
    private String ip;
    private String city;
    private String origin;
    private String referer;
}
