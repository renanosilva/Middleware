package middleware.identification.annotations;

import java.lang.annotation.Annotation;

public class Method {
    private final Method method;
    private String name;
    private Class<?> returnType;


    public Method(Method method) {
        this.method = method;
    }

    public String getName() {
        return method.getName();
    }

    public Class<?> getReturnType() {
        return method.getReturnType();
    }

    public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
        return method.isAnnotationPresent(annotationClass);
    }

    public Get getAnnotationGet() {
        return null;
    }

    public Post getAnnotationPost() {
        return null;
    }

}


