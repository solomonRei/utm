package org.utm.lab1.factory;

import java.io.IOException;
import java.net.URISyntaxException;

public class ApplicationContext {

    private final BeanFactory beanFactory = new BeanFactory();

    private final String basePackage;

    public ApplicationContext(String basePackage) {
        this.basePackage = basePackage;
    }

    public void run() throws ReflectiveOperationException, IOException, URISyntaxException {
        System.out.println("******Context is under construction******");

        beanFactory.instantiate(basePackage);
        beanFactory.injectBeanNames();
        beanFactory.processConsoleCommands();
    }

    public Object getBean(String beanName) {
        return beanFactory.getBean(beanName);
    }
}
