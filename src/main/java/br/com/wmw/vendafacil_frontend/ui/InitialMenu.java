package br.com.wmw.vendafacil_frontend.ui;

import br.com.wmw.vendafacil_frontend.util.Colors;
import br.com.wmw.vendafacil_frontend.util.Fonts;
import br.com.wmw.vendafacil_frontend.util.Images;
import br.com.wmw.vendafacil_frontend.util.MaterialConstants;
import totalcross.ui.Button;
import totalcross.ui.Container;
import totalcross.ui.Control;
import totalcross.ui.ImageControl;
import totalcross.ui.Label;
import totalcross.ui.Window;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.event.EventHandler;
import totalcross.ui.gfx.Color;

public class InitialMenu extends Window {

	private final Button btnPedidoList;
	private final Button btnPedidoCreate;

	public InitialMenu() {
		this.btnPedidoList = new Button("Ver pedidos");
		btnPedidoList.setBackForeColors(Colors.BLUE, Colors.WHITE);
		
		btnPedidoCreate = new Button("Criar pedido");
		btnPedidoCreate.setForeColor(Color.BLACK);

	}

	@Override
	public void onPopup() {
		ImageControl headLogo = new ImageControl(Images.getLogoWMW());
		headLogo.scaleToFit = true;
		add(headLogo, LEFT + MaterialConstants.BORDER_SPACING, TOP,
				PARENTSIZE + 50, PARENTSIZE + 50);
		
		Container bodyContainer = new Container();
		bodyContainer.transparentBackground = true;
		add(bodyContainer, LEFT+MaterialConstants.BORDER_SPACING, BOTTOM, FILL-MaterialConstants.BORDER_SPACING,
				PARENTSIZE + 50);
		
		Label lbl = new Label("Rapidez e facilidade \nna emissão dos seus \npedido de compra.");
		lbl.transparentBackground = true;
		lbl.setForeColor(Color.BLACK);
		lbl.setFont(Fonts.latoBoldPlus8);
		bodyContainer.add(lbl, LEFT, TOP+MaterialConstants.COMPONENT_SPACING);
		
		bodyContainer.add(this.btnPedidoList, Control.LEFT, AFTER + MaterialConstants.COMPONENT_SPACING, Control.FILL, Control.PREFERRED);
		bodyContainer.add(this.btnPedidoCreate, Control.SAME, AFTER + MaterialConstants.COMPONENT_SPACING, Control.FILL, Control.PREFERRED);
	}

	@Override
	public <H extends EventHandler> void onEvent(final Event<H> event) {
		if (event.type == ControlEvent.PRESSED) {
			if (event.target == this.btnPedidoList) {
				final ListPedidosWindow listPedidosWindow = new ListPedidosWindow();
				listPedidosWindow.popup();
			} else if (event.target == btnPedidoCreate) {
				final CreatePedidoWindow createPedidoWindow = new CreatePedidoWindow(null, false);
				createPedidoWindow.popup();
			}
		}
		super.onEvent(event);

	}

}
