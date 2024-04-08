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
    public List<String> getStockCode(String db_name) {
        List<String> dbs = availableDataMapper.getStockCode(db_name);
        //System.out.println("dbs3"+dbs);
        return dbs;
    }

    @Override
    public List<String> getSector(String stock_code) {
        List<String> dbs = availableDataMapper.getSector(stock_code);
        //System.out.println("dbs4"+dbs);
        return dbs;
    }

    @Override
    public List<String> getName(String sector) {
        List<String> dbs = availableDataMapper.getName(sector);
        //System.out.println("dbs5"+dbs);
        return dbs;
    }

    @Override
    public List<AvailableData> getAvailableDataByFilters( String nation, String db_name,String stock_code, String sector, String name) {
        AvailableData data = new AvailableData();

        data.setNation(nation);
        data.setDb_name(db_name);
        data.setStock_code(stock_code);
        data.setSector(sector);
        data.setName(name);
        List<AvailableData> dbs = availableDataMapper.getAvailableDataByFilters(data);
        System.out.println("dbs6"+dbs);
        return dbs;
    }
}
