package team.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.backend.domain.BranchHistory;
import team.backend.domain.ExcludedquarterHistory;
import team.backend.domain.ExclusionperiodHistory;
import team.backend.domain.ServiceUsage;
import team.backend.mapper.*;

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
    private  final ExclusionperiodHistoryMapper exclusionperiodHistoryMapper;

    @Autowired
    private  final ExcludedquarterHistoryMapper excludedquarterHistoryMapper;

    @Autowired
    private final HistoryMapper historyMapper;
    @Autowired
    addDataServiceImpl(AdditionMapper addMapper, ServiceUsageMapper serviceUsageMapper,HistoryMapper historyMapper,BranchHistoryMapper branchHistoryMapper, ExclusionperiodHistoryMapper exclusionperiodHistoryMapper,ExcludedquarterHistoryMapper excludedquarterHistoryMapper){
        this.addMapper=addMapper;
        this.serviceUsageMapper = serviceUsageMapper;
        this.historyMapper = historyMapper;
        this.branchHistoryMapper = branchHistoryMapper;
        this.exclusionperiodHistoryMapper = exclusionperiodHistoryMapper;
        this.excludedquarterHistoryMapper = excludedquarterHistoryMapper;
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
        list.put("branchHistory_seq", String.valueOf(branchHistory.getBranchHistory_seq()));
        list.put("stock_code1",branchHistory.getStock_code1());
        list.put("stock_code2",branchHistory.getStock_code2());
        list.put("name1",branchHistory.getName1());
        list.put("name2",branchHistory.getName2());
        list.put("start_date",branchHistory.getStart_date());
        list.put("end_date",branchHistory.getEnd_date());
        list.put("id",branchHistory.getId());
        list.put("report",branchHistory.getReport());
        list.put("resultCount", String.valueOf(branchHistory.getResultcount()));


        branchHistoryMapper.insertToBranchHistory(list);

    }
    @Override
    public void insertToExclusionperiodHistory(ExclusionperiodHistory exclusionperiodHistory){
        Map<String, Object> list =new HashMap<>();
        list.put("exclusionperiodHistory_seq", exclusionperiodHistory.getExclusionperiodHistory_seq());
        list.put("stock_code1",exclusionperiodHistory.getStock_code1());
        list.put("stock_code2",exclusionperiodHistory.getStock_code2());
        list.put("stock_code3",exclusionperiodHistory.getStock_code3());
        list.put("name1",exclusionperiodHistory.getName1());
        list.put("name2",exclusionperiodHistory.getName2());
        list.put("name3",exclusionperiodHistory.getName3());
        list.put("start_date",exclusionperiodHistory.getStart_date());
        list.put("end_date",exclusionperiodHistory.getEnd_date());
        list.put("id",exclusionperiodHistory.getId());
        list.put("report",exclusionperiodHistory.getReport());
        System.out.println("insertToExclusionperiodHistory : "+list);


        exclusionperiodHistoryMapper.insertToExclusionperiodHistory(list);

    }
    @Override
    public void insertExcludedquarterHistory(ExcludedquarterHistory excludedquarterHistory){
        Map<String, Object> list =new HashMap<>();
        list.put("excludedquarterHistory_seq", excludedquarterHistory.getExcludedquarterHistory_seq());
        list.put("stock_code1",excludedquarterHistory.getStock_code1());
        list.put("stock_code2",excludedquarterHistory.getStock_code2());
        list.put("stock_code3",excludedquarterHistory.getStock_code3());
        list.put("name1",excludedquarterHistory.getName1());
        list.put("name2",excludedquarterHistory.getName2());
        list.put("name3",excludedquarterHistory.getName3());
        list.put("start_date",excludedquarterHistory.getStart_date());
        list.put("end_date",excludedquarterHistory.getEnd_date());
        list.put("id",excludedquarterHistory.getId());
        list.put("report",excludedquarterHistory.getReport());

        System.out.println("insertExcludedquarterHistory : "+list);
        excludedquarterHistoryMapper.insertExcludedquarterHistory(list);

    }
    @Override
    public List<ServiceUsage> getHistory(String id){
        List<ServiceUsage> historyList = serviceUsageMapper.getHistory(id);
        System.out.println("historyList"+historyList);
        return historyList;
    }
    @Override
    public List<BranchHistory> getHistorybranch(String id){
        List<BranchHistory> historyList = branchHistoryMapper.getHistorybranch(id);
        System.out.println("historyList"+historyList);
        return historyList;
    }

    @Override
    public List<ExcludedquarterHistory> getHistoryByQuarter(String id){
        List<ExcludedquarterHistory> historyListByQuarter = excludedquarterHistoryMapper.getHistoryByQuarter(id);
        return historyListByQuarter;
    }
    @Override
    public List<ExclusionperiodHistory> getHistoryByPeriod(String id){
        List<ExclusionperiodHistory> historyListByPeriod = exclusionperiodHistoryMapper.getHistoryByPeriod(id);
        return historyListByPeriod;
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

}
