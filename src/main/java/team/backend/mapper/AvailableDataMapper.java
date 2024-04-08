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
    List<String> getSector(String db_name);
    List<String> getName(String sector);
    List<String> getStockCode(String name);
    List<String> getAvailableDataByFilters(String nation, String db_name,String sector,String name, String stock_code);
}
