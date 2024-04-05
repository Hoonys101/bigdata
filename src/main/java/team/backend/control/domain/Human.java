package team.backend.control.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor //기본생성자
@AllArgsConstructor // 파라미터 생성자
@Data
public class Human {
    private String name;
    private int age;
}
