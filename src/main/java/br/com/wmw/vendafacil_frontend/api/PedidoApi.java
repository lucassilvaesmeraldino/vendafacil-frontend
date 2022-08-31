package br.com.wmw.vendafacil_frontend.api;

import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import br.com.wmw.vendafacil_frontend.dao.PedidoDao;
import br.com.wmw.vendafacil_frontend.domain.Pedido;
import br.com.wmw.vendafacil_frontend.domain.PedidoRequestModel;
import br.com.wmw.vendafacil_frontend.domain.StatusPedido;
import br.com.wmw.vendafacil_frontend.exception.ApiException;
import totalcross.io.ByteArrayStream;
import totalcross.io.IOException;
import totalcross.net.HttpStream;
import totalcross.net.URI;
import totalcross.net.UnknownHostException;
import totalcross.sys.Vm;

public class PedidoApi {
	
	public static void sendPedidos(List<Pedido> pedidos) {
		boolean success = true;
		List<PedidoRequestModel> pedidosRequest = pedidos.stream().map(PedidoRequestModel::new).collect(Collectors.toList());
		for(int i = 0; i < pedidosRequest.size(); i++) {
			String pedidoJson = new Gson().toJson(pedidosRequest.get(i));
			if(makeRequest(pedidoJson)) {
				Pedido pedidoEnviado = pedidos.get(i);
				atualizaPedido(pedidoEnviado);
			} else {
				success = false;
			}
		}
		if(!success) throw new ApiException("Dispositivo sem conexão ou falha no envio dos pedidos.");
	}
	
	private static boolean makeRequest(String pedidoJson) {
		boolean success = false;
		try {
            HttpStream.Options options = new HttpStream.Options();
            options.httpType = HttpStream.POST;
            options.setContentType("application/json");
            options.data = pedidoJson;
            
            HttpStream httpStream = new HttpStream(new URI("http://localhost:8080/pedidos"), options);
            ByteArrayStream bas = new ByteArrayStream(4096);
            bas.readFully(httpStream, 10, 2048);
            String data = new String(bas.getBuffer(), 0, bas.available());
            bas.close();
            
            if(httpStream.responseCode == 201) success = true;
                      
		} catch (UnknownHostException e) {
			Vm.debug(e.getMessage());
		} catch (IOException e) {
			Vm.debug("Dispositivo sem conexão e/ou API indisponível para envio do pedido.");
		}
		
		return success;
	}
	
	private static void atualizaPedido(Pedido pedido) {
		pedido.statusPedido = StatusPedido.ENVIADO;
		new PedidoDao().update(pedido);
	}
}
