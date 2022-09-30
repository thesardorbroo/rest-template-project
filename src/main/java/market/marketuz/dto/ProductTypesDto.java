package market.marketuz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductTypesDto {

    private Integer id;

    private String name;

    private Units units;

    private Integer limitAmount;
}
