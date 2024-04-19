package team.backend.service;

import team.backend.domain.ServiceUsage;

import java.util.List;

public interface addDataService {

    List<ServiceUsage> getHistory(String id);
    List<String> getArchivedDataStockCode(String stockCode);
    void insertToAddition(String id, String stockCode);


    void insertToServiceUsage(ServiceUsage serviceUsage);
    boolean deleteHistoryBySeq(int serviceusage_seq);

    public List<ServiceUsage> getResult(String id);

}

