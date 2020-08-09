package oceans.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "表_公司优势")
public class Advantage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String content;
    // 暂时不开放图标接口
}
