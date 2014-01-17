/**
 * 
 */
package sorcer.fahrenheit.provider;



import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.jini.core.lookup.ServiceItem;
import net.jini.lookup.entry.UIDescriptor;
import net.jini.lookup.ui.MainUI;
import sorcer.core.provider.ServiceProvider;
import sorcer.fahrenheit.provider.Temperature;
import sorcer.ui.serviceui.UIComponentFactory;
import sorcer.ui.serviceui.UIDescriptorFactory;
import sorcer.util.Sorcer;


public class FahrenheitUI extends JPanel {

	private static final long serialVersionUID = -3171243785170712405L;

	private JTextField fahrenheitTextField;
	private JTextField celsiusTextField;
	private ServiceItem item;
	private Temperature temperature;
	
	private final static Logger logger = Logger.getLogger(FahrenheitUI.class
			.getName());

	public FahrenheitUI(Object provider) {
		super();
		getAccessibleContext().setAccessibleName("Fahrenheit");
		item = (ServiceItem) provider;

		if (item.service instanceof Temperature) {
			temperature = (Temperature) item.service;
			createUI();
			setTemperatureFields();
		}
	}
	
	protected void createUI() {
		setLayout(new BorderLayout());
		add(buildFahrenheitPanel(), BorderLayout.CENTER);
	}
	
	private void setTemperatureFields(){
		try{
			Degrees termometer=temperature.initializeTermometer();
			BigDecimal bd=new BigDecimal("" + termometer.getFahrenheit());
			bd=bd.setScale(2, BigDecimal.ROUND_HALF_EVEN);
			fahrenheitTextField.setText(bd.toString());
			BigDecimal bd2=new BigDecimal("" + termometer.getCelsius());
			//logger.info(" termometer.getCelsius(): " +  termometer.getCelsius());
			bd2=bd2.setScale(2, BigDecimal.ROUND_HALF_EVEN);
			celsiusTextField.setText(bd2.toString());	
		}catch(Exception e){
			
		}
	}
	
	private Degrees getDegreesFromFahrenheitTextField(JTextField textField){
		double fahrenheitTemperature=Double.parseDouble(textField.getText());
		Degrees fahrenheitTermometer=new Degrees();
		fahrenheitTermometer.setFahrenheit(fahrenheitTemperature);
		return fahrenheitTermometer;
	}
	
	private Degrees getDegreesFromCelsiusTextField(JTextField textField){
		double celsiusTemperature=Double.parseDouble(textField.getText());
		Degrees celsiusTermometer=new Degrees();
		celsiusTermometer.setCelsius(celsiusTemperature);
		return celsiusTermometer;
	}
	
	private class ConvertToCelsiusAction implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			try {
				Degrees fahrenheitBase = getDegreesFromFahrenheitTextField(fahrenheitTextField);
				logger.info("fahrenheitBase" + fahrenheitBase.getFahrenheit());
				temperature.convertToCelsius(fahrenheitBase);
				celsiusTextField.setText(new BigDecimal("" + temperature.initializeTermometer().getCelsius()).setScale(2, BigDecimal.ROUND_HALF_EVEN).toString());
				
			} catch (Exception exception) {
				logger.info("Couldn't talk to account. Error was" + exception);
				logger.throwing(getClass().getName(), "actionPerformed",
						exception);
			}
		}
	}
	
	private class ConvertToFahrenheitAction implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			try {
				Degrees celsiusBase = getDegreesFromCelsiusTextField(celsiusTextField);
				logger.info("celsiusBase: " + celsiusBase.getCelsius());
				temperature.convertToFahrenheit(celsiusBase);
				fahrenheitTextField.setText(new BigDecimal("" + temperature.initializeTermometer().getFahrenheit()).setScale(2, BigDecimal.ROUND_HALF_EVEN).toString());
				
			} catch (Exception exception) {
				logger.info("Couldn't talk to account. Error was" + exception);
				logger.throwing(getClass().getName(), "actionPerformed",
						exception);
			}
		}
	}

	private JPanel buildFahrenheitPanel() {
		
		JPanel panel = new JPanel();
		JPanel actionPanel = new JPanel(new GridLayout(2, 3, 5, 5));

		actionPanel.add(new JLabel("Fahrenheit:"));
		fahrenheitTextField = new JTextField();
		fahrenheitTextField.setEnabled(true);
		actionPanel.add(fahrenheitTextField);
		
		JButton fahrenheitButton = new JButton("Fahrenheit it!");
		fahrenheitButton.addActionListener(new ConvertToFahrenheitAction());
		actionPanel.add(fahrenheitButton);
		panel.add(actionPanel);
		
		actionPanel.add(new JLabel("Celsius:"));
		celsiusTextField = new JTextField();
		celsiusTextField.setEnabled(true);
		actionPanel.add(celsiusTextField);
		JButton celsiusButton = new JButton("Celsius it!");
		celsiusButton.addActionListener(new ConvertToCelsiusAction());
		actionPanel.add(celsiusButton);
		panel.add(actionPanel);
		
		/*celsiusResultTextField = new JTextField();
		celsiusResultTextField.setEnabled(false);
		JButton celsiusButton = new JButton("Celsius it!");
		celsiusButton.addActionListener(new ConvertToCelsiusAction());
		actionPanel.add(celsiusButton);
		actionPanel.add(celsiusResultTextField);
		panel.add(actionPanel);
		
		fahrenheitResultTextField = new JTextField();
		fahrenheitResultTextField.setEnabled(false);
		JButton fahrenheitButton = new JButton("Fahrenheit it!");
		// fahrenheitButton.addActionListener(new WithdrawAction());
		actionPanel.add(fahrenheitButton);
		actionPanel.add(fahrenheitResultTextField);
		panel.add(actionPanel);*/
		
		return panel;
		
	}
/*private JPanel buildCelsiusPanel() {
		
		JPanel panel = new JPanel();
		JPanel actionPanel = new JPanel(new GridLayout(3, 3));

		actionPanel.add(new JLabel("Celsius:"));
		celsiusTextField = new JTextField();
		celsiusTextField.setEnabled(true);
		actionPanel.add(fahrenheitTextField);
		
		fahrenheitResultTextField = new JTextField();
		fahrenheitResultTextField.setEnabled(false);
		JButton fahrenheitButton = new JButton("Fahrenheit it!");
		// fahrenheitButton.addActionListener(new WithdrawAction());
		actionPanel.add(fahrenheitButton);
		actionPanel.add(fahrenheitResultTextField);
		panel.add(actionPanel);
		return panel;
		
	}*/

	public static UIDescriptor getUIDescriptor() {
		UIDescriptor uiDesc = null;
		try {
			uiDesc = UIDescriptorFactory.getUIDescriptor(MainUI.ROLE,
					new UIComponentFactory(new URL[] { new URL(Sorcer
							.getWebsterUrl()
							+ "/fahrenheit.jar") }, FahrenheitUI.class.getName()));
		} catch (Exception ex) {
			logger.throwing(FahrenheitUI.class.getName(), "getUIDescriptor", ex);
		}
		return uiDesc;
	}

}

