package team.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.backend.domain.AvailableData;
import team.backend.mapper.AvailableDataMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MybatisAvailableDataService implements AvailableDataService {
    private static final Logger logger = LoggerFactory.getLogger(AvailableDataService.class);
    @Autowired
    private final AvailableDataMapper availableDataMapper;
    @Autowired
    public MybatisAvailableDataService( AvailableDataMapper availableDataMapper){
        this.availableDataMapper = availableDataMapper;
    }


    @Override
    public List<String> getNation(String id) {
        List<String> dbs = availableDataMapper.getNation(id);
        //System.out.println("dbs1"+dbs);
        return dbs;
    }

    @Override
    public List<String> getDb(String id, String nation) {
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        params.put("nation", nation);
        List<String> dbs = availableDataMapper.getDb(params);
        logger.debug("getDBsByCountry : {}", dbs);
        //System.out.println("dbs2"+dbs);
        return dbs;
    }



    @Override
    public List<String> getSector(String id, String db_name) {
        //System.out.println("dbs4"+dbs);
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        params.put("db_name", db_name);
        return availableDataMapper.getSector(params);
    }

    @Override
    public List<String> getName(String id, String sector) {
        //System.out.println("dbs5"+dbs);
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        params.put("sector", sector);
        return availableDataMapper.getName(params);
    }

    @Override
    public List<String> getStockCode(String id, String name) {
        //System.out.println("dbs3"+dbs);
        Map<String, String> params = new HashMap<>();
        System.out.println("params"+params);
        params.put("id", id);
        params.put("name", name);
        System.out.println("params"+params);
        return availableDataMapper.getStockCode(params);
    }
//    @Override
//    public List<String> getAvailableDataByFilters(String nation, String db_name,String sector,String name, String stock_code) {
//        List<String> data = availableDataMapper.getAvailableDataByFilters(nation, db_name, sector, name, stock_code);
//        System.out.println(data);
//        return data;
//    }
    @Override
    public List<AvailableData> getList(String id,AvailableData availableData){
        Map<String, String> params = new HashMap<>();
        System.out.println("params"+params);
        params.put("id", id);
        params.put("nation", availableData.getNation());
        params.put("db_name", availableData.getDb_name());
        params.put("sector", availableData.getSector());
        params.put("name", availableData.getName());
        params.put("stock_code", availableData.getStock_code());
        System.out.println("params"+params);
        return availableDataMapper.getList(params);
    }
    @Override

    public String getCompany(String stock_code){
        return availableDataMapper.getCompany(stock_code);
    }


    public List<AvailableData> getByNameOrStock_code(String id, String stock_code_or_name){
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        params.put("stock_code_or_name", stock_code_or_name);
        return availableDataMapper.getByNameOrStock_code(params);
    }
}

