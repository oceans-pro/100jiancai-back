package oceans.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface MessageMapper {

    /**
     * 根据查询ip和dateStr查询数目
     *
     * @param ip      ip地址
     * @param dateStr 2000-12-12
     */
    Integer selectExistNumByIpAndByDateStr(@Param("ip") String ip, @Param("dateStr") String dateStr);
}
