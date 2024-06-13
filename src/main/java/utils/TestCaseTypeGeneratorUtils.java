package utils;

public class TestCaseTypeGeneratorUtils {
    public static String getTestType(TestCaseTypesEnum type){
        return switch (type) {
            case POSITIVE -> "1";
            case NEGATIVE -> "0";
            default -> "Test case type not available!";
        };
    }
}
