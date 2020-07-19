package steps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.google.common.io.Files;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import pages.HomePage;
import pages.LoginPage;
import pages.ModalProdutoPage;
import pages.ProdutoPage;
import util.Funcoes;

public class ComprarProdutoSteps {

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
		assertEquals(int1, homePage.contarProdutos());
	}

	@Entao("carrinho esta zerado")
	public void carrinho_esta_zerado() {
		assertEquals(0, homePage.obterQuantidadeProdutosNoCarrinho());
	}

	LoginPage loginPage;
	String email = "monica@teste.com";

	@Quando("estou logado")
	public void estou_logado() {
		loginPage = homePage.clicarBotaoSignIn();
		loginPage.preencherEmail(email);
		loginPage.preencherSenha("123456");
		loginPage.clicarBotaoSignIn();
		assertEquals(true, homePage.estaLogado("Monica Teste"));
		homePage.carregarPaginaInicial();
	}

	ProdutoPage produtoPage;
	String nomeProduto_HomePage;
	String precoProduto_HomePage;
	String nomeProduto_ProdutoPage;
	String precoProduto_ProdutoPage;

	@Quando("seleciono um produto na posicao {int}")
	public void seleciono_um_produto_na_posicao(Integer indice) {
		nomeProduto_HomePage = homePage.obterNomeProduto(indice);
		precoProduto_HomePage = homePage.obterPrecoProduto(indice);

		produtoPage = homePage.clicarProduto(indice);

		nomeProduto_ProdutoPage = produtoPage.obterNomeProduto();
		precoProduto_ProdutoPage = produtoPage.obterPrecoProduto();
	}

	@Quando("nome do produto na tela principal e na tela produto eh {string}")
	public void nome_do_produto_na_tela_principal_eh(String nomeProduto) {
		assertEquals(nomeProduto_HomePage.toUpperCase(), nomeProduto.toUpperCase());
		assertEquals(nomeProduto_ProdutoPage.toUpperCase(), nomeProduto.toUpperCase());
	}

	@Quando("preco do produto na tela principal e na tela produto eh {string}")
	public void preco_do_produto_na_tela_principal_eh(String precoProduto) {
		assertEquals(precoProduto_HomePage, precoProduto);
		assertEquals(precoProduto_ProdutoPage, precoProduto);
	}

	ModalProdutoPage modalProdutoPage;

	@Quando("adiciono o produto no carrinho com tamanho {string} cor {string} e quantidade {int}")
	public void adiciono_o_produto_no_carrinho_com_tamanho_cor_e_quantidade(String tamanhoProduto, String corProduto,
			Integer quantidadeProduto) {
		// Selecionar tamanho
		List<String> listaOpcoes = produtoPage.obterOpcoesSelecionadas();
		produtoPage.selecionarOpcaoDropdown(tamanhoProduto);

		// Selecionar cor
		if (!corProduto.contentEquals("N/A"))
			produtoPage.selecionarCorPreta();

		// Selecionar a quantidade
		produtoPage.alterarQuantidade(quantidadeProduto);

		// Clicar no botao add to cart
		modalProdutoPage = produtoPage.clicarBotaoAddToCart();

		// Validacoes
		assertTrue(modalProdutoPage.obterMensagemProdutoAdicionado()
				.endsWith("Product successfully added to your shopping cart"));
	}

	@Entao("o produto aparece na confirmacao com nome {string} preco {string} tamanho {string} cor {string} e quantidade {int}")
	public void o_produto_aparece_na_confirmacao_com_nome_preco_tamanho_cor_e_quantidade(String nomeProduto,
			String precoProduto, String tamanhoProduto, String corProduto, Integer quantidadeProduto) {
		assertEquals(nomeProduto_ProdutoPage.toUpperCase(), modalProdutoPage.obterNomeProduto().toUpperCase());
		assertEquals(tamanhoProduto, modalProdutoPage.obterTamanhoProduto());
		if (!corProduto.contentEquals("N/A"))
			assertEquals(corProduto, modalProdutoPage.obterCorProduto());
		assertEquals(quantidadeProduto, modalProdutoPage.obterQuantidadeProduto());
		Double precoProdutoEncontrado = Funcoes.removeCifraoDevolveDouble(modalProdutoPage.obterPrecoProduto());
		Double precoProdutoEsperado = Funcoes.removeCifraoDevolveDouble(precoProduto);
		Double subtotalEncontrado = Funcoes.removeCifraoDevolveDouble(modalProdutoPage.obterSubtotal());

		Double subtotalCalculadoEsperado = quantidadeProduto * precoProdutoEsperado;

		assertEquals(subtotalCalculadoEsperado, subtotalEncontrado);
	}

	@After(order = 1)
	public void capturarTela(Scenario scenario) {
		TakesScreenshot camera = (TakesScreenshot) driver;
		File capturaDeTela = camera.getScreenshotAs(OutputType.FILE);
		String scenarioId = scenario.getId().substring(scenario.getId().lastIndexOf(".feature:")+9);
		String nomeArquivo = "resources/screenshots/" + scenario.getName() + "_" + scenarioId+"_"+ scenario.getStatus() + ".png";
		System.out.println(nomeArquivo);
		try {
			Files.move(capturaDeTela, new File(nomeArquivo));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@After(order = 0)
	public static void finalizar() {
		driver.quit();
	}
}
