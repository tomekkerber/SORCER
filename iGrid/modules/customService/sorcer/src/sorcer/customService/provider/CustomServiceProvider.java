package sorcer.customService.provider;

import java.rmi.RemoteException;

import com.sun.jini.start.LifeCycle;

import sorcer.service.Context;
import sorcer.core.provider.ServiceTasker; 

@SuppressWarnings("rawtypes")
public class CustomServiceProvider extends ServiceTasker implements ServiceCustomService{
	public CustomServiceProvider(String[] args, LifeCycle lifeCycle) throws Exception {
		super(args, lifeCycle);
	}

	public void launchSomething() throws RemoteException {
		System.err.println("#launchSomething called");
	}
	
	@Override
	public Context launchSomething(Context context) throws RemoteException {
		this.launchSomething();
		
		return context;
	}
}
