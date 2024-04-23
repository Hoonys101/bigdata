package team.backend.service;

import team.backend.domain.AvailableData;
import team.backend.domain.ServiceUsage;

import java.util.List;

public interface addDataService {

    List<ServiceUsage> getHistory(String id);

    List<ServiceUsage> getHistoryByReport(String id);
    List<String> getArchivedDataStockCode(String stockCode);
    void insertToAddition(String id, String stockCode);


    void insertToServiceUsage(ServiceUsage serviceUsage);
    boolean deleteHistoryBySeq(int serviceusage_seq);

    public List<ServiceUsage> getResult(String id);

    String getCompany1(String stock_code1);
    String getCompany2(String stock_code2);

}

