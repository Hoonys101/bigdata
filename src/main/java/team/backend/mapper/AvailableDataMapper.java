package team.backend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import team.backend.domain.AvailableData;

import java.util.List;

@Mapper
@Repository
public interface AvailableDataMapper {

    List<String> getNation();
    List<String> getDb(String nation);
    List<String> getStockCode(String db_name);
    List<String> getSector(String stock_code);
    List<String> getName(String sector);
    List<AvailableData> getAvailableDataByFilters(AvailableData data);
}
