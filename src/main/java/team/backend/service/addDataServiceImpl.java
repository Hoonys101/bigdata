package team.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.backend.domain.History;
import team.backend.domain.ServiceUsage;
import team.backend.mapper.AdditionMapper;
import team.backend.mapper.HistoryMapper;
import team.backend.mapper.ServiceUsageMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class addDataServiceImpl implements addDataService {
    @Autowired
    private final AdditionMapper addMapper;
    @Autowired
    private final ServiceUsageMapper serviceUsageMapper;

    @Autowired
    private final HistoryMapper historyMapper;
    @Autowired
    addDataServiceImpl(AdditionMapper addMapper, ServiceUsageMapper serviceUsageMapper,HistoryMapper historyMapper){
        this.addMapper=addMapper;
        this.serviceUsageMapper = serviceUsageMapper;
        this.historyMapper = historyMapper;
    }


    @Override
    public List<String> getArchivedDataStockCode(String stockCode) {
        List<String> result =addMapper.getArchivedDataStockCode(stockCode);
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
    public void insertToServiceUsage(ServiceUsage serviceUsage){
        Map<String, String> list =new HashMap<>();
        list.put("stock_code1",serviceUsage.getStock_code1());
        list.put("stock_code2",serviceUsage.getStock_code2());
        list.put("start_date",serviceUsage.getStart_date());
        list.put("end_date",serviceUsage.getEnd_date());
        list.put("id",serviceUsage.getId());
        serviceUsageMapper.insertToServiceUsage(list);

    }
    @Override
    public List<History> getHistory(String id){
        List<History> historyList = historyMapper.getHistory(id);
        System.out.println("historyList"+historyList);
        return historyList;
    }
    public void deleteHistoryById(String analysis_result) {
        historyMapper.deleteHistoryById(analysis_result);
    }
}
