import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CallbackClientImpl extends UnicastRemoteObject implements CallbackClientInterface {

    public CallbackClientImpl() throws RemoteException {
        super();
    }

    @Override
    public String notifyMe(String message) throws RemoteException {
        String returnMessage = "Callback received: " + message;
        System.out.println(returnMessage);
        return returnMessage;
    }
}
