package team.backend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import team.backend.domain.Addition;
import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface AdditionMapper {

    List<Addition> list();

    List<String> getArchivedDataStockCode(String stockCode);
    void insertToAddition(Map params);

}
