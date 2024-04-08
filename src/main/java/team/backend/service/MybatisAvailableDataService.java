package team.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.backend.domain.AvailableData;
import team.backend.mapper.AvailableDataMapper;

import java.util.List;

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
    public List<String> getNation() {
        List<String> dbs = availableDataMapper.getNation();
        //System.out.println("dbs1"+dbs);
        return dbs;
    }

    @Override
    public List<String> getDb(String nation) {
        List<String> dbs = availableDataMapper.getDb(nation);
        logger.debug("getDBsByCountry : {}", dbs);
        //System.out.println("dbs2"+dbs);
        return dbs;
    }



    @Override
    public List<String> getSector( String db_name) {
        //System.out.println("dbs4"+dbs);
        return availableDataMapper.getSector(db_name);
    }

    @Override
    public List<String> getName(String sector) {
        //System.out.println("dbs5"+dbs);
        return availableDataMapper.getName(sector);
    }

    @Override
    public List<String> getStockCode(String name) {
        //System.out.println("dbs3"+dbs);
        return availableDataMapper.getStockCode(name);
    }
    @Override
    public List<String> getAvailableDataByFilters(String nation, String db_name,String sector,String name, String stock_code) {
        List<String> data = availableDataMapper.getAvailableDataByFilters(nation, db_name, sector, name, stock_code);
        System.out.println(data);
        return data;
    }
}
