package Assignment1;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.github.bonigarcia.wdm.WebDriverManager;

public class PostStatusTest
{
	static WebDriver driver;
	static ExtentTest test;
	static ExtentReports extent;

	public static void main(String[] args) throws Exception
	{
		//Generating the .html report
		extent = new ExtentReports(System.getProperty("user.dir") + "/test-output/ExtentReport_Q1.html",true);
		test = extent.startTest("Test Started!");

		//Setting up chrome from WebDriverManager class so that it doesn't need to instantiate every time
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);

		//Property file
		Properties prop = new Properties();			
		FileInputStream file = new FileInputStream(System.getProperty("user.dir")+"/test-data/PostStatus.properties");
		prop.load(file);				

		//Navigating to facebook.com
		String url = prop.getProperty("url");	
		driver.get(url);
		test.log(LogStatus.PASS,"Page navigated to "+ url);

		//Login
		PostStatusBase base = PageFactory.initElements(driver, PostStatusBase.class);
		String userName = prop.getProperty("emailId");		//'userName' stored as a variable, fetched from PostStatus.properties file
		String passWord = prop.getProperty("password");		//'passWord' stored as a variable, fetched from PostStatus.properties file
		base.login(userName,passWord);  
		test.log(LogStatus.PASS,"User logged in is: " + base.myProfile.getText());

		//Posting Facebook status and verifying the same
		Assert.assertEquals(base.statusMessage(),"Hello World");
		test.log(LogStatus.PASS,"Verified: Message posted is " + base.postedMessage.getText());

		System.out.println("Message posted is " + base.postedMessage.getText());
		System.out.println("Execution Finished!! Please find the generated report at .\\WalletHub\\test-output");

		//Saving the report
		extent.endTest(test);
		extent.flush();
		extent.close();
		driver.quit();
	}
}