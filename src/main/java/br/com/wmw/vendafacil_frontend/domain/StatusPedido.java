package br.com.wmw.vendafacil_frontend.domain;

public enum StatusPedido {
	ABERTO, FECHADO, ENVIADO, REJEITADO;
	
	public static StatusPedido getByCodigo(int codigo) {
		StatusPedido statusPedido = null;
	     for (StatusPedido s : values()) {
	           if (s.ordinal() == codigo) {
	                 statusPedido = s;
	           }
	     }
	     return statusPedido;
	}
}
