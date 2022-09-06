package br.com.wmw.vendafacil_frontend.ui;

import br.com.wmw.vendafacil_frontend.dao.ClienteDao;
import br.com.wmw.vendafacil_frontend.domain.Cliente;
import br.com.wmw.vendafacil_frontend.domain.Pedido;
import br.com.wmw.vendafacil_frontend.util.Colors;
import br.com.wmw.vendafacil_frontend.util.DateUtils;
import br.com.wmw.vendafacil_frontend.util.Fonts;
import br.com.wmw.vendafacil_frontend.util.Images;
import br.com.wmw.vendafacil_frontend.util.MaterialConstants;
import br.com.wmw.vendafacil_frontend.util.Messages;
import totalcross.sys.Settings;
import totalcross.ui.Button;
import totalcross.ui.ComboBox;
import totalcross.ui.Container;
import totalcross.ui.Edit;
import totalcross.ui.ImageControl;
import totalcross.ui.Label;
import totalcross.ui.Window;
import totalcross.ui.dialog.MessageBox;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.event.EventHandler;
import totalcross.ui.image.Image;

public class CreatePedidoWindow extends Window {
	
	private Pedido pedido;

	private Button btnVoltar;
	private Button btnProximo;
	
	private ComboBox cbClientes;
	private Edit editDataEntrega;
	
	private ClienteDao clienteDao;
	private Cliente[] clientesArray;
	
	private boolean updating;
	
	public CreatePedidoWindow() {
		btnVoltar = new Button("Voltar");
		btnProximo = new Button("Proximo");
		
		clienteDao = new ClienteDao();
		clientesArray = clienteDao.findAllOnFormatArray();
		cbClientes = new ComboBox(clientesArray);
		
		editDataEntrega = new Edit("99/99/9999");
		editDataEntrega.setMode(Edit.DATE, true);
		editDataEntrega.setValidChars("0123456789");
		editDataEntrega.lineColor = Colors.WHITE;
	}
	
	public CreatePedidoWindow(Pedido pedido, boolean updating) {
		this();
		this.updating = updating;
		this.pedido = pedido == null && !updating ? new Pedido() : pedido;
		
		if(pedido != null) {
			editDataEntrega.setText(pedido.getDataEntrega());
			Cliente cliente = clienteDao.findByCodigo(pedido.getCodigoCliente());
			cbClientes.setSelectedItem(cliente);
		}
	}

	@Override
	protected void onPopup() {
		String title = "Novo Pedido";
		Image image = Images.getIconeAdicionar();
		int gap = 3;
		
		if(updating) {
			title = "Alterar Pedido";
			image = Images.getIconeEditar();
			gap = 0;
		}

		Container headContainer = new Container();
		add(headContainer, LEFT+MaterialConstants.BORDER_SPACING, TOP+MaterialConstants.BORDER_SPACING, 
				FILL-MaterialConstants.BORDER_SPACING, PARENTSIZE+20);
				
		ImageControl iconeAdicionar = new ImageControl(image);
		headContainer.add(iconeAdicionar, LEFT, TOP+gap);
		
		Label titleLabel = new Label(title);
		titleLabel.setFont(Fonts.latoBoldPlus12);
		headContainer.add(titleLabel, AFTER + MaterialConstants.COMPONENT_SPACING, TOP);
		
		Container bodyContainer = new Container();
		add(bodyContainer, LEFT+MaterialConstants.BORDER_SPACING, AFTER,
				FILL-MaterialConstants.BORDER_SPACING, PARENTSIZE+80);
		
		Label lblCbClientes = new Label("Informe o cliente referente a este pedido:");
		bodyContainer.add(lblCbClientes, LEFT, TOP);
		bodyContainer.add(cbClientes, LEFT, AFTER+MaterialConstants.COMPONENT_SPACING, FILL,
				MaterialConstants.COMBOBOX_HEIGHT);
		
		Label lblEditDataEntrega = new Label("Em qual data ele será entregue?");
		bodyContainer.add(lblEditDataEntrega, LEFT, AFTER+MaterialConstants.COMPONENT_SPACING);
		bodyContainer.add(editDataEntrega, LEFT, AFTER+MaterialConstants.COMPONENT_SPACING, FILL,
				MaterialConstants.EDIT_HEIGHT);
		
		Container footerContainer = new Container();
		add(footerContainer, LEFT+MaterialConstants.BORDER_SPACING, BOTTOM-MaterialConstants.BORDER_SPACING,
				FILL-MaterialConstants.BORDER_SPACING,PARENTSIZE+20);
		
		footerContainer.add(this.btnVoltar, LEFT, BOTTOM);
		footerContainer.add(btnProximo, RIGHT, BOTTOM);
	}

	@Override
	public <H extends EventHandler> void onEvent(final Event<H> event) {
		if (event.type == ControlEvent.PRESSED) {
			if (event.target == this.btnProximo) {
				nextScreen();
			} else if (event.target == btnVoltar) {
				InitialMenu initialMenu = new InitialMenu();
				initialMenu.popup();
			}
		}
		super.onEvent(event);
	}
	
	private void nextScreen() {
		if(validateFields()) {
			populatePedido();
			AddItensPedidoWindow addItensPedidoWindow = new AddItensPedidoWindow(pedido, updating);
			addItensPedidoWindow.popup();
		}
	}
	
	private void populatePedido(){
		pedido.setCodigoCliente(clientesArray[cbClientes.getSelectedIndex()].getCodigo());
		pedido.setDataEntrega(editDataEntrega.getText());

	}
	
	private boolean validateFields() {
		String dataEntregaWithMask = editDataEntrega.getText();
		String dataEntregaWithoutMask = editDataEntrega.getTextWithoutMask();
		if(cbClientes.getSelectedIndex() == -1) {
			new MessageBox(Messages.TYPE_WARNING, Messages.MESSAGE_CLIENTINCORRECT_EMPTY).popup();
			return false;
		}
		if(dataEntregaWithMask.isEmpty()) {
			new MessageBox(Messages.TYPE_WARNING, Messages.MESSAGE_DATEINCORRECT_EMPTY).popup();
			return false;
		}
		if(dataEntregaWithoutMask.length() < 8) {
			new MessageBox(Messages.TYPE_WARNING, Messages.MESSAGE_DATEINCORRECT_LENGTH).popup();
			return false;
		}
		int dayDataEntrega = Integer.parseInt(dataEntregaWithoutMask.substring(0, 2));
		if(dayDataEntrega <= 0 || dayDataEntrega > 31) {
			new MessageBox(Messages.TYPE_WARNING, Messages.MESSAGE_DATEINCORRECT_DAY).popup();
			return false;
		}
		int monthDataEntrega = Integer.parseInt(dataEntregaWithoutMask.substring(2, 4));
		if(monthDataEntrega <= 0 || monthDataEntrega > 12) {
			new MessageBox(Messages.TYPE_WARNING, Messages.MESSAGE_DATEINCORRECT_MONTH).popup();
			return false;
		}
		int yearDataEntrega = Integer.parseInt(dataEntregaWithoutMask.substring(4));
		if(yearDataEntrega <= 0) {
			new MessageBox(Messages.TYPE_WARNING, Messages.MESSAGE_DATEINCORRECT_YEAR).popup();
			return false;
		}
		if(!DateUtils.isPresentOrFutureDate(dataEntregaWithMask, Settings.DATE_DMY)) {
			new MessageBox(Messages.TYPE_WARNING, Messages.MESSAGE_DATEINCORRECT_INVALID).popup();
			return false;
		}
		return true;
	}	
}
