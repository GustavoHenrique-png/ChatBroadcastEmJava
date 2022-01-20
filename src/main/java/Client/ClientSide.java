package Client;

import Server.ServerSide;


import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

//Implement de Runnable para implementar o run que recebe a mensagem do servidor
public class ClientSide implements Runnable {
    private ClientSocket clientSocket;
    private Scanner scanner;
    private final String ServerAddres="127.0.0.1";

    //Criação do input do cliente
    public void Message(){
        scanner = new Scanner(System.in);
    }

    //Declaração do socket do client
    public void stat() throws IOException {
        clientSocket = new ClientSocket(new Socket(ServerAddres, ServerSide.PORT));
        System.out.println("Cliente conectado no servidor"+ServerAddres+":"+ServerSide.PORT);
        new Thread(this).start();
        messageLoop();
    }

    //Criação do Looping que envia mensagens e mantém aberto pra enviar mais mensagens
    private void messageLoop() throws IOException {
        Message();
        String message;
        do {
            System.out.println("Digite uma mensagem ou digite sair pra finalizar");
            message = scanner.nextLine();
            clientSocket.sendMessage(message);
        } while (!message.equalsIgnoreCase("sair"));

        clientSocket.close();

    }
    //Conectando com o servidor
    public static void main(String[] args) {
        ClientSide client = new ClientSide();
        try {
            client.stat();
        } catch (IOException e) {
            System.out.println("Não foi possível conectar ao servidor");
        }
    }

    //Run que recebe a mensagem do servidor de volta
    @Override
    public void run() {
        String message;
        while ((message = clientSocket.getMessage())!=null){
            System.out.println(message);
        }

    }
}
