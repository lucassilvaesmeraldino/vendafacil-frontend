package br.com.wmw.vendafacil_frontend.service;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import br.com.wmw.vendafacil_frontend.dao.ProdutoDao;
import br.com.wmw.vendafacil_frontend.domain.ItemPedido;
import br.com.wmw.vendafacil_frontend.domain.Produto;
import br.com.wmw.vendafacil_frontend.util.DoubleUtils;
import br.com.wmw.vendafacil_frontend.util.StringUtils;

public class ItemPedidoService {

	public ItemPedido createItemPedido(Produto produto, Integer quantidade, Double descontoEmPorcentagem) {
		ItemPedido itemPedido = new ItemPedido();
		itemPedido.setCodigoProduto(produto.getCodigo());
		itemPedido.setQuantidade(quantidade);
		itemPedido.setDescontoEmPorcentagem(descontoEmPorcentagem);
		itemPedido.setDescontoEmValor(calculaValorDoDesconto(produto, descontoEmPorcentagem));
		itemPedido.setPrecoUnitario(calculaPrecoUnitario(produto, itemPedido.getDescontoEmValor()));
		itemPedido.setTotalItem(calculaValorTotal(itemPedido.getPrecoUnitario(), quantidade));
		return itemPedido;
	}
	
	private Double calculaValorDoDesconto(Produto produto, Double descontoEmPorcentagem) {
		return produto.getPreco() * (descontoEmPorcentagem / 100);
	}
	
	private Double calculaPrecoUnitario(Produto produto, Double descontoEmValor) {
		return produto.getPreco() - descontoEmValor;
	}
	
	private Double calculaValorTotal(Double precoUnitario, Integer quantidade) {
		return precoUnitario * quantidade;
	}
	
	public List<String[]> instantiateItensSumarry(List<ItemPedido> itens, ProdutoDao produtoDao) {
		List<String[]> itensList = new ArrayList<>();
		for(ItemPedido item : itens) {
			String descricaoProduto = produtoDao.findByCodigo(item.getCodigoProduto()).getNome();
			String nomeProduto =   StringUtils.reduceLength(descricaoProduto, 36);
			String quantidade = String.valueOf(item.getQuantidade());
			String precoUnitario = String.valueOf("R$ "+ DoubleUtils.round(item.getPrecoUnitario(), RoundingMode.HALF_UP)).replace(".", ",");
			String valorTotal = String.valueOf("R$ "+ DoubleUtils.round(item.getTotalItem(), RoundingMode.HALF_UP)).replace(".", ",");
			itensList.add(new String[] {nomeProduto,quantidade,precoUnitario,valorTotal});
		}
		return itensList;
 	}
}
