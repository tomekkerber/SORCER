package sorcer.customService.provider;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CustomService extends Remote {
	public void launchSomething() throws RemoteException;
}