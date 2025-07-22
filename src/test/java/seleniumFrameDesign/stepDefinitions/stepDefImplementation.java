package seleniumFrameDesign.stepDefinitions;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import seleniumCourse.TestComponents.BaseTest;
import seleniumCourse.seleniumFrameDesign.pageobjects.CartPage;
import seleniumCourse.seleniumFrameDesign.pageobjects.CheckoutPage;
import seleniumCourse.seleniumFrameDesign.pageobjects.ConfirmationPage;
import seleniumCourse.seleniumFrameDesign.pageobjects.LandingPage;
import seleniumCourse.seleniumFrameDesign.pageobjects.PdtCataloguePage;

public class stepDefImplementation extends BaseTest {
	//we have to write matching code to execute for all the steps written in feature file
	
	public LandingPage lp; //we are declaring globally so we can use it in all the methods
	public PdtCataloguePage pdc;
	public ConfirmationPage lastpage;
	
	//first we will write for the background Given statement
	@Given("I landed on Ecommerce Page")
	public void i_landed_on_Ecommerce_Page() throws IOException
	{
		//we are passing the message as argument for given so cucumber can match in the feature file and select the correct Given sentence
		//the naming standard for the method is to separate the message using underscore and somewhat match with the sentence
		
		lp = launchApplication();		
	}
	
	//in the below given name and password are parameters passed during runtime, so we need to replace those with regular expression ie:(.+)
	//when we use regular expression, we need to use ^ in the starting and $ in the ending to tell cucumber that we have a regular expression
	@Given ("^Logged in with username (.+) and password (.+)$")
	public void logged_in_with_username_and_password(String username, String password)
	{
		pdc = lp.loginPage(username, password);
	}
	
	
	@When ("^I add product (.+) to Cart$")
	public void i_add_product_to_cart(String productName) throws InterruptedException
	{
		List<WebElement> items = pdc.getPdtsList(); 
		pdc.addPdtToCart(productName);
	}
	
	//next line is And , it is a continuation of when so we can use the when tag
	@When ("^Checkout (.+) and submit the order$")
	public void checkout_and_submit_order(String productName)
	{
		CartPage cp = pdc.goToCartPage();
		Boolean match = cp.findMatch(productName);
		Assert.assertTrue(match);
		CheckoutPage chk = cp.clickOnCheckout();
		chk.selectCountry("india");
		lastpage = chk.submitOrder();
	}
	
	//for the Then step, we have data in the message that is dynamic but not taken from examples,so we have to define differently
	@Then ("{string} message is displayed on the ConfirmationPage")
	public void message_displayed_on_confirmationpage(String string)
	{
		String confirmMsg = lastpage.getConfirmationMessage();
		Assert.assertTrue(confirmMsg.equalsIgnoreCase(string));
		driver.quit();
	}
	
	@Then ("{string} message is displayed")
	public void message_is_displayed(String string)
	{
		Assert.assertEquals(string, lp.getErrorMsg());
		driver.quit();
	}
	
	

}
