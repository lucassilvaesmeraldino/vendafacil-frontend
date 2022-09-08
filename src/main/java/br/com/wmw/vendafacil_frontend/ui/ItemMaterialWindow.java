package br.com.wmw.vendafacil_frontend.ui;

import br.com.wmw.vendafacil_frontend.domain.ItemPedido;
import br.com.wmw.vendafacil_frontend.domain.Pedido;
import br.com.wmw.vendafacil_frontend.domain.Produto;
import br.com.wmw.vendafacil_frontend.service.ItemPedidoService;
import br.com.wmw.vendafacil_frontend.util.Colors;
import br.com.wmw.vendafacil_frontend.util.DoubleUtils;
import br.com.wmw.vendafacil_frontend.util.Fonts;
import br.com.wmw.vendafacil_frontend.util.MaterialConstants;
import br.com.wmw.vendafacil_frontend.util.Messages;
import br.com.wmw.vendafacil_frontend.util.StringUtils;
import totalcross.ui.Button;
import totalcross.ui.Container;
import totalcross.ui.Edit;
import totalcross.ui.Label;
import totalcross.ui.MaterialWindow;
import totalcross.ui.dialog.MessageBox;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.event.EventHandler;

public class ItemMaterialWindow extends MaterialWindow {
		
	public ItemMaterialWindow(Pedido pedido, Produto produto, boolean updating) {
		
		super(false, () -> new Container() {
			
			private Edit editQuantidade;
			private Edit editDesconto;
			private Button btnAdicionar;
			private ItemPedidoService itemPedidoService = new ItemPedidoService();
			
			@Override
			public void initUI() {

				Label mainLabel = new Label("Informações do produto:");
				mainLabel.setFont(Fonts.latoBoldPlus6);
				add(mainLabel, LEFT + MaterialConstants.BORDER_SPACING, AFTER + MaterialConstants.BORDER_SPACING,
						PREFERRED, PREFERRED);

				Container produtoDetailsContainer = new Container();
				produtoDetailsContainer.setBorderStyle(BORDER_RAISED);
				add(produtoDetailsContainer, LEFT + MaterialConstants.BORDER_SPACING,
						AFTER + MaterialConstants.BORDER_SPACING, FILL - MaterialConstants.BORDER_SPACING,
						PARENTSIZE + 13);
				String produtoDescricao = StringUtils.reduceLength(produto.getNome(), 30);
				produtoDetailsContainer.add(new Label("Descrição: " + produtoDescricao),
						LEFT + MaterialConstants.COMPONENT_SPACING, TOP + MaterialConstants.COMPONENT_SPACING);
				produtoDetailsContainer.add(new Label("Preço: " + produto.getPreco()),
						LEFT + MaterialConstants.COMPONENT_SPACING, AFTER + MaterialConstants.COMPONENT_SPACING);

				Label secondaryLabel = new Label("Informações adicionais:");
				secondaryLabel.setFont(Fonts.latoBoldPlus6);
				add(secondaryLabel, LEFT + MaterialConstants.BORDER_SPACING, AFTER + MaterialConstants.BORDER_SPACING,
						PREFERRED, PREFERRED);

				Container itemDetailsContainer = new Container();
				itemDetailsContainer.setBorderStyle(BORDER_RAISED);
				add(itemDetailsContainer, LEFT + MaterialConstants.BORDER_SPACING,
						AFTER + MaterialConstants.BORDER_SPACING, FILL - MaterialConstants.BORDER_SPACING,
						PARENTSIZE + 33);

				itemDetailsContainer.add(new Label("Qual a quantidade desejada deste produto?"),
						LEFT + MaterialConstants.COMPONENT_SPACING, TOP + MaterialConstants.COMPONENT_SPACING);
				editQuantidade = new Edit("999999999");
				editQuantidade.setMode(Edit.NORMAL, true);
				editQuantidade.setValidChars("0123456789");
				itemDetailsContainer.add(editQuantidade, LEFT + MaterialConstants.COMPONENT_SPACING,
						AFTER + MaterialConstants.COMPONENT_SPACING, FILL - MaterialConstants.COMPONENT_SPACING,
						MaterialConstants.EDIT_HEIGHT);

				itemDetailsContainer.add(new Label("Algum desconto para ele? (%)"),
						LEFT + MaterialConstants.COMPONENT_SPACING, AFTER + MaterialConstants.COMPONENT_SPACING);
				editDesconto = new Edit("99,99");
				editDesconto.setMode(Edit.NORMAL, true);
				editDesconto.setValidChars("0123456789");
				itemDetailsContainer.add(editDesconto, LEFT + MaterialConstants.COMPONENT_SPACING,
						AFTER + MaterialConstants.COMPONENT_SPACING, FILL - MaterialConstants.COMPONENT_SPACING,
						MaterialConstants.EDIT_HEIGHT);

				btnAdicionar = new Button("Adicionar", Button.BORDER_ROUND);
				btnAdicionar.setBackForeColors(Colors.BLUE, Colors.WHITE);
				add(btnAdicionar, LEFT + MaterialConstants.BORDER_SPACING, AFTER + MaterialConstants.BORDER_SPACING,
						FILL - MaterialConstants.BORDER_SPACING, PREFERRED);
			}

			@Override
			public <H extends EventHandler> void onEvent(Event<H> event) {
				if (event.type == ControlEvent.PRESSED && event.target == btnAdicionar && validateFields()) {
					createItemPedido();
					AddItensPedidoWindow addItensPedidoWindow = new AddItensPedidoWindow(pedido, updating);
					addItensPedidoWindow.popup();
				}
			}
			
			private void createItemPedido() {
			 	ItemPedido itemPedido = screenToDomain();
				pedido.addItem(itemPedido);
			}
			
			private ItemPedido screenToDomain() {
				String quantidade = editQuantidade.getText();
				String desconto = editDesconto.getText();
				return createDomain(quantidade, desconto);
			}

			private boolean validateFields() {
				String desconto = editDesconto.getText().replace(",", ".");
				String quantidade = editQuantidade.getText();
				if (quantidade.isEmpty()) {
					new MessageBox(Messages.TYPE_WARNING, Messages.MESSAGE_DESCONTOINCORRECT_EMPTY).popup();
					return false;
				}
				if(Double.valueOf(quantidade).equals(Double.valueOf(0))) {
					new MessageBox(Messages.TYPE_WARNING, Messages.MESSAGE_DESCONTOINCORRECT_ZERO).popup();
					return false;
				}
				if (editDesconto.getText().isEmpty()) {
					new MessageBox(Messages.TYPE_WARNING, Messages.MESSAGE_DESCONTOINCORRECT_EMPTY).popup();
					return false;
				}
				if(!DoubleUtils.isBetween(Double.valueOf(0), Double.valueOf(100), Double.parseDouble(desconto))) {
					new MessageBox(Messages.TYPE_WARNING, Messages.MESSAGE_DESCONTOINCORRECT_INVALID).popup();
					return false;
				}
				return true;
			}
			
			private ItemPedido createDomain(String quantidade, String desconto) {				
				desconto = desconto.replace(",", ".");
				double descontoEmPorcentagemAsDouble = Double.parseDouble(desconto);
				int quantidadeAsInteger = Integer.parseInt(quantidade);
				
				return itemPedidoService.createItemPedido(produto, quantidadeAsInteger, descontoEmPorcentagemAsDouble);
			}
		});
		
		fadeOtherWindows = true;
		transparentBackground = true;

		setBackColor(Colors.BLUE);
		setSlackSpace(100);
	}

}
