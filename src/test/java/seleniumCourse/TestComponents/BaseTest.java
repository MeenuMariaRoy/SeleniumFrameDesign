package seleniumCourse.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import seleniumCourse.seleniumFrameDesign.pageobjects.LandingPage;

public class BaseTest {
	// this class will contain all common details needed for the test cases like
	// maximising window, implicit wait etc..

	// create a driver variable globally

	public WebDriver driver;
	public LandingPage lp;

	public WebDriver initializeDriver() throws IOException {

		// create an object for properties class
		Properties prop = new Properties();
		// we need to convert file as input stream, for that we have a class
		// FileInputStream
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "//src//main//java//selenium//resources//GlobalData.properties");
		// .load method will help you load the properties file which is already created
		// with global values, the argument is input stream
		prop.load(fis);

//the below code checks if browser values is not null, then set the value passed via terminal that is system level, else
		// get the value from eclipse. we have used ternary operator
		String browsername = System.getProperty("browser") != null ? System.getProperty("browser")
				: prop.getProperty("browser");

		// now we can access the property we have to use, below code used when global
		// properties taken only from eclipse
		// String browsername = prop.getProperty("browser");

		if (browsername.contains("chrome")) {
			// give information to chrome to run in headless mode
			ChromeOptions options = new ChromeOptions();

			if (browsername.contains("headless")) {
				options.addArguments("--headless", "--disable-gpu", "--window-size=1440,900"); // if we pass the
																								// argument as headless
																								// chrome options class
																								// will know to run in
																								// headless mode
				driver = new ChromeDriver(options);
				// driver.manage().window().setSize(new Dimension(1440,900));
			} else {

				driver = new ChromeDriver();

			}

			// driver = new ChromeDriver();
			// when running in headless we need to make sure browser window is maximised so
			// we need to add code for that too
			// driver.manage().window().setSize(new Dimension(1440,900));
		}

		else if (browsername.equalsIgnoreCase("firefox")) {
			// add the code for firefox
			driver = new FirefoxDriver();
		}

		// add implicit wait for 10 seconds, this is common for all browsers so it can
		// be outside the if else
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		return driver;

	}

	@BeforeMethod(alwaysRun = true)
	public LandingPage launchApplication() throws IOException {
		driver = initializeDriver();
		lp = new LandingPage(driver); // create object for LandingPage class file and pass driver in it
		// call the goToUrl method to open the url
		lp.goToUrl();
		return lp;
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		driver.quit();
	}

	// adding the data reader method here so we can use it in StandalonePOMTest.java
	// without creating object
	public List<HashMap<String, String>> getJsonDataToMap(String filepath) throws IOException {
		// read the json file into a string
		String Jsoncontent = FileUtils.readFileToString(new File(filepath), StandardCharsets.UTF_8);

		// convert the above string to HashMap - for this we shud get new dependency
		// Jackson databind

		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(Jsoncontent,
				new TypeReference<List<HashMap<String, String>>>() {
				});
		return data;

		// data will have the hashmap in format {map,map}

	}

	// implement a section to take screenshot whenever a test fails

	public String takeScreenshot(String testCaseName, WebDriver driver) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
		File dest = new File(System.getProperty("user.dir") + "//reports//" + testCaseName + ".png");
		FileUtils.copyFile(src, dest);
		// return the path where the screenshot is saved in our local system
		return System.getProperty("user.dir") + "//reports//" + testCaseName + ".png";
	}

}
