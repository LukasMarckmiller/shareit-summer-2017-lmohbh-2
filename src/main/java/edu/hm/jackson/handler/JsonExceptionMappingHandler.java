/*
 * (C) 2017, Lukas, l.marckmiller@hm.edu on 02.06.2017. 
 * Java 1.8.0_121, Windows 10 Pro 64bit
 * Intel Core i5-6600K CPU/3.50GHz overclocked 4.1GHz, 4 cores, 16000 MByte RAM)
 * with IntelliJ IDEA 2017.1.1
 *
 */
package edu.hm.jackson.handler;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import org.json.JSONObject;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class JsonExceptionMappingHandler implements ExceptionMapper<PropertyBindingException> {
    @Override
    public Response toResponse(PropertyBindingException e) {

        //check for exception fields like an wrong mapping value
        final JSONObject returnJsonObject = new JSONObject();

        //modify Response and return.
        returnJsonObject.put("Status", Response.Status.BAD_REQUEST);
        returnJsonObject.put("Message", "'"+e.getPropertyName()+"' is not a property. Use the following: "+e.getMessageSuffix()+"Hallo Lukas & Basti");
        return Response.status(Response.Status.BAD_REQUEST).entity(returnJsonObject.toString()).build();
    }
}
