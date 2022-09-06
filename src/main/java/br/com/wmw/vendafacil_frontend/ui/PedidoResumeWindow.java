package br.com.wmw.vendafacil_frontend.ui;

import java.math.RoundingMode;
import java.util.List;

import br.com.wmw.vendafacil_frontend.dao.ClienteDao;
import br.com.wmw.vendafacil_frontend.dao.PedidoDao;
import br.com.wmw.vendafacil_frontend.dao.ProdutoDao;
import br.com.wmw.vendafacil_frontend.domain.Pedido;
import br.com.wmw.vendafacil_frontend.domain.StatusPedido;
import br.com.wmw.vendafacil_frontend.exception.PersistenceException;
import br.com.wmw.vendafacil_frontend.service.ItemPedidoService;
import br.com.wmw.vendafacil_frontend.service.PedidoService;
import br.com.wmw.vendafacil_frontend.util.Colors;
import br.com.wmw.vendafacil_frontend.util.DoubleUtils;
import br.com.wmw.vendafacil_frontend.util.Fonts;
import br.com.wmw.vendafacil_frontend.util.Images;
import br.com.wmw.vendafacil_frontend.util.MaterialConstants;
import br.com.wmw.vendafacil_frontend.util.Messages;
import totalcross.sys.Settings;
import totalcross.ui.Button;
import totalcross.ui.Container;
import totalcross.ui.ImageControl;
import totalcross.ui.Label;
import totalcross.ui.ScrollContainer;
import totalcross.ui.Window;
import totalcross.ui.dialog.MessageBox;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.event.EventHandler;

public class PedidoResumeWindow extends Window{

	private Pedido pedido;
	private PedidoDao pedidoDao;
	
	private ScrollContainer listaItensPedido;
	
	private ClienteDao clienteDao;
	private ProdutoDao produtoDao;
	
	private PedidoService pedidoService;
	private ItemPedidoService itemPedidoService;

	private Button btnFechar;
	private Button btnVoltar;
	private Button btnSalvar;
	private Button btnAtualizar;
	
	private boolean updating;
	
	public PedidoResumeWindow(Pedido pedido, boolean updating) {
		this.pedido = pedido;
		this.updating = updating;
		
		clienteDao = new ClienteDao();
		produtoDao = new ProdutoDao();
		pedidoDao = new PedidoDao();
		
		pedidoService = new PedidoService();
		itemPedidoService = new ItemPedidoService();
		
		listaItensPedido = new ScrollContainer();
		
		btnFechar = new Button("Fechar");
		btnSalvar = new Button("Salvar");
		btnVoltar = new Button("Voltar");
		btnAtualizar = new Button("Atualizar");
	}
	
	@Override
	public void onPopup() {
		constructScreen();
	}
	
	private void constructScreen() {
		Container headContainer = new Container();
		add(headContainer, LEFT+MaterialConstants.BORDER_SPACING, TOP+MaterialConstants.BORDER_SPACING, 
				FILL-MaterialConstants.BORDER_SPACING, PARENTSIZE+10);
		
		ImageControl iconeProcurar = new ImageControl(Images.getIconeProcurar());
		headContainer.add(iconeProcurar, LEFT, TOP+3);
		
		Label titleLabel = new Label("Resumo do pedido");
		titleLabel.setFont(Fonts.latoBoldPlus12);
		headContainer.add(titleLabel, AFTER+MaterialConstants.COMPONENT_SPACING, TOP);
		
		Container bodyContainer = new Container();
		add(bodyContainer, LEFT+MaterialConstants.BORDER_SPACING, AFTER,
				FILL-MaterialConstants.BORDER_SPACING, PARENTSIZE+80);
		
		Label pedidoDataLabel = new Label("Informações do pedido");
		pedidoDataLabel.setFont(Fonts.latoBoldPlus6);
		bodyContainer.add(pedidoDataLabel, LEFT, TOP);
		
		Container pedidoDataContainer = new Container();
		pedidoDataContainer.setBorderStyle(BORDER_RAISED);
		bodyContainer.add(pedidoDataContainer, LEFT, AFTER+MaterialConstants.COMPONENT_SPACING, FILL, PARENTSIZE+15);
		
		ImageControl iconePessoa = new ImageControl(Images.getIconePessoa());
		pedidoDataContainer.add(iconePessoa, LEFT+MaterialConstants.COMPONENT_SPACING, TOP+MaterialConstants.COMPONENT_SPACING);
		
		Label clienteLabel = new Label(clienteDao.findByCodigo(pedido.getCodigoCliente()).getNome());
		clienteLabel.setFont(Fonts.latoBoldPlus4);
		pedidoDataContainer.add(clienteLabel, AFTER+MaterialConstants.COMPONENT_SPACING, SAME);
		
		ImageControl iconeEntrega = new ImageControl(Images.getIconeEntrega());
		pedidoDataContainer.add(iconeEntrega, LEFT+MaterialConstants.COMPONENT_SPACING, AFTER+MaterialConstants.COMPONENT_SPACING);
		
		Label dataEntregaLabel = new Label(pedido.getDataEntrega());
		dataEntregaLabel.setFont(Fonts.latoBoldPlus4);
		pedidoDataContainer.add(dataEntregaLabel, AFTER+MaterialConstants.COMPONENT_SPACING, SAME);
		
		Label itensPedidoDataLabel = new Label("Itens do pedido");
		itensPedidoDataLabel.setFont(Fonts.latoBoldPlus6);
		bodyContainer.add(itensPedidoDataLabel, LEFT, AFTER+MaterialConstants.COMPONENT_SPACING);
		
		createItensScrollContainer(bodyContainer);
		createLabelTotalValue(bodyContainer);
		
		Container footerContainer = new Container();
		add(footerContainer, LEFT+MaterialConstants.BORDER_SPACING, BOTTOM-MaterialConstants.BORDER_SPACING,
				FILL-MaterialConstants.BORDER_SPACING,PARENTSIZE+10);
		
		footerContainer.add(this.btnVoltar, LEFT, BOTTOM);
		footerContainer.add(btnFechar, RIGHT, BOTTOM);
		if(updating) {
			footerContainer.add(btnAtualizar, BEFORE-MaterialConstants.COMPONENT_SPACING, BOTTOM);
		}else {
			footerContainer.add(btnSalvar, BEFORE-MaterialConstants.COMPONENT_SPACING, BOTTOM);
		}
	}
	
	private void createItensScrollContainer(Container bodyContainer) {
		bodyContainer.add(listaItensPedido, LEFT, AFTER, FILL, getScrollContainerSize());
		loadListItens();
	}
	
	private int getScrollContainerSize() {
		int size = (pedido.getItens().size()*58) + (pedido.getItens().size()*MaterialConstants.COMPONENT_SPACING) + 10;
		size = size > Settings.screenHeight - 312 ? PARENTSIZE+55 - 10 : size;
		return size;
	}
	
	private void loadListItens() {
		List<String[]> itensList = itemPedidoService.instantiateItensSumarry(pedido.getItens(), produtoDao);
		for(int index = 0; index < itensList.size(); index++) {
			String[] dadosItem = itensList.get(index);
			Container itemContainer = new Container();
			Button btnRemover = new Button(Images.getIconeRemover());
			btnRemover.addPressListener(event -> {
				Button button = (Button) event.target;
				removerItemPedido(button.appId);
			});
			itemContainer.appId = index;
			itemContainer.setBorderStyle(TAB_BORDER);
			itemContainer.eventsEnabled = true;
			itemContainer.focusOnPenDown = true;
			itemContainer.onEventFirst = true;
			itemContainer.borderColor = Colors.BLUE;
			listaItensPedido.add(itemContainer, LEFT, AFTER+MaterialConstants.COMPONENT_SPACING, listaItensPedido.getWidth()-10, 58);
			for(int indexItem = 0; indexItem < dadosItem.length; indexItem++) {
				int horizontalPosition = indexItem % 2 == 0 ? LEFT+MaterialConstants.COMPONENT_SPACING : RIGHT-MaterialConstants.COMPONENT_SPACING;
				int verticalPosition = indexItem % 2 == 0 ? AFTER+MaterialConstants.COMPONENT_SPACING : SAME;
				itemContainer.add(new Label(dadosItem[indexItem]), horizontalPosition, verticalPosition);
			}
			itemContainer.add(btnRemover,CENTER, BOTTOM-3, 16, 16);
		}
	}
	
	private void createLabelTotalValue(Container bodyContainer) {
		calculateTotalPedido();
		Double totalPedido = DoubleUtils.round(pedido.getTotalPedido(), RoundingMode.HALF_UP);
		Container totalContainer = new Container();
		totalContainer.setBorderStyle(BORDER_TOP);
		totalContainer.borderColor = Colors.BLUE;
		bodyContainer.add(totalContainer, LEFT+MaterialConstants.COMPONENT_SPACING, AFTER+MaterialConstants.COMPONENT_SPACING, 
				FILL, PARENTSIZE+10);
		Label totalLabel = new Label("Total: R$ " + totalPedido.toString().replace(".", ","));
		totalLabel.setFont(Fonts.latoBoldPlus8);
		totalContainer.add(totalLabel, RIGHT-MaterialConstants.COMPONENT_SPACING, TOP+MaterialConstants.COMPONENT_SPACING);
	}
	
	private void calculateTotalPedido() {
		Double valorTotalPedido = pedidoService.calculateValorTotal(pedido);
		pedido.setTotalPedido(valorTotalPedido);
	}
	
	@Override
	public void popup() {
		setRect(0,0,Settings.screenWidth, Settings.screenHeight);
		super.popup();
	}
	
	@Override
	public <H extends EventHandler> void onEvent(Event<H> event) {
		if(event.type == ControlEvent.PRESSED) {
			if(event.target == btnVoltar) {
				this.unpop();
			} else if (event.target == btnSalvar) {
				savePedido();
				ListPedidosWindow listPedidosWindow = new ListPedidosWindow();
				listPedidosWindow.popup();
			} else if(event.target == btnAtualizar) {
				updatePedido();
				ListPedidosWindow listPedidosWindow = new ListPedidosWindow();
				listPedidosWindow.popup();
			} else if (event.target == btnFechar) {
				closePedido();
				ListPedidosWindow listPedidosWindow = new ListPedidosWindow();
				listPedidosWindow.popup();
			}

		}
		super.onEvent(event);
	}
	
	private void savePedido() {
		try {
			pedidoDao.insert(pedido);
			new MessageBox(Messages.TYPE_INFO, Messages.MESSAGE_PEDIDOSAVED).popup();
		} catch (PersistenceException e) {
			new MessageBox(Messages.TYPE_ERROR, e.getMessage()).popup();
		}
	}
	
	private void updatePedido() {
		try {
			pedidoDao.update(pedido);
			new MessageBox(Messages.TYPE_INFO, Messages.MESSAGE_PEDIDOUPDATED).popup();
		} catch (PersistenceException e) {
			new MessageBox(Messages.TYPE_ERROR, e.getMessage()).popup();
		}
	}
	
	private void closePedido() {
		pedido.setStatusPedido(StatusPedido.FECHADO);
		if(updating) {
			updatePedido();
		}else {
			savePedido();
		}
	}
	
	private void removerItemPedido(int indexItem) {
		if(pedido.getItens().size() == 1) {
			new MessageBox(Messages.TYPE_WARNING, Messages.MESSAGE_PEDIDOINCORRECT_NOITEMS).popup();
		} else {
			pedido.getItens().remove(indexItem);
			this.reloadScreen();
		}
	}
	
	private void reloadScreen() {
		listaItensPedido = new ScrollContainer();
		removeAll();
		constructScreen();
		reposition();
	}
}
