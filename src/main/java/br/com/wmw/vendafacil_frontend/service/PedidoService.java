package br.com.wmw.vendafacil_frontend.service;

import java.math.RoundingMode;

import br.com.wmw.vendafacil_frontend.dao.ClienteDao;
import br.com.wmw.vendafacil_frontend.domain.ItemPedido;
import br.com.wmw.vendafacil_frontend.domain.Pedido;
import br.com.wmw.vendafacil_frontend.util.DoubleUtils;

public class PedidoService {

	public Double calculateValorTotal(Pedido pedido) {
		double sum = 0;
		for(ItemPedido item : pedido.getItens()) {
			sum += item.getTotalItem();
		}
		return sum;
	}
	
	public String[] pedidoToArray(Pedido pedido, ClienteDao clienteDao) {
		String[] pedidoDadosArray = new String[4];
		pedidoDadosArray[0] = "#" + pedido.getCodigoPedido() + " - " + 
				clienteDao.findByCodigo(pedido.getCodigoCliente());
		pedidoDadosArray[1] = pedido.getStatusPedido().name();
		pedidoDadosArray[2] = !pedido.getItens().isEmpty() ? "Com " + pedido.getItens().size() + " item(s)" : "Sem itens"; 
		pedidoDadosArray[3] = "R$" + String.valueOf(DoubleUtils.round(pedido.getTotalPedido(), RoundingMode.HALF_UP)).replace(".",",");
		return pedidoDadosArray;
	}
	
	public boolean hasEmptyItemList(Pedido pedido) {
		return pedido.getItens().isEmpty();
	}
	
}