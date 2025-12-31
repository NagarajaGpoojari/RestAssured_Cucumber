package com.api.utils;

import org.apache.poi.ss.usermodel.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class ExcelReader {
    private static final String FILE_PATH = "resources/testdata.xlsx";

    public static Map<String, String> getRowData(String sheetName, int rowIndex) {
        Map<String, String> data = new HashMap<>();
        try (FileInputStream fis = new FileInputStream(FILE_PATH);
             Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            Row headerRow = sheet.getRow(0);
            Row dataRow = sheet.getRow(rowIndex);

            for (int col = 0; col < headerRow.getLastCellNum(); col++) {
                String key = headerRow.getCell(col).getStringCellValue();
                String value = dataRow.getCell(col).toString();
                data.put(key, value);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading Excel file", e);
        }
        return data;
    }

    public static List<Map<String, String>> getAllRows(String sheetName) {
        List<Map<String, String>> rows = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(FILE_PATH);
             Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            Row headerRow = sheet.getRow(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Map<String, String> rowData = new HashMap<>();
                Row dataRow = sheet.getRow(i);
                for (int col = 0; col < headerRow.getLastCellNum(); col++) {
                    String key = headerRow.getCell(col).getStringCellValue();
                    String value = dataRow.getCell(col).toString();
                    rowData.put(key, value);
                }
                rows.add(rowData);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading Excel file", e);
        }
        return rows;
    }
}
