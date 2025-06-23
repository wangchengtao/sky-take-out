package com.summer;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class POITest {

    @Test
    public void write() throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFSheet sheet = workbook.createSheet("itcast");

        XSSFRow row1 = sheet.createRow(0);
        row1.createCell(0).setCellValue("姓名");
        row1.createCell(1).setCellValue("城市");

        XSSFRow row2 = sheet.createRow(1);
        row2.createCell(0).setCellValue("张三");
        row2.createCell(1).setCellValue("西安");

        XSSFRow row3 = sheet.createRow(2);
        row3.createCell(0).setCellValue("李四");
        row3.createCell(1).setCellValue("北京");

        FileOutputStream out = new FileOutputStream("itcast.xlsx");

        workbook.write(out);
    }

    @Test
    public void read() throws IOException {
        FileInputStream in = new FileInputStream("itcast.xlsx");

        XSSFWorkbook workbook = new XSSFWorkbook(in);
        XSSFSheet sheet = workbook.getSheetAt(0);

        int lastRowNum = sheet.getLastRowNum();

        for (int i = 0; i < lastRowNum; i++) {
            XSSFRow titleRow = sheet.getRow(i);
            XSSFCell cell1 = titleRow.getCell(0);
            String value1 = cell1.getStringCellValue();
            XSSFCell cell2 = titleRow.getCell(1);
            String value2 = cell2.getStringCellValue();

            System.out.println(value1 + ":" + value2);
        }
    }
}
