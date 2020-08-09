package oceans.model;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "表_公司历史")
public class CompanyHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String date;
    private String description;
    private Boolean active;
    private String color;
    private String type;
}
