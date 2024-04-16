package team.backend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import team.backend.domain.History;
import team.backend.domain.ServiceUsage;

import java.util.List;
import java.util.Map;
@Mapper
@Repository
public interface HistoryMapper {

    List<History> getHistory(String id);

    void deleteHistoryById(String analysis_result);
}
