package br.com.wmw.vendafacil_frontend.domain;

public class ItemPedidoRequest {

	private Long numeroSequencia;
	private Integer quantidade;
	private Double precoUnitario;
	private Double desconto;
	private Double valorTotal;
	private Long codigoProduto;
	
	public ItemPedidoRequest(ItemPedido item) {
		numeroSequencia = item.numeroSequencia;
		quantidade = item.quantidade;
		precoUnitario = item.precoUnitario;
		desconto = item.descontoEmValor;
		valorTotal = item.totalItem;
		codigoProduto = item.codigoProduto;
	}

	public Long getNumeroSequencia() {
		return numeroSequencia;
	}

	public void setNumeroSequencia(Long numeroSequencia) {
		this.numeroSequencia = numeroSequencia;
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

	public Double getDesconto() {
		return desconto;
	}

	public void setDesconto(Double desconto) {
		this.desconto = desconto;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Long getCodigoProduto() {
		return codigoProduto;
	}

	public void setCodigoProduto(Long codigoProduto) {
		this.codigoProduto = codigoProduto;
	}
}
