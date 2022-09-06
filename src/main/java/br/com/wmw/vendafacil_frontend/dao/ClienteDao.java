package br.com.wmw.vendafacil_frontend.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.wmw.vendafacil_frontend.domain.Cliente;
import br.com.wmw.vendafacil_frontend.exception.PersistenceException;
import br.com.wmw.vendafacil_frontend.util.DatabaseManager;
import totalcross.sql.PreparedStatement;
import totalcross.sql.ResultSet;
import totalcross.sql.Statement;
import totalcross.sys.Vm;

public class ClienteDao {
	
	public void insertClientesList(List<Cliente> clientes) {
		try {
			boolean success = true;
			final PreparedStatement pst = DatabaseManager.getConnection().prepareStatement("INSERT OR IGNORE INTO TB_CLIENTE (CD_CLIENTE, NM_CLIENTE) VALUES (?, ?)");
			for(Cliente cliente : clientes) {
				pst.clearParameters();
				pst.setLong(1, cliente.getCodigo());
				pst.setString(2, cliente.getNome());	
				int inserted = pst.executeUpdate();
				if(inserted <= 0) success = false;
			}
			if(!success) throw new PersistenceException("Erro ao inserir cadastros de clientes vindo da API.");
		} catch (final SQLException e) {
			Vm.debug(e.getMessage());
		}
	}

	public Cliente[] findAllOnFormatArray() {
		final List<Cliente> clientesList = new ArrayList<>();
		try (Statement st = DatabaseManager.getConnection().createStatement()){
			final ResultSet rs = st.executeQuery("SELECT CD_CLIENTE, NM_CLIENTE FROM TB_CLIENTE");
			while(rs.next()) {
				clientesList.add(new Cliente(rs.getLong("CD_CLIENTE"), rs.getString("NM_CLIENTE")));
			}
			rs.close();
		} catch (final SQLException e) {
			Vm.debug(e.getMessage());
		}
		Cliente[] clientesArray = new Cliente[clientesList.size()];
		for(int i = 0; i < clientesList.size();i++) {
			clientesArray[i] = clientesList.get(i);
		}
		return clientesArray;
	}
	
	public Cliente findByCodigo(Long codigo) {
		Cliente cliente = new Cliente();
		try{
			final PreparedStatement pst = DatabaseManager.getConnection().prepareStatement("SELECT CD_CLIENTE, NM_CLIENTE FROM TB_CLIENTE WHERE CD_CLIENTE = ?");
			pst.setLong(1, codigo);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				cliente.setCodigo(rs.getLong("CD_CLIENTE"));
				cliente.setNome(rs.getString("NM_CLIENTE"));
			}
			pst.close();
			rs.close();
		}catch(final SQLException e) {
			Vm.debug(e.getMessage());
		}
		return cliente;
	}
	
	public void removeAll() {
		try (Statement st = DatabaseManager.getConnection().createStatement()){
			st.execute("DELETE FROM TB_CLIENTE");
		} catch (final SQLException e) {
			Vm.debug(e.getMessage());
		}
	}

}
