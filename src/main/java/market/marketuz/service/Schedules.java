package market.marketuz.service;

import lombok.RequiredArgsConstructor;
import market.marketuz.dto.ProductDto;
import market.marketuz.dto.ResponseDto;
import market.marketuz.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class Schedules {

    private final UserServiceImpl service;

    private final ExcelServiceImpl excelService;

    @Scheduled(fixedDelay = 1000*60*60)
    public void exportProducts() throws IOException {
        ResponseDto<List<ProductDto>> responseDto = service.getAllProductWithOutPage();
        System.out.println(responseDto);
//        excelService.exportToExcel(responseDto.getData());
        excelService.exportToExcel(new ArrayList<>());
    }
}
