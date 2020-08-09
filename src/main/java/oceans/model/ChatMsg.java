package oceans.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "表_聊天记录")
public class ChatMsg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnoreProperties
    private Integer id;
    private String datetime;
    private String username;
    private String nickname;
    private String word;
    @Transient
    private String avatar; // 聊天面吧展示头像

    public ChatMsg() {
        this.setDatetime("");
        this.setUsername("");
        this.setNickname("");
        this.setWord("");
        this.setAvatar("");
    }
}
