package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage {
	private WebDriver driver;
	List<WebElement> listaProdutos = new ArrayList();
	private By produtos = By.className("product-description");
	private By textoProdutosCarrinho = By.className("cart-products-count");
	private By descricoesDosProdutos = By.cssSelector(".product-description a");
	private By precosDosProdutos = By.className("price");

	public HomePage(WebDriver driver) {
		this.driver = driver;
	}

	public int contarProdutos() {
		carregarListaProdutos();
		return listaProdutos.size();
	}

	private void carregarListaProdutos() {
		listaProdutos = driver.findElements(produtos);
	}

	public int obterQuantidadeProdutosNoCarrinho() {
		String quantidadeProdutosNoCarrinho = driver.findElement(textoProdutosCarrinho).getText();
		quantidadeProdutosNoCarrinho = quantidadeProdutosNoCarrinho.replace("(", "");
		quantidadeProdutosNoCarrinho = quantidadeProdutosNoCarrinho.replace(")", "");

		int qtdProdutosNoCarrinho = Integer.parseInt(quantidadeProdutosNoCarrinho);
		return qtdProdutosNoCarrinho;
	}

	public String obterNomeProduto(int indice) {
		return driver.findElements(descricoesDosProdutos).get(indice).getText();
	}

	public String obterPrecoProduto(int indice) {
		return driver.findElements(precosDosProdutos).get(indice).getText();
	}

	public ProdutoPage clicarProduto(int indice) {
		 driver.findElements(descricoesDosProdutos).get(indice).click();
		 return new ProdutoPage(driver);
	}
}
