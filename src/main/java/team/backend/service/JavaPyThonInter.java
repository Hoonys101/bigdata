package team.backend.service;

public interface JavaPythonInter {
    boolean strParameter(String... args);
    /*첫번째 인자가 "add_data"인 경우
      "add_data", "db_name", "stock_code"를 인자로 호출
      첫번째 인자가 "cal_data"인 경우
      "cal_data",
      "first_db_name","first_stock_code",
      "second_db_name","second_stock_code",
      "startdate","lastdate"를 인자로 호출
      //
      첫번째 인자가 "diff_call_data"인 경우
      "diff_call_data",
      "first_db_name","first_stock_code",
      "diff_db_name","diff_stock_code",
      "second_db_name","second_stock_code",
      "startdate","lastdate"를 인자로 호출*/

}