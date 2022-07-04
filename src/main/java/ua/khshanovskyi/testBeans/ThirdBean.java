package ua.khshanovskyi.testBeans;

import lombok.Data;
import lombok.ToString;
import ua.khshanovskyi.annotation.Bean;
import ua.khshanovskyi.annotation.Inject;

@Bean(beanName = "third")
@Data
@ToString
public class ThirdBean {
    @Inject
    private First first;
    @Inject
    private Second second;
}
