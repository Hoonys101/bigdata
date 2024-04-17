package team.backend.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import team.backend.domain.History;
import team.backend.domain.ServiceUsage;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface ServiceUsageMapper {

    List<ServiceUsage> getHistory(String id);

    void deleteHistoryBySeq(int serviceusage_seq);
    void insertToServiceUsage(Map list);
}
