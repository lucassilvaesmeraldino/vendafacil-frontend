package br.com.wmw.vendafacil_frontend.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import br.com.wmw.vendafacil_frontend.dao.ClienteDao;
import br.com.wmw.vendafacil_frontend.domain.Pedido;
import br.com.wmw.vendafacil_frontend.domain.StatusPedido;

public class PedidoService {

	public Double calculateValorTotal(Pedido pedido) {
		return pedido.itens.stream().mapToDouble(i -> i.totalItem).sum();
	}
	
	public String[] pedidoToArray(Pedido pedido, ClienteDao clienteDao) {
		String[] pedidoDadosArray = new String[4];
		pedidoDadosArray[0] = "#" + String.valueOf(pedido.codigoPedido) + " - " + 
				String.valueOf(clienteDao.findByCodigo(pedido.codigoCliente));
		pedidoDadosArray[1] = pedido.statusPedido.name();
		pedidoDadosArray[2] = !pedido.itens.isEmpty() ? String.format("Com %d item(s)", pedido.itens.size()): "Sem itens"; 
		pedidoDadosArray[3] = "R$" + String.valueOf(
				new BigDecimal(pedido.totalPedido).setScale(2, RoundingMode.HALF_UP).doubleValue()).replace(".",",");
		return pedidoDadosArray;
	}
	
}
