package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage {
	private WebDriver driver;

	private By totalComTaxa = By.cssSelector("div.cart-total span.value");
	private By nomeCliente = By.cssSelector("div.address");
	private By botaoContinueAddress = By.name("confirm-addresses");
	private By valorEntrega = By.cssSelector("span.carrier-price");
	private By botaoContinueShipping = By.name("confirmDeliveryOption");
	private By radioPayByCheck = By.id("payment-option-1");
	private By amountPayByCheck = By
			.cssSelector("#payment-option-1-additional-information > section > dl > dd:nth-child(2)");
	private By checkboxIAgree = By.id("conditions_to_approve[terms-and-conditions]");
	private By botaoOrder = By.cssSelector("button.btn.btn-primary.center-block");

	public CheckoutPage(WebDriver driver) {
		this.driver = driver;
	}

	public String obter_totalComTaxa() {
		return driver.findElement(totalComTaxa).getText();
	}

	public String obter_nomeCliente() {
		return driver.findElement(nomeCliente).getText();
	}

	public void clicar_botaoContinueAddress() {
		driver.findElement(botaoContinueAddress).click();
	}

	public String obter_valorEntrega() {
		return driver.findElement(valorEntrega).getText();
	}

	public void clicar_botaoContinueShipping() {
		driver.findElement(botaoContinueShipping).click();
	}

	public void selecionar_radioPayByCheck() {
		driver.findElement(radioPayByCheck).click();
	}

	public String obter_amountPayByCheck() {
		return driver.findElement(amountPayByCheck).getText();
	}

	public void selecionar_checkboxIAgree() {
		driver.findElement(checkboxIAgree).click();
	}

	public boolean estaSelecionadoCheckboxIAgree() {
		return driver.findElement(checkboxIAgree).isSelected();
	}

	public PedidoPage clicar_botaoOrder() {
		driver.findElement(botaoOrder).click();
		return new PedidoPage(driver);
	}
}
