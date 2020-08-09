package oceans.model;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "汇总表_访客数目")
public class VisitorTodaySummary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String date;
    private Integer publicClick;
    private Integer adminClick;
    private Integer publicHead;
    private Integer adminHead;
}
