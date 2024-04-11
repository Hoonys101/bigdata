package team.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.backend.domain.ServiceUsage;
import team.backend.mapper.AdditionMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class addDataServiceImpl implements addDataService {
    @Autowired
    private final AdditionMapper addMapper;
    @Autowired
    addDataServiceImpl(AdditionMapper addMapper){
        this.addMapper=addMapper;
    }
    @Override
    public List<String> getArchivedDataStockCode(String stockCode) {
        List<String> result=addMapper.getArchivedDataStockCode(stockCode);
        return result;
    }

    @Override
    public void insertToAddition(String id, String stockCode) {
        Map<String, String> params =new HashMap<>();
        params.put("id",id);
        params.put("stockCode",stockCode);
        addMapper.insertToAddition(params);
        return;
    }
    @Override
    public List<ServiceUsage> getServiceUsageStockCode(ServiceUsage serviceUsage){
        return  null;
    }
    @Override
    public void insertToServiceUsage(Map params){

    }
}
