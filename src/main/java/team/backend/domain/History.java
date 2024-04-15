package team.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class History {
    private String stock_code1;
    private String stock_code2;
    private String start_date;
    private String end_date;
    private String analysis_result;
    private String id;

}
