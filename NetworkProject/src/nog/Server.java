package nog;
 
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
 
 
public class Server {
 
        /**
         * @param args
         */
        public static void main(String[] args) throws IOException {
                boolean listening = true;
                ServerSocket serverSocket = null;
       /*****************
        * JAG LA TILL DEN HÄR KOMMENTAREN FÖR ATT TESTA GIT.ghjkyuh
        */
        try {
            serverSocket = new ServerSocket(5555);
        } catch (IOException e) {
            System.out.println(e);
        }
       
        while(listening) {
                Socket clientSocket = serverSocket.accept();
                new TSVRead(clientSocket).start();
        }
       
        serverSocket.close();
        }
}