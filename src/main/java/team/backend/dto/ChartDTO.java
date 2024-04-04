package team.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChartDTO {
    private String item;
    private int number;
    @Override
    public String toString(){
        return "#ChartDTO item: " + item + ", number: "+ number;
    }

}
