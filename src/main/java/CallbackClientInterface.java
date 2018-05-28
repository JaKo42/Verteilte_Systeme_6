import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CallbackClientInterface extends Remote {


    public String notifyMe(String message) throws RemoteException;
}
