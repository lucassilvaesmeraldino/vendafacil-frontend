package br.com.wmw.vendafacil_frontend.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.wmw.vendafacil_frontend.domain.ItemPedido;
import br.com.wmw.vendafacil_frontend.domain.Pedido;
import br.com.wmw.vendafacil_frontend.domain.StatusPedido;
import br.com.wmw.vendafacil_frontend.exception.PersistenceException;
import br.com.wmw.vendafacil_frontend.util.DatabaseManager;
import totalcross.sql.PreparedStatement;
import totalcross.sql.ResultSet;
import totalcross.sql.Statement;
import totalcross.sys.Vm;

public class PedidoDao {
	
	public void insert(Pedido pedido) {
		try {
			PreparedStatement ps = DatabaseManager.getConnection().
					prepareStatement("INSERT INTO TB_PEDIDO (DATA_EMISSAO, DATA_ENTREGA, CD_CLIENTE, CD_STATUSPEDIDO, TOTAL_PEDIDO) VALUES (?,?,?,?,?)");
			ps.setString(1, pedido.dataEmissao);
			ps.setString(2, pedido.dataEntrega);
			ps.setLong(3, pedido.codigoCliente);
			ps.setLong(4, pedido.statusPedido.ordinal());
			ps.setDouble(5, pedido.totalPedido);
					
			int affectedRows = ps.executeUpdate();
			if(affectedRows == 0) throw new PersistenceException("Erro ao inserir o pedido.");
			ps.close();
			
			boolean inserted = new ItemPedidoDao().insertItensByPedido(pedido.itens, getLastInsertedId());
			if(!inserted) throw new PersistenceException("Erro ao inserir itens do pedido.");
			
		} catch (SQLException e) {
			Vm.debug(e.getMessage());
		}
		
	}
	
	public void update(Pedido pedido) {
		try {
			PreparedStatement ps = DatabaseManager.getConnection().
					prepareStatement("UPDATE TB_PEDIDO SET DATA_ENTREGA = ?, CD_CLIENTE = ?, CD_STATUSPEDIDO = ?, TOTAL_PEDIDO = ? WHERE CD_PEDIDO = ?");
			ps.setString(1, pedido.dataEntrega);
			ps.setLong(2, pedido.codigoCliente);
			ps.setLong(3, pedido.statusPedido.ordinal());
			ps.setDouble(4, pedido.totalPedido);
			ps.setDouble(5, pedido.codigoPedido);
					
			int affectedRows = ps.executeUpdate();
			if(affectedRows == 0) throw new PersistenceException("Erro ao atualizar o pedido.");
			ps.close();
			
			boolean updated = new ItemPedidoDao().updateItensByPedido(pedido.itens, pedido.codigoPedido);
			if(!updated) throw new PersistenceException("Erro ao atualizar itens do pedido.");
			
		} catch(SQLException e) {
			Vm.debug(e.getMessage());
		}
	}
	
	public List<Pedido> findAll() {
		List<Pedido> pedidosList = new ArrayList<>();
		try {
			Statement st = DatabaseManager.getConnection().createStatement();
			ResultSet rs = st.executeQuery("SELECT CD_PEDIDO, CD_CLIENTE, CD_STATUSPEDIDO, DATA_ENTREGA, DATA_EMISSAO, TOTAL_PEDIDO"
					+ " FROM TB_PEDIDO");
			while(rs.next()) {
				Pedido pedido = new Pedido();
				pedido.codigoPedido = rs.getInt("CD_PEDIDO");
				pedido.codigoCliente = rs.getInt("CD_CLIENTE");
				pedido.statusPedido = StatusPedido.getByCodigo(rs.getInt("CD_STATUSPEDIDO"));
				pedido.dataEntrega = rs.getString("DATA_ENTREGA");
				pedido.dataEntrega = rs.getString("DATA_ENTREGA");
				pedido.dataEmissao = rs.getString("DATA_EMISSAO");
				pedido.totalPedido = rs.getDouble("TOTAL_PEDIDO");
				
				List<ItemPedido> itensPedido = new ItemPedidoDao().findByCodigoPedido(pedido.codigoPedido);
				pedido.itens = itensPedido;
				
				pedidosList.add(pedido);
			}
		} catch (SQLException e) {
			Vm.debug(e.getMessage());
		}
		return pedidosList;
		
	}
	
	public List<Pedido> findAllByStatus(StatusPedido statusPedido) {
		List<Pedido> pedidosList = new ArrayList<>();
		try {
			Statement st = DatabaseManager.getConnection().createStatement();
			ResultSet rs = st.executeQuery("SELECT CD_PEDIDO, CD_CLIENTE, CD_STATUSPEDIDO, DATA_ENTREGA, DATA_EMISSAO, TOTAL_PEDIDO"
					+ " FROM TB_PEDIDO WHERE CD_STATUSPEDIDO = " + statusPedido.ordinal());
			while(rs.next()) {
				Pedido pedido = new Pedido();
				pedido.codigoPedido = rs.getInt("CD_PEDIDO");
				pedido.codigoCliente = rs.getInt("CD_CLIENTE");
				pedido.statusPedido = StatusPedido.getByCodigo(rs.getInt("CD_STATUSPEDIDO"));
				pedido.dataEntrega = rs.getString("DATA_ENTREGA");
				pedido.dataEntrega = rs.getString("DATA_ENTREGA");
				pedido.dataEmissao = rs.getString("DATA_EMISSAO");
				pedido.totalPedido = rs.getDouble("TOTAL_PEDIDO");
				
				List<ItemPedido> itensPedido = new ItemPedidoDao().findByCodigoPedido(pedido.codigoPedido);
				pedido.itens = itensPedido;
				
				pedidosList.add(pedido);
			}
		} catch (SQLException e) {
			Vm.debug(e.getMessage());
		}
		return pedidosList;
		
	}
	
	private int getLastInsertedId() throws SQLException {
		Statement st = DatabaseManager.getConnection().createStatement();
		ResultSet rs = st.executeQuery("SELECT MAX(CD_PEDIDO) AS MAX_CDPEDIDO FROM TB_PEDIDO");
		int lastIdInserted = rs.getInt("MAX_CDPEDIDO");
		rs.close();
		st.close();
		return lastIdInserted;
	}
}
