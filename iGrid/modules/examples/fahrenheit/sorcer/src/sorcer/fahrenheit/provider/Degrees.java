package sorcer.fahrenheit.provider;

/**
 * 
 */

/**
 * @author Tom
 *
 */
import java.io.Serializable;

public class Degrees implements Serializable {

	private double fahrenheit;
	private double celsius;

	/*public Degrees() {
		this.fahrenheit = 0;
		this.celsius = 0;
	}*/
	public Degrees(Double fahrenheit, Double celsius){
		if(fahrenheit!=null){
			this.fahrenheit=fahrenheit;
			this.toCelsius();
		}else if(celsius!=null){
			this.celsius=celsius;
			this.toFahrenheit();
		}
	}
	
	public Degrees(){
		this.fahrenheit=0;
		this.celsius=0;
	}

	public void toFahrenheit() {
		this.fahrenheit = this.celsius * 1.8 + 32;
	}

	public void toCelsius() {
		this.celsius = (this.fahrenheit - 32.0) / 1.8;
	}

	public double getFahrenheit() {
		return fahrenheit;
	}

	public void setFahrenheit(double fahrenheit) {
		this.fahrenheit = fahrenheit;
	}

	public double getCelsius() {
		return celsius;
	}

	public void setCelsius(double celsius) {
		this.celsius = celsius;
	}
}
