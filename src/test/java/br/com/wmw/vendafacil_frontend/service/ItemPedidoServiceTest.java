package br.com.wmw.vendafacil_frontend.service;

import br.com.wmw.vendafacil_frontend.domain.ItemPedido;
import br.com.wmw.vendafacil_frontend.domain.Produto;
import totalcross.unit.TestCase;

public class ItemPedidoServiceTest extends TestCase{
	
	private static final Long CODIGO_PRODUTO = Long.valueOf(1);
	private static final String NOME_PRODUTO = "Iphone 13 MAX";
	private static final Double PRECO_PRODUTO = Double.valueOf(3500);
	
	private static final Integer QUANTIDADE_ITEM = 5;
	private static final Double DESCONTO_PORCENTO_ITEM = 20.55;
	private static final Double DESCONTO_VALOR_ITEM = PRECO_PRODUTO * (DESCONTO_PORCENTO_ITEM / 100);
	private static final Double PRECO_ITEM = PRECO_PRODUTO - DESCONTO_VALOR_ITEM;
	private static final Double TOTAL_ITEM = PRECO_ITEM * QUANTIDADE_ITEM;

	private static final ItemPedidoService itemPedidoService = new ItemPedidoService();

	public void shouldCreateAnItemPedido() {
		Produto produto = new Produto(CODIGO_PRODUTO,NOME_PRODUTO,PRECO_PRODUTO);
		ItemPedido itemCreated = itemPedidoService.createItemPedido(produto, QUANTIDADE_ITEM, DESCONTO_PORCENTO_ITEM);
		
		assertEquals(CODIGO_PRODUTO, itemCreated.getCodigoProduto());
		assertEquals(QUANTIDADE_ITEM, itemCreated.getQuantidade());
		assertEquals(DESCONTO_PORCENTO_ITEM, itemCreated.getDescontoEmPorcentagem());
		assertEquals(DESCONTO_VALOR_ITEM, itemCreated.getDescontoEmValor());
		assertEquals(PRECO_ITEM, itemCreated.getPrecoUnitario());
		assertEquals(TOTAL_ITEM, itemCreated.getTotalItem());
	}

	@Override
	public void testRun() {
		shouldCreateAnItemPedido();
	}

}
