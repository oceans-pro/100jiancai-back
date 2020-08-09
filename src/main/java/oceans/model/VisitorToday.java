package oceans.model;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "表_访客记录")
public class VisitorToday {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String datetime;
    private String ip;
    private Integer type;
    private String refer;
    private Integer device;
}
