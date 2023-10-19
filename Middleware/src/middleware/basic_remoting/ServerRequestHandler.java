package middleware.basic_remoting;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.json.JSONObject;

public class ServerRequestHandler {
	private static int serverPort;
	
	public ServerRequestHandler(int serverPort) {
        this.serverPort = serverPort; // Porta em que o servidor estar· ouvindo
    }	
	
	public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(serverPort);
            System.out.println("Middleware em execuÁ„o na porta: " + serverPort);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nova conex„o de cliente recebida.");

                ConnectionPool clientConnection = new ConnectionPool(clientSocket);
                clientConnection.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	// Thread independente de conex„o - NAO IMPLEMENTADA
	static class threadPool extends Thread{}
	
	// Uma Thread por conex„o
	static class ConnectionPool extends Thread{
		private Socket clientSocket;

        public ConnectionPool(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {        	
            // Processar a solicitacao recebida do cliente
            try {
            	
            	// Crie fluxos de entrada e saida para comunicacao com o cliente
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

                // Leitura do objeto serializado do cliente
                Object clientRequest = in.readObject();
                System.out.println("Requisicao recebida.");
            	
                //JSONParser parser = new JSONParser();
                //JSONObject jsonRequest = (JSONObject) parser.parse(clientRequest);
                JSONObject jsonRequest = new JSONObject(clientRequest.toString());
                //JSONObject jsonRequest = (JSONObject) clientRequest;
                System.out.println(jsonRequest.toString());
                String methodName = jsonRequest.getString("router");

                if ("/calc/add".equals(methodName)) {
                    int var1 = jsonRequest.getInt("var1");
                    int var2 = jsonRequest.getInt("var2");

                    int result = var1 + var2;

                    // Preparar a resposta
                    JSONObject jsonResponse = new JSONObject();
                    jsonResponse.put("result", result);

                    out.writeObject(jsonResponse.toString());
                } else if ("/calc/sub".equals(methodName)) {
                    int var1 = jsonRequest.getInt("var1");
                    int var2 = jsonRequest.getInt("var2");

                    int result = var1 - var2;

                    // Preparar a resposta
                    JSONObject jsonResponse = new JSONObject();
                    jsonResponse.put("result", result);

                    out.writeObject(jsonResponse.toString());
                } else {
                    System.out.println("M√©todo n√£o reconhecido: " + methodName);
                }
            } catch (ClassCastException | IOException | ClassNotFoundException e) {
                System.out.println("Erro ao processar a solicita√ß√£o do cliente: " + e.getMessage());
            }

            // Feche a conexcao com o cliente
            try {
				clientSocket.close();
	            System.out.println("Solicitacao finalizada.");
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
	}
}
