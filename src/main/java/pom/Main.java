package pom;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v119.network.Network;

//import io.github.bonigarcia.wdm.WebDriverManager;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		String userName = System.getProperty("user.name");
		String userPath = "C:\\Users\\" + userName + "\\AppData\\Local\\Google\\Chrome\\User Data\\";
		String currentDirectory = System.getProperty("user.dir");

		Path path2 = Paths.get("C:\\Users\\" + userName + "\\AppData\\Local\\Google\\Chrome\\User Data\\");

		// WebDriverManager.chromedriver().setup();
		System.setProperty("webdriver.chrome.driver", currentDirectory + "\\chromedriver.exe");

		ChromeOptions option = new ChromeOptions();
		option.addArguments("user-data-dir=" + userPath);

		WebDriver driver = null;
		DevTools devTools = null;
		boolean isValid = false;
		try {

			File file = new File(path2.toString());
			File[] files = file.listFiles();
			for (File fil : files) {
				if(fil.toString().contains("Profile ")) {
					deleteRecur(fil);
				}
				if (fil.toString().contains("Local State")) {
					System.out.println("entrou no "+ fil.getName() +" e deletou " + fil.delete());
				}
				if (fil.toString().contains("Default")) {
					File file2 = new File(path2.toString() + "\\" + fil.getName());
					for (File fil2 : file2.listFiles()) {
						if (fil2.toString().contains("History") || fil2.toString().contains("Login Data")) {
							System.out.println("entrou no "+ fil2.getName() +" e deletou " + fil2.delete());
						}
					}
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
			if (isValid) {
				driver.close();
				devTools.close();
				driver.quit();
				System.out.println("Fechou");
			}
		}
	}
	public static void deleteRecur(File file) {
		if(file.isDirectory()) {
			File[] files = file.listFiles();
			if(file != null) {
				for(File f : files) {
					deleteRecur(f);
				}
			}
		}
		boolean deleted = file.delete();
		if(deleted) {
			System.out.println("deletou");
		}else {
			System.out.println("não deletou");
		}
	}
}
