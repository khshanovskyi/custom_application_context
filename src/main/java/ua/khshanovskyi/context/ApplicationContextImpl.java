package ua.khshanovskyi.context;

import lombok.SneakyThrows;
import org.reflections8.Reflections;
import ua.khshanovskyi.annotation.Bean;
import ua.khshanovskyi.exception.NoSuchBeanException;
import ua.khshanovskyi.exception.NoUniqueBeanException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ApplicationContextImpl implements ApplicationContext {
    private Map<String, Object> nameBeanMap;

    public ApplicationContextImpl(String packageScan) {
        nameBeanMap = new HashMap<>();
        collectBeans(scanPackageAndCreateBeans(packageScan));
    }

    @Override
    public <T> T getBean(Class<T> beanType) throws NoSuchBeanException, NoUniqueBeanException {
        List<T> beans = getAllBeans(beanType);

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
    public <T> List<T> getAllBeans(Class<T> beanType) {
        return nameBeanMap.values().stream()
                .filter(bean -> bean.getClass().isAssignableFrom(beanType))
                .map(bean -> ((T) bean))
                .collect(Collectors.toList());
    }

    private void collectBeans(List<Object> beans) {
        beans.forEach(bean -> nameBeanMap.put(BeanNameProvider.provideNameForBean(bean.getClass()), bean));
    }

    private List<Object> scanPackageAndCreateBeans(String packageScan) {
        return new Reflections(packageScan)
                .getTypesAnnotatedWith(Bean.class)
                .stream()
                .map(ApplicationContextImpl::createBean)
                .collect(Collectors.toList());
    }

    @SneakyThrows
    private static <T> T createBean(Class<T> beanType) {
        return beanType.getConstructor().newInstance();
    }
}
