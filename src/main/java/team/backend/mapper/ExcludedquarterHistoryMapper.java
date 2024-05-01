package team.backend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import team.backend.domain.ExcludedquarterHistory;
import team.backend.domain.ServiceUsage;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface ExcludedquarterHistoryMapper {

    List<ExcludedquarterHistory> getHistoryByQuarter(String id);



    void deleteHistoryBySeq(int excludedquarterHistory_seq);
    void insertExcludedquarterHistory(Map list);


    List<ServiceUsage> getResult(String id);

    String getCompany1(String stock_code1);
    String getCompany2(String stock_code2);

    String getCompany3(String stock_code3);
}
