package EC_Utilities;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;


public class ExtentReport  extends TestListenerAdapter {


	public ExtentSparkReporter extentSparkReporter;
	public ExtentReports extentReports;
	public ExtentTest extentTest;


	public void onStart(ITestContext testContext) {

		String timestamp = new SimpleDateFormat("yyyy.mm.dd.hh.mm.ss").format(new Date());
		String reportName = "OrderProduct_Report" + timestamp + ".html";

		extentSparkReporter=new ExtentSparkReporter(".//Reports//"+reportName);
		extentSparkReporter.config().setDocumentTitle("Automation Report");
		extentSparkReporter.config().setReportName("API Testing Report");
		extentSparkReporter.config().setTheme(Theme.DARK);
		extentSparkReporter.config().setTimelineEnabled(true);

		extentReports=new ExtentReports();
		extentReports.attachReporter(extentSparkReporter);
		//add system environment info to extent report,so it is common to all reporter

		extentReports.setSystemInfo("OS", System.getProperty("os.name"));
		extentReports.setSystemInfo("Java Varsion", System.getProperty("java.version"));
		extentReports.setSystemInfo("APP URL", "https://rahulshettyacademy.com/");
		
		/*
     	//to add browser info,we need Capabilities interface

		Capabilities capabilities=((RemoteWebDriver)TestBase.driver).getCapabilities();
		extentReports.setSystemInfo("Browser Name", capabilities.getBrowserName());
		extentReports.setSystemInfo("Browser Varsion",capabilities.getBrowserVersion());
		*/
		
		//extentReports.setSystemInfo("Host-Name", "Localhost");
		extentReports.setSystemInfo("Environment", "QA");
		extentReports.setSystemInfo("User", "Aniket");

	}



	public void onTestSuccess(ITestResult tr) {

		extentTest=extentReports.createTest(tr.getName());//creating new entry
		extentTest.log(Status.PASS,"Passed Test Case is "+tr.getName());//adding log details
	}


	public void onTestFailure(ITestResult tr) {

		extentTest=extentReports.createTest(tr.getName());
		extentTest.log(Status.FAIL,"Failed Test Case is "+tr.getName());
		extentTest.log(Status.FAIL,"Test Case is Failed because "+tr.getThrowable().getMessage());
		
	}


	public void onTestSkipped(ITestResult tr) {

		extentTest=extentReports.createTest(tr.getName());
		extentTest.log(Status.SKIP,"Skipped Test Case is "+tr.getName());
		extentTest.log(Status.SKIP,"Test Case is Skipped because "+tr.getThrowable().getMessage());

	}

	public void onFinish(ITestContext testContext) {
		extentReports.flush();
	}

}



