package ua.khshanovskyi.test;

import lombok.SneakyThrows;
import ua.khshanovskyi.context.ApplicationContext;
import ua.khshanovskyi.context.ApplicationContextImpl;
import ua.khshanovskyi.testBeans.First;
import ua.khshanovskyi.testBeans.ThirdBean;

public class Task {
    @SneakyThrows
    public static void main(String[] args) {
        ApplicationContext appContext = new ApplicationContextImpl("ua.khshanovskyi.testBeans");
        First first = appContext.getBean(First.class);
        var third = appContext.getBean("third",ThirdBean.class);
        System.out.println(first);
        System.out.println(third);
        System.out.println(third.getSecond());
    }
}
