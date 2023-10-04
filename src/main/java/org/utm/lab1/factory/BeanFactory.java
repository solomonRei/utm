package org.utm.lab1.factory;

import org.utm.lab1.factory.annotations.Command;
import org.utm.lab1.factory.annotations.Component;
import org.utm.lab1.factory.interfaces.BeanNameAware;
import org.utm.lab1.utils.ConsoleOutputMessages;
import org.utm.lab1.utils.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanFactory {

    private final Map<String, Object> singletons = new HashMap();

    public Object getBean(String beanName) {
        return singletons.get(beanName);
    }

    public void instantiate(String basePackage) throws IOException, URISyntaxException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        String path = basePackage.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);

        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            File file = new File(resource.toURI());
            List<Class<?>> classes = findClasses(file, basePackage);

            for (Class<?> classObject : classes) {
                if (classObject.isAnnotationPresent(Component.class)) {
                    Object instance = classObject.newInstance();
                    var className = classObject.getSimpleName();
                    var beanName = className.substring(0, 1).toLowerCase() + className.substring(1);

                    singletons.put(beanName, instance);

                    Method[] methods = classObject.getMethods();
                    Map<String, List<String>> methodsMap = new HashMap<>();

                    for (Method method : methods) {
                        if (method.isAnnotationPresent(Command.class)) {
                            List<String> methodInfoList = new ArrayList<>();
                            methodInfoList.add(method.getName());
                            methodInfoList.add(beanName);

                            var commandAnnotation = method.getAnnotation(Command.class);

                            methodsMap.put(commandAnnotation.name(), methodInfoList);
                        }
                    }
                    singletons.putAll(methodsMap);
                }
            }
        }
    }

    public void processConsoleCommands() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int counter = 0;

        ConsoleOutputMessages.greetingMessage();
        System.out.print(ConsoleOutputMessages.INPUT_YOUR_COMMAND_MESSAGE);

        String commandInput = reader.readLine();
        String[] parts = commandInput.split("/");
        String commandName = parts[0];

        while (!commandName.equals("q")) {
            parts = commandInput.split("/");
            commandName = parts[0];

            if (commandName.equals("q")) {
                System.out.println("Exiting...");
                break;
            }

            if (singletons.containsKey(commandName)) {
                ArrayList<String> commandObject = (ArrayList<String>) singletons.get(commandName);

                Method method = findMatchingMethod(singletons.get(commandObject.get(1)), commandObject.get(0));

                if (method != null) {
                    Class<?>[] parameterTypes = method.getParameterTypes();

                    try {
                        if (parts.length - 1 == parameterTypes.length && method.getParameterCount() > 0) {
                            Object[] params = new Object[parameterTypes.length];
                            for (int i = 0; i < params.length; i++) {
                                params[i] = convertToType(parts[i + 1], parameterTypes[i]);
                            }

                            method.invoke(singletons.get(commandObject.get(1)), params);
                            System.out.print(ConsoleOutputMessages.INPUT_YOUR_COMMAND_MESSAGE);
                        } else if (parameterTypes.length == 0 && parts.length == 1) {
                            method.invoke(singletons.get(commandObject.get(1)));
                            System.out.print(ConsoleOutputMessages.INPUT_YOUR_COMMAND_MESSAGE);
                        } else {
                            Logger.log("Wrong amount of parameters for command: " + commandName + "\n Expected: " +
                                    parameterTypes.length + " Actual: " + (parts.length - 1));
                            System.out.println("Wrong amount of parameters for command: " + commandName + "\n Expected: " +
                                    parameterTypes.length + " Actual: " + (parts.length - 1));
                            System.out.print(ConsoleOutputMessages.INPUT_YOUR_COMMAND_MESSAGE);
                        }
                    } catch (Exception e) {
                        Logger.log("Error while executing command: " + commandName);
                        e.printStackTrace();
                    }
                } else {
                    Logger.log("Incorrect method to command: " + commandName);
                    System.out.println("Incorrect method to command: " + commandName);
                    System.out.print(ConsoleOutputMessages.INPUT_YOUR_COMMAND_MESSAGE);
                }
            } else {
                Logger.log("Wrong Command: " + commandName);
                System.out.println("Wrong Command: " + commandName);
                System.out.print(ConsoleOutputMessages.INPUT_YOUR_COMMAND_MESSAGE);
            }

            commandInput = reader.readLine();
        }

    }


    private Method findMatchingMethod(Object controllerObject, String methodName) {
        Class<?> controllerClass = controllerObject.getClass();

        for (Method method : controllerClass.getDeclaredMethods()) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }

        return null;
    }

    private Object convertToType(String value, Class<?> targetType) {
        if (targetType.equals(String.class)) {
            return value;
        } else if (targetType.equals(int.class) || targetType.equals(Integer.class)) {
            return Integer.parseInt(value);
        } else {
            throw new IllegalArgumentException("Unsupported data type: " + targetType.getName());
        }
    }


    private List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }

        File[] files = directory.listFiles();
        if (files == null) {
            return classes;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                var className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
                Class<?> classObject = Class.forName(className);
                classes.add(classObject);
            }
        }
        return classes;
    }

    public void injectBeanNames() {
        for (String name : singletons.keySet()) {
            Object bean = singletons.get(name);
            if (bean instanceof BeanNameAware) {
                ((BeanNameAware) bean).setBeanName(name);
            }
        }
    }
}
