package Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class GiveIndiaPage {

	public static void main(String[] args) throws InterruptedException {

		System.setProperty("webdriver.chrome.driver", "Driver/chromedriver"); // provide proper path of chrome exe file

		WebDriver driver = new ChromeDriver();

		JavascriptExecutor js = (JavascriptExecutor) driver;

// Test case 1 - Open the page https://en.wikipedia.org/wiki/...

		driver.navigate().to("https://en.wikipedia.org/wiki/Selenium");

// Test case 2 - Verify that the external links in “External links“ section work

		Thread.sleep(5000);
		WebElement updateButton = driver.findElement(By.xpath("//*[@id='mw-content-text']/div/ul[2]//a"));
		js.executeScript("arguments[0].scrollIntoView();", updateButton);
		List<WebElement> links = driver.findElements(By.xpath("//*[@id='mw-content-text']/div/ul[2]//a"));
			for(WebElement e: links){				
			String url = e.getAttribute("href");
			try {
				URL link = new URL(url);
				HttpURLConnection httpConn = (HttpURLConnection) link.openConnection();
				httpConn.connect();
				if (httpConn.getResponseCode() == 200) {
					System.out.println(url + " - " + httpConn.getResponseMessage());
				}else{
					System.out.println(url + " - " + httpConn.getResponseMessage() + " - " + httpConn.getResponseCode() + " - This Link Doesnt work");
				}
			} catch (Exception exp) {
			}
		}

// Test case 3 - Click on the “Oxygen” link on the Periodic table at the bottom of the page and Verify that it is a “featured article”

		driver.findElement(By.xpath("//a/span[text()='O']")).click();
		Thread.sleep(3000);

		WebElement verify = driver.findElement(By.xpath("//*[@id='mw-indicator-featured-star']//a"));
		System.out.println(verify.getAttribute("title"));
		if (verify.getAttribute("title").contains("This is a featured article.")) {
			System.out.println("'Oxygen' link is a featured link");
		} else {
			System.out.println("'Oxygen' link is NOT a featured link");
		}

		Thread.sleep(5000);

// Test case 4 - Count the number of pdf links in “Citations“

		List<WebElement> citationsPdf = driver.findElements(By.xpath("//span[text()='(PDF)']"));
		int count = citationsPdf.size();
		System.out.println("Number of PDF links is " + count);

// Test case 5 - In the search bar on top right enter “pluto” and verify that the 2 nd suggestion is “Plutonium”

		WebElement ele = driver.findElement(By.id("searchInput"));
		js.executeScript("arguments[0].scrollIntoView();", ele);
		ele.click();
		ele.sendKeys("Pluto");
		Thread.sleep(5000);
		WebElement text = driver.findElement(By.xpath("//a[2]/div"));
		System.out.println(text.getAttribute("textContent"));
		if (text.getAttribute("textContent").equals("Plutonium")) {
			System.out.println("Expected text is matching with 'Plutonium'");
		} else {
			System.out.println("Expected text is NOT matching with 'Plutonium'");
		}

		driver.close();

	}
}
