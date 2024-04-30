package team.backend.service;

import team.backend.domain.BranchHistory;
import team.backend.domain.ExcludedquarterHistory;
import team.backend.domain.ServiceUsage;

import java.util.List;
import java.util.Map;

public interface addDataService {

    List<ServiceUsage> getHistory(String id);

    List<ServiceUsage> getHistory1(String id);

    List<ServiceUsage> getHistoryByReport(String id);
    List<ServiceUsage> getHistoryByReport1(String id);
    List<String> getArchivedDataStockCode(String stockCode);
    void insertToAddition(String id, String stockCode);


    void insertToServiceUsage(ServiceUsage serviceUsage);
    void insertToBranchHistory(BranchHistory branchHistory);
    List<BranchHistory> getHistorybranch(String id);
    void insertToExcludedquarterHistory(ExcludedquarterHistory excludedquarterHistory);
    boolean deleteHistoryBySeq(int serviceusage_seq);

    public List<ServiceUsage> getResult(String id);

    String getCompany1(String stock_code1);
    String getCompany2(String stock_code2);


}

