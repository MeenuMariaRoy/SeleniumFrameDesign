package seleniumCourse.seleniumFrameDesign.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import selenium.AbstractComponents.AbstractComponent;

//instead of having all locators in single class file, we can use page object model design , where we split the locators based on the pages/functionalities

public class LandingPage extends AbstractComponent{
	// in the link https://rahulshettyacademy.com/client this is the login page

	WebDriver driver;

	public LandingPage(WebDriver driver1) {
		
		super(driver1);
		this.driver = driver1; // assigning the value of driver from standalonepom to local driver variable
		// we need to pass driver when we use pagefactory for that we use below step
		PageFactory.initElements(driver1, this);
	}

	// email id locator
//	WebElement emailId =driver.findElement(By.id("userEmail")); //instead of writing this whole step we can use pageFactory method
	// same step which we gave above can be written using pageFactory as below

	@FindBy(id = "userEmail") // this is telling that find by id locator with value as "userEmail"
	WebElement emailId;

	// write same for password and login button

	@FindBy(id = "userPassword")
	WebElement password;

	@FindBy(id = "login")
	WebElement loginBtn;
	
	@FindBy(css="[class*='flyInOut']")
	WebElement errorMsg;
	

	// above 3 are webelements we have declared , and now we need to declare a
	// method, an action method to carry out all actions

	public PdtCataloguePage loginPage(String email, String pass) {
		// we shudnt pass any details in page class, we shud get values from main test
		emailId.sendKeys(email);
		password.sendKeys(pass);
		loginBtn.click();
		PdtCataloguePage pdc = new PdtCataloguePage(driver);
		return pdc;
	}
	
	public void goToUrl()
	{
		driver.get("https://rahulshettyacademy.com/client");
	}
	
	public String getErrorMsg()
	{
		waitUntilWebElementAppears(errorMsg);
		return errorMsg.getText();
	}

}
