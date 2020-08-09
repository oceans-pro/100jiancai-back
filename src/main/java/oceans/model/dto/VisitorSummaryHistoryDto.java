package oceans.model.dto;

import lombok.Data;

@Data
public class VisitorSummaryHistoryDto {
    private Integer publicClickTotal;
    private Integer publicHeadTotal;
    private Integer adminClickTotal;
    private Integer adminHeadTotal;
}
