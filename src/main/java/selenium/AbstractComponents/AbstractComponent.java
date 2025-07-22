package selenium.AbstractComponents;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import seleniumCourse.seleniumFrameDesign.pageobjects.CartPage;
import seleniumCourse.seleniumFrameDesign.pageobjects.OrderPage;

public class AbstractComponent {
	
//This class file will contain all the reusable wait, scrolls and things like that, so this class can be used in other classes
	//this class will act as parent and other classes will be children , by inheritance property
		
	WebDriver driver;
	//we need to pass driver to parent class as well, for that we can pass from child using super keyword
	
	public AbstractComponent(WebDriver driver1) {
		
		this.driver = driver1;
		PageFactory.initElements(driver1,this);
	}
	
	@FindBy(css = "[routerlink*='cart']")
	WebElement cartClick;
	

	@FindBy(css = "[routerlink*='myorders']")
	WebElement ordersClick;
	

	//method to wait for visibility
	public void waitUntilElementAppears(By findBy)
	{
		//we are not passing webelement, we are just passing the locator inside wait method ie; By FindBy
		
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
		
	}
	
	//method to wait for visibility
	public void waitUntilWebElementAppears(WebElement findBy)
	{
		//we are not passing webelement, we are just passing the locator inside wait method ie; By FindBy
		
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(findBy));
		
	}
	
	//method to wait for invisiblity of elements
	public void waitUntilElementDisappears(WebElement element) throws InterruptedException
	{
		Thread.sleep(1000);
		/*WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
		wait.until(ExpectedConditions.invisibilityOf(element)); */
		
	}
	
	public CartPage goToCartPage()
	{
		
		cartClick.click();
		return new CartPage(driver);
	}
	
	public OrderPage goToordersPage()
	{
		ordersClick.click();
		return new OrderPage(driver);
	}
	


}
