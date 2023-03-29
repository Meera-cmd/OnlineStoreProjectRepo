package com.onlinestore.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

public class Utilities {

	public static final int IMPLICIT_WAIT_TIME=10;
	public static final int PAGE_LOAD_TIME=5;

	public static String generateRandomEmailAddress() {
		
		Random rand = new Random();
		int rand_int=rand.nextInt(100);
		return "xyz"+rand_int+"@gmail.com";
	}
	
	public static Object[][] getTestDataFromExcel(String sheetName) {
		File excelFile=new File(System.getProperty(("user.dir")+"\\src\\main\\java\\"));
		XSSFWorkbook workbook=null;
		try {
		FileInputStream fisExcel=new FileInputStream(excelFile);
		workbook=new XSSFWorkbook();
		}catch(Throwable e) {
			e.printStackTrace();
		}
		XSSFSheet sheet=workbook.getSheet(sheetName);

		int rows=sheet.getLastRowNum();
		int cols=sheet.getRow(0).getLastCellNum();
		Object[][] data= new Object[rows][cols];
		
		for(int i=0;i<rows;i++) {
			XSSFRow row=sheet.getRow(i+1);
			for(int j=0;j<cols;j++) {
				XSSFCell cell= row.getCell(j);
				CellType cellType= cell.getCellType();
				switch(cellType) {
				case STRING:
					data[i][j]=cell.getRichStringCellValue();
					break;
				case NUMERIC:
					data[i][j]=Integer.toString((int)cell.getNumericCellValue());
					break;
				case BOOLEAN:
					data[i][j]=cell.getBooleanCellValue();
					break;
				}
			}
				}
		return data;
				
			}
		
		public static String captureScreenShot(WebDriver driver,String testName) {
			File srcScreenshot=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			String destinationScreenshotPath=System.getProperty("user.dir")+"\\Screenshots\\"+testName+".png";
			try {
				FileHandler.copy(srcScreenshot, new File(destinationScreenshotPath));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return destinationScreenshotPath;
		}
	}

