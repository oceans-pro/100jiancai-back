package oceans.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class VisitorTodayDto {
    private String type; // 'public' or 'admin'
    private Integer clickCountTotal; // 总访问人次
    private Integer headCountTotal; // 总访问人数
    private List<Integer> clickCountList; // 最近的访问人次列表
    private List<Integer> headCountList; // 最近的访问人数列表
}
