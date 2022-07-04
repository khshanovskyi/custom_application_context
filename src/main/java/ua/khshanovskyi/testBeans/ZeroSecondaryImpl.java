package ua.khshanovskyi.testBeans;

import lombok.Data;
import lombok.ToString;
import ua.khshanovskyi.annotation.Bean;

@Bean(beanName = "zzero")
@Data
@ToString
public class ZeroSecondaryImpl implements Zero{
    @Override
    public void invoke() {

    }
}
