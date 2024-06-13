package users.POJO;


import lombok.*;
import utils.RandomDataGeneratorEnum;
import utils.RandomDataGeneratorUtils;

import java.util.Arrays;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder(toBuilder = true)
public class Users {
    @Builder.Default
    private String email = RandomDataGeneratorUtils.getRandomData(RandomDataGeneratorEnum.EMAIL);
    @Builder.Default
    private String password = RandomDataGeneratorUtils.getRandomData(RandomDataGeneratorEnum.PASSWORD);
    @Builder.Default
    private String name = RandomDataGeneratorUtils.getRandomData(RandomDataGeneratorEnum.NAME);
    @Builder.Default
    private String role = Arrays.asList("customer", "admin").get(RandomDataGeneratorUtils.getRandomNumber(0, 2));
    @Builder.Default
    private String avatar = RandomDataGeneratorUtils.getRandomData(RandomDataGeneratorEnum.AVATAR);
}
