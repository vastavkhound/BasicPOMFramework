package Resources;

import java.io.File;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class Reports {
	
	public static ExtentReports extentReports() {
		File file=new File("src\\test\\java\\Results\\testreport.html");
		ExtentSparkReporter extentSparkReporter=new ExtentSparkReporter(file);
		extentSparkReporter.config().setDocumentTitle("Test Report");
		extentSparkReporter.config().setReportName("QA Report");
		
		ExtentReports extentReports=new ExtentReports();
		extentReports.attachReporter(extentSparkReporter);
		extentReports.setSystemInfo("Tester", "QA Team");
		return extentReports;
	}

}
