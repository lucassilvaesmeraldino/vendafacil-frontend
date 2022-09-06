package br.com.wmw.vendafacil_frontend.service;

import br.com.wmw.vendafacil_frontend.domain.ItemPedido;
import br.com.wmw.vendafacil_frontend.domain.Pedido;
import totalcross.unit.TestCase;

public class PedidoServiceTest extends TestCase{
	
	private static final Double PRECO_PRODUTO = Double.valueOf(3500);
	private static final Integer QUANTIDADE_ITEM = 5;
	private static final Double DESCONTO_PORCENTO_ITEM = 20.55;
	private static final Double DESCONTO_VALOR_ITEM = PRECO_PRODUTO * (DESCONTO_PORCENTO_ITEM / 100);
	private static final Double PRECO_ITEM = PRECO_PRODUTO - DESCONTO_VALOR_ITEM;
	private static final Double TOTAL_ITEM = PRECO_ITEM * QUANTIDADE_ITEM;
	private static final Double TOTAL_PEDIDO = TOTAL_ITEM + TOTAL_ITEM;
	
	private static final PedidoService pedidoService = new PedidoService();

	public void shouldCalculateValorTotalPedido() {
		Pedido pedido = new Pedido();
		
		ItemPedido itemPedido1 = new ItemPedido();
		itemPedido1.setTotalItem(TOTAL_ITEM);
		
		ItemPedido itemPedido2 = new ItemPedido();
		itemPedido2.setTotalItem(TOTAL_ITEM);
		
		pedido.addItem(itemPedido1);
		pedido.addItem(itemPedido2);
		
		Double valorTotalPedido = pedidoService.calculateValorTotal(pedido);
		
		assertEquals(TOTAL_PEDIDO, valorTotalPedido);
	}

	@Override
	public void testRun() {
		shouldCalculateValorTotalPedido();
	}

}
