package oceans.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "表_公司介绍")
public class CompanyIntroduction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String html;
}
