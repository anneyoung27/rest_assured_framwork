package users;

import users.POJO.Users;
import utils.RandomDataGeneratorEnum;
import utils.RandomDataGeneratorUtils;

import java.util.HashMap;
import java.util.Map;

public class Payload {

    public static Map<String, Object> getCreateUserPayload(String email, String password, String name, String role, String avatar){
        Map<String, Object> payLoad = new HashMap<>();
        payLoad.put("email", email);
        payLoad.put("password", password);
        payLoad.put("name", name);
        payLoad.put("role", role);
        payLoad.put("avatar", avatar);

        return payLoad;
    }

    public static Users getCreateUserPayload(){
        return Users.builder()
                .email(RandomDataGeneratorUtils.getRandomData(RandomDataGeneratorEnum.EMAIL))
                .password(RandomDataGeneratorUtils.getRandomData(RandomDataGeneratorEnum.PASSWORD))
                .name(RandomDataGeneratorUtils.getRandomData(RandomDataGeneratorEnum.NAME))
                .role(RandomDataGeneratorUtils.getRandomData(RandomDataGeneratorEnum.ROLE))
                .avatar(RandomDataGeneratorUtils.getRandomData(RandomDataGeneratorEnum.AVATAR))
                .build();
    }

    public static Map<String, Object> getUpdateUserPayload(String emailToUpdate, String nameToUpdate){
        Map<String, Object> updatePayLoad = new HashMap<>();
        updatePayLoad.put("email", emailToUpdate);
        updatePayLoad.put("name", nameToUpdate);

        return updatePayLoad;
    }

}
