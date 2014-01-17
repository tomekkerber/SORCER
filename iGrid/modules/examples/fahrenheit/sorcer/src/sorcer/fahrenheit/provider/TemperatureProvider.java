/**
 * 
 */
package sorcer.fahrenheit.provider;

/**
 * @author Tom
 *
 */
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.logging.Logger;

import sorcer.account.provider.AccountException;
import sorcer.account.provider.Money;
import sorcer.account.provider.ServiceAccount;
import sorcer.core.SorcerConstants;
import sorcer.core.provider.ServiceTasker;
import sorcer.service.Context;
import sorcer.util.Log;

import com.sun.jini.start.LifeCycle;

@SuppressWarnings("rawtypes")
public class TemperatureProvider extends ServiceTasker implements Temperature, 
	ServiceTemperature, SorcerConstants{
	
	private Degrees termometer;
	
	public TemperatureProvider(String[] args, LifeCycle lifeCycle) throws Exception {
		super(args, lifeCycle);
		
		String fahrenheit=getProperty("provider.fahrenheit");
		String celsius=getProperty("provider.celsius");
		if(fahrenheit!=null){
			termometer=new Degrees(Double.parseDouble(fahrenheit), null);
		}else if(celsius!=null){
			termometer=new Degrees(null, Double.parseDouble(celsius));
		}
	}
	
	@Override
	public Context initializeTermometer(Context context) throws RemoteException {
		return process(context, ServiceTemperature.TERMOMETER);
	}
	public Context getFahrenheit(Context context) throws RemoteException {
		return process(context, ServiceTemperature.FAHRENHEIT);
	}
	public Context getCelsius(Context context) throws RemoteException {
		return process(context, ServiceTemperature.CELSIUS);
	}	
	public Context convertToCelsius(Context context) throws RemoteException {
		return process(context, ServiceTemperature.CONVERTTOCELSIUS);
	}
	public Context convertToFahrenheit(Context context) throws RemoteException {
		return process(context, ServiceTemperature.CONVERTTOFAHRENHEIT);
	}
	
	private Context process(Context context, String selector)
			throws RemoteException {
		try {

			Degrees result = null, temperature=null;
			if (selector.equals(ServiceTemperature.TERMOMETER)) {
				result = initializeTermometer();
			} else if (selector.equals(ServiceTemperature.CONVERTTOCELSIUS)) {
				temperature = (Degrees) context.getValue(ServiceTemperature.CONVERTTOCELSIUS);
				convertToCelsius(temperature);
				result = initializeTermometer();
			} else if (selector.equals(ServiceTemperature.CONVERTTOFAHRENHEIT)) {
				temperature = (Degrees) context.getValue(ServiceTemperature.CONVERTTOFAHRENHEIT);
				convertToFahrenheit(temperature);
				result = initializeTermometer();
			}
			context.putValue(
					ServiceTemperature.TERMOMETER, result);
			if (context.getReturnPath() != null) {
				context.setReturnValue(result);
			}

		} catch (Exception ex) {
			// ContextException, UnknownHostException
			throw new RemoteException(selector + " process execption", ex);
		}
		return context;
	}
	
	public Degrees initializeTermometer(){
		return termometer;
	}
	public Degrees getFahrenheit(){
		return termometer;
	}
	private String getHostname() throws UnknownHostException {
		return InetAddress.getLocalHost().getHostName();
	}

	/* (non-Javadoc)
	 * @see sorcer.src.sorcer.fahrenheit.provider.Temperature#convertToCelsius(sorcer.src.sorcer.fahrenheit.provider.Degrees)
	 */
	@Override
	public void convertToCelsius(Degrees termometer) throws RemoteException {
		this.termometer.setFahrenheit(termometer.getFahrenheit());
		this.termometer.toCelsius();
		return;
		
	}
	
	public void convertToFahrenheit(Degrees termometer) throws RemoteException {
		this.termometer.setCelsius(termometer.getCelsius());
		this.termometer.toFahrenheit();
		return;
		
	}
}

