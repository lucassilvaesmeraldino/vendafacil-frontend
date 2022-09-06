package br.com.wmw.vendafacil_frontend;

import br.com.wmw.vendafacil_frontend.api.PedidoApi;
import br.com.wmw.vendafacil_frontend.dao.PedidoDao;
import br.com.wmw.vendafacil_frontend.domain.StatusPedido;
import br.com.wmw.vendafacil_frontend.exception.ConnectionException;
import br.com.wmw.vendafacil_frontend.exception.FailedToSendException;
import br.com.wmw.vendafacil_frontend.exception.PersistenceException;
import br.com.wmw.vendafacil_frontend.ui.InitialMenu;
import br.com.wmw.vendafacil_frontend.util.DatabaseManager;
import br.com.wmw.vendafacil_frontend.util.Images;
import br.com.wmw.vendafacil_frontend.util.Messages;
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
		} catch (FailedToSendException|PersistenceException|ConnectionException e) {
			new MessageBox(Messages.TYPE_ERROR, e.getMessage()).popup();
		}
		InitialMenu initialMenu = new InitialMenu();
		initialMenu.popup();
	}
}
