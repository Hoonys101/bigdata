package team.backend.service;

import team.backend.domain.AvailableData;

import java.util.List;

public interface AvailableDataService {

    List<String> getNation();
    List<String> getDb(String nation);
    //List<String> getStockCode(String db_name);

    List<String> getSector(String db_name);

    List<String> getName(String sector);
    List<String> getStockCode(String name);

    List<String> getAvailableDataByFilters(String nation, String db_name,String sector,String name, String stock_code) ;
}

