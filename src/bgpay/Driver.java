package bgpay;

import java.awt.EventQueue;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.swing.UIManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

import bgpay.database.Database;
import bgpay.ui.ApplicationUi;

public class Driver {

	private static final String DB_PROPERTIES_FILENAME = "db.properties";
	private static final String LOG4J_CONFIG_FILENAME = "log4j2.xml";
	
	static {
		configureLogging();
	}

	private static final Logger LOG = LogManager.getLogger();
	
	
	private Database db;
	
	@SuppressWarnings("static-access")
	public Driver() {
		Properties dbProperties = new Properties();
		try {
			dbProperties.load(new FileInputStream(DB_PROPERTIES_FILENAME));
			LOG.debug("Loading properties file");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db = Database.getDatabase();
		db.init(dbProperties);
		LOG.debug("Initializing database");
		ApplicationUi window = new ApplicationUi(db);
		window.frame.setVisible(true);
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Driver();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}
	
	private static void configureLogging() {
		ConfigurationSource source;
		try {
			source = new ConfigurationSource(new FileInputStream(LOG4J_CONFIG_FILENAME));
			Configurator.initialize(null, source);

		} catch (IOException e) {
			System.out.println(String.format("Can't find the log4j logging configuration file %s.", LOG4J_CONFIG_FILENAME));
		}
	}

}
