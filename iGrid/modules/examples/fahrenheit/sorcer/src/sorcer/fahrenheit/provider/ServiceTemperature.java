package sorcer.fahrenheit.provider;

import java.rmi.Remote;
import java.rmi.RemoteException;

import sorcer.service.Context;

@SuppressWarnings("rawtypes")
public interface ServiceTemperature extends Remote{
	
	public Context initializeTermometer(Context temperature) throws RemoteException;
	
	public Context convertToCelsius (Context temperature) throws RemoteException;
	
	public Context convertToFahrenheit (Context temperature) throws RemoteException;
	
	public final static String TERMOMETER = "termometer";
	public final static String TEMPERATURE = "temperature";
	public final static String FAHRENHEIT = "fahrenheit";
	public final static String CELSIUS = "celsius";
	public final static String CONVERTTOCELSIUS = "converttocelsius";
	public final static String CONVERTTOFAHRENHEIT = "converttofahrenheit";
}
