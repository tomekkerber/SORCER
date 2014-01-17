/**
 * 
 */
package sorcer.fahrenheit.provider;



import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.logging.Logger;

import net.jini.admin.Administrable;
import net.jini.core.transaction.Transaction;
import net.jini.core.transaction.TransactionException;
import sorcer.core.SorcerConstants;
import sorcer.core.provider.Provider;
import sorcer.core.proxy.Partnership;
import sorcer.core.proxy.RemotePartner;
import sorcer.service.Context;
import sorcer.service.Exertion;
import sorcer.service.ExertionException;
import sorcer.fahrenheit.provider.Degrees;
import sorcer.fahrenheit.provider.ServiceTemperature;
import sorcer.util.Log;

public class ServiceTemperatureImpl implements Temperature, ServiceTemperature, SorcerConstants{
	
	private Degrees termometer;
	
	public Context initializeTermometer(Context context) throws RemoteException {
		return process(context, ServiceTemperature.TERMOMETER);
	}
	
	@Override
	public Context convertToCelsius(Context context) throws RemoteException {
		// TODO Auto-generated method stub
		return process(context, ServiceTemperature.CONVERTTOCELSIUS);
	}

	/* (non-Javadoc)
	 * @see sorcer.fahrenheit.provider.ServiceTemperature#convertToFahrenheit(sorcer.service.Context)
	 */
	@Override
	public Context convertToFahrenheit(Context context)
			throws RemoteException {
		// TODO Auto-generated method stub
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

			String outputMessage = "processed by " + getHostname();
			context.putValue(
					ServiceTemperature.TERMOMETER, result);
		//	context.putValue(ServiceAccount.COMMENT, outputMessage);
			if (context.getReturnPath() != null) {
				context.setReturnValue(result);
			}
		} catch (Exception ex) {
			// ContextException, UnknownHostException
			throw new RemoteException(selector + " process execption", ex);
		}
		return context;
	}
	
	public ServiceTemperatureImpl(Degrees startingTermometer) throws RemoteException {
		termometer = startingTermometer;
	}

	public Degrees initializeTermometer() throws RemoteException {
		return termometer;
	}
	
	private Provider partner;

	private Administrable admin;

	/*
	 * (non-Javadoc)
	 * 
	 * @see sorcer.core.provider.proxy.Partnership#getPartner()
	 */
	public Remote getInner() throws RemoteException {
		return (Remote) partner;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sorcer.base.Service#service(sorcer.base.Exertion)
	 */
	public Exertion service(Exertion exertion, Transaction transaction)
			throws RemoteException, ExertionException, TransactionException {
		return partner.service(exertion, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.jini.admin.Administrable#getAdmin()
	 */
	public Object getAdmin() throws RemoteException {
		return admin;
	}

	public void setInner(Object provider) {
		partner = (Provider) provider;
	}

	public void setAdmin(Object admin) {
		this.admin = (Administrable) admin;
	}

	/**
	 * Returns name of the local host.
	 * 
	 * @return local host name
	 * @throws UnknownHostException
	 */
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

	/* (non-Javadoc)
	 * @see sorcer.fahrenheit.provider.ServiceTemperature#convertToCelsius(sorcer.service.Context)
	 */
	
	
	
	

}
