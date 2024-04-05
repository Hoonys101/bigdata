package team.backend.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;

@Entity //JDA 추가
@AllArgsConstructor
@NoArgsConstructor
@Data
@SequenceGenerator(name="ADDRESS_SEQ_GENERATOR", sequenceName="ADDRESS_SEQ",initialValue = 1,allocationSize = 1)//JDA는 시퀀스를 사용하지못한다? 그래서추가?? 이름은 알아서 Oracle일경우 추가
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ADDRESS_SEQ_GENERATOR") //JDA Oracle일경우 추가
    private long seq;
    //@Column(name="username")//DB와 이름이 같지않은경우 지금은 같으니 사용할필요없
    private String name;
    private String addr;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    @CreationTimestamp//SQL문을 사용하지못해서 ex = SYSDATE
    private Date rdate;


}
