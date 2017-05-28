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

/*
*ShareIt
* Date: 18.05.2017
* java version 1.8.0_73
* Windows 10 (1607)
* Intel(R) Core(TM) i5-4200U CPU @ 1.60GHz 2.30 GHz
* @Author Sebastian Heunke, heunke@hm.edu
*/

/**
 * A authorization handler.
 * <p>
 * This authorization handler acts as a filter
 * and checks if the client/caller has the rights
 * to use the service.
 *
 * @author Hauser Oliver, Heunke Sebastian, Marckmiller Lukas
 * @version 1.2
 */
public class AuthorisationHandler extends HandlerWrapper {

    private static final String TOKEN_HEADER = "Authorization";
    private static final String AUTH_SERVER = "authserverhm.herokuapp.com";
    private static final int AUTH_PORT = 8083;
    private static final int OK = 200;
    private static final int BAD_GATEWAY = 502;
    private static final int UNAUTHORIZED = 401;

    /**
     * Receives the incoming request.
     * <p>
     * Receives a incoming request from a client/caller and checks if
     * he has the rights to access the resource server via authentication server.
     *
     * @param target      target server
     * @param baseRequest base request
     * @param request     http request
     * @param response    http response
     * @throws IOException      IOException
     * @throws ServletException ServletException
     */
    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("Incoming request.");
        JSONObject authResponse = sendRestRequest(baseRequest.getHeader(TOKEN_HEADER));
        if (authResponse.getInt("Status") == OK) {
            super.handle(target, baseRequest, request, response);
        } else {
            //Unauthorized access
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(UNAUTHORIZED);
            JSONObject responseMessage = new JSONObject();
            responseMessage.put("Message", "Unauthorized access, contact Authorization Server to gain access.");
            if (authResponse.getInt("Status") != BAD_GATEWAY && authResponse.has("Message")) {
                responseMessage.put("Status", UNAUTHORIZED);
                responseMessage.put("Reason", authResponse.getString("Message"));
            } else {
                responseMessage.put("Status", BAD_GATEWAY);
                responseMessage.put("Reason", "Authorization Server failed.");
            }
            responseMessage.put("Authorization Server", AUTH_SERVER);
            responseMessage.put("Authorization Port", AUTH_PORT);
            responseMessage.write(response.getWriter());
            response.getWriter().flush();
        }
    }

    /**
     * Sends a rest request to the authentication server.
     * <p>
     * This sends a request to the authentication server to
     * check if the caller/client requesting the resource server
     * is validated (has the rights) to access it.
     *
     * @param token validated token
     * @return Response in JSON format
     * @throws MalformedURLException Exception in case URL is wrong
     */
    private static JSONObject sendRestRequest(String token) throws MalformedURLException {
        JSONObject response = new JSONObject();
        response.put("Status", BAD_GATEWAY);

        //URL authUrl = new URL("http://" + AUTH_SERVER + ":" + AUTH_PORT + "/auth/a4/token");
        URL authUrl = new URL("http://" + AUTH_SERVER + "/auth/a4/token");
        try {
            HttpURLConnection authConnection = (HttpURLConnection) authUrl.openConnection();
            authConnection.setUseCaches(false);
            authConnection.setRequestMethod("GET");
            authConnection.setRequestProperty(TOKEN_HEADER, token);
            //response = authConnection.getResponseCode();
            StringBuilder json = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(authConnection.getInputStream()));
            reader.lines().forEach(json::append);
            reader.close();
            response = new JSONObject(json.toString());
        } catch (IOException e) {
            System.err.println("Authentication Server did not respond.");
            e.printStackTrace();
        }
        return response;
    }

}
