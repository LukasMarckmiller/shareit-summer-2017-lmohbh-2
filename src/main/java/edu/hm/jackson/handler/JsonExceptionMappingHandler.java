/* (C) 2017, O. Hauser, ohauser@hm.edu
 * Munich University of Applied Sciences, Department 07, Computer Science
 * Java 1.8.0_131, Linux x86_64 4.4.0-66-generic
 * Dell (Intel Core i7-5500U CPU @ 2.40GHz, 4 cores, 8000 MByte RAM)
 **/

package edu.hm.jackson.handler;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import org.json.JSONObject;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class JsonExceptionMappingHandler implements ExceptionMapper<PropertyBindingException> {
    @Override
    public Response toResponse(PropertyBindingException e) {

        //check for exception fields like an wrong mapping value
        final JSONObject returnJsonObject = new JSONObject();

        //modify Response and return.
        returnJsonObject.put("Status", Response.Status.BAD_REQUEST);
        returnJsonObject.put("Message", "'"+e.getPropertyName()+"' is not a property. Use the following: "+e.getMessageSuffix());
        return Response.status(Response.Status.BAD_REQUEST).entity(returnJsonObject.toString()).build();
    }
}
