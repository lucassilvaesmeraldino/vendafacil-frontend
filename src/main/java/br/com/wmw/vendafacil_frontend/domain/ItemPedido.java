package br.com.wmw.vendafacil_frontend.domain;

public class ItemPedido {

	private Integer numeroSequencia;
	private Long codigoProduto;
	private Integer codigoPedido;
	private Integer quantidade;
	private Double precoUnitario;
	private Double descontoEmPorcentagem;
	private Double descontoEmValor;
	private Double totalItem;
	
	public Integer getNumeroSequencia() {
		return numeroSequencia;
	}
	public void setNumeroSequencia(Integer numeroSequencia) {
		this.numeroSequencia = numeroSequencia;
	}
	public Long getCodigoProduto() {
		return codigoProduto;
	}
	public void setCodigoProduto(Long codigoProduto) {
		this.codigoProduto = codigoProduto;
	}
	public Integer getCodigoPedido() {
		return codigoPedido;
	}
	public void setCodigoPedido(Integer codigoPedido) {
		this.codigoPedido = codigoPedido;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	public Double getPrecoUnitario() {
		return precoUnitario;
	}
	public void setPrecoUnitario(Double precoUnitario) {
		this.precoUnitario = precoUnitario;
	}
	public Double getDescontoEmPorcentagem() {
		return descontoEmPorcentagem;
	}
	public void setDescontoEmPorcentagem(Double descontoEmPorcentagem) {
		this.descontoEmPorcentagem = descontoEmPorcentagem;
	}
	public Double getDescontoEmValor() {
		return descontoEmValor;
	}
	public void setDescontoEmValor(Double descontoEmValor) {
		this.descontoEmValor = descontoEmValor;
	}
	public Double getTotalItem() {
		return totalItem;
	}
	public void setTotalItem(Double totalItem) {
		this.totalItem = totalItem;
	}
	
}
