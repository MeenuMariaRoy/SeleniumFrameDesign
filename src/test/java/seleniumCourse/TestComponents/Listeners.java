package seleniumCourse.TestComponents;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import selenium.resources.ExtentReporterNG;

public class Listeners extends BaseTest implements ITestListener {
	
	
	ExtentReports extent = ExtentReporterNG.getReportObject();
	ExtentTest test;
	//to avoid concurrency we are using thread safe approach
	ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();

	@Override
	public void onTestStart(ITestResult result) {
		// The control will come here before the start of any test
		//the result variable will have the test case name 
		test = extent.createTest(result.getMethod().getMethodName());		
		extentTest.set(test); //what this will do is, for every test object it will assign an unique thread id
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		//The control will come here on any successful test
		extentTest.get().log(Status.PASS, "Test Passed");	
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		extentTest.get().fail(result.getThrowable());		
		
		//since we need to pass driver in screenshot utility we have to extract the driver details from the result object
		try {
			driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		
		}
		
		
		
		//Take screenshot & attach the same to report, these are the next steps to be done
		//call the utility to take screenshot
		String filepath = null;
		try {
			filepath = takeScreenshot(result.getMethod().getMethodName(), driver);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		extentTest.get().addScreenCaptureFromPath(filepath, result.getMethod().getMethodName());
	
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		extent.flush();
		
	}
	
	

}
