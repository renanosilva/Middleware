// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
package middleware;

import middleware.basic_remoting.ServerRequestHandler;
import middleware.identification.annotations.*;

import java.lang.reflect.Method;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.ServerSocket;
import org.json.JSONObject;

public class Middleware {
    public static void main(String[] args) {
        // Press Alt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        System.out.printf("Main");

    }

    public void start(int serverPort) {
        
        ServerRequestHandler serverRequestHandler = new ServerRequestHandler(serverPort);
        serverRequestHandler.main(null);
        
    }

    public Socket connect(String serverAddress, int serverPort) {
        System.out.println("Conectado ao Middleware no endereco: " + serverAddress + ":" + serverPort);

        Socket socket = null;
        try {
            socket = new Socket(serverAddress, serverPort);

            return socket;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return socket;
    }

    public void addMethods(Object object) {
        //Extract the remote object class
        Class<?> clazz = object.getClass();
        // Walks through all the methods of the class and checks if they have annotations
        // Checks if the annotation is a Get
        for (Method method : clazz.getDeclaredMethods())
            if (method.isAnnotationPresent(Get.class)) {
                System.out.println("addMethod GET: " + clazz.getAnnotation(RequestMap.class).router() + method.getAnnotation(Get.class).router());
                method.setAccessible(true);
                RemoteObject.addMethodGet("get" + clazz.getAnnotation(RequestMap.class).router() + method.getAnnotation(Get.class).router(), method);
            } else if (method.isAnnotationPresent(Post.class)) {
                method.setAccessible(true);
                RemoteObject.addMethodPost("post" + clazz.getAnnotation(RequestMap.class).router() + method.getAnnotation(Post.class).router(), method);
                System.out.println("addMethod POST: " + clazz.getAnnotation(RequestMap.class).router() + method.getAnnotation(Post.class).router());
            }
    }

    public void Requestor(Socket socket, JSONObject Request) throws IOException {
        // Crie fluxos de entrada e saída para comunicação com o servidor
        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            out.writeObject(Request.toString());

            System.out.println("Requestor enviado!");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String Invoker(Socket socket) throws IOException {
        // Crie fluxos de entrada e saída para comunicação com o servidor
        String result = "";

        try {
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            Object Response = in.readObject();

            result = Response.toString();

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public boolean Close(Socket socket) throws IOException {
        boolean result = false;
        try {
            socket.close();

            if (socket.isClosed()){
                result = true;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}