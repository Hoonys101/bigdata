package team.backend.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import team.backend.domain.ServiceUsage;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface ServiceUsageMapper {


    void insertToServiceUsage(Map list);
}
