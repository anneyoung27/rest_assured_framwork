package utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AssertionKeyUtils {
    private String jsonPath;
    private Object expectedValue;
    private Object actualValue;
    private String result;
}
