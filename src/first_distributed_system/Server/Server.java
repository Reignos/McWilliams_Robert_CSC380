package first_distributed_system.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: Reignos
 * Date: 7/9/13
 * Time: 6:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class Server {

    public Server(){
        System.out.println("Server Running");
        MathLogic mathLogic = new MathLogic();
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(4444);
        }
        catch (IOException e) {
            System.out.println("Could not listen on port: 4444");
            System.exit(-1);
        }

        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in =
                    new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                if (inputLine.toLowerCase().equals("bye"))
                    break;
                String[] spaceSplit = inputLine.split(" ");
                int a = Integer.parseInt(spaceSplit[1]);
                int b = Integer.parseInt(spaceSplit[2]);
                if(spaceSplit[0].toLowerCase().equals("add"))
                    out.println(a + " + " + b + " = " + mathLogic.add(a,b));

                else if(spaceSplit[0].toLowerCase().equals("sub"))
                    out.println(a + " - " + b + " = " + mathLogic.subtract(a, b));
            }
            out.close();
            in.close();
            clientSocket.close();
            serverSocket.close();
        }
        catch (IOException e) {
            System.out.println("Accept failed: 4444");
            System.exit(-1);
        }
    }

    public static void main(String [ ] args){
        new Server();
    }
}
