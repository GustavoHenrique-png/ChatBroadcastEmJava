package Server;
import Client.ClientSide;
import Client.ClientSocket;

import java.io.*;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ServerSide{
    //Crição das variaveis e set da porta
    public static final int PORT = 10000;
    private ServerSocket serverSocket;
    private final List<ClientSocket> clients = new LinkedList<>();
    //private final HashMap<ClientSide, ClientSocket> clientes = new HashMap<>();

    //clientes.put("01", new ServerSide(ClientSocket));
    //clientes.put("02", new String(ClientSocket.getRemoteAddres))

    //Instanciamento do Socket
    public void stat() throws IOException {
        serverSocket = new ServerSocket(PORT);
        ConnectionLooping();
    }

    //Criação do looping que mantém o servidor aberto
    private void ConnectionLooping() throws IOException {
        while (true){
            ClientSocket clientSocket = new ClientSocket(serverSocket.accept());
            clients.add(clientSocket);
            new Thread(()->clientLoop(clientSocket)).start(); //Função lambda para manter o esrver recevendo as mensagens
        }
    }

    //Loop que espera mensagem do client  e permanece aberto pra continuar recebendo
    public void clientLoop(ClientSocket clientSocket){
        String message;
        try{
            while ((message = clientSocket.getMessage()) != null){
                if ("sair".equalsIgnoreCase(message))
                    return;
                System.out.println("mensagem recebida do cliente: "+clientSocket.getRemoteSocketAddress()+": "+message);
                sendMessageToHer(clientSocket,message);
            }
        } finally {
            clientSocket.close();
        }
    }


    //Metodo que distribui a mensagem para todos os usuários da lista
    private void sendMessageToHer(ClientSocket sender, String message){
        for (ClientSocket clientSocket:clients){
            if(!sender.equals(clientSocket)){
                clientSocket.sendMessage(message);
            }
        }

    }

    //Abertura do servidor
    public static void main(String[] args) {
        ServerSide server = new ServerSide();
        try {
            server.stat();
        } catch (IOException e) {
            System.out.println("Não foi possível abrir ao servidor");;
        }
    }
}



