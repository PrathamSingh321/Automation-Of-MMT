package testNG.testNG;


import java.time.Duration;

import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;


//@Listeners(testNG.testNG.ExtentReportManager.class)
public class MakemyTrip {
	WebDriver driver;
	@BeforeTest
	@Parameters({"browser"})
	 void open_MMT(String br) throws InterruptedException {
		if(br.equals("chrome"))
		{
		WebDriverManager.chromedriver().setup();
		driver=new ChromeDriver();
		}
		else if(br.equals("edge"))
		{
			WebDriverManager.edgedriver().setup();
			driver=new EdgeDriver();
		}
		else {
			WebDriverManager.firefoxdriver().setup();
			driver=new FirefoxDriver();
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://www.makemytrip.com/");
		driver.manage().window().maximize(); 
		try {
			driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@title,'notif')]")));
		
		Thread.sleep(2000);
		WebElement close_popup = driver.findElement(By.cssSelector("a.close"));
		
		close_popup.click();
		} catch(Exception e) {
			e.printStackTrace();
		}
		 
		
		
	}
	@Test()
	void FROM() throws InterruptedException {
		WebElement From=driver.findElement(By.xpath("//input[@data-cy='fromCity']"));
		From.click();
		WebElement fromData = driver.findElement(By.xpath("//input[@aria-controls='react-autowhatever-1']"));
		fromData.sendKeys("Lucknow");
		//Thread.sleep(3000);
		WebElement LKO=driver.findElement(By.xpath("//p[text()='Lucknow, India']"));
		LKO.click();
		//WebElement kolkata=driver.findElement(By.xpath("//p[text()='Kolkata, India']"));
		//kolkata.click();
		WebElement To=driver.findElement(By.xpath("//label[@for='toCity']"));
		To.click();
		WebElement ToData=driver.findElement(By.xpath("//input[@aria-controls='react-autowhatever-1']"));
		ToData.sendKeys("Pune");
		WebElement PUNE=driver.findElement(By.xpath("//p[text()='Pune, India']"));
		PUNE.click();
		Thread.sleep(4000);
	}

		
		
		

	@Test(priority=1)
	void departureDate_selection() throws InterruptedException {
	String month="March 2024";
	String day="20";
	while(true) {
	String text=driver.findElement(By.xpath("(//div[@class='DayPicker-Caption'])")).getText();
	if (text.equalsIgnoreCase(month)){	
		
		
		break;
	}
	else{
		driver.findElement(By.xpath("//span[@aria-label='Next Month']")).click(); 
	}
	
	}
	driver.findElement(By.xpath("(//p[contains(text(),'"+ day + "')])[1]")).click();
	
		Thread.sleep(3000);
	}
	
	
	@Test(priority=2)
	void Return_Date() throws InterruptedException {
		driver.findElement(By.xpath("//span[text()='Return']")).click();
		String month1= "April2024";
		String day1="11";
		while(true) {
			String text1=driver.findElement(By.xpath("(//div[@class='DayPicker-Caption'])[2]")).getText();
			System.out.println(text1);
			if (text1.equalsIgnoreCase(month1)) {
				break;
			}
			else 
			{
				driver.findElement(By.xpath("//span[@aria-label='Next Month']")).click(); 
			}
		}
		driver.findElement(By.xpath("(//p[text()='"+day1+"'])[2]")).click();
		driver.findElement(By.xpath("//a[text()='Search']")).click();
	}
	@Test(priority=3, dependsOnMethods= {"Return_Date"})
	void Details() throws InterruptedException {
		WebElement Okay=driver.findElement(By.xpath("//button[text()='OKAY, GOT IT!']"));
		Thread.sleep(4000);
		WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.visibilityOf(Okay));
		Okay.click();
		WebElement checkbox=driver.findElement(By.xpath("(//p[text()=' Non Stop   '])[1]"));
		checkbox.click();
		JavascriptExecutor js=(JavascriptExecutor)driver;
		WebElement upto=driver.findElement(By.xpath("//div[@class='listingCard ']//span[text()='Air India Express ']"));
		js.executeScript("arguments[0].scrollIntoView(true)",upto);
		Thread.sleep(4000);
		driver.findElement(By.xpath("//button[text()='Book Now']")).click();
		Thread.sleep(4000);
		driver.findElement(By.xpath("(//button[ contains(@class,'buttonPrimary') ])[2]")).click();
		try{driver.findElement(By.xpath("//button[text()='BOOK NOW']")).click();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	
	}
	@Test(priority=4, dependsOnMethods= {"Details","Return_Date"})
	void window_Swt() throws InterruptedException {
		
		Set<String> windowID=driver.getWindowHandles();
		Iterator <String> itr= windowID.iterator();
		
		String parentWindow=itr.next();
		String childWindow=itr.next();
		
		driver.switchTo().window(childWindow);
		
		JavascriptExecutor js=(JavascriptExecutor)driver;
		WebElement scroll=driver.findElement(By.xpath("//button[text()='LOGIN NOW']"));
		js.executeScript("arguments[0].scrollIntoView(true)",scroll);
		Thread.sleep(4000);
		driver.findElement(By.xpath("//button[text()='+ ADD NEW ADULT']")).click();
		driver.findElement(By.xpath("//div[@class='adultList ']//input[@type='checkbox']")).click();
}
	@Test(priority=5,dataProvider="dp")
	void coust_details(String Fname, String Lname) {
		driver.findElement(By.xpath("//input[@placeholder='First & Middle Name']")).sendKeys(Fname);
		driver.findElement(By.xpath("//input[@placeholder='Last Name']")).sendKeys(Lname);
		
		
	}
@DataProvider(name="dp")
String [][]detailsData(){
	String data[][]= {
			{"Pratham","Singh"}			
	};
	return data;	
}
@Test(priority=6,dataProvider="dp2")
void per_details(String MobNo, String Email) {
	driver.findElement(By.xpath("//input[@placeholder='Mobile No(Optional)']")).sendKeys(MobNo);
	driver.findElement(By.xpath("//input[@placeholder='Email(Optional)']")).sendKeys(Email);
	
}
@DataProvider(name="dp2")
String [][]newData(){
	String data[][]= {
			{"6388504467","prathamsingh@gmail.com"}			
	};
	return data;
}

@Test(priority=7)
void otheraction() throws InterruptedException {
	Thread.sleep(4000);
	Actions action = new Actions(driver);
	WebElement Gender= driver.findElement(By.xpath("//span[text()='MALE']"));
	Thread.sleep(4000);
	/*action.click(Gender).build().perform();*/
	
}
}


