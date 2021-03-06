package commonFunctionLibrary;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import utilities.PropertyFileUtil;

public class FuctionLibrary {
	
	static WebDriver driver;
	
	public static WebDriver startBrowser() throws Exception{
		
		if(PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("chrome")){
		
		System.setProperty("webdriver.chrome.driver","D:\\sridevi_82\\StockAccounting_DDF\\drivers\\chromedriver.exe");
		driver=new ChromeDriver();
		
		}else if(PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("firefox")){
	
		System.setProperty("webdriver.gecko.driver","D:\\sridevi_82\\StockAccounting_Hybrid\\drivers\\geckodriver.exe");
		driver=new FirefoxDriver();
		}else{
		
		System.setProperty("webdriver.ie.driver","D:\\sridevi_82\\StockAccounting_Hybrid\\drivers\\IEDriverServer.exe");
		driver=new InternetExplorerDriver();
			
		}
		return driver;
	
    }
	
	public static void openApplication(WebDriver driver) throws Exception{
		driver.get(PropertyFileUtil.getValueForKey("Url"));	
    	driver.manage().window().maximize();
    
    }
    
    public static void waitForElement(WebDriver driver,String locatortype,String locationvalue,String waittime){
    	
    	WebDriverWait mywait=new WebDriverWait(driver,Integer.parseInt(waittime));
    	if(locatortype.equalsIgnoreCase("id")){
    		mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locationvalue)));
    		
    	}
    	
    	else if(locatortype.equalsIgnoreCase("xpath")){
    		
    		mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locationvalue)));
       	
    	}
    	 else if(locatortype.equalsIgnoreCase("name")){
    	
    	mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(locationvalue)));
    } else
	{
	System.out.println("unable to locate for waitForElement method with "+locatortype);
	}			
}


	public static void typeAction(WebDriver driver,String locatortype,String locatorvalue,String testdata){
		
		if(locatortype.equalsIgnoreCase("id")){
			driver.findElement(By.id(locatorvalue)).clear();
			driver.findElement(By.id(locatorvalue)).sendKeys(testdata);
			
		}
		
		if(locatortype.equalsIgnoreCase("xpath")){
			driver.findElement(By.id(locatorvalue)).clear();
			driver.findElement(By.id(locatorvalue)).sendKeys(testdata);
		}
		
		if(locatortype.equalsIgnoreCase("name")){
			driver.findElement(By.id(locatorvalue)).clear();
			driver.findElement(By.id(locatorvalue)).sendKeys(testdata);
		}else
		{
			System.out.println("unable to locate for typeAction method with"+locatortype);
		
		}
	}
	

	public static void clickAction(WebDriver driver,String locatortype,String locatorvalue){
		
		if(locatortype.equalsIgnoreCase("id")){
			driver.findElement(By.id(locatorvalue)).click();
	
	}
		
		if(locatortype.equalsIgnoreCase("xpath")){
			driver.findElement(By.xpath(locatorvalue)).click();
		}
		
		if(locatortype.equalsIgnoreCase("name")){
			driver.findElement(By.id(locatorvalue)).click();
		}else
		{
			System.out.println("unable to locate for typeAction method with"+locatortype);
		}
	}
	
	public static void tableValidation(WebDriver driver,String column) throws Exception {
		
		FileReader fr=new FileReader("./CaptureData/suppnumber.txt");
		BufferedReader br=new BufferedReader(fr);
		
		String Exp_data=br.readLine();
		
		if(driver.findElement(By.id(PropertyFileUtil.getValueForKey("searchrextbox"))).isDisplayed()){
			Thread.sleep(5000);
			driver.findElement(By.id(PropertyFileUtil.getValueForKey("searchrextbox"))).sendKeys(Exp_data);
			driver.findElement(By.id(PropertyFileUtil.getValueForKey("searchbutton"))).click();
		}else{
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("searchpanelbutton"))).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("searchtextbox"))).sendKeys(Exp_data);
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("searchbutton"))).click();
		}
		
		WebElement table=driver.findElement(By.id(PropertyFileUtil.getValueForKey("suppliertable")));
		
		List<WebElement>rows=table.findElements(By.tagName("tr"));
		
		
		for(int i=1;i<rows.size();i++){
			String act_data=driver.findElement(By.xpath("//table[@id='tbl_a_supplierslist']/tbody/tr["+i+"]/td["+column+"]/div/span")).getText();
			Assert.assertEquals(Exp_data, act_data);
			System.out.println(act_data+"  "+Exp_data);
			break;
		}
		
	}
		
		public static void closeBrowser(WebDriver driver ){
			driver.close();
		}
		
		public static String generateData(){
			Date d=new Date();
			SimpleDateFormat sdf=new SimpleDateFormat("YYY-MM-DD-HH-MM-SS");
			String reqiredDate=sdf.format(d);
			return reqiredDate;
		}
		
		public static void captureData(WebDriver driver,String locatortytpe,
				String locatorvalue) throws Exception{
			
			String supplierdata="";
			
			if(locatortytpe.equalsIgnoreCase("id")){
				supplierdata=driver.findElement(By.id(locatorvalue)).getAttribute("value");
			}
			
			else if(locatortytpe.equalsIgnoreCase("xpath")){
				supplierdata=driver.findElement(By.xpath(locatorvalue)).getAttribute("value");
			}
			
			else if(locatortytpe.equalsIgnoreCase("name")){
				supplierdata=driver.findElement(By.name(locatorvalue)).getAttribute("value");
			}
			
			FileWriter fw=new FileWriter ("D:\\Batch82\\StockAccounting_Hybrid\\CaptureData\\suppnumber.txt");
			BufferedWriter bw=new BufferedWriter(fw);
			bw.write(supplierdata);
			bw.flush();
			bw.close();	
			
}
}
		