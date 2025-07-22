package seleniumCourse.TestComponents;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

//There are times when few test fails due to the flakiness or problem in the application and not in our test, in that cases we can use 
//another component like listeners to retry the failed cases again
//For this we can use IRetryAnalyzer

public class Retry implements IRetryAnalyzer {

	int cnt = 0;
	int maxTry = 1; //this is the maximum no of times u need ur failed test to rerun
	
	@Override
	public boolean retry(ITestResult result) {
	//If we want system to retry then this method should return true, we can decide the no of times the test should rerun after failure
		if(cnt < maxTry)
		{
			cnt++; //we need to increment cnt everytime test is rerun
			return true;
		}
		
		return false;
	}

}
