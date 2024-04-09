package team.backend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import team.backend.domain.AvailableData;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface AvailableDataMapper {

    List<String> getNation(String id);
    List<String> getDb(Map params);
    List<String> getSector(Map params);
    List<String> getName(Map params);
    List<String> getStockCode(Map params);
    List<String> getAvailableDataByFilters(String nation, String db_name,String sector,String name, String stock_code);
}
