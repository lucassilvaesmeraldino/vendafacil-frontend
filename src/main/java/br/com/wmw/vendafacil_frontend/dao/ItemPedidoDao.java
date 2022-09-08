package br.com.wmw.vendafacil_frontend.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.wmw.vendafacil_frontend.domain.ItemPedido;
import br.com.wmw.vendafacil_frontend.util.DatabaseManager;
import totalcross.sql.Connection;
import totalcross.sql.PreparedStatement;
import totalcross.sql.ResultSet;
import totalcross.sql.Statement;
import totalcross.sys.Vm;

public class ItemPedidoDao {

	public boolean insert(ItemPedido item) throws SQLException {
		Connection connection = DatabaseManager.getConnection();
		try(PreparedStatement ps = connection.
				prepareStatement("INSERT INTO TB_ITEMPEDIDO (NM_SEQUENCIA, QUANTIDADE, PRECO_UNITARIO, DSC_PORCENTO, DSC_VALOR, TOTAL, CD_PRODUTO, CD_PEDIDO) "
						+ "VALUES (?,?,?,?,?,?,?,?)")) {
			ps.setLong(1, item.getNumeroSequencia());
			ps.setInt(2, item.getQuantidade());
			ps.setDouble(3, item.getPrecoUnitario());
			ps.setDouble(4, item.getDescontoEmPorcentagem());
			ps.setDouble(5, item.getDescontoEmValor());
			ps.setDouble(6, item.getTotalItem());
			ps.setLong(7, item.getCodigoProduto());
			ps.setLong(8, item.getCodigoPedido());
			
			int inserted = ps.executeUpdate();
			
			return inserted > 0;
		}		
	}
	
	public boolean insertItensByPedido(List<ItemPedido> itens, Integer codigoPedido) throws SQLException{
		boolean success = true;
		
		int nmSequencia = 1;
		for(ItemPedido item : itens) {
			item.setCodigoPedido(codigoPedido);
			item.setNumeroSequencia(nmSequencia++);
			boolean created = insert(item);
			if(!created) success = false;
		}
		
		return success;
	}
	
	public boolean updateItensByPedido(List<ItemPedido> itens, Integer codigoPedido) throws SQLException{
		boolean success = true;
		Connection connection = DatabaseManager.getConnection();
		try (Statement st = connection.createStatement()){
			if(st.executeUpdate("DELETE FROM TB_ITEMPEDIDO WHERE CD_PEDIDO = " + codigoPedido) == 0) success = false;
			
			int nmSequencia = 1;
			for(ItemPedido item : itens) {
				item.setCodigoPedido(codigoPedido);
				item.setNumeroSequencia(nmSequencia++);
				boolean created = insert(item);
				if(!created) success = false;
			}	
		}
		return success;
	}
	
	public List<ItemPedido> findByCodigoPedido(Integer codPedido) {
		List<ItemPedido> itens = new ArrayList<>();
		try{
			Connection connection = DatabaseManager.getConnection();
			Statement st = connection.createStatement();
			try {
				try(ResultSet rs = st.executeQuery("SELECT NM_SEQUENCIA, QUANTIDADE, PRECO_UNITARIO, DSC_PORCENTO, DSC_VALOR, TOTAL, CD_PRODUTO, CD_PEDIDO FROM TB_ITEMPEDIDO WHERE CD_PEDIDO = " + codPedido)) {
					while(rs.next()) {
						ItemPedido item = new ItemPedido();
						item.setNumeroSequencia(rs.getInt("NM_SEQUENCIA"));
						item.setQuantidade(rs.getInt("QUANTIDADE"));
						item.setPrecoUnitario(rs.getDouble("PRECO_UNITARIO"));
						item.setDescontoEmPorcentagem(rs.getDouble("DSC_PORCENTO"));
						item.setDescontoEmValor(rs.getDouble("DSC_VALOR"));
						item.setTotalItem(rs.getDouble("TOTAL"));
						item.setCodigoProduto(rs.getLong("CD_PRODUTO"));
						item.setCodigoPedido(rs.getInt("CD_PEDIDO"));
						itens.add(item);
					}
				}
			} finally {
				st.close();
			}
		} catch (SQLException e) {
			Vm.debug(e.getMessage());
		}
		
		return itens;
	}
}
