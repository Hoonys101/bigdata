package team.backend.service;

import team.backend.domain.AvailableData;

import java.util.List;

public interface AvailableDataService {

    List<String> getNation();
    List<String> getDb(String nation);
    List<String> getStockCode(String db_name);
    List<String> getSector(String stock_code);

    List<String> getName(String sector);

    List<AvailableData> getAvailableDataByFilters(String nation, String db_name,String stock_code , String sector, String name) ;
}

