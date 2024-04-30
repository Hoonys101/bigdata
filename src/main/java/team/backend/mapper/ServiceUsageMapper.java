package team.backend.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import team.backend.domain.AvailableData;
import team.backend.domain.History;
import team.backend.domain.ServiceUsage;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface ServiceUsageMapper {

    List<ServiceUsage> getHistory(String id);

    List<ServiceUsage> getHistory1(String id);
    List<ServiceUsage> getHistoryByReport(String id);
    List<ServiceUsage> getHistoryByReport1(String id);

    void deleteHistoryBySeq(int serviceusage_seq);
    void insertToServiceUsage(Map list);
    void insertToServiceUsage1(Map list);

    List<ServiceUsage> getResult(String id);

    String getCompany1(String stock_code1);
    String getCompany2(String stock_code2);

}
