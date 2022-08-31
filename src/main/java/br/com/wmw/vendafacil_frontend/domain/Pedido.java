package br.com.wmw.vendafacil_frontend.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Pedido {

	public int codigoPedido;
	public long codigoCliente;
	public String dataEmissao;
	public String dataEntrega;
	public double totalPedido;
	public StatusPedido statusPedido = StatusPedido.ABERTO;
	public List<ItemPedido> itens =  new ArrayList<>();
	
	public Pedido() {
		dataEmissao = LocalDate.now().format(DateTimeFormatter.ofPattern("d/MM/yyyy")).toString();
	}
	
}
