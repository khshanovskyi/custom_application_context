package ua.khshanovskyi.context;

import ua.khshanovskyi.exception.NoSuchBeanException;
import ua.khshanovskyi.exception.NoUniqueBeanException;

import java.util.List;

public interface ApplicationContext {
    <T> T getBean(Class<T> beanType) throws NoSuchBeanException, NoUniqueBeanException;

    <T> T getBean(String name, Class<T> beanType) throws NoSuchBeanException;

    <T> List<T> getAllBeans(Class<T> beanType);
}
