@tag
Feature: Purchase the order from Ecommerce Website
Purchasing order from rahulshettyacademy/client website

Background:
Given I landed on Ecommerce Page


@Regression
Scenario Outline:
Given Logged in with username <name> and password <password>
When I add product <productName> to Cart
And Checkout <productName> and submit the order
Then "Thankyou for the order." message is displayed on the ConfirmationPage

Examples:
|	name				|	password	|	productName	|
|barosingh@gmail.com	|	Tuktuk@123	|	ZARA COAT 3	|