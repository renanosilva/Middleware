package middleware.identification.annotations;

import java.util.HashMap;
import java.util.Map;

public class RemoteObject {
    private static final Map<String, java.lang.reflect.Method> getMethods = new HashMap<String, java.lang.reflect.Method>();
    private static final Map<String, java.lang.reflect.Method> postMethods = new HashMap<String, java.lang.reflect.Method>();

    public static void addMethodPost(String name, java.lang.reflect.Method method) {

        postMethods.put(name, method);
    }

    public static void addMethodGet(String name, java.lang.reflect.Method method) {

        getMethods.put(name, method);
    }

    // Outros métodos para acessar e processar os métodos
}

