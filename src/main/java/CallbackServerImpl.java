import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

public class CallbackServerImpl extends UnicastRemoteObject implements CallbackServerInterface {


    private Vector clientList;

    public CallbackServerImpl() throws RemoteException {
        super( );
        clientList = new Vector();
    }


    @Override
    public String sayHello() throws RemoteException {
        String wellcome = "\n***Wellcome to random number server!***\n***You get a random number in no time!***";
        return wellcome;
    }

    private int deliverNumber() throws RemoteException {
        int min = 0;
        int max = 9999;
        int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);

        return randomNum;
    }

    @Override
    public synchronized void registerForCallback(CallbackClientInterface callbackClientObject) throws RemoteException {
        if (!(clientList.contains(callbackClientObject))) {
            clientList.add(callbackClientObject);
            System.out.println("New Client registred!");
        }
    }

    @Override
    public synchronized void unregisterForCallback(CallbackClientInterface callbackClientObject) throws RemoteException {
        if (clientList.remove(callbackClientObject)) {
            System.out.println("Client removed!");
        } else {
            System.out.println("unregister: client wasn't registered!");
        }
    }


    public synchronized void doCallbacks() throws RemoteException {
        System.out.println(
                "**************************************\n"
                        + "Callbacks initiated ---");
        for (int i = 0; i < clientList.size(); i++){
            System.out.println("doing "+ (i+1) +" callback(s)\n");
            // convert the vector object to a callback object
            CallbackClientInterface nextClient =
                    (CallbackClientInterface)clientList.elementAt(i);
            // invoke the callback method
            nextClient.notifyMe("Your random Number:" + deliverNumber());


            // nextClient.notifyMe("Number of registered clients="
           //         +  clientList.size());



        }// end for
        System.out.println("********************************\n" +
                "Server completed callbacks ---");


    }
}
