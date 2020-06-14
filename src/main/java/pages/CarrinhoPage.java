package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CarrinhoPage {
	private WebDriver driver;
	private By nomeProduto = By.cssSelector("div.product-line-info a");
	private By precoProduto = By.cssSelector("span.price");
	private By tamanhoProduto = By
			.xpath("//div[contains(@class,'product-line-grid-body')]//div[3]//span[contains(@class,'value')]");
	private By corProduto = By
			.xpath("//div[contains(@class,'product-line-grid-body')]//div[4]//span[contains(@class,'value')]");
	private By input_quantidadeProduto = By.cssSelector("input.js-cart-line-product-quantity form-control");
	private By subtotalProduto = By.cssSelector("span.product-price strong");
	private By numeroItensTotal = By.cssSelector("span.js-subtotal");
	private By subtotalTotal = By.cssSelector("#cart-subtotal-products span.value");
	private By valorEntrega = By.cssSelector("#cart-subtotal-shipping span.value");
	private By totalSemTaxa = By.cssSelector("div.cart-summary-totals div.cart-summary-line:nth-child(1) span.value");
	private By totalComTaxa = By.cssSelector("div.cart-summary-totals div.cart-summary-line:nth-child(2) span.value");
	private By taxasTotal = By.cssSelector("div.cart-summary-totals div.cart-summary-line:nth-child(3) span.value");

	public CarrinhoPage(WebDriver driver) {
		this.driver = driver;
	}
		
	public String obter_nomeProduto() {
		return driver.findElement(nomeProduto).getText();
	}
	
	public String obter_precoProduto() {
		return driver.findElement(precoProduto).getText();
	}
	
	public String obter_tamanhoProduto() {
		return driver.findElement(tamanhoProduto).getText();
	}
	
	public String obter_corProduto() {
		return driver.findElement(corProduto).getText();
	}
	
	public String obter_input_quantidadeProduto() {
		return driver.findElement(input_quantidadeProduto).getText();
	}
	
	public String obter_subtotalProduto() {
		return driver.findElement(subtotalProduto).getText();
	}
	
	public String obter_numeroItensTotal() {
		return driver.findElement(numeroItensTotal).getText();
	}
	
	public String obter_subtotalTotal() {
		return driver.findElement(subtotalTotal).getText();
	}
	
	public String obter_valorEntrega() {
		return driver.findElement(valorEntrega).getText();
	}
	
	public String obter_totalSemTaxa() {
		return driver.findElement(totalSemTaxa).getText();
	}
	
	public String obter_totalComTaxa() {
		return driver.findElement(totalComTaxa).getText();
	}
	
	public String obter_taxasTotal() {
		return driver.findElement(taxasTotal).getText();
	}                       
}
