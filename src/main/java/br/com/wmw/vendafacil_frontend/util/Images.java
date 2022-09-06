package br.com.wmw.vendafacil_frontend.util;

import totalcross.io.IOException;
import totalcross.sys.Vm;
import totalcross.ui.image.Image;
import totalcross.ui.image.ImageException;

public class Images {
	
	private Images(){		
	}

	private static Image logoWMW;
	private static Image iconeAdicionar;
	private static Image iconeSelecionar;
	private static Image iconeProcurar;
	private static Image iconePessoa;
	private static Image iconeEntrega;
	private static Image iconeRemover;
	private static Image iconeListagem;
	private static Image iconeEditar;

	public static void loadImages() {
		try {
			logoWMW = new Image("images/logoWMW molde.png");
			iconeAdicionar = new Image("images/icone-adicionar.png");
			iconeSelecionar = new Image("images/icone-selecionar.png");
			iconeProcurar = new Image("images/icone-procurar.png");
			iconePessoa = new Image("images/icone-pessoa.png");
			iconeEntrega = new Image("images/icone-entrega.png");
			iconeRemover = new Image("images/icone-remover.png");
			iconeListagem = new Image("images/icone-listagem.png");
			iconeEditar = new Image("images/icone-editar.png");
		} catch (final ImageException | IOException ex) {
			Vm.debug(ex.getMessage());
		}
	}

	public static Image getLogoWMW() {
		return logoWMW;
	}

	public static Image getIconeAdicionar() {
		return iconeAdicionar;
	}

	public static Image getIconeSelecionar() {
		return iconeSelecionar;
	}

	public static Image getIconeProcurar() {
		return iconeProcurar;
	}

	public static Image getIconePessoa() {
		return iconePessoa;
	}

	public static Image getIconeEntrega() {
		return iconeEntrega;
	}

	public static Image getIconeRemover() {
		return iconeRemover;
	}

	public static Image getIconeListagem() {
		return iconeListagem;
	}

	public static Image getIconeEditar() {
		return iconeEditar;
	}
	
}
