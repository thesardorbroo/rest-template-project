package market.marketuz.service;

import lombok.RequiredArgsConstructor;
import market.marketuz.dto.ProductDto;
import market.marketuz.service.impl.UserServiceImpl;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class ExcelServiceImpl {

    private final UserServiceImpl service;

    public void exportToExcel(List<ProductDto> productDtoList) throws IOException {
//        List<ProductDto> productDtos = new ArrayList<>();
//        productDtoList.clear();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Top products");
        Row row = sheet.createRow(0);

        row.createCell(0).setCellValue("ID");
        row.createCell(1).setCellValue("Name");
        row.createCell(2).setCellValue("Price");
        row.createCell(3).setCellValue("Product type name");
        row.createCell(4).setCellValue("Amount");

        AtomicInteger i = new AtomicInteger(1);
//        productDtoList.stream().forEach( p -> {
        for(ProductDto p: productDtoList){
            Row r = sheet.createRow(i.get());
            r.createCell(0).setCellValue(p.getId());
            r.createCell(1).setCellValue(p.getName());
            r.createCell(2).setCellValue(p.getPrice());
            r.createCell(3).setCellValue(p.getType().getName());
            r.createCell(4).setCellValue(p.getAmount());
            i.getAndIncrement();
        }

        Date d = new Date();

        Long time = d.getTime();
        int year = d.getYear();
        int month = d.getMonth();
        int date = d.getDate();

        File y = new File("src/main/resources/templates/" + year + "");
        if(!y.exists())y.mkdirs();
        File m = new File(y + "/" + month + "");
        if(!m.exists())m.mkdirs();
        File s = new File(m.getPath() + "/" + date);
        if(!s.exists())s.mkdirs();
        File file = new File(s.getPath() + "/" + time + ".xlsx");
        FileOutputStream fos = new FileOutputStream(file);
        workbook.write(fos);
//        workbook.write(new FileOutputStream(new File("asdf.xlsx")));
        fos.close();
        workbook.close();
        System.out.println("erqewrtrewrq");
    }
}
