package Assignment2;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ReviewBase
{
	WebDriver driver;

	@FindBy(xpath="//a[@class='login']")
	public WebElement loginButton;

	@FindBy(id="email")
	public WebElement userName;

	@FindBy(id="password")
	public WebElement passWord;

	@FindBy(xpath="//button[@type='button']//span")
	public WebElement loginCTA;
	
	@FindBy(xpath="//nav//span[text()='Devesh']")
	public WebElement userProfile;

	@FindBy(xpath="(//span[text()='Reviews'])[1]")
	public WebElement reviewsTab;

	@FindBy(xpath="//*[name()='svg'][@aria-label='4 star rating'][@aria-hidden='false']")
	public WebElement fourthStar;

	@FindBy(xpath="(//*[name()='svg'][@aria-label='4 star rating'][@aria-hidden='false'])[2]//*[@fill][1]")
	public WebElement fourthStarFilled;

	@FindBy(xpath="//*[@class='dropdown second']")
	public WebElement dropdown;

	@FindBy(xpath="//div[@class='android textarea-user']//textarea")
	public WebElement writeReview;
	
	@FindBy(xpath="//li[text()='Health Insurance']")
	public WebElement healthInsurance;
	
	@FindBy(xpath="//div[contains(.,'Submit')][@role='button']")
	public WebElement submitCTA;
	
	@FindBy(xpath="//a[@href][text()='Test Insurance Company']")
	public WebElement userPostedReview;

	//Constructor to initialize the driver
	public ReviewBase(WebDriver driver)
	{
		this.driver = driver;
	}

	//Login method
	public void login(String uname, String passwd) throws Exception
	{
		loginButton.click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		userName.sendKeys(uname);
		passWord.sendKeys(passwd);
		loginCTA.click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	//Posting the status message and returning the same
	public String reviewStar() throws Exception
	{
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		reviewsTab.click();
		
		WebDriverWait wait= new WebDriverWait(driver, 15);
		wait.until(ExpectedConditions.elementToBeClickable(fourthStar));
		
		Actions act = new Actions(driver);
		act.moveToElement(fourthStar).build().perform();
		fourthStar.click();
		
		String color = fourthStarFilled.getAttribute("fill");
		return color;
	}
	
	public void reviewPopup() throws Exception
	{
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		dropdown.click();
		Thread.sleep(2000);
		healthInsurance.click();
				
		String review = "This is a review for Wallet Hub. ";
		for(int i=0;i<5;i++)
		{
			writeReview.sendKeys(review);
		}
		
		submitCTA.click();
		Thread.sleep(5000);
	}
	
	//Wait for the success message
	public void postedReview()
	{
		driver.get("https://wallethub.com/profile/68740014i");
		WebDriverWait wait= new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOf(userPostedReview));
	}
}