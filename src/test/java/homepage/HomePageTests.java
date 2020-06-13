package homepage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import base.BaseTests;
import pages.LoginPage;
import pages.ModalProdutoPage;
import pages.ProdutoPage;

public class HomePageTests extends BaseTests {

	LoginPage loginPage;
	ProdutoPage produtoPage;
	String nomeProduto_ProdutoPage;

	@Test
	public void testContarProdutos_oitoProdutosDiferentes() {
		carregarPaginaInicial();
		assertEquals(8, homePage.contarProdutos());
	}

	@Test
	public void testValidarCarrinhoZerado_zeroItensNoCarrinho() {
		int produtosNoCarrinho = homePage.obterQuantidadeProdutosNoCarrinho();
		assertEquals(0, produtosNoCarrinho);
	}

	@Test
	public void testValidarDetalhesDoProduto_descricaoEValorIguais() {
		int indice = 0;
		String nomeProduto_HomePage = homePage.obterNomeProduto(indice);
		String precoProduto_HomePage = homePage.obterPrecoProduto(indice);

		produtoPage = homePage.clicarProduto(indice);

		nomeProduto_ProdutoPage = produtoPage.obterNomeProduto();
		String precoProduto_ProdutoPage = produtoPage.obterPrecoProduto();

		assertEquals(nomeProduto_HomePage.toUpperCase(), nomeProduto_ProdutoPage.toUpperCase());
		assertEquals(precoProduto_HomePage, precoProduto_ProdutoPage);
	}

	@Test
	public void testLoginComSucesso_UsuarioLogado() {
		loginPage = homePage.clicarBotaoSignIn();
		loginPage.preencherEmail("monica@teste.com");
		loginPage.preencherSenha("123456");
		loginPage.clicarBotaoSignIn();
		assertEquals(true, homePage.estaLogado("Monica Teste"));
		carregarPaginaInicial();
	}

	@Test
	public void testIncluirProdutoNoCarrinho_ProdutoIncluidoComSucesso() {

		String tamanhoProduto = "M";
		String corProduto = "Black";
		int quantidadeProduto = 2;

		// Pré-condição - usuário logado
		if (!homePage.estaLogado("Monica Teste")) {
			testLoginComSucesso_UsuarioLogado();
		}
		// Selecionando o produto
		testValidarDetalhesDoProduto_descricaoEValorIguais();

		// Selecionar tamanho
		List<String> listaOpcoes = produtoPage.obterOpcoesSelecionadas();
		produtoPage.selecionarOpcaoDropdown(tamanhoProduto);

		// Selecionar cor
		produtoPage.selecionarCorPreta();

		// Selecionar a quantidade
		produtoPage.alterarQuantidade(quantidadeProduto);

		// Clicar no botão Add to cart
		ModalProdutoPage modalProdutoPage = produtoPage.clicarBotaoAddToCart();

		// Validações
		
		assertTrue(modalProdutoPage.obterMensagemProdutoAdicionado()
				.endsWith("Product successfully added to your shopping cart"));
		assertEquals(nomeProduto_ProdutoPage.toUpperCase(), modalProdutoPage.obterNomeProduto().toUpperCase());
		assertEquals(tamanhoProduto, modalProdutoPage.obterTamanhoProduto());
		assertEquals(corProduto, modalProdutoPage.obterCorProduto());
		assertEquals(quantidadeProduto, modalProdutoPage.obterQuantidadeProduto());
		String precoProdutoString = modalProdutoPage.obterPrecoProduto();
		precoProdutoString = precoProdutoString.replace("$", "");
		Double precoProduto = Double.parseDouble(precoProdutoString);
				
		String subtotalString = modalProdutoPage.obterSubtotal();
		subtotalString = subtotalString.replace("$", "");
		Double subtotal = Double.parseDouble(subtotalString);

		Double subtotalCalculado = quantidadeProduto * precoProduto;
		
		assertEquals(subtotalCalculado, subtotal);
		
	}

}
