package team.backend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface AdditionMapper {
    List<String> getArchivedDataStockCode(String stockCode);
    void insertToAddition(Map params);
}
