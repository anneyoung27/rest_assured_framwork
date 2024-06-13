package utils;

import com.aventstack.extentreports.markuputils.MarkupHelper;
import io.restassured.response.Response;
import reporting.ExtentReportManager;
import reporting.Setup;

import java.util.*;

public class AssertionsUtils {
    public static void assertExpectedValuesWithJsonPath(Response response, Map<String, Object> expectedValuesMap){
        List<AssertionKeyUtils> actualValuesMap = new ArrayList<>();
        // Table headers
        actualValuesMap.add(new AssertionKeyUtils("JSON_PATH", "EXPECTED_VALUE", "ACTUAL_VALUE", "RESULT"));
        boolean allMatches = true;
        // Iterate to extract value from response using jsonpath
        Set<String> jsonPaths = expectedValuesMap.keySet(); // Make sure unique
        for(String jsonPath : jsonPaths){
            Optional<Object> actualValue = Optional.ofNullable(response.jsonPath().get(jsonPath)); // Expected Value
            Object expectedValue = expectedValuesMap.get(jsonPath); // Expected Value
            Object value = actualValue.orElse("");  // Actual Value

            // Convert expectedValue and actualValue to string for comparison
            String expectedValueStr = expectedValue == "" ? expectedValue.toString() : "";
            String actualValueStr = value == "" ? value.toString() : "";

            if (expectedValueStr.equals(actualValueStr)) {
                // If actual & expected value match
                actualValuesMap.add(new AssertionKeyUtils(jsonPath, expectedValueStr, actualValueStr, "MATCHED"));
            } else {
                // otherwise
                allMatches = false;
                actualValuesMap.add(new AssertionKeyUtils(jsonPath, expectedValueStr, actualValueStr, "NOT-MATCHED"));
            }
        }
        // To decide final result
        if(allMatches)
            ExtentReportManager.logPassDetails("All assertions are passed.");
        else
            ExtentReportManager.logFailureDetails("All assertion are not passed");

        // To log the details in a tabular format in extent report
        String[][] finalAssertionsMap = actualValuesMap.stream()
                .map(assertions -> new String[]{assertions.getJsonPath(),
                        String.valueOf(assertions.getExpectedValue()),
                        String.valueOf(assertions.getActualValue()),
                        assertions.getResult()})
                .toArray(String[][]::new);
        Setup.extentTest.get().info(MarkupHelper.createTable(finalAssertionsMap));
    }
}
