package ua.khshanovskyi.context;

import lombok.SneakyThrows;
import org.reflections8.Reflections;
import ua.khshanovskyi.annotation.Bean;
import ua.khshanovskyi.annotation.Inject;
import ua.khshanovskyi.exception.NoSuchBeanException;
import ua.khshanovskyi.exception.NoUniqueBeanException;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ApplicationContextImpl implements ApplicationContext {
    private Map<String, Object> nameBeanMap;

    public ApplicationContextImpl(String packageScan) {
        nameBeanMap = new HashMap<>();
        scanPackageAndCreateBeans(packageScan);
        injectAll();
    }

    @Override
    public <T> T getBean(Class<T> beanType) throws NoSuchBeanException, NoUniqueBeanException {
        List<T> beans = nameBeanMap.values().stream()
                .filter(bean -> bean.getClass().isAssignableFrom(beanType))
                .map(bean -> ((T) bean))
                .collect(Collectors.toList());

        if (beans.isEmpty()) {
            throw new NoSuchBeanException("Bean for object " + beanType.getName() + " is absent");
        } else if (beans.size() > 1) {
            throw new NoUniqueBeanException("Object " + beanType.getName() + " contains " + beans.size() + " beans");
        }

        return beans.get(0);
    }

    @Override
    public <T> T getBean(String name, Class<T> beanType) throws NoSuchBeanException {
        return nameBeanMap.entrySet().stream()
                .filter(entry -> entry.getKey().equals(name))
                .map(Map.Entry::getValue)
                .filter(bean -> bean.getClass().isAssignableFrom(beanType))
                .map(bean -> ((T) bean))
                .findAny()
                .orElseThrow(() -> new NoSuchBeanException("Bean for object " + beanType.getName() + " is absent"));
    }

    @Override
    public <T> Map<String, T> getAllBeans(Class<T> beanType) {
        return nameBeanMap.entrySet().stream()
                .filter(entry -> entry.getValue().getClass().isAssignableFrom(beanType))
                .collect(Collectors.toMap(Map.Entry::getKey, e -> (T) e.getValue()));
    }


    private void scanPackageAndCreateBeans(String packageScan) {
        new Reflections(packageScan)
                .getTypesAnnotatedWith(Bean.class)
                .stream()
                .map(ApplicationContextImpl::createBean)
                .collect(Collectors.toList())
                .forEach(bean -> nameBeanMap.put(BeanNameProvider.provideNameForBean(bean.getClass()), bean));
    }

    private void injectAll() {
        nameBeanMap.values().forEach(bean ->
                Arrays.stream(bean.getClass().getDeclaredFields())
                        .filter(field -> field.isAnnotationPresent(Inject.class))
                        .collect(Collectors.toList())
                        .forEach(field -> inject(bean, field)));
    }

    @SneakyThrows
    private void inject(Object bean, Field field) {
        field.setAccessible(true);
        field.set(bean, getBean(field.getType()));
    }

    @SneakyThrows
    private static <T> T createBean(Class<T> beanType) {
        return beanType.getConstructor().newInstance();
    }
}
