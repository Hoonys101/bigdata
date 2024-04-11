package team.backend.service;

import team.backend.domain.AvailableData;

import java.util.List;

public interface AvailableDataService {

    List<String> getNation(String id);
    List<String> getDb(String id, String nation);
    //List<String> getStockCode(String db_name);

    List<String> getSector(String id, String db_name);

    List<String> getName(String id, String sector);
    List<String> getStockCode(String id, String name);

//    List<String> getAvailableDataByFilters(String nation, String db_name,String sector,String name, String stock_code) ;
}

