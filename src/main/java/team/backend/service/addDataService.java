package team.backend.service;

import org.springframework.stereotype.Service;
import team.backend.domain.History;
import team.backend.domain.ServiceUsage;

import java.util.List;
import java.util.Map;

public interface addDataService {

    List<History> getHistory(String id);
    List<String> getArchivedDataStockCode(String stockCode);
    void insertToAddition(String id, String stockCode);


    void insertToServiceUsage(ServiceUsage serviceUsage);
    void deleteHistoryById(String analysis_result);

}

