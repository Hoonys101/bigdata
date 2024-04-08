package team.backend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class AvailableData {
    @Id
    private String stock_code;
    private String nation;
    private String db_name;
    private String sector;
    private String name;

}
