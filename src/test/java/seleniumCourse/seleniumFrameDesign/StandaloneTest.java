package seleniumCourse.seleniumFrameDesign;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class StandaloneTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		WebDriver driver = new ChromeDriver();
		//add implicit wait for 10 seconds
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		driver.get("https://rahulshettyacademy.com/client");
				
		String productName = "ZARA COAT 3";
		
		//Login by giving email, password and click on login button
		driver.findElement(By.id("userEmail")).sendKeys("barosingh@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("Tuktuk@123");
		driver.findElement(By.id("login")).click();
		
		//once logged in
		
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
		List<WebElement> items = driver.findElements(By.cssSelector(".mb-3")); 
		
	    WebElement item =items.stream().filter(s ->s.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);
	    //there might be multiple items with name ZARA COAT 3" so we are specifying to get the 1st one which comes and if u dont find then do nothing
	    
	    //in the item, there are 2 buttons to select the 2ns one we use css button:last-of-type
	    item.findElement(By.cssSelector(".card-body button:last-of-type")).click();
	    
	    //once add to cart is clicked, the page will load and message "Product added to Cart" will pop up, we need to capture that message
	    //first we need to add explicit wait for that element to show up
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
	    
	    //we have to wait until the loading animating button disappears, we can ask developer for the tag name
	    wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
	    
	    driver.findElement(By.cssSelector("[routerlink*='cart']")).click();
	    
	    //Check in the cart page if whatever products we added are in the cart page, eg: ZARA COAT 3, for that get the list of items in the cart
	    List<WebElement> cartItems = driver.findElements(By.cssSelector(".cartSection h3"));
  //we are not using filter coz filter method returns webelement, in our case we just need to know if the productname is present in cart, so we use anyMatch
	    Boolean match = cartItems.stream().anyMatch(s -> s.getText().equalsIgnoreCase(productName));
	    Assert.assertTrue(match); //if match returns false then our assertion will fail & we will know ZARA is not in cart
	    
	    driver.findElement(By.cssSelector(".totalRow button")).click();
	    
	    //in the checkout page input country
	    Actions a = new Actions(driver);
	    a.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), "india").build().perform();
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
	    //click on India from the options
	    driver.findElement(By.xpath("(//button[contains(@class,'ta-item')])[2]")).click();
	    
	    WebElement placeOrder = driver.findElement(By.cssSelector(".action__submit"));

        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript("arguments[0].click();", placeOrder);
	    
//	    JavascriptExecutor js = (JavascriptExecutor) driver;
//	    js.executeScript("window.scrollBy(0,600)");
	    
//	    //click on place order
//	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".action__submit")));
//	    driver.findElement(By.cssSelector(".action__submit")).click();
	    
	    //grab the thank you for ur order text
	    String confirmMsg = driver.findElement(By.cssSelector(".hero-primary")).getText();
	    Assert.assertTrue(confirmMsg.equalsIgnoreCase("Thankyou for the order."));
	    driver.quit();
	    
		
		

	}

}
