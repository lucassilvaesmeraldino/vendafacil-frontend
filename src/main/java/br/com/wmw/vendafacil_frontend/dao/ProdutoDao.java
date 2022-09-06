package br.com.wmw.vendafacil_frontend.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.wmw.vendafacil_frontend.domain.Produto;
import br.com.wmw.vendafacil_frontend.exception.PersistenceException;
import br.com.wmw.vendafacil_frontend.util.DatabaseManager;
import totalcross.sql.PreparedStatement;
import totalcross.sql.ResultSet;
import totalcross.sql.Statement;
import totalcross.sys.Vm;

public class ProdutoDao {
	
	public void insertProdutosList(List<Produto> produtos) {
		try {
			boolean success = true;
			final PreparedStatement pst = DatabaseManager.getConnection().prepareStatement("INSERT OR IGNORE INTO TB_PRODUTO (CD_PRODUTO, NM_PRODUTO, PRECO) VALUES (?, ?, ?)");
			for(Produto produto : produtos) {
				pst.clearParameters();
				pst.setLong(1, produto.getCodigo());
				pst.setString(2, produto.getNome());
				pst.setDouble(3, produto.getPreco());
				int inserted = pst.executeUpdate();
				if(inserted <= 0) success = false;
			}
			if(!success) throw new PersistenceException("Erro ao inserir produtos da lista.");
		} catch (final SQLException e) {
			Vm.debug(e.getMessage());
		}
	}

	public List<Produto> findAll() {
		final List<Produto> produtos = new ArrayList<>();
		try(Statement st = DatabaseManager.getConnection().createStatement()) {
			final ResultSet rs = st.executeQuery("SELECT CD_PRODUTO, NM_PRODUTO, PRECO FROM TB_PRODUTO");
			while (rs.next()) {
				produtos.add(new Produto(rs.getLong("CD_PRODUTO"), rs.getString("NM_PRODUTO"), rs.getDouble("PRECO")));
			}
			rs.close();
		} catch (final SQLException e) {
			Vm.debug(e.getMessage());
		}
		return produtos;
	}
	
	public Produto findByCodigo(Long codigo) {
		Produto produto = new Produto();
		try{
			final PreparedStatement pst = DatabaseManager.getConnection().prepareStatement("SELECT CD_PRODUTO, NM_PRODUTO, PRECO FROM TB_PRODUTO WHERE CD_PRODUTO = ?");
			pst.setLong(1, codigo);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				produto.setCodigo(rs.getLong("CD_PRODUTO"));
				produto.setNome(rs.getString("NM_PRODUTO"));
				produto.setPreco(rs.getDouble("PRECO"));
			}
			pst.close();
			rs.close();
		}catch(final SQLException e) {
			Vm.debug(e.getMessage());
		}
		return produto;
	}

	public void removeAll() {
		try(Statement st = DatabaseManager.getConnection().createStatement()){
			st.execute("DELETE FROM TB_PRODUTO");
		} catch (final SQLException e) {
			Vm.debug(e.getMessage());
		}
	}
}
