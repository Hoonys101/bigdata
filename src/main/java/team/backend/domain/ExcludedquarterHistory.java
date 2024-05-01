package team.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Repository
public class ExcludedquarterHistory { //제외분기
        private int excludedquarterHistory_seq;
        private String stock_code1;
        private String stock_code2;
        private String stock_code3;
        private String name1;
        private String name2;
        private String name3;
        private String start_date;
        private String end_date;
        private String id;
        private String report;

    }

