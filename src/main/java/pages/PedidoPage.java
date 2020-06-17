package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import util.Funcoes;

public class PedidoPage {
	WebDriver driver;
	private By mensagemPedidoConfirmado = By.cssSelector("h3.h1.card-title");
	private By email = By.cssSelector("#content-hook_order_confirmation p");
	private By totalProdutos = By.cssSelector("div.order-confirmation-table div.order-line div.row div.bold");
	private By totalComTaxa = By
			.cssSelector("div.order-confirmation-table tr.total-value.font-weight-bold td:nth-child(2)");
	private By metodoPagamento = By.cssSelector("#order-details > ul > li:nth-child(2)");

	public PedidoPage(WebDriver driver) {
		this.driver = driver;
	}

	public String obter_mensagemPedidoConfirmado() {
		return driver.findElement(mensagemPedidoConfirmado).getText();
	}

	public String obter_email() {
		String texto = driver.findElement(email).getText();
		texto = Funcoes.removeTexto(texto, "An email has been sent to the ");
		texto = Funcoes.removeTexto(texto, " address.");
		return texto;
	}

	public String obter_totalProdutos() {
		return driver.findElement(totalProdutos).getText();
	}

	public String obter_totalComTaxa() {
		return driver.findElement(totalComTaxa).getText();
	}

	public String obter_metodoPagamento() {
		return Funcoes.removeTexto(driver.findElement(metodoPagamento).getText(), "Payment method: Payments by ");
	}
}
