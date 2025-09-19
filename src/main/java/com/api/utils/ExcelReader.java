package com.api.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
	
	    public static Map<String, String> getUserData(String sheetName) {
	        Map<String, String> data = new HashMap<>();
	        try (FileInputStream fis = new FileInputStream("resources/testdata.xlsx")) {
	            Workbook workbook = new XSSFWorkbook(fis);
	            Sheet sheet = workbook.getSheet(sheetName);
	            Row row = sheet.getRow(1); 
	            // Assuming headers in row 0
	            data.put("name", row.getCell(0).getStringCellValue());
	            data.put("job", row.getCell(1).getStringCellValue());
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return data;
	    }
	}



