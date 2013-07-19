package first_distributed_system.Client;

import sun.reflect.generics.scope.MethodScope;

import java.io.*;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
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
public class Client_Part2 {

    public Client_Part2(){
        Socket socket = null;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;

        try {
            Object[] input = new Object[4];
            Object[] output;
            socket = new Socket("127.0.0.1", 4444);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            boolean running = true;
            System.out.println("waiting for server input");
            String inputMethods = (String) in.readObject();
            System.out.println("got input");
            String[] methods = inputMethods.split(";");
            System.out.println("Here are the possible methods to call:");
            int count = 1;
            for(String method : methods){
                System.out.println((count) + ": " + method);
                count++;
            }
            while(running){
                System.out.println("Input a number corresponding to the method you want to call or 'bye' to quit");
                Scanner scan = new Scanner(System.in);
                String methodChoice = scan.nextLine();
                int[] serverOutput = new int[3];
                if(methodChoice.toLowerCase().equals("bye") || input == null){
                    running = false;
                    methodChoice = -1 +"";
                }
                else if(running){
                    System.out.println("Enter the first int: ");
                    serverOutput[1] = scan.nextInt();
                    System.out.println("Enter the second int: ");
                    serverOutput[2] = scan.nextInt();
                }
                serverOutput[0] = Integer.parseInt(methodChoice);
                out.writeObject(serverOutput);
                String response = (String)in.readObject();
                System.out.println("Server: " + response);
            }

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
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    public static void main(String [ ] args){
        new Client_Part2();
    }
}
