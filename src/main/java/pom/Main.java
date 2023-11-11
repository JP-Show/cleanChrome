package pom;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v119.network.Network;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		String userName = System.getProperty("user.name");
		String userPath = "C:\\Users\\"+ userName +"\\AppData\\Local\\Google\\Chrome\\User Data\\";
		String currentDirectory = System.getProperty("user.dir");

		Path path = Paths.get("C:\\Users\\" + userName + "\\AppData\\Local\\Google\\Chrome\\User Data\\Profile 1\\");
		
		WebDriverManager.chromedriver().setup();
		//System.setProperty("webdriver.chrome.driver", currentDirectory + "\\chromedriver.exe");

		System.out.println(currentDirectory + "\\chromedriver.exe");
		
		ChromeOptions option = new ChromeOptions();
		option.addArguments("user-data-dir=" + userPath);
		
		WebDriver driver = null;
		DevTools devTools = null;
		boolean isValid = false;
		try {

			File file = new File(path.toString());
			File[] files = file.listFiles();
			for (File fil : files) {
				if (fil.toString().contains("Login Data") || fil.toString().contains("History")) {
					fil.delete();
					System.out.println("entrou e deletou");
				}
			}

			driver = new ChromeDriver(option);
			devTools = ((ChromeDriver) driver).getDevTools();
			isValid = true;
			
			devTools.createSession();

			Thread.sleep(2000);

			devTools.send(Network.clearBrowserCache());
			devTools.send(Network.clearBrowserCookies());

			Thread.sleep(2000);

			System.out.println("end");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if(isValid) {
				driver.close();
				devTools.close();
				driver.quit();
				System.out.println("Fechou");
			}
		}
	}

}
