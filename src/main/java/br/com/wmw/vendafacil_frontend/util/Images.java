package br.com.wmw.vendafacil_frontend.util;

import totalcross.io.IOException;
import totalcross.sys.Vm;
import totalcross.ui.image.Image;
import totalcross.ui.image.ImageException;

public class Images {

	public static Image logoWMW;
	public static Image iconeAdicionar;
	public static Image iconeSelecionar;
	public static Image iconeProcurar;
	public static Image iconePessoa;
	public static Image iconeEntrega;
	public static Image iconeRemover;
	public static Image iconeListagem;
	public static Image iconeEditar;

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
		} catch (final ImageException ex) {
			Vm.debug(ex.getMessage());
		} catch (final IOException ex) {
			Vm.debug(ex.getMessage());
		}
	}
}
