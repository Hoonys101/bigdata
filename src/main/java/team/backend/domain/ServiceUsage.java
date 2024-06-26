package team.backend.domain;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Repository
public class ServiceUsage {
    private int serviceusage_seq;
    private String stock_code1;
    private String stock_code2;
    private String name1;
    private String name2;
    private String start_date;
    private String end_date;
    private String id;
    private String report;

}
