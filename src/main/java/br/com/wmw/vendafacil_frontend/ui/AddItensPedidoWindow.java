package br.com.wmw.vendafacil_frontend.ui;

import java.util.List;

import br.com.wmw.vendafacil_frontend.dao.ProdutoDao;
import br.com.wmw.vendafacil_frontend.domain.Pedido;
import br.com.wmw.vendafacil_frontend.domain.Produto;
import br.com.wmw.vendafacil_frontend.util.Colors;
import br.com.wmw.vendafacil_frontend.util.Fonts;
import br.com.wmw.vendafacil_frontend.util.Images;
import br.com.wmw.vendafacil_frontend.util.MaterialConstants;
import totalcross.sql.SQLException;
import totalcross.sys.Settings;
import totalcross.sys.Vm;
import totalcross.ui.Button;
import totalcross.ui.ClippedContainer;
import totalcross.ui.Container;
import totalcross.ui.ImageControl;
import totalcross.ui.Label;
import totalcross.ui.ScrollContainer;
import totalcross.ui.Window;
import totalcross.ui.dialog.MessageBox;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.event.EventHandler;
import totalcross.ui.event.PenEvent;

public class AddItensPedidoWindow extends Window{
	
	private Pedido pedido;
	
	private ScrollContainer produtoScrollContainer;
	private List<Produto> produtosList; 
	private ProdutoDao produtoDao;
	
	private Container headContainer;
	private Container footerContainer;
		
	private Button btnProximo;
	private Button btnVoltar;
	
	private boolean updating;

	public AddItensPedidoWindow(Pedido pedido, boolean updating) {
		this.pedido = pedido;
		this.updating = updating;
		
		produtoDao = new ProdutoDao();
		produtosList = produtoDao.findAll();
		
		headContainer = new Container();
		produtoScrollContainer = new ScrollContainer();
		footerContainer = new Container();
		
		btnProximo = new Button("Proximo");
		btnVoltar = new Button("Voltar");

	}
	
	@Override
	public void onPopup() {
		add(headContainer, LEFT+MaterialConstants.BORDER_SPACING, TOP+MaterialConstants.BORDER_SPACING, 
				FILL-MaterialConstants.BORDER_SPACING, PARENTSIZE+13);
		
		ImageControl iconeSelecionar = new ImageControl(Images.iconeSelecionar);
		headContainer.add(iconeSelecionar, LEFT, TOP+3);
		
		Label titleLabel = new Label("Selecionar produtos");
		titleLabel.setFont(Fonts.latoBoldPlus12);
		headContainer.add(titleLabel, AFTER + MaterialConstants.COMPONENT_SPACING, TOP);
		
		add(produtoScrollContainer, LEFT+MaterialConstants.BORDER_SPACING+15, AFTER,
				FILL-MaterialConstants.BORDER_SPACING-5, getScrollContainerSize());
		try {
			loadList();
		} catch (SQLException e) {
			Vm.debug(e.getMessage());
		}
		
		add(footerContainer, LEFT+MaterialConstants.BORDER_SPACING, BOTTOM-MaterialConstants.BORDER_SPACING,
				FILL-MaterialConstants.BORDER_SPACING,PARENTSIZE+13);
		
		footerContainer.add(this.btnVoltar, LEFT, BOTTOM);
		footerContainer.add(btnProximo, RIGHT, BOTTOM);
	}
	
	private int getScrollContainerSize() {
		int size = (produtosList.size()*80) + (produtosList.size()*5) + 10;
		size = size > Settings.screenHeight - 100 ? PARENTSIZE+64 : size;
		return size;
	}
	
	private void loadList() throws SQLException {
		int index = 0;
		for(Produto produto : produtosList) {
			String[] dadosProduto = produtoToArray(produto);
			Container produtoContainer = new Container();
			produtoContainer.appId = index++;
			produtoContainer.setBorderStyle(BORDER_ROUNDED);
			produtoContainer.borderColor = Colors.GRAY;
			produtoScrollContainer.add(produtoContainer, LEFT, AFTER+10, produtoScrollContainer.getWidth()-10, 80);
			for(int indexDados = 0; indexDados < dadosProduto.length; indexDados++) {
				int horizontalPosition = indexDados % 2 == 0 ? LEFT+MaterialConstants.COMPONENT_SPACING : RIGHT-MaterialConstants.COMPONENT_SPACING;
				int verticalPosition = indexDados % 2 == 0 ? AFTER+MaterialConstants.COMPONENT_SPACING : SAME;
				Label dadosLabel = new Label(dadosProduto[indexDados]);
				dadosLabel.setFont(indexDados == 0 ? Fonts.latoBoldPlus6 : Fonts.latoLightPlus6);
				produtoContainer.add(dadosLabel, horizontalPosition, verticalPosition);
			}
		}
	}
	
	private String[] produtoToArray(Produto produto) {
		String[] produtoDadosArray = new String[3];
		produtoDadosArray[0] = String.valueOf(produto.codigo);
		produtoDadosArray[1] = String.valueOf(produto.preco); 
		produtoDadosArray[2] = produto.nome.length() > 30 ? produto.nome.substring(0, 28).concat("...") : produto.nome;
		return produtoDadosArray;
	}
	
	@Override
	public <H extends EventHandler> void onEvent(Event<H> event) {
		switch(event.type) {
			case ControlEvent.PRESSED:
				if(event.target == btnVoltar) {
					CreatePedidoWindow createPedidoWindow = new CreatePedidoWindow(pedido, updating);
					createPedidoWindow.popup();
				} else if(event.target == btnProximo) {
					if(validateItensPedido()) {
						PedidoResumeWindow pedidoResumeWindow = new PedidoResumeWindow(pedido, updating);
						pedidoResumeWindow.popup();
					} else {
						new MessageBox("Atenção!", "Adicione pelo menos um item ao pedido!").popup();
					}
				}
				break;
			case PenEvent.PEN_DOWN:
				if(event.target.getClass() == Container.class && !(event.target instanceof Window) 
						&& event.target != headContainer && event.target != footerContainer)  {
					Container c = (Container) event.target;
					Produto produto = produtosList.get(c.appId);
					final ItemMaterialWindow itemMaterialWindow = new ItemMaterialWindow(pedido,produto,updating);
					itemMaterialWindow.popup();
				}
				break;
				
			default:
				break;
		}
		super.onEvent(event);
	}
	
	private boolean validateItensPedido() {
		return !pedido.itens.isEmpty();
	}
}
