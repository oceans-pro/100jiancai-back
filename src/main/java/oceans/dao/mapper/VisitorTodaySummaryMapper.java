package oceans.dao.mapper;


import oceans.model.dto.VisitorSummaryHistoryDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface VisitorTodaySummaryMapper {
  VisitorSummaryHistoryDto selectVisitorSumHistory();
}
