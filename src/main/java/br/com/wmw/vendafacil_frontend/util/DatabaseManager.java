package br.com.wmw.vendafacil_frontend.util;

import java.sql.SQLException;
import java.util.List;

import br.com.wmw.vendafacil_frontend.api.ClienteApi;
import br.com.wmw.vendafacil_frontend.api.ProdutoApi;
import br.com.wmw.vendafacil_frontend.dao.ClienteDao;
import br.com.wmw.vendafacil_frontend.dao.ProdutoDao;
import br.com.wmw.vendafacil_frontend.domain.Cliente;
import br.com.wmw.vendafacil_frontend.domain.Produto;
import totalcross.db.sqlite.SQLiteUtil;
import totalcross.sql.Connection;
import totalcross.sql.Statement;
import totalcross.sys.Settings;
import totalcross.sys.Vm;

public class DatabaseManager {

	private static SQLiteUtil sqlLiteUtil;

	static {
		try {
			DatabaseManager.sqlLiteUtil = new SQLiteUtil(Settings.appPath, "vendafacil.db");
		} catch (final SQLException e) {
			Vm.debug(e.getMessage());
		}
	}

	public static Connection getConnection() throws SQLException {
		return DatabaseManager.sqlLiteUtil.con();
	}

	public static void loadTables() {
		try {
			final Statement st = DatabaseManager.sqlLiteUtil.con().createStatement();
			st.execute("CREATE TABLE IF NOT EXISTS TB_CLIENTE (CD_CLIENTE bigint, NM_CLIENTE varchar(255), primary key(CD_CLIENTE))");
			st.execute("CREATE TABLE IF NOT EXISTS TB_PRODUTO (CD_PRODUTO bigint, NM_PRODUTO varchar(255), PRECO decimal, "
					+ "primary key(CD_PRODUTO))");
			st.execute("CREATE TABLE IF NOT EXISTS TB_PEDIDO "
					+ "(CD_PEDIDO integer primary key autoincrement, DATA_EMISSAO varchar(255), DATA_FINALIZACAO varchar(255), "
					+ "DATA_ENTREGA varchar(255), TOTAL_PEDIDO decimal(19,0), CD_CLIENTE bigint, CD_STATUSPEDIDO bigint,"
					+ "FOREIGN KEY(CD_CLIENTE) REFERENCES TB_CLIENTE(CD_CLIENTE))");
			st.execute("CREATE TABLE IF NOT EXISTS TB_ITEMPEDIDO (CD_ITEM integer primary key autoincrement, NM_SEQUENCIA bigint, "
					+ "QUANTIDADE integer, PRECO_UNITARIO decimal, DSC_PORCENTO decimal, DSC_VALOR decimal, TOTAL decimal,"
					+ "CD_PRODUTO bigint, CD_PEDIDO bigint, FOREIGN KEY(CD_PRODUTO) REFERENCES TB_PRODUTO(CD_PRODUTO), "
					+ "FOREIGN KEY(CD_PEDIDO) REFERENCES TB_PEDIDO(CD_PEDIDO))");
			st.close();
		} catch (SQLException e) {
			Vm.debug(e.getMessage());
		}
	}
	
	public static void syncDataWithBackend() {
		ClienteDao clienteDao = new ClienteDao();
		ProdutoDao produtoDao = new ProdutoDao();
		List<Cliente> clientesList = ClienteApi.getAllClientes();
		List<Produto> produtosList = ProdutoApi.getAllProdutos();
		if(!clientesList.isEmpty() && !produtosList.isEmpty()) {
			clienteDao.removeAll();
			produtoDao.removeAll();
			clienteDao.insertClientesList(clientesList);
			produtoDao.insertProdutosList(produtosList);
		}	
	}
}
