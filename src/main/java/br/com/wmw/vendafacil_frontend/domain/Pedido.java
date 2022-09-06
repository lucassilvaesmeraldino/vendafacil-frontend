package br.com.wmw.vendafacil_frontend.domain;

import java.util.ArrayList;
import java.util.List;

import totalcross.sys.Settings;
import totalcross.util.Date;

public class Pedido {

	private Integer codigoPedido;
	private Long codigoCliente;
	private String dataEmissao;
	private String dataEntrega;
	private Double totalPedido;
	private StatusPedido statusPedido = StatusPedido.ABERTO;
	private List<ItemPedido> itens =  new ArrayList<>();
	
	public Pedido() {
		dataEmissao = new Date().toString(Settings.DATE_DMY, "/");
	}

	public Integer getCodigoPedido() {
		return codigoPedido;
	}

	public void setCodigoPedido(Integer codigoPedido) {
		this.codigoPedido = codigoPedido;
	}

	public long getCodigoCliente() {
		return codigoCliente;
	}

	public void setCodigoCliente(Long codigoCliente) {
		this.codigoCliente = codigoCliente;
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

	public Double getTotalPedido() {
		return totalPedido;
	}

	public void setTotalPedido(double totalPedido) {
		this.totalPedido = totalPedido;
	}

	public StatusPedido getStatusPedido() {
		return statusPedido;
	}

	public void setStatusPedido(StatusPedido statusPedido) {
		this.statusPedido = statusPedido;
	}

	public List<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(List<ItemPedido> itens) {
		this.itens = itens;
	}
	
	public void addItem(ItemPedido item) {
		itens.add(item);
	}
	
}
