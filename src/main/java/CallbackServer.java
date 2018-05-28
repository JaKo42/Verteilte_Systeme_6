import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class CallbackServer {

    public static void main(String[] args) {
        InputStreamReader is =
                new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(is);
        String portNum, registryURL;
        try {
            System.out.println(
                    "Enter the hostname:");
            String hostname = br.readLine();
            System.out.println(
                    "Enter the RMIregistry port number:");
            portNum = (br.readLine()).trim();
            int RMIPortNum = Integer.parseInt(portNum);
            startRegistry(RMIPortNum);
            CallbackServerImpl exportedObj =
                    new CallbackServerImpl();
            registryURL =
                    "rmi://" + hostname + ":" + portNum + "/callback";
            Naming.rebind(registryURL, exportedObj);
            System.out.println("Callback Server ready.");

            Thread inputThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Scanner scanner = new Scanner(System.in);
                    String input = scanner.nextLine();
                    if (input.equals("stop"))
                        System.exit(0);
                }
            });

            System.out.println("Callbacks starting in 5 seconds");
            System.out.println("To stop the server enter 'stop'");
            inputThread.start();
            Thread.sleep(5 * 1000);
            while (true) {
                exportedObj.doCallbacks();
                Thread.sleep(5 * 1000);
            }


        }// end try
        catch (Exception re) {
            System.out.println(
                    "Exception in HelloServer.main: " + re);
        } // end catch
    }

    private static void startRegistry(int RMIPortNum) throws RemoteException {
        try {
            Registry registry =
                    LocateRegistry.getRegistry(RMIPortNum);
            registry.list();
            // This call will throw an exception
            // if the registry does not already exist
        } catch (RemoteException e) {
            // No valid registry at that port.
            Registry registry =
                    LocateRegistry.createRegistry(RMIPortNum);
        }

    }


}
