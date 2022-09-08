package br.com.wmw.vendafacil_frontend.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.wmw.vendafacil_frontend.domain.ItemPedido;
import br.com.wmw.vendafacil_frontend.domain.Pedido;
import br.com.wmw.vendafacil_frontend.domain.StatusPedido;
import br.com.wmw.vendafacil_frontend.exception.PersistenceException;
import br.com.wmw.vendafacil_frontend.util.DatabaseManager;
import totalcross.sql.Connection;
import totalcross.sql.PreparedStatement;
import totalcross.sql.ResultSet;
import totalcross.sql.Statement;
import totalcross.sys.Vm;

public class PedidoDao {
	
	public void insert(Pedido pedido) {
		try {
			Connection connection = DatabaseManager.getConnection();
			try {
				PreparedStatement ps = connection.
						prepareStatement("INSERT INTO TB_PEDIDO (DATA_EMISSAO, DATA_ENTREGA, CD_CLIENTE, CD_STATUSPEDIDO, TOTAL_PEDIDO) VALUES (?,?,?,?,?)");
				try {
					ps.setString(1, pedido.getDataEmissao());
					ps.setString(2, pedido.getDataEntrega());
					ps.setLong(3, pedido.getCodigoCliente());
					ps.setLong(4, pedido.getStatusPedido().ordinal());
					ps.setDouble(5, pedido.getTotalPedido());
							
					int affectedRows = ps.executeUpdate();
					if(affectedRows == 0) throw new PersistenceException("Erro ao inserir o pedido.");
					
				} finally {
					ps.close();
				}

				boolean inserted = new ItemPedidoDao().insertItensByPedido(pedido.getItens(), getLastInsertedId());
				if(!inserted) throw new PersistenceException("Erro ao inserir itens do pedido.");
				
			} finally {
				connection.close();
			}
			
		} catch (SQLException e) {
			Vm.debug(e.getMessage());
		}
		
	}
	
	public void update(Pedido pedido) {
			try {
				Connection connection = DatabaseManager.getConnection();
				try {
					PreparedStatement ps = connection.
							prepareStatement("UPDATE TB_PEDIDO SET DATA_ENTREGA = ?, CD_CLIENTE = ?, CD_STATUSPEDIDO = ?, TOTAL_PEDIDO = ? WHERE CD_PEDIDO = ?");
					try {
						ps.setString(1, pedido.getDataEntrega());
						ps.setLong(2, pedido.getCodigoCliente());
						ps.setLong(3, pedido.getStatusPedido().ordinal());
						ps.setDouble(4, pedido.getTotalPedido());
						ps.setDouble(5, pedido.getCodigoPedido());
								
						int affectedRows = ps.executeUpdate();
						if(affectedRows == 0) throw new PersistenceException("Erro ao atualizar o pedido.");
					} finally {
						ps.close();
					}
					
					boolean updated = new ItemPedidoDao().updateItensByPedido(pedido.getItens(), pedido.getCodigoPedido());
					if(!updated) throw new PersistenceException("Erro ao atualizar itens do pedido.");
					
				} finally {
					connection.close();
				}
				
			} catch(SQLException e) {
				Vm.debug(e.getMessage());
			}
	}
	
	public List<Pedido> findAll() {
		List<Pedido> pedidosList = new ArrayList<>();
		try {
			Connection connection = DatabaseManager.getConnection();
			try{
				Statement st = connection.createStatement();
				try {
					ResultSet rs = st.executeQuery("SELECT CD_PEDIDO, CD_CLIENTE, CD_STATUSPEDIDO, DATA_ENTREGA, DATA_EMISSAO, TOTAL_PEDIDO"
							+ " FROM TB_PEDIDO");
					while(rs.next()) {
						Pedido pedido = new Pedido();
						pedido.setCodigoPedido(rs.getInt("CD_PEDIDO"));
						pedido.setCodigoCliente(rs.getLong("CD_CLIENTE"));
						pedido.setStatusPedido(StatusPedido.getByCodigo(rs.getInt("CD_STATUSPEDIDO")));
						pedido.setDataEntrega(rs.getString("DATA_ENTREGA"));
						pedido.setDataEmissao(rs.getString("DATA_EMISSAO"));
						pedido.setTotalPedido(rs.getDouble("TOTAL_PEDIDO"));
						
						List<ItemPedido> itensPedido = new ItemPedidoDao().findByCodigoPedido(pedido.getCodigoPedido());
						pedido.setItens(itensPedido);
						
						pedidosList.add(pedido);
					}
					
				} finally {
					st.close();
				}
			
			} finally {
				connection.close();
			}
		} catch (SQLException e) {
			Vm.debug(e.getMessage());
		}
		
		return pedidosList;	
	}
	
	public List<Pedido> findAllByStatus(StatusPedido statusPedido) {
		List<Pedido> pedidosList = new ArrayList<>();
		try {
			Connection connection = DatabaseManager.getConnection();
			try {
				Statement st = connection.createStatement();
				try (ResultSet rs = st.executeQuery("SELECT CD_PEDIDO, CD_CLIENTE, CD_STATUSPEDIDO, DATA_ENTREGA, DATA_EMISSAO, TOTAL_PEDIDO"
							+ " FROM TB_PEDIDO WHERE CD_STATUSPEDIDO = " + statusPedido.ordinal())){
					while(rs.next()) {
						Pedido pedido = new Pedido();
						pedido.setCodigoPedido(rs.getInt("CD_PEDIDO"));
						pedido.setCodigoCliente(rs.getLong("CD_CLIENTE"));
						pedido.setStatusPedido(StatusPedido.getByCodigo(rs.getInt("CD_STATUSPEDIDO")));
						pedido.setDataEntrega(rs.getString("DATA_ENTREGA"));
						pedido.setDataEmissao(rs.getString("DATA_EMISSAO"));
						pedido.setTotalPedido(rs.getDouble("TOTAL_PEDIDO"));
						
						List<ItemPedido> itensPedido = new ItemPedidoDao().findByCodigoPedido(pedido.getCodigoPedido());
						pedido.setItens(itensPedido);
						
						pedidosList.add(pedido);
					}
				} finally {
					st.close();
				}
				
			} finally {
				connection.close();
			}
		} catch (SQLException e) {
			Vm.debug(e.getMessage());
		}
		return pedidosList;
		
	}
	
	private Integer getLastInsertedId() {
		int lastIdInserted = 0;
		try{
			Connection connection = DatabaseManager.getConnection();
			try {
				Statement st = connection.createStatement();
				try {
					try (ResultSet rs = st.executeQuery("SELECT MAX(CD_PEDIDO) AS MAX_CDPEDIDO FROM TB_PEDIDO")){
						lastIdInserted = rs.getInt("MAX_CDPEDIDO");
					}
				} finally {
					st.close();
				}
			} finally {
				connection.close();
			}
			
		} catch (SQLException e) {
			Vm.debug(e.getMessage());
		}

		return lastIdInserted;
	}
}
