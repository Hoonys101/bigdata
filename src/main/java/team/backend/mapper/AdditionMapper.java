package team.backend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import team.backend.domain.Addition;
import java.util.List;

@Mapper
@Repository
public interface AdditionMapper {
    List<Addition> list();
}
