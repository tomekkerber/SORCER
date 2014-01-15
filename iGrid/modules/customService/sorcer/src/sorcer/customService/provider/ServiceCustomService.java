package sorcer.customService.provider;

import java.rmi.RemoteException;

import sorcer.service.Context;

@SuppressWarnings("rawtypes")
public interface ServiceCustomService {
	public Context launchSomething(Context account) throws RemoteException;
}
