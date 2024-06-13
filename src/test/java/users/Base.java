package users;

import utils.JsonUtils;

import java.io.IOException;
import java.util.Map;

public class Base {
    public static Map<String, Object> dataFromJsonFile;

    static{
        String env = System.getProperty("dev_data_test") == null ? "qa_data_test" : "dev_data_test";
        try{
            dataFromJsonFile = JsonUtils.getJsonDataAsMap("testdata/users/"+env+"/usersAPI.json");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
