package users;

import io.restassured.response.Response;
import users.POJO.Users;
import utils.RestUtils;
import utils.TestCaseTypeGeneratorUtils;
import utils.TestCaseTypesEnum;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class UsersAPI {
    // GET
    public Response getListOfUsers(String testType){
        String endPoint = "";
        if(TestCaseTypeGeneratorUtils.getTestType(TestCaseTypesEnum.POSITIVE).equalsIgnoreCase(testType)){
            endPoint = (String) Base.dataFromJsonFile.get("operateUserEndPoint");
        }else if (TestCaseTypeGeneratorUtils.getTestType(TestCaseTypesEnum.NEGATIVE).equalsIgnoreCase(testType)){
            endPoint = (String) Base.dataFromJsonFile.get("invalidUserEndPoint");
        }else{
            endPoint = null;
        }

        return RestUtils.performGetRequest(endPoint);
    }

    public Response getSingleUser(int id){
        String endPoint = (String) Base.dataFromJsonFile.get("operateUserEndPoint");
        return RestUtils.performGetSingleRequest(endPoint, id);
    }

    // POST
    public Response createUser(Map<String, Object> createUserPayload) throws FileNotFoundException {
        String endPoint = (String) Base.dataFromJsonFile.get("operateUserEndPoint");
        return RestUtils.performPostRequest(endPoint, createUserPayload, new HashMap<>());
    }

    public Response createUser(Users createUserPayload) throws FileNotFoundException {
        String endPoint = (String) Base.dataFromJsonFile.get("operateUserEndPoint");
        return RestUtils.performPostRequest(endPoint, createUserPayload, new HashMap<>());
    }

    // PUT
    public Response updateUser(int idToUpdate, Map<String, Object> updateUserPayload){
        String endPoint = (String) Base.dataFromJsonFile.get("operateUserEndPoint");
        return RestUtils.performPutRequest(endPoint, idToUpdate, updateUserPayload, new HashMap<>());
    }

    // DELETE
    public Response deleteCreatedUser(int idToDelete){
        String endPoint = (String) Base.dataFromJsonFile.get("operateUserEndPoint");
        return RestUtils.performDeleteRequest(endPoint, idToDelete, new HashMap<>());
    }
}
