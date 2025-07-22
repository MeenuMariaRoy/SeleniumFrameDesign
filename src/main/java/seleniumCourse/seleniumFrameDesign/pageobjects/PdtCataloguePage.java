package seleniumCourse.seleniumFrameDesign.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import selenium.AbstractComponents.AbstractComponent;

//instead of having all locators in single class file, we can use page object model design , where we split the locators based on the pages/functionalities

public class PdtCataloguePage extends AbstractComponent {
	// in the link https://rahulshettyacademy.com/client this is the login page

	WebDriver driver;

	public PdtCataloguePage(WebDriver driver1) {

		super(driver1);
		this.driver = driver1; // assigning the value of driver from standalonepom to local driver variable
		// we need to pass driver when we use pagefactory for that we use below step
		PageFactory.initElements(driver1, this);
	}

	// convert this step "List<WebElement> items =
	// driver.findElements(By.cssSelector(".mb-3"));" to page model, shown below
	// step 27 & 28

	@FindBy(css = ".mb-3")
	List<WebElement> items;

	@FindBy(css = ".ng-animating")
	WebElement loading;

	
	By pdt = By.cssSelector(".mb-3");
	By addtoCart = By.cssSelector(".card-body button:last-of-type");
	By toastMessage = By.cssSelector("#toast-container");

	// method to get the list of products and return them after waiting
	public List<WebElement> getPdtsList() {
		waitUntilElementAppears(pdt);
		return items;
	}

	public WebElement getPdtByName(String productName) {

		WebElement item = items.stream().filter(s -> s.findElement(By.cssSelector("b")).getText().equals(productName))
				.findFirst().orElse(null);
		return item;
	}

	public void addPdtToCart(String productName) throws InterruptedException {

		// item.findElement(By.cssSelector(".card-body button:last-of-type")).click();
		WebElement item = getPdtByName(productName);
		item.findElement(addtoCart).click();
		waitUntilElementAppears(toastMessage);
		waitUntilElementDisappears(loading);
		
	}

}
