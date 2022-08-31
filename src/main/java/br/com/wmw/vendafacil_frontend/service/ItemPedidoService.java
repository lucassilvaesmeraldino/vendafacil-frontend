package br.com.wmw.vendafacil_frontend.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import br.com.wmw.vendafacil_frontend.dao.ProdutoDao;
import br.com.wmw.vendafacil_frontend.domain.ItemPedido;
import br.com.wmw.vendafacil_frontend.domain.Produto;

public class ItemPedidoService {

	public ItemPedido createItemPedido(Produto produto, Integer quantidade, Double descontoEmPorcentagem) {
		ItemPedido itemPedido = new ItemPedido();
		itemPedido.codigoProduto = produto.codigo;
		itemPedido.quantidade = quantidade;
		itemPedido.descontoEmPorcentagem = descontoEmPorcentagem;
		itemPedido.descontoEmValor = calculaValorDoDesconto(produto, descontoEmPorcentagem);
		itemPedido.precoUnitario = calculaPrecoUnitario(produto, itemPedido.descontoEmValor);
		itemPedido.totalItem = calculaValorTotal(itemPedido.precoUnitario, quantidade);
		return itemPedido;
	}
	
	private Double calculaValorDoDesconto(Produto produto, Double descontoEmPorcentagem) {
		return produto.preco * (descontoEmPorcentagem / 100);
	}
	
	private Double calculaPrecoUnitario(Produto produto, Double descontoEmValor) {
		return produto.preco - descontoEmValor;
	}
	
	private Double calculaValorTotal(Double precoUnitario, Integer quantidade) {
		return precoUnitario * quantidade;
	}
	
	public List<String[]> instantiateItensSumarry(List<ItemPedido> itens, ProdutoDao produtoDao) {
		List<String[]> itensList = new ArrayList<>();
		for(ItemPedido item : itens) {
			String descricaoProduto = produtoDao.findByCodigo(item.codigoProduto).nome;
			String nomeProduto =  descricaoProduto.length() > 36 ? descricaoProduto.substring(0, 36).concat("...") : descricaoProduto;
			String quantidade = String.valueOf(item.quantidade);
			String precoUnitario = String.valueOf("R$ "+ roundDoubleValue(item.precoUnitario)).replace(".", ",");
			String valorTotal = String.valueOf("R$ "+ roundDoubleValue(item.totalItem)).replace(".", ",");
			itensList.add(new String[] {nomeProduto,quantidade,precoUnitario,valorTotal});
		}
		return itensList;
 	}
	
	private Double roundDoubleValue(Double value) {
		return new BigDecimal(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
	}
}
