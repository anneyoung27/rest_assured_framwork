package utils;

import net.datafaker.Faker;

import java.util.Arrays;

public class RandomDataGeneratorUtils {
    public static Faker faker = new Faker();

    public static String getRandomData(RandomDataGeneratorEnum genData){
        return switch(genData){
            case EMAIL -> faker.internet().emailAddress();
            case PASSWORD -> faker.internet().password();
            case NAME -> faker.name().fullName();
            case ROLE -> Arrays.asList("customer", "admin").get(RandomDataGeneratorUtils.getRandomNumber(0, 2));
            case AVATAR -> faker.internet().image();
            default -> "Data type name not available!";
        };
    }

    public static int getRandomNumber(int min, int max){
        return faker.number().numberBetween(min, max);
    }
}
