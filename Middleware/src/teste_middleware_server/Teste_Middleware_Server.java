package teste_middleware_server;

import middleware.Middleware;

public class Teste_Middleware_Server {
    public static void main(String[] args){
        Calculator calc = new Calculator();

        Middleware server = new Middleware();

        server.addMethods(calc);

        server.start(7080);
    }

}
