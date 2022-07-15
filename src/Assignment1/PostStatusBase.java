package Assignment1;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PostStatusBase
{
	WebDriver driver;

	@FindBy(xpath="//input[@type='text']")
	public WebElement userName;

	@FindBy(xpath="//input[@type='password']")
	public WebElement passWord;

	@FindBy(xpath="//button[@type='submit']")
	public WebElement loginButton;
	
	@FindBy(xpath="//*[contains(text(),'on your mind')]")
	public WebElement createPost;

	@FindBy(xpath="(//*[contains(text(),'WalletHub Tester')])[1]")
	public WebElement myProfile;
	
	@FindBy(xpath="(//*[contains(@id,'placeholder')])[2]")
	public WebElement postPopup;
	
	@FindBy(xpath="//div[@*='Post']")
	public WebElement postButton;
	
	@FindBy(xpath="//div[@*='ProfileTimeline']//*[text()='Hello World']")
	public WebElement postedMessage;
	
	//Constructor to initialize the driver
	public PostStatusBase(WebDriver driver)
	{
		this.driver = driver;
	}

	//Login method
	public void login(String uname, String passwd)
	{
		userName.sendKeys(uname);
		passWord.sendKeys(passwd);
		loginButton.click();
	}
	
	//Posting the status message and returning the same
	public String statusMessage() throws Exception
	{
		WebDriverWait wait= new WebDriverWait(driver, 15);
		wait.until(ExpectedConditions.elementToBeClickable(myProfile));
		myProfile.click();

		wait.until(ExpectedConditions.elementToBeClickable(createPost));
		createPost.click();
		
		Thread.sleep(3000);
		Actions act = new Actions(driver);
		act.click(postPopup).build().perform();
		String message = "Hello World";
		act.sendKeys(message).build().perform();
		
		postButton.click();
		
		String postedMsg = postedMessage.getText();
		return postedMsg;
	}
}