package thirdPartyToken;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public  class getHandler implements HttpHandler {
	public void handle(HttpExchange httpExchange) throws IOException {
	      String query = httpExchange.getRequestURI().getQuery();
	      String response = handleGetRequest(query);
	      writeResponse(httpExchange, response);
	      
	    }
	    public String handleGetRequest(String query) {
	    		String[] splitQuery=query.split("&");
	    		ArrayList<String> as = new ArrayList<String>();
	    		for(String params:splitQuery) {
	    			String[] pair=params.split("=");
	    			as.add(pair[0].toLowerCase());
	    			as.add(pair[1].toLowerCase());
	    		}
	    		if(as.get(as.indexOf("auth")+1).contains("androidapp")) {
		    		if(as.get(as.indexOf("type")+1).contains("validate")){
		    			String username=as.get(as.indexOf("username")+1);
		    			System.out.println(username);
		    			String token=as.get(as.indexOf("token")+1);
		    			System.out.println(token);
		    			tokenCollection.printTokens();
		    			if(tokenCollection.ifExists(Integer.parseInt(token),username)) {
		    				return "1";
		    			}
		    			else {
		    				return "0";
		    			}
		    		}
		    		else if(query.contains("type=generate")) {
		    			String username=as.get(as.indexOf("username")+1);
		    			tokenCollection.generateToken(username);
		    			System.out.println("generated");
		    			return "true";
		    			}
		    		return "false";
	    		}
		    else {
		    	return "error";
		    }
	    }
	    public static void writeResponse(HttpExchange httpExchange, String response) throws IOException {
		    httpExchange.sendResponseHeaders(200, response.length());
		    OutputStream os = httpExchange.getResponseBody();
		    os.write(response.getBytes());
		    os.close();
		  }

}
