package oceans.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface VisitorTodayMapper {
    int selectCountByDateAndByTypeAndWithIpDistinct(@Param("date") String date,
                                                    @Param("type") int type);

    List<String> selectDistinctIPList(@Param("date") String date,
                                      @Param("type") int type);
}
