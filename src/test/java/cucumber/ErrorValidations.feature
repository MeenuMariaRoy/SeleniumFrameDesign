@tag
Feature: Error Validations 
Error validations from rahulshettyacademy/client website

@ErrorValidation
Scenario Outline:
Given I landed on Ecommerce Page
When Logged in with username <name> and password <password>
Then "Incorrect email or password." message is displayed

Examples:
|	name				|	password	|
|barosingh@gmail.com	|	Tuktuk@1	|