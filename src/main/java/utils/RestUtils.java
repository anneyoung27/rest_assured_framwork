package utils;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import reporting.ExtentReportManager;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Map;

public class RestUtils {

    // GET
    public static RequestSpecification requestGetSpecification(String endPoint) {
        return RestAssured.given()
                .baseUri(endPoint);
    }

    public static RequestSpecification requestGetSingleSpecification(String endPoint, int id) {
        RestAssured.baseURI = endPoint;
        return RestAssured.given()
                .pathParam("id", id);
    }

    // POST
    public static RequestSpecification requestPostSpecification(String endPoint, Object requestPayLoad, Map<String, String> headers){
        return RestAssured.given()
                .baseUri(endPoint)
                .headers(headers)
                .contentType(ContentType.JSON)
                .body(requestPayLoad);
    }

    // PUT
    public static RequestSpecification requestPutSpecification(String endPoint, int id, Object requestPayload, Map<String, String> headers){
        return RestAssured.given()
                .pathParam("id", id)
                .baseUri(endPoint)
                .headers(headers)
                .contentType(ContentType.JSON)
                .body(requestPayload);
    }

    // DELETE
    public static RequestSpecification requestDeleteSpecification(String endPoint, int id, Map<String, String> headers){
        return RestAssured.given()
                .pathParam("id", id)
                .baseUri(endPoint)
                .headers(headers);
    }


    // PERFORM GET - REQUEST
    public static Response performGetRequest(String endPoint){
        RequestSpecification requestSpecification = requestGetSpecification(endPoint);
        Response response = requestSpecification.get();
        printRequestLogInReport(requestSpecification);
        printResponseLogInReport(response);
        return response;
    }

    public static Response performGetSingleRequest(String endPoint, int getId){
        RequestSpecification requestSpecification = requestGetSingleSpecification(endPoint, getId);
        Response response =requestSpecification.get("/{id}");
        printRequestLogInReport(requestSpecification);
        printResponseLogInReport(response);
        return response;
    }

    // PERFORM POST - REQUEST
    public static Response performPostRequest(String endPoint, Map<String, Object> requestPayload, Map<String, String> headers) throws FileNotFoundException {
        // Add log to output
        PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
        RequestSpecification requestSpecification = requestPostSpecification(endPoint, requestPayload, headers);
        requestSpecification.filter(RequestLoggingFilter.logRequestTo(log));
        requestSpecification.filter(ResponseLoggingFilter.logResponseTo(log));
        Response response = requestSpecification.post();
        printRequestLogInReport(requestSpecification);
        printResponseLogInReport(response);
        return response;
    }

    public static Response performPostRequest(String endPoint, Object requestPayLoad, Map<String, String> headers) throws FileNotFoundException {
        // Add log to output
        PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
        RequestSpecification requestSpecification = requestPostSpecification(endPoint, requestPayLoad, headers);
        requestSpecification.filter(RequestLoggingFilter.logRequestTo(log));
        requestSpecification.filter(ResponseLoggingFilter.logResponseTo(log));
        Response response = requestSpecification.post();
        printRequestLogInReport(requestSpecification);
        printResponseLogInReport(response);
        return response;
    }

    // PERFORM PUT - REQUEST
    public static Response performPutRequest(String endPoint, int updateId, Map<String, Object> requestPayload, Map<String, String> headers){
        RequestSpecification requestSpecification = requestPutSpecification(endPoint, updateId, requestPayload, headers);
        Response response = requestSpecification.put("/{id}");
        printRequestLogInReport(requestSpecification);
        printResponseLogInReport(response);
        return response;
    }

    public static Response performPutRequest(String endPoint, int updateId, Object requestPayload, Map<String, String> headers){
        RequestSpecification requestSpecification = requestPutSpecification(endPoint, updateId, requestPayload, headers);
        Response response = requestSpecification.put("/{id}");
        printRequestLogInReport(requestSpecification);
        printResponseLogInReport(response);
        return response;
    }

    // PERFORM DELETE - REQUEST
    public static Response performDeleteRequest(String endPoint, int deleteId, Map<String, String> headers){
        RequestSpecification requestSpecification = requestDeleteSpecification(endPoint, deleteId, headers);
        Response response = requestSpecification.delete("/{id}");
        printRequestLogInReport(requestSpecification);
        printResponseLogInReport(response);
        return response;
    }

    // FOR REPORT

    private static void printRequestLogInReport(RequestSpecification requestSpecification) {
        QueryableRequestSpecification queryableRequestSpecification = SpecificationQuerier.query(requestSpecification);
        ExtentReportManager.logInfoDetails("Endpoint is " + queryableRequestSpecification.getBaseUri());
        ExtentReportManager.logInfoDetails("Method is " + queryableRequestSpecification.getMethod());
        ExtentReportManager.logInfoDetails("Headers are ");
        ExtentReportManager.logHeaders(queryableRequestSpecification.getHeaders().asList());
        ExtentReportManager.logInfoDetails("Request body is ");
        ExtentReportManager.logJson(queryableRequestSpecification.getBody());
    }

    private static void printResponseLogInReport(Response response) {
        ExtentReportManager.logInfoDetails("Response status is " + response.getStatusCode());
        ExtentReportManager.logInfoDetails("Response Headers are ");
        ExtentReportManager.logHeaders(response.getHeaders().asList());
        ExtentReportManager.logInfoDetails("Response body is ");
        ExtentReportManager.logJson(response.getBody().prettyPrint());
    }

}
