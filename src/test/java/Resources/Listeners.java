package Resources;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import Test.BaseTest;

public class Listeners extends BaseTest implements ITestListener{

	ExtentReports extentReports=Reports.extentReports();
	
	ExtentTest extentTest;
	
	ThreadLocal<ExtentTest> threadLocal=new ThreadLocal<ExtentTest>();
	
	
	//WebDriver driver;
	
	//BaseTest baseTest;
	
	@Override
	public void onTestFailure(ITestResult result) {

		threadLocal.get().log(Status.FAIL, "Failed"); 
		ITestContext context = result.getTestContext();
		 driver = (WebDriver) context.getAttribute("WebDriver");
		
		 if(driver!=null) {	
			String path = null;
			try {
				path = screenshot(result.getMethod().getMethodName(),driver);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			threadLocal.get().addScreenCaptureFromPath(System.getProperty("user.dir")+path, result.getMethod().getMethodName());
			LogEntries logs= driver.manage().logs().get(LogType.BROWSER);
			List<LogEntry> entryLogs =logs.getAll();
			entryLogs.stream().forEach(s->System.out.println(s));
		}
		 else {
			 String path = (String) context.getAttribute("path");
			 threadLocal.get().addScreenCaptureFromPath(System.getProperty("user.dir")+path, result.getMethod().getMethodName());
		 }
	}
	
	@Override
	public void onTestSuccess(ITestResult result) {
		
		threadLocal.get().log(Status.PASS, "Pass");
		 ITestContext context = result.getTestContext();
		 driver = (WebDriver) context.getAttribute("WebDriver");
		 if(driver!=null) {			
			String path = null;
			try {
				path = screenshot(result.getMethod().getMethodName(),driver);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			threadLocal.get().addScreenCaptureFromPath(System.getProperty("user.dir")+path, result.getMethod().getMethodName());
		}
		 else {
			 String path = null;
			 try {
				System.out.println(result.getTestClass().getRealClass().getField("WebDriver"));
			} catch (NoSuchFieldException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 threadLocal.get().addScreenCaptureFromPath(System.getProperty("user.dir")+path, result.getMethod().getMethodName());
		 }
		
	}
	
	@Override
	public void onTestStart(ITestResult result) {
		extentTest=extentReports.createTest(result.getMethod().getMethodName());
		threadLocal.set(extentTest);
		
	}
	
	@Override
	public void onFinish(ITestContext context) {
		extentReports.flush();
	}
}
