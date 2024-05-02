package team.backend.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import team.backend.domain.ExclusionperiodHistory;
import team.backend.domain.ServiceUsage;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface ExclusionperiodHistoryMapper {//제외기간


    List<ExclusionperiodHistory> getHistoryByPeriod(String id);

    void deleteHistoryBySeq(int excludedquarterHistory_seq);
    void insertToServiceUsage(Map list);
    void insertToExclusionperiodHistory(Map list);

    List<ServiceUsage> getResult(String id);

    String getCompany1(String stock_code1);
    String getCompany2(String stock_code2);

    String getCompany3(String stock_code3);


    void deleteExclusionperiodHistoryBySeq(int exclusionperiodHistory_Seq);
}
