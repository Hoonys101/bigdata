package team.backend.control.domain;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
public class ToDo {
    private String subject;
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private java.util.Date cdate;
}
