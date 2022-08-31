package br.com.wmw.vendafacil_frontend;

import br.com.wmw.vendafacil_frontend.api.PedidoApi;
import br.com.wmw.vendafacil_frontend.dao.PedidoDao;
import br.com.wmw.vendafacil_frontend.domain.StatusPedido;
import br.com.wmw.vendafacil_frontend.exception.ApiException;
import br.com.wmw.vendafacil_frontend.exception.PersistenceException;
import br.com.wmw.vendafacil_frontend.ui.InitialMenu;
import br.com.wmw.vendafacil_frontend.util.DatabaseManager;
import br.com.wmw.vendafacil_frontend.util.Images;
import totalcross.sys.Settings;
import totalcross.ui.MainWindow;
import totalcross.ui.dialog.MessageBox;

public class VendaFacil extends MainWindow{

	public VendaFacil() {
		setUIStyle(Settings.MATERIAL_UI);
	}
	
	@Override
	public void initUI() {
		DatabaseManager.loadTables();
		Images.loadImages();
		try {
			DatabaseManager.syncDataWithBackend();
			PedidoApi.sendPedidos(new PedidoDao().findAllByStatus(StatusPedido.FECHADO));
		} catch (ApiException|PersistenceException e) {
			new MessageBox("Error", e.getMessage()).popup();
		}
		InitialMenu initialMenu = new InitialMenu();
		initialMenu.popup();
	}
}
