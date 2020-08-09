package oceans.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "表_新闻标签")
@NoArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String value;
    @ManyToMany(mappedBy = "tags", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<News> newsList;

    public Tag(String value) {
        this.value = value;
    }
}
