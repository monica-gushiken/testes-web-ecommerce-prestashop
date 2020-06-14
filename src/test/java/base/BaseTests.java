package base;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import pages.HomePage;

public class BaseTests {
	private static WebDriver driver;
	protected HomePage homePage;
	
	@BeforeAll
	public static void inicializar() {
		System.setProperty("webdriver.chrome.driver", "C:\\webdrivers\\chromedriver\\83\\chromedriver.exe");
		driver = new ChromeDriver();
		// todos os comandos findElement e findElements esperarão por até 10s para encontrar o(s) elemento(s)
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	@BeforeEach
	public void carregarPaginaInicial() {
		driver.get("https://marcelodebittencourt.com/demoprestashop/");
		homePage = new HomePage(driver);
	}
	
	@AfterAll
	public static void finalizar() {
		driver.quit();
	}
}
