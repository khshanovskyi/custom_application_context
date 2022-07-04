package ua.khshanovskyi.context;

import ua.khshanovskyi.annotation.Bean;

public class BeanNameProvider {
    private BeanNameProvider() {
    }

    static String provideNameForBean(Class<?> beanType) {
        String beanName = beanType.getAnnotation(Bean.class).beanName();
        return beanName.equals("") ?
                nameToLoverCaseFirstLetter(beanType) :
                beanName;
    }

    private static String nameToLoverCaseFirstLetter(Class<?> beanType) {
        String name = beanType.getSimpleName();
        return name.replace(name.charAt(0), Character.toLowerCase(name.charAt(0)));
    }


}
