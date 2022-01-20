package Client;

import java.io.*;
import java.net.Socket;
import java.net.SocketAddress;

public class ClientSocket {
    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;

    //Uso dos metodos que recebem e transmitem e recebem mensagem (in e out) in que recebe e out que envia
    public ClientSocket(Socket socket) throws IOException {
        this.socket = socket;
        System.out.println("Cliente"+socket.getRemoteSocketAddress()+"conectado");
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(),true); //Flush para envio de mensagem de forma não bloqueante
    }

    //Pega o remote addres(ip e porta)
    public SocketAddress getRemoteSocketAddress(){
        return socket.getRemoteSocketAddress();
    }

    //Recebe o objeto mensagem
    public String getMessage(){
        try{
            return in.readLine();
        }catch (IOException e){
            return null;
        }
    }

    //Envia o objeto mensagem
    public boolean sendMessage(String message){
        out.println(message);
        return !out.checkError();
    }

    //Fecha os sockets
    public void close(){
        try{ in.close();
            out.close();
            socket.close();} catch (IOException e) {
            System.out.println("Deu erro ai amigão");
        }
    }


}
