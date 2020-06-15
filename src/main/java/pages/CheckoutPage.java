package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage {
	private WebDriver driver;

	private By totalComTaxa = By.cssSelector("div.cart-total span.value");

	public CheckoutPage(WebDriver driver) {
		this.driver = driver;
	}

	public String obter_totalComTaxa() {
		return driver.findElement(totalComTaxa).getText();
	}
}
