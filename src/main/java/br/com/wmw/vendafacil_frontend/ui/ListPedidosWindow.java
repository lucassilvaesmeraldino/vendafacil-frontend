package br.com.wmw.vendafacil_frontend.ui;

import java.util.List;

import br.com.wmw.vendafacil_frontend.dao.ClienteDao;
import br.com.wmw.vendafacil_frontend.dao.PedidoDao;
import br.com.wmw.vendafacil_frontend.domain.Pedido;
import br.com.wmw.vendafacil_frontend.domain.StatusPedido;
import br.com.wmw.vendafacil_frontend.service.PedidoService;
import br.com.wmw.vendafacil_frontend.util.Colors;
import br.com.wmw.vendafacil_frontend.util.Fonts;
import br.com.wmw.vendafacil_frontend.util.Images;
import br.com.wmw.vendafacil_frontend.util.MaterialConstants;
import totalcross.sys.Settings;
import totalcross.ui.Button;
import totalcross.ui.Container;
import totalcross.ui.Control;
import totalcross.ui.ImageControl;
import totalcross.ui.Label;
import totalcross.ui.ScrollContainer;
import totalcross.ui.Window;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.event.EventHandler;
import totalcross.ui.event.PenEvent;
import totalcross.ui.gfx.Color;

public class ListPedidosWindow extends Window {

	private ScrollContainer pedidoScrollContainer;
	private List<Pedido> pedidosList;
	private PedidoDao pedidoDao;
	private ClienteDao clienteDao;
	private PedidoService pedidoService;
	
	private Container headContainer;
	private Container footerContainer;

	private final Button btnIncluirPedido;
	private final Button btnVoltar;

	public ListPedidosWindow() {
		pedidoDao = new PedidoDao();
		clienteDao = new ClienteDao();
		pedidosList = pedidoDao.findAll();
		pedidoService = new PedidoService();
		pedidoScrollContainer = new ScrollContainer();
		headContainer = new Container();
		footerContainer = new Container();
		
		this.btnIncluirPedido = new Button("Novo Pedido");
		btnIncluirPedido.setBackForeColors(Colors.BLUE, Colors.WHITE);
		btnVoltar = new Button("Voltar");
	}

	@Override
	public void onPopup() {
		constructScreen();
	}
	
	private void constructScreen() {
		add(headContainer, LEFT+MaterialConstants.BORDER_SPACING, TOP+MaterialConstants.BORDER_SPACING, 
				FILL-MaterialConstants.BORDER_SPACING, PARENTSIZE+10);
		
		ImageControl iconeListagem = new ImageControl(Images.getIconeListagem());
		headContainer.add(iconeListagem, LEFT, TOP+2);
		
		Label titleLabel = new Label("Pedidos criados");
		titleLabel.setFont(Fonts.latoBoldPlus12);
		headContainer.add(titleLabel, AFTER+MaterialConstants.COMPONENT_SPACING, TOP);
		
		add(pedidoScrollContainer, LEFT+MaterialConstants.COMPONENT_SPACING+10, AFTER+MaterialConstants.BORDER_SPACING, FILL
				-MaterialConstants.COMPONENT_SPACING, getScrollContainerSize());
		
		loadPedidosList();
	
		add(footerContainer, LEFT+MaterialConstants.BORDER_SPACING, BOTTOM-MaterialConstants.BORDER_SPACING, 
				FILL-MaterialConstants.BORDER_SPACING, PARENTSIZE+10);
		
		footerContainer.add(this.btnVoltar, Control.LEFT, Control.BOTTOM);
		footerContainer.add(this.btnIncluirPedido, Control.RIGHT, Control.BOTTOM);
	}
	
	private int getScrollContainerSize() {
		int size = (pedidosList.size()*51) + (pedidosList.size()*6) + MaterialConstants.COMPONENT_SPACING;
		size = size > Settings.screenHeight - 220 ? Settings.screenHeight - 220 : size;
		return size;
	}
	
	private void loadPedidosList() {
		int index = 0;
		for(Pedido pedido : pedidosList) {
			String[] dadosPedido = pedidoService.pedidoToArray(pedido, clienteDao);
			Container pedidoContainer = new Container();
			pedidoContainer.appId = index++;
			pedidoContainer.setBorderStyle(BORDER_ROUNDED);
			pedidoScrollContainer.add(pedidoContainer, LEFT, AFTER+MaterialConstants.COMPONENT_SPACING, pedidoScrollContainer.getWidth()-10, 50);
			for(int indexDados = 0; indexDados < dadosPedido.length; indexDados++) {
				int horizontalPosition = indexDados % 2 == 0 ? LEFT+10 : RIGHT-10;
				int verticalPosition = indexDados % 2 == 0 ? AFTER+3 : SAME;
				Label dataLabel = new Label(dadosPedido[indexDados]);
				if(indexDados == 1) dataLabel.setForeColor(getLabelColorByStatusPedido(pedido.getStatusPedido()));
				pedidoContainer.add(dataLabel, horizontalPosition, verticalPosition);
			}
		}
	}
	
	private int getLabelColorByStatusPedido(StatusPedido statusPedido) {
		int labelColor = 0;
		if(statusPedido == StatusPedido.ABERTO) labelColor = Colors.YELLOW;
		if(statusPedido == StatusPedido.FECHADO) labelColor = Color.RED;
		if(statusPedido == StatusPedido.ENVIADO) labelColor = Colors.GREEN;
		return labelColor;
				
	}

	@Override
	public <H extends EventHandler> void onEvent(final Event<H> event) {
		switch (event.type) {
		case ControlEvent.PRESSED:
			if (event.target == this.btnIncluirPedido) {
				final CreatePedidoWindow incluirPedidoWindow = new CreatePedidoWindow(null,false);
				incluirPedidoWindow.popup();
			} else if (event.target == btnVoltar) {
				InitialMenu initialMenu = new InitialMenu();
				initialMenu.popup();
			}
			break;
		case PenEvent.PEN_DOWN:
			if (event.target.getClass() == Container.class && !(event.target instanceof Window) 
					&& event.target != headContainer && event.target != footerContainer) {
				Container c = (Container) event.target;
				Pedido pedido = pedidosList.get(c.appId);
				if(pedido == null || pedido.getStatusPedido() != StatusPedido.ABERTO) {
					return;
				} else {
					final CreatePedidoWindow atualizarPedidoWindow = new CreatePedidoWindow(pedido, true);
					atualizarPedidoWindow.popup();
				}
			}
			break;
			
		default:
			break;
		}
		super.onEvent(event);
	}
}
