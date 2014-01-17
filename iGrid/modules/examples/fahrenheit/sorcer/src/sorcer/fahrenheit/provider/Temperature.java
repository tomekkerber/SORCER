/**
 * 
 */
package sorcer.fahrenheit.provider;

import java.rmi.Remote;
import java.rmi.RemoteException;

import sorcer.fahrenheit.provider.Degrees;

public interface Temperature extends Remote{
	
	public Degrees initializeTermometer() throws RemoteException;
	
	public void convertToCelsius(Degrees termometer) throws RemoteException;
	
	public void convertToFahrenheit(Degrees termometer) throws RemoteException;
	
}
