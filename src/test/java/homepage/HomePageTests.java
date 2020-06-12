package homepage;

import static org.junit.jupiter.api.Assertions.assertEquals;

//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.Test;

import base.BaseTests;
import pages.LoginPage;
import pages.ProdutoPage;

public class HomePageTests extends BaseTests {
	@Test
	public void testContarProdutos_oitoProdutosDiferentes() {
		carregarPaginaInicial();
//		assertThat(homePage.contarProdutos(),is(8));
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

		ProdutoPage produtoPage = homePage.clicarProduto(indice);

		String nomeProduto_ProdutoPage = produtoPage.obterNomeProduto();
		String precoProduto_ProdutoPage = produtoPage.obterPrecoProduto();

		assertEquals(nomeProduto_HomePage.toUpperCase(), nomeProduto_ProdutoPage.toUpperCase());
		assertEquals(precoProduto_HomePage, precoProduto_ProdutoPage);
	}

	@Test
	public void testLoginComSucesso_UsuarioLogado() {
		LoginPage loginPage = homePage.clicarBotaoSignIn();
		loginPage.preencherEmail("monica@teste.com");
		loginPage.preencherSenha("123456");
		loginPage.clicarBotaoSignIn();
		assertEquals(true, homePage.estaLogado("Monica Teste"));
	}
}
