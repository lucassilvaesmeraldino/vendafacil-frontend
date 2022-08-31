package br.com.wmw.vendafacil_frontend.api;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import br.com.wmw.vendafacil_frontend.domain.Produto;
import totalcross.io.ByteArrayStream;
import totalcross.io.IOException;
import totalcross.json.JSONException;
import totalcross.json.JSONFactory;
import totalcross.net.HttpStream;
import totalcross.net.URI;
import totalcross.net.UnknownHostException;
import totalcross.sys.Vm;

public class ProdutoApi {

	public static List<Produto> getAllProdutos() {
		List<Produto> produtosList = new ArrayList<>();
		try {
            HttpStream.Options options = new HttpStream.Options();
            options.httpType = HttpStream.GET;
            
            HttpStream httpStream = new HttpStream(new URI("http://localhost:8080/produtos"), options);
            ByteArrayStream bas = new ByteArrayStream(4096);
            bas.readFully(httpStream, 10, 2048);
            String data = new String(bas.getBuffer(), 0, bas.available());
            
            if(httpStream.responseCode == 200) produtosList = JSONFactory.asList(data, Produto.class);
            
		} catch (UnknownHostException | ArrayIndexOutOfBoundsException | InstantiationException | IllegalAccessException |
				IllegalArgumentException | InvocationTargetException | JSONException | NoSuchMethodException | SecurityException e) {
			Vm.debug(e.getMessage());
		} catch (IOException e) {
			Vm.debug("Dispositivo sem conexão e/ou API indisponível para recebimento dos cadastros de produtos.");
		}
		return produtosList;
	}
}
