package teste_middleware_client;
import middleware.Middleware;
import middleware.basic_remoting.Requestor;

import org.json.JSONObject;
import teste_middleware_server.Calculator;

import java.io.IOException;
import java.net.Socket;

public class Teste_Middleware_Client {
    public static void main(String[] args){
        Calculator calc = new Calculator();

        Middleware client = new Middleware();

        client.addMethods(calc);

        Socket socket = client.connect("127.0.0.1", 7080);

        try {

            JSONObject addRequest = new JSONObject();
            addRequest.put("router", "/calc/add");
            addRequest.put("var1", 3);
            addRequest.put("var2", 6);

            System.out.println("Requestor: " + addRequest.toString());
            client.Requestor(socket, addRequest);

            System.out.println("Aguardando resposta do servidor!");
            System.out.println("Resposta do servidor: " + client.Invoker(socket));

            client.Close(socket);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        socket = client.connect("127.0.0.1", 7080);

        try {

            JSONObject subRequest = new JSONObject();
            subRequest.put("router", "/calc/sub");
            subRequest.put("var1", 5);
            subRequest.put("var2", 9);

            System.out.println("Requestor: " + subRequest.toString());
            client.Requestor(socket, subRequest);

            System.out.println("Aguardando resposta do servidor!");
            System.out.println("Resposta do servidor: " + client.Invoker(socket));

            client.Close(socket);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

