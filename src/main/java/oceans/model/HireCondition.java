package oceans.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "表_招聘需求")
public class HireCondition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String count;
    private String jobName;
    private String date;
    private String content;
    private Boolean active;
}
