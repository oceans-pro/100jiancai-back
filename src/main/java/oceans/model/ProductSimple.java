package oceans.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "表_产品展示")
public class ProductSimple {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String url;
    private Integer type;
    private Integer position;
}
