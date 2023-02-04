package Resources;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer{

	int i=1;
	@Override
	public boolean retry(ITestResult result) {
		// TODO Auto-generated method stub
		
		while(i>=1) {
			System.out.println(i);
			i--;
			return true;
		}
		return false;
	}

}
