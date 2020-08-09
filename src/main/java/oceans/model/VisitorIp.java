package oceans.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "汇总表_访客ip") // 会自动变为小写 Linux上的mysql区分大小写...
public class VisitorIp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String ip;
    private String lastDate;
    private Integer type;
}
