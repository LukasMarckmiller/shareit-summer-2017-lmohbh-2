package edu.hm.jackson.handler;

import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by oliver on 6/14/17.
 */
public class JsonExceptionMappingHandlerTest {

    @Test
    public void
    ResponseTitleIsNotTitel() {

        given().
                port(8082).
                contentType(ContentType.JSON).
                body("{\"titel\":\"hp\",\"author\":\"jk\",\"isbn\":\"0123456789ABC\"}").
        when().
                post("/shareit/media/books").

        then().
                contentType(ContentType.TEXT).
                statusCode(400).
                body(equalTo("{\"Status\":\"Bad Request\",\"Message\":\"'titel' is not a property. Use the following:  (3 known properties: \\\"title\\\", \\\"author\\\", \\\"isbn\\\"])\"}"));
    }

    @Test
    public void
    ResponseAutorIsNotAuthor() {

        given().
                port(8082).
                contentType(ContentType.JSON).
                body("{\"title\":\"hp\",\"autor\":\"jk\",\"isbn\":\"0123456789ABC\"}").
                when().
                post("/shareit/media/books").

                then().
                contentType(ContentType.TEXT).
                statusCode(400).
                body(equalTo("{\"Status\":\"Bad Request\",\"Message\":\"'autor' is not a property. Use the following:  (3 known properties: \\\"title\\\", \\\"author\\\", \\\"isbn\\\"])\"}"));
    }

    @Test
    public void
    ResponseTitelAndAutorIsNotAutorAndAuthor() {

        given().
                port(8082).
                contentType(ContentType.JSON).
                body("{\"titel\":\"hp\",\"autor\":\"jk\",\"isbn\":\"0123456789ABC\"}").
                when().
                post("/shareit/media/books").

                then().
                contentType(ContentType.TEXT).
                statusCode(400).
                body(equalTo("{\"Status\":\"Bad Request\",\"Message\":\"'titel' is not a property. Use the following:  (3 known properties: \\\"title\\\", \\\"author\\\", \\\"isbn\\\"])\"}"));
    }
}
