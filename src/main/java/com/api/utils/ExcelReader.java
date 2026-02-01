package com.api.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class ExcelReader {
    private static final String DEFAULT_PATH = "src/test/resources/";

    public static Map<String, String> getRowData(String sheetName, int rowIndex) {
        Map<String, String> data = new HashMap<>();
        try (FileInputStream fis = new FileInputStream(DEFAULT_PATH + "testdata.xlsx");
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
        try (FileInputStream fis = new FileInputStream(DEFAULT_PATH + "testdata.xlsx");
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

    // ✅ NEW: Row lookup by key
    public static Map<String, String> getRow(String fileName, String sheetName, String rowKey) {
        Map<String, String> rowData = new HashMap<>();

        try (FileInputStream fis = new FileInputStream(DEFAULT_PATH + fileName);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) throw new RuntimeException("Sheet not found: " + sheetName);

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) throw new RuntimeException("Header row missing in sheet: " + sheetName);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;

                Cell keyCell = row.getCell(0); // assume first column is the key
                if (keyCell != null && keyCell.getStringCellValue().equalsIgnoreCase(rowKey)) {
                    for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                        Cell headerCell = headerRow.getCell(i);
                        Cell valueCell = row.getCell(i);

                        String header = headerCell != null ? headerCell.getStringCellValue() : "Column" + i;
                        String value = valueCell != null ? valueCell.toString() : "";

                        rowData.put(header.trim(), value.trim());
                    }
                    break;
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to read Excel row: " + e.getMessage(), e);
        }

        return rowData;
    }
}
