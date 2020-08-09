package oceans.model.dto;

import lombok.Data;

import java.util.Set;

@Data
public class OnlineInfo {
    private Integer num;
    // 根据权限有无进行展示
    private Set<String> usernameList;
}
