import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CallbackServerInterface extends Remote {


    public String sayHello() throws RemoteException;

    public void registerForCallback(CallbackClientInterface callbackClientObject) throws RemoteException;

    public void unregisterForCallback(CallbackClientInterface callbackClientObject) throws RemoteException;

}
