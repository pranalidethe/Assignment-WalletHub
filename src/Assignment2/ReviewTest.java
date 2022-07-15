package Assignment2;

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

public class ReviewTest
{
	static WebDriver driver;
	static ExtentTest test;
	static ExtentReports extent;

	public static void main(String[] args) throws Exception
	{
		//Generating the .html report
		extent = new ExtentReports(System.getProperty("user.dir") + "/test-output/ExtentReport_Q2.html",true);
		test = extent.startTest("Test Started!");

		//Setting up edge from WebDriverManager class so that we don't need to specify the local path every time.
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

		//Property file
		Properties prop = new Properties();			
		FileInputStream file = new FileInputStream(System.getProperty("user.dir")+"/test-data/Review.properties");
		prop.load(file);				

		//Navigating to wallethub.com
		String url = prop.getProperty("url");	
		driver.get(url);
		test.log(LogStatus.PASS,"Page navigated to "+ url);

		//Login
		ReviewBase base = PageFactory.initElements(driver, ReviewBase.class);
		String userName = prop.getProperty("emailId");		//'userName' stored as a variable, fetched from Review.properties file
		String passWord = prop.getProperty("password");		//'passWord' stored as a variable, fetched from Review.properties file
		base.login(userName,passWord);  
		
		//Navigating to the Test Insurance Company
		String urlReview = prop.getProperty("urlReview");	
		driver.get(urlReview);
		test.log(LogStatus.PASS,"Page navigated to "+ urlReview);
		
		//Giving the star rating and writing the review
		Assert.assertNotEquals(base.reviewStar(),"#ffffff");	//Comparing the filled color hex value with white hex value i.e. #ffffff 
		test.log(LogStatus.PASS,"Verified: The star highlighted has different color " + base.fourthStarFilled.getAttribute("fill"));		
		
		//Verify the 'Policy Dropdown' and 'Write a Review' section
		base.reviewPopup();
		test.log(LogStatus.PASS,"Verified: 'Policy Dropdown' and 'Write a Review' section");		
		
		//Verify the posted review in the profile section
		base.postedReview();
		Assert.assertTrue(base.userPostedReview.isDisplayed());	 
		test.log(LogStatus.PASS,"Verified: The review has been posted on the profile page for: " + base.userPostedReview.getText());		
		
		System.out.println("Execution Finished!! Please find the generated report at .\\WalletHub\\test-output");
		
		//Saving the report
		extent.endTest(test);
		extent.flush();
		extent.close();
		driver.quit();
	}
}