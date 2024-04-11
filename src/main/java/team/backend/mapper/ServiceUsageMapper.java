package team.backend.mapper;


import team.backend.domain.ServiceUsage;

import java.util.List;
import java.util.Map;

public interface ServiceUsageMapper {

    List<ServiceUsage> getServiceUsageStockCode(String stock_code);

    void insertToServiceUsage(Map params);
}
