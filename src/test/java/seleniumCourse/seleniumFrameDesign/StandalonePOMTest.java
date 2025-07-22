package seleniumCourse.seleniumFrameDesign;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import seleniumCourse.TestComponents.BaseTest;
import seleniumCourse.seleniumFrameDesign.pageobjects.CartPage;
import seleniumCourse.seleniumFrameDesign.pageobjects.CheckoutPage;
import seleniumCourse.seleniumFrameDesign.pageobjects.ConfirmationPage;
import seleniumCourse.seleniumFrameDesign.pageobjects.OrderPage;
import seleniumCourse.seleniumFrameDesign.pageobjects.PdtCataloguePage;

public class StandalonePOMTest extends BaseTest {

	// declare this here so all tests in the class can access

//	String productName = "ZARA COAT 3";

	@Test(dataProvider = "getData", groups = {"Purchase"})

//whatever data we are passing using data provider we should catch that in arguments of the method in which it's going to get used	
	
	public void Standalone(HashMap<String,String> input) throws IOException, InterruptedException {

		// call the loginpage method from LandingPage class
		PdtCataloguePage pdc = lp.loginPage(input.get("email"), input.get("password"));

//instead of creating objects for each class in test, we are creating object for the upcoming class in current class & returning the same
		List<WebElement> items = pdc.getPdtsList(); // getting list of all products
		pdc.addPdtToCart(input.get("product")); // getting the product link based on pdt name and clicking add to cart
		CartPage cp = pdc.goToCartPage();

		// the delay in opening the cart page is due to the application/website code to
		// handle load

		Boolean match = cp.findMatch(input.get("product"));
		Assert.assertTrue(match);
		CheckoutPage chk = cp.clickOnCheckout();

		chk.selectCountry("india");
		ConfirmationPage lastpage = chk.submitOrder();

		String confirmMsg = lastpage.getConfirmationMessage();
		Assert.assertTrue(confirmMsg.equalsIgnoreCase("Thankyou for the order."));

	}

	// Verify if ZARA COAT 3 is available in the orders page, this test will have
	// dependency on the above standalone test

	@Test(dependsOnMethods = { "Standalone" })
	public void orderHistoryTest() {
		
		String productName = "ZARA COAT 3";
		PdtCataloguePage pdc = lp.loginPage("barosingh@gmail.com", "Tuktuk@123");
		OrderPage op = pdc.goToordersPage();
		Boolean value = op.VerifyOrderDisplay(productName);
		Assert.assertTrue(value);
	}
	
	//implement the data provider section
	
	@DataProvider
	public Object[][] getData() throws IOException
	{
		//create and return a 2D array of Object datatype , so we can pass 2 values in a set
		//one set of value is enclosed in one curly brace
		
		List<HashMap<String,String>> data = getJsonDataToMap(System.getProperty("user.dir") + "//src//test//java//seleniumFrameDesign//data//Purchase.json");		
		return new Object[][] {{data.get(0)}, {data.get(1)}};
		 
	}
	

	
 /*   Lecture 172 code
	@DataProvider
	public Object[][] getData()
	{
		//create and return a 2D array of Object datatype , so we can pass 2 values in a set
		//one set of value is enclosed in one curly brace
		
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("email","barosingh@gmail.com");
		map.put("password", "Tuktuk@123");
		map.put("product", "ZARA COAT 3");
		
		HashMap<String,String> map1 = new HashMap<String,String>();
		map1.put("email","resin@gmail.com");
		map1.put("password", "Resin@123");
		map1.put("product", "ADIDAS ORIGINAL");
				
		return new Object[][] {{map}, {map1} };
		
	}
	
	Lecture 171 method
	@DataProvider
	public Object[][] getData()
	{
		//create and return a 2D array of Object datatype , so we can pass 2 values in a set
		//one set of value is enclosed in one curly brace
				
	 return new Object[][] {{"barosingh@gmail.com", "Tuktuk@123", "ZARA COAT 3"}, {"resin@gmail.com", "Resin@123","ADIDAS ORIGINAL"} };
		
	}
*/
	

}
