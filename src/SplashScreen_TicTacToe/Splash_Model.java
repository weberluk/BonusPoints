package SplashScreen_TicTacToe;

import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import TicTacToe.Configuration;
import TicTacToe.ServiceLocator;
import TicTacToe.Translator;
import javafx.concurrent.Task;

public class Splash_Model {
	
    ServiceLocator serviceLocator;
    
    public Splash_Model() {
    	
    }
    
    final Task<Void> initializer = new Task<Void>() {
        @Override
        protected Void call() throws Exception {
            this.updateProgress(1,  6);

            // Create the service locator to hold our resources
            serviceLocator = ServiceLocator.getServiceLocator();
            this.updateProgress(2,  6);

            // Initialize the resources in the service locator
            serviceLocator.setLogger(configureLogging());
            serviceLocator.getLogger().info("Loading Logger");
            this.updateProgress(3,  6);

            serviceLocator.setConfiguration(new Configuration());
            serviceLocator.getLogger().info("Loading Configuration");
            this.updateProgress(4,  6);

//            String language = serviceLocator.getConfiguration().getOption("Language");
//            serviceLocator.getLogger().info("Loading Language");
//            serviceLocator.setTranslator(new Translator(language));
//            this.updateProgress(5,  6);
            
            // ... more resources would go here...
            // First, take some time, update progress
            Integer i = 0;
            for (; i < 1000000000; i++) {
                if ((i % 1000000) == 0)
                    this.updateProgress(i, 1000000000);
            }
            this.updateProgress(6,  6);

            return null;
        }
    };
    
    private Logger configureLogging() {
        Logger rootLogger = Logger.getLogger("");
        rootLogger.setLevel(Level.FINEST);

        // By default there is one handler: the console
        Handler[] defaultHandlers = Logger.getLogger("").getHandlers();
        defaultHandlers[0].setLevel(Level.INFO);

        // Add our logger
        Logger ourLogger = Logger.getLogger(serviceLocator.getAPP_NAME());
        ourLogger.setLevel(Level.FINEST);
        
        // Add a file handler, putting the rotating files in the tmp directory
        try {
            Handler logHandler = new FileHandler("%t/"
                    + serviceLocator.getAPP_NAME() + "_%u" + "_%g" + ".log",
                    1000000, 9);
            logHandler.setLevel(Level.FINEST);
            ourLogger.addHandler(logHandler);
        } catch (Exception e) { // If we are unable to create log files
            throw new RuntimeException("Unable to initialize log files: "
                    + e.toString());
        }

        return ourLogger;
    }
    
    public void initialize() {
        new Thread(initializer).start();
    }

}
