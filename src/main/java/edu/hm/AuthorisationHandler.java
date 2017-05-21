package edu.hm;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.HandlerWrapper;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/*
*ShareIt
* Date: 18.05.2017
* java version 1.8.0_73
* Windows 10 (1607)
* Intel(R) Core(TM) i5-4200U CPU @ 1.60GHz 2.30 GHz
* @Author Sebastian Heunke, heunke@hm.edu
*/
public class AuthorisationHandler extends HandlerWrapper {

    private static final String TOKEN_HEADER = "Token";
    private static final String AUTH_SERVER = "localhost";
    private static final int AUTH_PORT = 8083;


    @Override public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        boolean isAuthorized = false;
        isAuthorized = baseRequest.getHeader(TOKEN_HEADER) != null;
        if(isAuthorized)
            super.handle(target,baseRequest,request,response);
        else{
            //Unauthorized access
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(401);
            JSONObject responseMessage = new JSONObject();
            responseMessage.put("Status",401);
            responseMessage.put("Message","Unauthorized access, contact Authorization Server to gain access.");
            //Maybe add reason authorization failed from auth response.
            responseMessage.put("Authorization Server",AUTH_SERVER);
            responseMessage.put("Authorization Port",AUTH_PORT);
            responseMessage.write(response.getWriter());
            response.getWriter().flush();
        }
    }

    private static void sendRestRequest() throws MalformedURLException {
        URL authUrl = new URL(AUTH_SERVER + ":" + AUTH_PORT + "/auth/vallidate/"); //todo: insert correct path

        try {
            HttpURLConnection authConnection =(HttpURLConnection) authUrl.openConnection();
            authConnection.setRequestMethod("GET");
            //todo: see implementation of auth server
        } catch (IOException e) {
            System.err.println("Authentication Server did not respond.");
        }
    }

}
