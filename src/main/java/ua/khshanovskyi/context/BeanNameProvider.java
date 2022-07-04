package ua.khshanovskyi.context;

import ua.khshanovskyi.annotation.Bean;

public class BeanNameProvider {
    private BeanNameProvider() {
    }

    static <T> String provideNameForBean(Class<T> beanType) {
        String beanName = beanType.getAnnotation(Bean.class).beanName();
        return beanName.equals("") ?
                nameToLoverCaseFirstLetter(beanType) :
                beanName;
    }

    private static <T> String nameToLoverCaseFirstLetter(Class<T> beanType) {
        String name = beanType.getSimpleName();
        return name.replace(name.charAt(0), Character.toLowerCase(name.charAt(0)));
    }


}
