package first_distributed_system.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: Reignos
 * Date: 7/9/13
 * Time: 6:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class Client {

    public Client(){
        System.out.println("Would you like to add or subtract numbers? 1 for add, 2 for subtract");
        Scanner scanner  = new Scanner(System.in);
        String firstInput = scanner.next();
        String giveToServer = "";
        if (firstInput.equals("1")){
            giveToServer += "add ";
        }
        else if(firstInput.equals("2")){
            giveToServer += "sub ";
        }
        System.out.println("What is the first number?");
        String secondInput = scanner.next();
        giveToServer += secondInput + " ";

        System.out.println("What is the second number?");
        String thirdInput = scanner.next();
        giveToServer += thirdInput;
        Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            socket = new Socket("127.0.0.1", 4444);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            out.println(giveToServer);
            String fromServer = in.readLine();
            System.out.println("Server: " + fromServer);

            out.close();
            in.close();
            stdIn.close();
            socket.close();
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: taranis.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: taranis.");
            System.exit(1);
        }

    }

    public static void main(String [ ] args){
        new Client();
    }
}
