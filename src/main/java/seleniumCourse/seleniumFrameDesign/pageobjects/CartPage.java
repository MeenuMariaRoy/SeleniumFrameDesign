package seleniumCourse.seleniumFrameDesign.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import selenium.AbstractComponents.AbstractComponent;

public class CartPage extends AbstractComponent {
	
	WebDriver driver;

	public CartPage(WebDriver driver1) {
		super(driver1);
		this.driver = driver1;
		PageFactory.initElements(driver1, this);
	}
	
	@FindBy(css=".cartSection h3")
	List<WebElement> cartItems;
	
	@FindBy(css=".totalRow button")
	WebElement chkout;
	
	public Boolean findMatch(String productName)
	{
		Boolean match = cartItems.stream().anyMatch(s -> s.getText().equalsIgnoreCase(productName));
		return match;
	}
	
	public CheckoutPage clickOnCheckout()
	{
		chkout.click();
		return new CheckoutPage(driver);
	}
	
	

}
