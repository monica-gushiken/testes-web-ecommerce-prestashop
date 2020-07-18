package steps;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import pages.HomePage;

public class VisualizaProdutosSteps {

	private static WebDriver driver;
	private HomePage homePage = new HomePage(driver);

	@Before
	public static void inicializar() {
		System.setProperty("webdriver.chrome.driver", "C:\\webdrivers\\chromedriver\\83\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Dado("que estou na pagina inicial")
	public void que_estou_na_pagina_inicial() {
		homePage.carregarPaginaInicial();
		assertEquals("Loja de Teste", homePage.obterTituloPagina());
	}

	@Quando("nao estou logado")
	public void nao_estou_logado() {
		assertEquals(false, homePage.estaLogado());
	}

	@Entao("visualizo {int} produtos disponiveis")
	public void visualizo_produtos_disponiveis(Integer int1) {
		assertEquals(int1,homePage.contarProdutos());
	}

	@Entao("carrinho esta zerado")
	public void carrinho_esta_zerado() {
		assertEquals(0,homePage.obterQuantidadeProdutosNoCarrinho());
	}

	@After
	public static void finalizar() {
		driver.quit();
	}
}
