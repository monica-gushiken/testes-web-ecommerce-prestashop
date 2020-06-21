package homepage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import base.BaseTests;
import pages.CarrinhoPage;
import pages.CheckoutPage;
import pages.LoginPage;
import pages.ModalProdutoPage;
import pages.PedidoPage;
import pages.ProdutoPage;
import util.Funcoes;

public class HomePageTests extends BaseTests {

	LoginPage loginPage;
	ProdutoPage produtoPage;
	ModalProdutoPage modalProdutoPage;
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

	String email = "monica@teste.com";

	@Test
	public void testLoginComSucesso_UsuarioLogado() {
		loginPage = homePage.clicarBotaoSignIn();
		loginPage.preencherEmail(email);
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
		modalProdutoPage = produtoPage.clicarBotaoAddToCart();

		// Validações

		assertTrue(modalProdutoPage.obterMensagemProdutoAdicionado()
				.endsWith("Product successfully added to your shopping cart"));
		assertEquals(nomeProduto_ProdutoPage.toUpperCase(), modalProdutoPage.obterNomeProduto().toUpperCase());
		assertEquals(tamanhoProduto, modalProdutoPage.obterTamanhoProduto());
		assertEquals(corProduto, modalProdutoPage.obterCorProduto());
		assertEquals(quantidadeProduto, modalProdutoPage.obterQuantidadeProduto());

		Double precoProduto = Funcoes.removeCifraoDevolveDouble(modalProdutoPage.obterPrecoProduto());
		Double subtotal = Funcoes.removeCifraoDevolveDouble(modalProdutoPage.obterSubtotal());

		Double subtotalCalculado = quantidadeProduto * precoProduto;

		assertEquals(subtotalCalculado, subtotal);

	}

	// Valores esperados
	String esperado_nomeProduto = "Hummingbird printed t-shirt";
	Double esperado_precoProduto = 19.12;
	String esperado_tamanhoProduto = "M";
	String esperado_corProduto = "Black";
	int esperado_input_quantidadeProduto = 2;
	Double esperado_subtotalProduto = esperado_precoProduto * esperado_input_quantidadeProduto;

	int esperado_numeroItensTotal = esperado_input_quantidadeProduto;
	Double esperado_subtotalTotal = esperado_subtotalProduto;
	Double esperado_valorEntrega = 7.00;
	Double esperado_totalSemTaxa = esperado_subtotalTotal + esperado_valorEntrega;
	Double esperado_totalComTaxa = esperado_totalSemTaxa;
	Double esperado_taxasTotal = 0.00;

	String esperado_nomeCliente = "Monica Teste";
	CarrinhoPage carrinhoPage;

	@Test
	public void testIrParaCarrinho_InformacoesPersistidas() {
		testIncluirProdutoNoCarrinho_ProdutoIncluidoComSucesso();
		carrinhoPage = modalProdutoPage.clicarBotaoProceedToCheckout();

		System.out.println("*** TELA DO CARRINHO ***");
		System.out.println(carrinhoPage.obter_nomeProduto());
		System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_precoProduto()));
		System.out.println(carrinhoPage.obter_tamanhoProduto());
		System.out.println(carrinhoPage.obter_corProduto());
		System.out.println(carrinhoPage.obter_input_quantidadeProduto());
		System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_subtotalProduto()));

		System.out.println("*** TOTAIS NO CARRINHO ***");
		System.out.println(Funcoes.removeTextoItemsDevolveInt(carrinhoPage.obter_numeroItensTotal()));
		System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_subtotalTotal()));
		System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_valorEntrega()));
		System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_totalSemTaxa()));
		System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_totalComTaxa()));
		System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_taxasTotal()));

		assertEquals(esperado_nomeProduto.toUpperCase(), carrinhoPage.obter_nomeProduto().toUpperCase());
		assertEquals(esperado_precoProduto, Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_precoProduto()));
		assertEquals(esperado_tamanhoProduto, carrinhoPage.obter_tamanhoProduto());
		assertEquals(esperado_corProduto, carrinhoPage.obter_corProduto());
		assertEquals(esperado_input_quantidadeProduto, Integer.parseInt(carrinhoPage.obter_input_quantidadeProduto()));
		assertEquals(esperado_subtotalProduto, Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_subtotalProduto()));

		assertEquals(esperado_numeroItensTotal,
				Funcoes.removeTextoItemsDevolveInt(carrinhoPage.obter_numeroItensTotal()));
		assertEquals(esperado_subtotalTotal, Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_subtotalTotal()));
		assertEquals(esperado_valorEntrega, Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_valorEntrega()));
		assertEquals(esperado_totalSemTaxa, Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_totalSemTaxa()));
		assertEquals(esperado_totalComTaxa, Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_totalComTaxa()));
		assertEquals(esperado_taxasTotal, Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_taxasTotal()));
	}

	CheckoutPage checkoutPage;

	@Test
	public void testIrParaCheckout_freteMeioPagamentoEFreteListadosOk() {
		// Pré-condições
		// Produto disponível no carrinho de compras
		testIrParaCarrinho_InformacoesPersistidas();

		checkoutPage = carrinhoPage.clicarBotaoProceedToCheckout();
		assertEquals(esperado_totalComTaxa, Funcoes.removeCifraoDevolveDouble(checkoutPage.obter_totalComTaxa()));
		// assertEquals(esperado_nomeCliente, checkoutPage.obter_nomeCliente());
		assertTrue(checkoutPage.obter_nomeCliente().startsWith(esperado_nomeCliente));
		checkoutPage.clicar_botaoContinueAddress();

		String encontrado_valorEntrega = checkoutPage.obter_valorEntrega();
		encontrado_valorEntrega = Funcoes.removeTexto(encontrado_valorEntrega, " tax excl.");
		assertEquals(esperado_valorEntrega, Funcoes.removeCifraoDevolveDouble(encontrado_valorEntrega));

		checkoutPage.clicar_botaoContinueShipping();
		checkoutPage.selecionar_radioPayByCheck();

		String encontrado_amountPayByCheck = checkoutPage.obter_amountPayByCheck();
		encontrado_amountPayByCheck = Funcoes.removeTexto(encontrado_amountPayByCheck, " (tax incl.)");
		assertEquals(esperado_totalComTaxa, Funcoes.removeCifraoDevolveDouble(encontrado_amountPayByCheck));

		checkoutPage.selecionar_checkboxIAgree();
		assertEquals(true, checkoutPage.estaSelecionadoCheckboxIAgree());
	}

	@Test
	public void testFinalizarPedido_pedidoFinalizadoComSucesso() {
		testIrParaCheckout_freteMeioPagamentoEFreteListadosOk();
		PedidoPage pedidoPage = checkoutPage.clicar_botaoOrder();
		String encontrado_mensagePedidoConfirmado = pedidoPage.obter_mensagemPedidoConfirmado();
		assertTrue(encontrado_mensagePedidoConfirmado.endsWith("YOUR ORDER IS CONFIRMED"));

		assertEquals(email, pedidoPage.obter_email());
		assertEquals(esperado_subtotalTotal, Funcoes.removeCifraoDevolveDouble(pedidoPage.obter_totalProdutos()));
		assertEquals(esperado_totalComTaxa, Funcoes.removeCifraoDevolveDouble(pedidoPage.obter_totalComTaxa()));
		assertEquals("check", pedidoPage.obter_metodoPagamento());
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/massaTeste_Login.csv", numLinesToSkip = 1, delimiter = ';')
	public void testLogin_UsuarioLogadoComDadosValidos(String nomeTeste, String email, String password,
			String nomeUsuario, String resultado) {
		loginPage = homePage.clicarBotaoSignIn();
		loginPage.preencherEmail(email);
		loginPage.preencherSenha(password);
		loginPage.clicarBotaoSignIn();

		boolean esperado_loginOk;
		if (resultado.equals("positivo"))
			esperado_loginOk = true;
		else
			esperado_loginOk = false;

		assertEquals(esperado_loginOk, homePage.estaLogado(nomeUsuario));

		capturarTela(nomeTeste, resultado);

		if (esperado_loginOk)
			homePage.clicarBotaoSignOut();
		carregarPaginaInicial();
	}

}
