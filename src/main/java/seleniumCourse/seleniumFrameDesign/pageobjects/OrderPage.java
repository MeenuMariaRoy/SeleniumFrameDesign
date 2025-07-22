package seleniumCourse.seleniumFrameDesign.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import selenium.AbstractComponents.AbstractComponent;

public class OrderPage extends AbstractComponent {
	
	WebDriver driver;

	public OrderPage(WebDriver driver1) {
		// TODO Auto-generated constructor stub
		super(driver1);
		this.driver = driver1;
		PageFactory.initElements(driver1, this);
	}
	
	@FindBy(css="tr td:nth-child(3)")
	List<WebElement> names;
	
	 public Boolean VerifyOrderDisplay(String productName)
	{
		Boolean match = names.stream().anyMatch(s -> s.getText().equalsIgnoreCase(productName));
		return match;
	}

}
