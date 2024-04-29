package team.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.backend.domain.AvailableData;
import team.backend.domain.BranchHistory;
import team.backend.domain.ServiceUsage;
import team.backend.domain.ServiceUsage1;
import team.backend.mapper.AdditionMapper;
import team.backend.mapper.BranchHistoryMapper;
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
    private  final BranchHistoryMapper branchHistoryMapper;

    @Autowired
    private final HistoryMapper historyMapper;
    @Autowired
    addDataServiceImpl(AdditionMapper addMapper, ServiceUsageMapper serviceUsageMapper,HistoryMapper historyMapper,BranchHistoryMapper branchHistoryMapper){
        this.addMapper=addMapper;
        this.serviceUsageMapper = serviceUsageMapper;
        this.historyMapper = historyMapper;
        this.branchHistoryMapper = branchHistoryMapper;
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
        list.put("serviceusage_seq", String.valueOf(serviceUsage.getServiceusage_seq()));
        list.put("stock_code1",serviceUsage.getStock_code1());
        list.put("stock_code2",serviceUsage.getStock_code2());
        list.put("name1",serviceUsage.getName1());
        list.put("name2",serviceUsage.getName2());
        list.put("start_date",serviceUsage.getStart_date());
        list.put("end_date",serviceUsage.getEnd_date());
        list.put("id",serviceUsage.getId());
        list.put("report",serviceUsage.getReport());


        serviceUsageMapper.insertToServiceUsage(list);

    }
    @Override
    public void insertToBranchHistory(BranchHistory branchHistory){
        Map<String, String> list =new HashMap<>();
        list.put("serviceusage_seq", String.valueOf(branchHistory.getBranchHistory_seq()));
        list.put("stock_code1",branchHistory.getStock_code1());
        list.put("stock_code2",branchHistory.getStock_code2());
        list.put("name1",branchHistory.getName1());
        list.put("name2",branchHistory.getName2());
        list.put("start_date",branchHistory.getStart_date());
        list.put("end_date",branchHistory.getEnd_date());
        list.put("id",branchHistory.getId());
        list.put("report",branchHistory.getReport());


        branchHistoryMapper.insertToBranchHistory(list);

    }
    @Override
    public void insertToServiceUsage1(ServiceUsage1 serviceUsage1){
        Map<String, String> list =new HashMap<>();
        list.put("serviceusage_seq", String.valueOf(serviceUsage1.getServiceusage_seq()));
        list.put("stock_code1",serviceUsage1.getStock_code1());
        list.put("stock_code2",serviceUsage1.getStock_code2());
        list.put("stock_code3",serviceUsage1.getStock_code3());
        list.put("name1",serviceUsage1.getName1());
        list.put("name2",serviceUsage1.getName2());
        list.put("name3",serviceUsage1.getName3());
        list.put("start_date",serviceUsage1.getStart_date());
        list.put("end_date",serviceUsage1.getEnd_date());
        list.put("id",serviceUsage1.getId());
        list.put("report",serviceUsage1.getReport());


        serviceUsageMapper.insertToServiceUsage1(list);

    }
    @Override
    public List<ServiceUsage> getHistory(String id){
        List<ServiceUsage> historyList = serviceUsageMapper.getHistory(id);
        System.out.println("historyList"+historyList);
        return historyList;
    }
    @Override
    public List<BranchHistory> getHistorybranch(String id){
        List<BranchHistory> historyList = branchHistoryMapper.getHistory(id);
        System.out.println("historyList"+historyList);
        return historyList;
    }
    @Override
    public List<ServiceUsage> getHistory1(String id){
        List<ServiceUsage> historyList = serviceUsageMapper.getHistory1(id);
        System.out.println("historyList"+historyList);
        return historyList;
    }
    @Override
    public List<ServiceUsage> getHistoryByReport(String id){
        List<ServiceUsage> historyListByReport = serviceUsageMapper.getHistoryByReport(id);
        return historyListByReport;
    }

    @Override
    public List<ServiceUsage> getHistoryByReport1(String id){
        List<ServiceUsage> historyListByReport = serviceUsageMapper.getHistoryByReport1(id);
        return historyListByReport;
    }
    @Override
    public List<ServiceUsage> getResult(String id){
        List<ServiceUsage> resultList = serviceUsageMapper.getResult(id);
        return resultList;
    }
    @Override
    public boolean deleteHistoryBySeq(int serviceusage_seq) {
        serviceUsageMapper.deleteHistoryBySeq(serviceusage_seq);
        return false;
    }
    @Override
    public String getCompany1(String stock_code1){
        String com = serviceUsageMapper.getCompany1(stock_code1);
        System.out.println("com"+com);
        return com;
    }
    @Override
    public String getCompany2(String stock_code2){
        return serviceUsageMapper.getCompany2(stock_code2);

    }
    @Override
    public String getCompany3(String stock_code3){
        return serviceUsageMapper.getCompany3(stock_code3);

    }
}
