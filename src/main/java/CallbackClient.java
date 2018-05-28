import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class CallbackClient {

    public static void main(String[] args) {


        try {
            int RMIPort;
            String hostName;

            InputStreamReader is = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(is);

            System.out.println("Enter the RMIRegistry host name:");
            hostName = br.readLine();

            System.out.println("Enter the RMIRegistry port number:");
            String portNumber = br.readLine();
            RMIPort = Integer.parseInt(portNumber);

            System.out.println("Enter how many seconds to stay registered:");
            String timeDuration = br.readLine();
            int time = Integer.parseInt(timeDuration);

            String registryUrl = "rmi://" + hostName + ":" + RMIPort + "/callback";

            CallbackServerInterface h = (CallbackServerInterface) Naming.lookup(registryUrl);
            System.out.println("Lookup completed ");
            System.out.println("Server said " + h.sayHello());

            CallbackClientInterface callbackObj =
                    new CallbackClientImpl();
            // register for callback
            h.registerForCallback(callbackObj);

            System.out.println("Registered for callback!");

            Thread inputThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Scanner scanner = new Scanner(System.in);
                    while (true) {
                        try {
                            String input = scanner.nextLine();
                            switch (input) {

                                case "stop": {
                                    h.unregisterForCallback(callbackObj);
                                    System.out.println("Unregistered for callback!");
                                    input = null;
                                    break;
                                }
                                case "start": {
                                    h.registerForCallback(callbackObj);
                                    System.out.println("Registered for callback!");
                                    input = null;
                                    break;
                                }
                                case "exit": {
                                    h.unregisterForCallback(callbackObj);
                                    System.out.println("Unregistered for callback!");
                                    System.exit(0);

                                }
                            }
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }

            });

            System.out.println("\n****To immediately unregister the client enter 'stop'****");
            System.out.println("\n****To register the client enter 'start'****");
            inputThread.start();


            try {
                Thread.sleep(time * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            h.unregisterForCallback(callbackObj);
            System.out.println("Unregistered for callback!");

        } catch (IOException | NotBoundException e) {
            e.printStackTrace();
        }
    }

}
