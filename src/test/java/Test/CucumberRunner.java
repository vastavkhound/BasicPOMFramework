package Test;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;//,"src\\test\\java\\Resources\\CartPageTest.feature"

@CucumberOptions(features = {"src\\test\\java\\Resources\\ProductPageTest.feature"},glue = {"Test"},monochrome = true)
public class CucumberRunner extends AbstractTestNGCucumberTests{
	

}
