package first_distributed_system.Server;

import java.io.*;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: Reignos
 * Date: 7/9/13
 * Time: 6:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class Server_Part2 {

    private class ClientRun extends Thread{
        public Socket clientSocket;
        public ClientRun(Socket cSocket){
            clientSocket = cSocket;
        }
        @Override
        public void run() {
            try{
                MathLogic mathLogic = new MathLogic();
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
                int[] inputArray;
                String output;
                Method[] methods = Class.forName("first_distributed_system.Server.MathLogic").getDeclaredMethods();
                String methodNames = "";
                for(Method m : methods){
                    methodNames += m.toString() + ";";
                }
                methodNames = methodNames.substring(0, methodNames.length()-1);
                out.writeObject(methodNames);
                boolean running = true;
                while (running) {
                    System.out.println("waiting on input");
                    inputArray = (int[])in.readObject();
                    int input = (int)inputArray[0];
                    if (input == -1){
                        break;
                    }
                    System.out.println("Client inputted: " + input);
                    Method toCall = methods[inputArray[0]-1];
                    int[] args = new int[inputArray.length-1];
                    if(inputArray.length > 1){
                        for(int i = 1; i < inputArray.length; i++){
                            args[i-1] = inputArray[i];
                        }
                    }
                    System.out.println("about to invoke");
                    output = "" + toCall.invoke(mathLogic, args[0], args[1]);
                    out.writeObject(output);
                    System.out.println("done invoking");
                    System.out.println(output + " sent to Client");
                }

                out.close();
                in.close();
                clientSocket.close();
            }
            catch (Exception e) {
                System.out.println("Accept failed: 4444");
                System.exit(-1);
            }
        }
    }

    public Server_Part2(){
        System.out.println("Server Running");

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(4444);
        }
        catch (IOException e) {
            System.out.println("Could not listen on port: 4444");
            System.exit(-1);
        }

        boolean running = true;
        do{
            try {
                new ClientRun(serverSocket.accept()).start();
                System.out.println("A client has connected.");
            }
            catch (IOException e) {
                System.out.println("Accept failed: 4444");
                System.exit(-1);
            }
        } while(running);
    }

    public static void main(String [ ] args){
        new Server_Part2();
    }
}
