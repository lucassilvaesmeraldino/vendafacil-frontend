package br.com.wmw.vendafacil_frontend.api;

import java.util.ArrayList;
import java.util.List;

import br.com.wmw.vendafacil_frontend.domain.Pedido;
import br.com.wmw.vendafacil_frontend.util.DateUtils;
import totalcross.json.JSONArray;
import totalcross.json.JSONObject;
import totalcross.sys.Settings;

public class PedidoRequest {

	private String dataEmissao;
	private String dataEntrega;
	private Long codigoStatusPedido;
	private Long codigoCliente;
	private Double valorTotal;
	private List<ItemPedidoRequest> itens;
	
	public PedidoRequest(Pedido pedido) {
		dataEmissao = DateUtils.convert(pedido.getDataEmissao(), Settings.DATE_DMY).toString(Settings.DATE_YMD, "-");
		dataEntrega = DateUtils.convert(pedido.getDataEntrega(), Settings.DATE_DMY).toString(Settings.DATE_YMD, "-");
		codigoStatusPedido = (long) pedido.getStatusPedido().ordinal();
		codigoCliente = pedido.getCodigoCliente();
		valorTotal = pedido.getTotalPedido();
		List<ItemPedidoRequest> itensPedidoRequest = new ArrayList<>();
		pedido.getItens().forEach(i -> itensPedidoRequest.add(new ItemPedidoRequest(i)));
		itens = itensPedidoRequest;
	}
	
	public JSONObject toJson() {
		JSONObject pedidoJson = new JSONObject();
		pedidoJson.put("dataEmissao", dataEmissao);
		pedidoJson.put("dataEntrega", dataEntrega);
		pedidoJson.put("codigoStatusPedido", codigoStatusPedido);
		pedidoJson.put("codigoCliente", codigoCliente);
		pedidoJson.put("valorTotal", valorTotal);
		JSONArray itensArray = new JSONArray();
		for(ItemPedidoRequest itemRequest : itens) {
			itensArray.put(itemRequest.toJson());
		}
		pedidoJson.put("itens", itensArray);
		return pedidoJson;
	}

}
