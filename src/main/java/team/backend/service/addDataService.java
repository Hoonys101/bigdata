package team.backend.service;

import org.springframework.stereotype.Service;
import team.backend.domain.ServiceUsage;

import java.util.List;
import java.util.Map;

public interface addDataService {

    List<String> getArchivedDataStockCode(String stockCode);
    void insertToAddition(String id, String stockCode);


    void insertToServiceUsage(ServiceUsage serviceUsage);
}

