package ua.khshanovskyi.testBeans;

import ua.khshanovskyi.annotation.Bean;

@Bean
public class ZeroImpl implements Zero {
    @Override
    public void invoke() {
        System.out.println("hello from zero");
    }
}
