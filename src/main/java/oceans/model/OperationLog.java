package oceans.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "表_操作日志")
@ToString
public class OperationLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private Date visitTime;
    private String username;
    private String ip;
    private String uri;
    private Long executionTime;
    private String method;
    private String data;
}
