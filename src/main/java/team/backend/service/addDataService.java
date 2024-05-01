package team.backend.service;

import team.backend.domain.BranchHistory;
import team.backend.domain.ExcludedquarterHistory;
import team.backend.domain.ExclusionperiodHistory;
import team.backend.domain.ServiceUsage;

import java.util.List;
import java.util.Map;

public interface addDataService {

    List<ServiceUsage> getHistory(String id);
    List<String> getArchivedDataStockCode(String stockCode);
    void insertToAddition(String id, String stockCode);


    void insertToServiceUsage(ServiceUsage serviceUsage);
    void insertToBranchHistory(BranchHistory branchHistory);
    List<BranchHistory> getHistorybranch(String id);
    void insertToExclusionperiodHistory(ExclusionperiodHistory exclusionperiodHistory);
    void insertExcludedquarterHistory(ExcludedquarterHistory excludedquarterHistory);
    boolean deleteHistoryBySeq(int serviceusage_seq);
    List<ExcludedquarterHistory> getHistoryByQuarter(String id);
    List<ExclusionperiodHistory> getHistoryByPeriod(String id);

    String getCompany1(String stock_code1);
    String getCompany2(String stock_code2);


}

