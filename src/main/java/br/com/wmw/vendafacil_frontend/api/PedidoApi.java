package br.com.wmw.vendafacil_frontend.api;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import br.com.wmw.vendafacil_frontend.dao.PedidoDao;
import br.com.wmw.vendafacil_frontend.domain.Pedido;
import br.com.wmw.vendafacil_frontend.domain.StatusPedido;
import br.com.wmw.vendafacil_frontend.exception.ApiException;
import br.com.wmw.vendafacil_frontend.exception.ConnectionException;
import br.com.wmw.vendafacil_frontend.exception.FailedToSendException;
import totalcross.io.ByteArrayStream;
import totalcross.io.IOException;
import totalcross.json.JSONException;
import totalcross.json.JSONFactory;
import totalcross.net.HttpStream;
import totalcross.net.URI;
import totalcross.net.UnknownHostException;
import totalcross.sys.Vm;

public class PedidoApi {
	
	private PedidoApi(){
	}
	
	public static void sendPedidos(List<Pedido> pedidos) {
		boolean success = true;
		List<PedidoRequest> pedidosRequest = new ArrayList<>();
		List<String> messagesErrors = new ArrayList<>();
		pedidos.forEach(p -> pedidosRequest.add(new PedidoRequest(p)));
		for(int indexPedido = 0; indexPedido < pedidosRequest.size(); indexPedido++) {
			String pedidoJson = pedidosRequest.get(indexPedido).toJson().toString();
			Pedido pedidoEntitySelected = pedidos.get(indexPedido);
			try{
				makeRequest(pedidoJson);
				updatePedidoEnviado(pedidoEntitySelected);
			} catch(ApiException e) {
				messagesErrors.addAll(formatErrorsAndReturnList(e.getMessage(), pedidos.get(indexPedido).getCodigoPedido()));
				success = false;
				updatePedidoNaoEnviado(pedidoEntitySelected);
			} catch(ConnectionException e) {
				throw e;
			}
			
		}
		
		StringBuilder returnedMessageError = new StringBuilder();
		returnedMessageError.append("Falha no envio dos pedidos: ");
		messagesErrors.forEach(me -> returnedMessageError.append("\n"+me));
		if(!success) throw new FailedToSendException(returnedMessageError.toString());
	}
	
	private static void makeRequest(String pedidoJson) {
		try (ByteArrayStream bas = new ByteArrayStream(4096)){
            HttpStream.Options options = new HttpStream.Options();
            options.httpType = HttpStream.POST;
            options.setContentType("application/json");
            options.data = pedidoJson;
            
            HttpStream httpStream = new HttpStream(new URI("http://localhost:8080/pedidos"), options);
            bas.readFully(httpStream, 10, 2048);
            String data = new String(bas.getBuffer(), 0, bas.available());
            
            if(httpStream.responseCode == 400) {
            	mapErrorsAndThrowsException(data);	
            }
            
		} catch (UnknownHostException e) {
			Vm.debug(e.getMessage());
		} catch (IOException e) {
			throw new ConnectionException("Dispositivo sem conexão e/ou API indisponível para envio dos pedidos.");
		}
		
	}
	
	private static void updatePedidoEnviado(Pedido pedido) {
		pedido.setStatusPedido(StatusPedido.ENVIADO);
		new PedidoDao().update(pedido);
	}
	
	private static void updatePedidoNaoEnviado(Pedido pedido) {
		pedido.setStatusPedido(StatusPedido.REJEITADO);
		new PedidoDao().update(pedido);
	}
	
	private static void mapErrorsAndThrowsException(String responseData) {
		try {
			if(responseData.endsWith("]")) {
				StringBuilder messages = new StringBuilder();
				List<FieldInvalidError> fieldInvalidErrorList = JSONFactory.asList(responseData, FieldInvalidError.class);
				for(FieldInvalidError fie : fieldInvalidErrorList) {
					messages.append(fie.getMessage() + ";");
				}
				throw new ApiException(messages.toString());
				
			} else if (responseData.endsWith("}")){
				MessageError messageError = JSONFactory.parse(responseData, MessageError.class);
				throw new ApiException(messageError.getMessage());
			}
		} catch (ArrayIndexOutOfBoundsException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException | JSONException | NoSuchMethodException
				| SecurityException e) {
			Vm.debug(e.getMessage());
		}
		
	}
	
	private static List<String> formatErrorsAndReturnList(String message, Integer codigoPedido) {
		List<String> errorsList = new ArrayList<>();
		if(message.contains(";")) {
			String[] splitMessage = message.split(";");
			for(int indexMessage = 0; indexMessage < splitMessage.length; indexMessage++) {
				errorsList.add("Pedido " + codigoPedido + ": " + splitMessage[indexMessage]);
			}
		} else if(!message.endsWith(";")){
			errorsList.add("Pedido " + codigoPedido + ": " + message);
		}
		return errorsList;
	}
}
