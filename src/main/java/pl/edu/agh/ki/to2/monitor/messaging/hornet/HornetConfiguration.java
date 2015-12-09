package pl.edu.agh.ki.to2.monitor.messaging.hornet;

import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.core.config.Configuration;
import org.hornetq.core.config.impl.ConfigurationImpl;
import org.hornetq.core.remoting.impl.invm.InVMAcceptorFactory;

class HornetConfiguration {

    private static final boolean PERSISTENCE_ENABLED = false;
    private static final boolean SECURITY_ENABLED = false;

    HornetConfiguration() {}

    Configuration getConfiguration() {

        Configuration configuration = new ConfigurationImpl();
        configuration.setJournalDirectory("target/data/journal");
        configuration.setPersistenceEnabled(PERSISTENCE_ENABLED);
        configuration.setSecurityEnabled(SECURITY_ENABLED);
        configuration.getAcceptorConfigurations().add(new TransportConfiguration(InVMAcceptorFactory.class.getName()));

        return configuration;
    }

}
