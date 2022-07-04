package ua.khshanovskyi.testBeans;

import lombok.Data;
import lombok.ToString;
import ua.khshanovskyi.annotation.Bean;
import ua.khshanovskyi.annotation.Inject;
import ua.khshanovskyi.annotation.Qualifier;

@Bean
@Data
@ToString
public class Second {
    @Inject
    @Qualifier(name = "zzero")
    private Zero zero;
}
