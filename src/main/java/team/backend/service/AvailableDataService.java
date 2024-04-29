package team.backend.service;

import team.backend.domain.AvailableData;

import java.util.List;
import java.util.Map;

public interface AvailableDataService {

    List<String> getNation(String id);
    List<String> getDb(String id, String nation);
    //List<String> getStockCode(String db_name);

    List<String> getSector(String id, String db_name);

    List<String> getName(String id, String sector);
    List<String> getStockCode(String id, String name);

//    List<String> getAvailableDataByFilters(String nation, String db_name,String sector,String name, String stock_code) ;

    List<AvailableData> getList(String id, AvailableData availableData);
    List<AvailableData> getByNameOrStock_code(String stock_code_or_name);

    String getCompany(String stock_code);



}

