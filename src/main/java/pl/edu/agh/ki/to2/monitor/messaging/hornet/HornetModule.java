package pl.edu.agh.ki.to2.monitor.messaging.hornet;

import dagger.Module;
import dagger.Provides;
import org.hornetq.api.core.HornetQException;
import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.api.core.client.*;
import org.hornetq.core.remoting.impl.invm.InVMConnectorFactory;
import org.hornetq.core.server.HornetQServer;
import org.hornetq.core.server.HornetQServers;

import javax.inject.Singleton;

@Module(complete = false, library = true)
public class HornetModule {

    private static final String QUEUE_NAME = "queue.monitorQueue";

    @Provides
    @Singleton
    HornetConfiguration hornetConfiguration() {
        return new HornetConfiguration();
    }

    @Provides
    @Singleton
    HornetQServer createHornetServer(HornetConfiguration configuration) {
        return HornetQServers.newHornetQServer(configuration.getConfiguration());
    }

    @Provides
    @Singleton
    ClientSessionFactory createClientSessionFactory() throws Exception {
        ServerLocator serverLocator = HornetQClient.createServerLocatorWithoutHA(new TransportConfiguration(InVMConnectorFactory.class.getName()));
        return serverLocator.createSessionFactory();
    }

    @Provides
    @Singleton
    ClientSession createClientSession(ClientSessionFactory sessionFactory) throws HornetQException {

        ClientSession queueSession = sessionFactory.createSession(false, false, false);
        queueSession.createQueue(QUEUE_NAME, QUEUE_NAME, true);
        queueSession.close();

        return sessionFactory.createSession();
    }

    @Provides
    @Singleton
    ClientProducer createProducer(ClientSession session) throws HornetQException {
        return session.createProducer(QUEUE_NAME);
    }

    @Provides
    @Singleton
    ClientConsumer createConsumer(ClientSession session) throws HornetQException {
        return session.createConsumer(QUEUE_NAME);
    }



}
