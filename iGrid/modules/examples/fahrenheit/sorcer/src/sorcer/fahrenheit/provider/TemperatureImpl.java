package sorcer.fahrenheit.provider;

import java.rmi.RemoteException;

import sorcer.account.provider.Money;

public class TemperatureImpl implements Temperature {
	private Degrees termometer;
	
	public TemperatureImpl(Degrees startingTemperature) throws RemoteException {
		termometer = startingTemperature;
	}
	public Degrees initializeTermometer(){
		return termometer;
	}
	
	public void convertToCelsius(Degrees Termometer){
		this.termometer.setFahrenheit(Termometer.getFahrenheit());
		this.termometer.toCelsius();
		return;	
	}
	
	public void convertToFahrenheit(Degrees Termometer){
		this.termometer.setCelsius(Termometer.getCelsius());
		this.termometer.toFahrenheit();
		return;	
	}

}
