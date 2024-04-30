package team.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Repository
public class ExclusionperiodHistory { //제외기간
    private int exclusionperiodHistory_seq;
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
//2013-12-26
//        2014-03-26
//        0
//        2014-09-29
//        2014-12-26
//        0
//        2019-04-08
//        2019-07-05
//        0
//        2019-07-08
//        2019-10-04
//        0
//        2020-01-07
//        2020-04-02
//        0
//        2020-07-06
//        2020-09-29
//        0
//        2021-07-02
//        2021-10-01
//        0
//        2021-10-05
//        2021-12-30
//        0