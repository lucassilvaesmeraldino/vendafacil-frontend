package br.com.wmw.vendafacil_frontend.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class PedidoRequestModel {

	private String dataEmissao;
	private String dataEntrega;
	private Long codigoStatusPedido;
	private Long codigoCliente;
	private Double valorTotal;
	private List<ItemPedidoRequest> itens;
	
	public PedidoRequestModel(Pedido pedido) {
		dataEmissao = LocalDate.parse(pedido.dataEmissao, DateTimeFormatter.ofPattern("d/MM/yyyy")).toString();
		dataEntrega = LocalDate.parse(pedido.dataEntrega, DateTimeFormatter.ofPattern("d/MM/yyyy")).toString();
		codigoStatusPedido = (long) pedido.statusPedido.ordinal();
		codigoCliente = pedido.codigoCliente;
		valorTotal = pedido.totalPedido;
		itens = pedido.itens.stream().map(ItemPedidoRequest::new).collect(Collectors.toList());
	}

	public String getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(String dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public String getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(String dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

	public Long getCodigoStatusPedido() {
		return codigoStatusPedido;
	}

	public void setCodigoStatusPedido(Long codigoStatusPedido) {
		this.codigoStatusPedido = codigoStatusPedido;
	}

	public Long getCodigoCliente() {
		return codigoCliente;
	}

	public void setCodigoCliente(Long codigoCliente) {
		this.codigoCliente = codigoCliente;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public List<ItemPedidoRequest> getItens() {
		return itens;
	}

	public void setItens(List<ItemPedidoRequest> itens) {
		this.itens = itens;
	}
}
