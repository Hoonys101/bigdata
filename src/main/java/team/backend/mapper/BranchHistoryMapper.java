package team.backend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import team.backend.domain.BranchHistory;

import java.util.List;
import java.util.Map;
@Mapper
@Repository
public interface BranchHistoryMapper {

    void insertToBranchHistory(Map list);

    List<BranchHistory> getHistorybranch(String id);
}
