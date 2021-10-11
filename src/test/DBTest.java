package test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class DBTest {

	private static WebElement check;

	@SuppressWarnings("unlikely-arg-type")
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		String URL = "jdbc:mysql://localhost:3306/ecommerce";
		String UserName = "root";
		String Password = "root";
		
		DBConnection dbObj = new DBConnection(URL, UserName, Password);
		Statement statement = dbObj.getConnection().createStatement();
		
		System.setProperty("webdriver.chrome.driver", "chromedriver");
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.saucedemo.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);
		
		ResultSet result = statement.executeQuery("select * from login_details");
		
		String username = "";
		String pass = "";
		
		while(result.next()) {
			username = result.getString("username");
			pass = result.getString("password");
		}
		
		WebElement email = driver.findElement(By.xpath("//input[@id='user-name']"));
		email.sendKeys(username);
		
		WebElement password = driver.findElement(By.cssSelector("input[id=password]"));
		password.sendKeys(pass);
		
		WebElement btn_sign = driver.findElement(By.cssSelector("input[id=login-button]"));
		btn_sign.click();
		
		WebElement check_status = driver.findElement(By.xpath("//span[text()='Products']"));
		
		if(check_status.equals("Products")) {
			
			System.out.println("Fail");
			
		}else {
			
			System.out.println("Success");
			
		}
		
		ResultSet result1 = statement.executeQuery("select * from eproduct");
		String Name = "";
		Double Price = 0.0;
		
		while(result1.next()) {
			Name = result1.getString("name");
			Price = result1.getDouble("price");
		}
		
		WebElement add = driver.findElement(By.xpath("//div[@class='inventory_item_name' and text()='" + Name + "']/following::button[1]"));
		add.click();
		
		WebElement cart = driver.findElement(By.cssSelector("a[class=shopping_cart_link]"));
		cart.click();
		
		WebElement price_cart = driver.findElement(By.xpath("//div[@class='inventory_item_price']"));
		
		if(price_cart.equals(Price)) {
			
			System.out.println("Price Changed " + Price);
			
		}else {
			
			System.out.println("Price is Same " + Price);
			
		}
		
		WebElement checkout = driver.findElement(By.cssSelector("button[id=checkout]"));
		checkout.click();
		
		WebElement FName = driver.findElement(By.cssSelector("input[id=first-name]"));
		FName.sendKeys("Abcde");
		
		WebElement LName = driver.findElement(By.cssSelector("input[id=last-name]"));
		LName.sendKeys("Vwxyz");
		
		WebElement ZipCode = driver.findElement(By.cssSelector("input[id=postal-code]"));
		ZipCode.sendKeys("123456");
		
		WebElement Continue = driver.findElement(By.cssSelector("input[id=continue]"));
		Continue.click();
		
		WebElement Finish = driver.findElement(By.cssSelector("button[id=finish]"));
		Finish.click();
		
		String Status = "Your order has been dispatched, and will arrive just as fast as the pony can get there!";
		
		check = driver.findElement(By.cssSelector("div[class=complete-text]"));
		
		if(Status.equals(check)) {
			
			System.out.println("Order Failed!");
			
		}else {
			
			System.out.println("Ordered Successfull");
			
		}
		
		driver.quit();

	}

}