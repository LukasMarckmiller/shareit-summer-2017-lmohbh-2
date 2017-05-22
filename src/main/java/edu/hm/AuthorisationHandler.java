package edu.hm;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.HandlerWrapper;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

    private static final String TOKEN_HEADER = "Authorization";
    private static final String AUTH_SERVER = "localhost";
    private static final int AUTH_PORT = 8083;


    @Override public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        JSONObject authResponse = sendRestRequest(baseRequest.getHeader(TOKEN_HEADER));
        if(authResponse.getInt("Status") == 200)
            super.handle(target,baseRequest,request,response);
        else{
            //Unauthorized access
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(401);
            JSONObject responseMessage = new JSONObject();
            responseMessage.put("Status",401);
            responseMessage.put("Message","Unauthorized access, contact Authorization Server to gain access.");
            responseMessage.put("Reason",authResponse.getString("Message"));
            responseMessage.put("Authorization Server",AUTH_SERVER);
            responseMessage.put("Authorization Port",AUTH_PORT);
            responseMessage.write(response.getWriter());
            response.getWriter().flush();
        }
    }

    private static JSONObject sendRestRequest(String token) throws MalformedURLException {
        JSONObject response = new JSONObject();

        URL authUrl = new URL("http://" + AUTH_SERVER + ":" + AUTH_PORT + "/auth/token"); //todo: insert correct path
        try {
            HttpURLConnection authConnection =(HttpURLConnection) authUrl.openConnection();
            authConnection.setUseCaches(false);
            authConnection.setRequestMethod("GET");
            authConnection.setRequestProperty(TOKEN_HEADER,token);
            //response = authConnection.getResponseCode();
            StringBuilder json = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(authConnection.getInputStream()));
            reader.lines().forEach(json::append);
            reader.close();
            response = new JSONObject(json.toString());
            System.out.println(json.toString()); //todo: remove
            //BufferedReader reader = new BufferedReader(new InputStreamReader(authConnection.getInputStream()));
            //reader.lines().forEach(System.out::println);
        } catch (IOException e) {
            System.err.println("Authentication Server did not respond.");
            e.printStackTrace();
        }
        return response;
    }

}
