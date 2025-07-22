package seleniumCourse.seleniumFrameDesign;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import seleniumCourse.TestComponents.BaseTest;
import seleniumCourse.TestComponents.Retry;
import seleniumCourse.seleniumFrameDesign.pageobjects.CartPage;
import seleniumCourse.seleniumFrameDesign.pageobjects.PdtCataloguePage;

public class ErrorValidations extends BaseTest {
	
	//we can write a class and include multiple test cases , including negative validations

	@Test(groups= {"errorhandling"}, retryAnalyzer=Retry.class)
	public void errorMsg() {

		lp.loginPage("barosingh@gmail.com", "Tuktuk");
		Assert.assertEquals("Incorrect email or password.", lp.getErrorMsg());
	}

	@Test
	public void pdtErrorValidation() throws InterruptedException {
		
		
		String productName = "ZARA COAT 3";
		PdtCataloguePage pdc = lp.loginPage("tuktuk@gmail.com", "Tuktuk@123");
		List<WebElement> items = pdc.getPdtsList(); // getting list of all products
		pdc.addPdtToCart(productName); // getting the product link based on pdt name and clicking add to cart
		CartPage cp = pdc.goToCartPage();

		// the delay in opening the cart page is due to the application/website code to
		// handle load

		Boolean match = cp.findMatch("ZARA 33");
		Assert.assertFalse(match);
	}

}
