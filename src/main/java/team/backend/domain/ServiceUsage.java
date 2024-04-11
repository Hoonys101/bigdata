package team.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServiceUsage {
    private String db1_name;
    private String stock_code1;
    private String db2_name;
    private String stock_code2;
    private String start_date;
    private String end_date;
    private String id;
}
