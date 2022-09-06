package br.com.wmw.vendafacil_frontend.api;

import br.com.wmw.vendafacil_frontend.domain.ItemPedido;
import totalcross.json.JSONObject;

public class ItemPedidoRequest {

	private Integer numeroSequencia;
	private Integer quantidade;
	private Double precoUnitario;
	private Double desconto;
	private Double valorTotal;
	private Long codigoProduto;
	
	public ItemPedidoRequest(ItemPedido item) {
		numeroSequencia = item.getNumeroSequencia();
		quantidade = item.getQuantidade();
		precoUnitario = item.getPrecoUnitario();
		desconto = item.getDescontoEmValor();
		valorTotal = item.getTotalItem();
		codigoProduto = item.getCodigoProduto();
	}
	
	public JSONObject toJson() {
		JSONObject itemJson = new JSONObject();
		itemJson.put("numeroSequencia", numeroSequencia);
		itemJson.put("quantidade", quantidade);
		itemJson.put("precoUnitario", precoUnitario);
		itemJson.put("desconto", desconto);
		itemJson.put("valorTotal", valorTotal);
		itemJson.put("codigoProduto", codigoProduto);
		return itemJson;
	}
}
