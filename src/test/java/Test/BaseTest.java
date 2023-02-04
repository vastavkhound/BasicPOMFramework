package Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestContext;
import org.testng.TestRunner;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;

public class BaseTest {
	public WebDriver driver;

	@BeforeMethod(alwaysRun = true)
	public void initialize() throws IOException {
		String browserName = accessPropertiesFile("browser");
		switch (browserName) {
		case "edge":
			System.setProperty("webderiver.edge.driver", "E:\\Applications\\edgedriver_win64\\msedgedriver.exe");
			driver = new EdgeDriver();
			break;
		case "chrome":
			System.setProperty("webderiver.chrome.driver", "E:\\Applications\\chromedriver_win32\\chromedriver.exe");
			driver = new ChromeDriver();
			break;
		case "firefox":
			System.setProperty("webderiver.gecko.driver", "");
			driver = new FirefoxDriver();
			break;
		default:
			break;
		}

		driver.get("https://rahulshettyacademy.com/client");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
	}

	@BeforeMethod(dependsOnMethods = { "initialize" })
	public void testContext(ITestContext context) {
		context.setAttribute("WebDriver", this.driver);
	}

	@AfterMethod(alwaysRun = true)
	public void closeBrowser() {
		driver.quit();
	}

	public String accessPropertiesFile(String key) throws IOException {
		File file = new File(System.getProperty("user.dir") + "\\src\\test\\java\\Resources\\detail.properties"); // represents
																													// an
																													// actual
																													// file(path)
																													// as
																													// a
																													// class
		FileInputStream fileInputStream = new FileInputStream(file);// reads and retrieves data from file as a byte
																	// stream
		Properties properties = new Properties();// represents the properties(key-value pair)
		properties.load(fileInputStream);// reads incoming byte stream as a property key value pair
		String value = properties.getProperty(key);
		return value;
		// set browser from windows command prompt
	}

	public List<HashMap<String, String>> accessJSONFile(String path) throws IOException {
		File file = new File(path);
		String contents = FileUtils.readFileToString(file, "UTF-8");
		ObjectMapper objectMapper = new ObjectMapper();
		List<HashMap<String, String>> data = objectMapper.readValue(contents,
				new TypeReference<List<HashMap<String, String>>>() {
				});

		return data;
	}

	public String screenshot(String name, WebDriver driver) throws IOException {
		File file = new File("src\\test\\java\\Resources\\" + name + ".png");
		TakesScreenshot screenshot = (TakesScreenshot) driver;
		File scr = screenshot.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scr, file);
		return "src\\test\\java\\Resources\\" + name + ".png";
	}

	public List<String> accessExcelFile(String columnHeader) throws IOException {
		List<String> products = new ArrayList<>();
		File file = new File("C:\\Users\\vasta\\OneDrive\\Desktop\\Book1.xlsx");
		FileInputStream fis = new FileInputStream(file);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		int numberOfSheets = workbook.getNumberOfSheets();
		XSSFSheet sheet = null;
		for (int i = 0; i < numberOfSheets; i++) {
			if (workbook.getSheetName(i).equalsIgnoreCase("sheet1")) {
				sheet = workbook.getSheetAt(i);
			}
		}
		int count = sheet.getLastRowNum() - sheet.getFirstRowNum();
		int rowNumber = 0;
		int cellNumber = 0;
		boolean k = false;
		for (int i = 0; i < count + 1; i++) {
			if (sheet.getRow(i) == null) {
				continue;
			} else {
				int cellCount = sheet.getRow(i).getLastCellNum();
				for (int j = 0; j < cellCount; j++) {
					if (sheet.getRow(i).getCell(j) == null) {
						continue;
					} else {
						if (sheet.getRow(i).getCell(j).getStringCellValue().contains(columnHeader)) {
							rowNumber = i;
							cellNumber = j;
							k = true;
							break;
						}
					}
				}
			}
			if (k == true) {
				break;
			}
		}
		Iterator<Row> rowIt = sheet.rowIterator();
		while (rowIt.hasNext()) {
			Row row = rowIt.next();
			if (row.equals(sheet.getRow(rowNumber))) {
				int i = row.getRowNum() + 1;
				while (i <= sheet.getLastRowNum()) {
					products.add(sheet.getRow(i).getCell(cellNumber).getStringCellValue());
					i++;
				}
				break;
			}
		}
		return products;
	}

	public void insertIntoExcel(String rowHeader,String insertItem,int noOfProducts) throws IOException, InterruptedException {		
		File file = new File("C:\\Users\\vasta\\OneDrive\\Desktop\\Book1.xlsx");
		FileInputStream fis = new FileInputStream(file);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = null;
		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
			if (workbook.getSheetName(i).equals("Sheet1")) {
				sheet = workbook.getSheetAt(i);
			}
		}
		int rowNum = 0;
		int cellNum=0;
		Iterator<Row> rowIterator = sheet.rowIterator();
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				if (cell == null) {
					continue;
				} else {
					if (cell.getStringCellValue().equals(rowHeader)) {	
						rowNum=row.getRowNum();
						cellNum=cell.getColumnIndex();
						
					}
				}
			}
		}
		int i=1;
		while(i<=noOfProducts) {
			if(sheet.getRow(rowNum).getCell(cellNum+i) == null) {
				sheet.getRow(rowNum).createCell(cellNum+i).setCellValue(insertItem);
				break;
			}
			i++;
		}
		
		FileOutputStream fos=new FileOutputStream(file);
		workbook.write(fos);
		workbook.close();
		fis.close();
		fos.close();

	}

}
