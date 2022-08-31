package br.com.wmw.vendafacil_frontend.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.wmw.vendafacil_frontend.domain.ItemPedido;
import br.com.wmw.vendafacil_frontend.util.DatabaseManager;
import totalcross.sql.PreparedStatement;
import totalcross.sql.ResultSet;
import totalcross.sql.Statement;

public class ItemPedidoDao {

	public boolean insert(ItemPedido item) throws SQLException {
		PreparedStatement ps = DatabaseManager.getConnection().
				prepareStatement("INSERT INTO TB_ITEMPEDIDO (NM_SEQUENCIA, QUANTIDADE, PRECO_UNITARIO, DSC_PORCENTO, DSC_VALOR, TOTAL, CD_PRODUTO, CD_PEDIDO) "
						+ "VALUES (?,?,?,?,?,?,?,?)");
		ps.setLong(1, item.numeroSequencia);
		ps.setInt(2, item.quantidade);
		ps.setDouble(3, item.precoUnitario);
		ps.setDouble(4, item.descontoEmPorcentagem);
		ps.setDouble(5, item.descontoEmValor);
		ps.setDouble(6, item.totalItem);
		ps.setLong(7, item.codigoProduto);
		ps.setLong(8, item.codigoPedido);
		
		int inserted = ps.executeUpdate();
		
		return inserted > 0;
	}
	
	public boolean insertItensByPedido(List<ItemPedido> itens, int codigoPedido) throws SQLException{
		boolean success = true;
		
		int nmSequencia = 1;
		for(ItemPedido item : itens) {
			item.codigoPedido = codigoPedido;
			item.numeroSequencia = nmSequencia++;
			boolean created = insert(item);
			if(!created) success = false;
		}
		
		return success;
	}
	
	public boolean updateItensByPedido(List<ItemPedido> itens, int codigoPedido) throws SQLException{
		boolean success = true;
		Statement st = DatabaseManager.getConnection().createStatement();
		if(st.executeUpdate("DELETE FROM TB_ITEMPEDIDO WHERE CD_PEDIDO = " + codigoPedido) == 0) success = false;
		
		int nmSequencia = 1;
		for(ItemPedido item : itens) {
			item.codigoPedido = codigoPedido;
			item.numeroSequencia = nmSequencia++;
			boolean created = insert(item);
			if(!created) success = false;
		}
		
		return success;
	}
	
	public List<ItemPedido> findByCodigoPedido(int codPedido) throws SQLException {
		List<ItemPedido> itens = new ArrayList<>();
		Statement st = DatabaseManager.getConnection().createStatement();
		ResultSet rs = st.executeQuery("SELECT NM_SEQUENCIA, QUANTIDADE, PRECO_UNITARIO, DSC_PORCENTO, DSC_VALOR, TOTAL, CD_PRODUTO, CD_PEDIDO FROM TB_ITEMPEDIDO WHERE CD_PEDIDO = " + codPedido);
		while(rs.next()) {
			ItemPedido item = new ItemPedido();
			item.numeroSequencia = rs.getLong("NM_SEQUENCIA");
			item.quantidade = rs.getInt("QUANTIDADE");
			item.precoUnitario = rs.getDouble("PRECO_UNITARIO");
			item.descontoEmPorcentagem = rs.getDouble("DSC_PORCENTO");
			item.descontoEmValor = rs.getDouble("DSC_VALOR");
			item.totalItem = rs.getDouble("TOTAL");
			item.codigoProduto = rs.getLong("CD_PRODUTO");
			item.codigoPedido = rs.getLong("CD_PEDIDO");
			itens.add(item);
		}
		return itens;
	}
}
