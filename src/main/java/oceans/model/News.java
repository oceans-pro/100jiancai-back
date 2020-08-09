package oceans.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "表_新闻")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private Boolean flagExternal;
    private String img;
    private String date;
    private Boolean active;
    private String summary;
    private String link;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "关联_新闻_标签",
            joinColumns = @JoinColumn(name = "news_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    private List<Tag> tags;
}
