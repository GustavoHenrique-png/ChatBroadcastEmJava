package Client;

import java.io.*;
import java.net.Socket;
import java.net.SocketAddress;

public class ClientSocket {
    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;

    public ClientSocket(Socket socket) throws IOException {
        this.socket = socket;
        System.out.println("Cliente"+socket.getRemoteSocketAddress()+"conectado");
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(),true);
    }

    public SocketAddress getRemoteSocketAddress(){
        return socket.getRemoteSocketAddress();
    }

    public String getMessage(){
        try{
            return in.readLine();
        }catch (IOException e){
            return null;
        }
    }

    public boolean sendMessage(String message){
        out.println(message);
        return !out.checkError();
    }

    public void close(){
       try{ in.close();
        out.close();
        socket.close();} catch (IOException e) {
           System.out.println("Deu erro ai amigão");
       }
    }


}
