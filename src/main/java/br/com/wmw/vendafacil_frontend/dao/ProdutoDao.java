package br.com.wmw.vendafacil_frontend.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.wmw.vendafacil_frontend.domain.Produto;
import br.com.wmw.vendafacil_frontend.exception.PersistenceException;
import br.com.wmw.vendafacil_frontend.util.DatabaseManager;
import totalcross.sql.Connection;
import totalcross.sql.PreparedStatement;
import totalcross.sql.ResultSet;
import totalcross.sql.Statement;
import totalcross.sys.Vm;

public class ProdutoDao {
	
	public void insertProdutosList(List<Produto> produtos) {
		try {
			boolean success = true;
			Connection connection = DatabaseManager.getConnection();
			try {
				PreparedStatement pst = connection.prepareStatement("INSERT OR IGNORE INTO TB_PRODUTO (CD_PRODUTO, NM_PRODUTO, PRECO) VALUES (?, ?, ?)");
				try {
					for(Produto produto : produtos) {
						pst.clearParameters();
						pst.setLong(1, produto.getCodigo());
						pst.setString(2, produto.getNome());
						pst.setDouble(3, produto.getPreco());
						int inserted = pst.executeUpdate();
						if(inserted <= 0) success = false;
					}
					if(!success) throw new PersistenceException("Erro ao inserir produtos da lista.");
				} finally {
					pst.close();
				}
				
			} finally {
				connection.close();
			}
		} catch (final SQLException e) {
			Vm.debug(e.getMessage());
		}
	}

	public List<Produto> findAll() {
		final List<Produto> produtos = new ArrayList<>();
		try {
			Connection connection = DatabaseManager.getConnection();
			try {
				Statement st = connection.createStatement();
				try {
					try(ResultSet rs = st.executeQuery("SELECT CD_PRODUTO, NM_PRODUTO, PRECO FROM TB_PRODUTO")) {
						while (rs.next()) {
							produtos.add(new Produto(rs.getLong("CD_PRODUTO"), rs.getString("NM_PRODUTO"), rs.getDouble("PRECO")));
						}
					}
				} finally {
					st.close();
				}
			} finally {
				connection.close();
			}
			
		} catch (final SQLException e) {
			Vm.debug(e.getMessage());
		}
		return produtos;
	}
	
	public Produto findByCodigo(Long codigo) {
		Produto produto = new Produto();
		try{
			Connection connection = DatabaseManager.getConnection();
			try {
				PreparedStatement pst = connection.prepareStatement("SELECT CD_PRODUTO, NM_PRODUTO, PRECO FROM TB_PRODUTO WHERE CD_PRODUTO = ?");
				try {
					pst.setLong(1, codigo);
					try(ResultSet rs = pst.executeQuery()){
						while(rs.next()) {
							produto.setCodigo(rs.getLong("CD_PRODUTO"));
							produto.setNome(rs.getString("NM_PRODUTO"));
							produto.setPreco(rs.getDouble("PRECO"));
						}
					}
				} finally {
					pst.close();
				}
			} finally {
				connection.close();
			}
		}catch(final SQLException e) {
			Vm.debug(e.getMessage());
		}
		return produto;
	}

	public void removeAll() {
		try{
			Connection connection = DatabaseManager.getConnection();
			try {
				try(Statement st = connection.createStatement()){
					st.execute("DELETE FROM TB_PRODUTO");
				}
			} finally {
				connection.close();
			}
		} catch (final SQLException e) {
			Vm.debug(e.getMessage());
		}
	}
}
