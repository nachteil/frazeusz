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

@Module(library = true)
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
    ClientSessionFactory createClientSessionFactory(HornetQServer server) {
        ServerLocator serverLocator = HornetQClient.createServerLocatorWithoutHA(new TransportConfiguration(InVMConnectorFactory.class.getName()));
        ClientSessionFactory sessionFactory = null;
        try {
             server.start();
             sessionFactory = serverLocator.createSessionFactory();
        } catch (Exception e) {
            System.out.println("Factory failed");
            e.printStackTrace();
        }
        return sessionFactory;
    }

    @Provides
    @Singleton
    ClientSession createClientSession(ClientSessionFactory sessionFactory) {

        ClientSession session = null;
        try {
            ClientSession queueSession = sessionFactory.createSession(false, false, false);
            queueSession.createQueue(QUEUE_NAME, QUEUE_NAME, true);
            queueSession.close();
            session = sessionFactory.createSession();
            session.start();
        } catch (HornetQException exception) {
            System.out.println("Client session failed");
            exception.printStackTrace();
        }
        return session;
    }

    @Provides
    @Singleton
    ClientProducer createProducer(ClientSession session) {
        ClientProducer producer = null;
        try {
            producer = session.createProducer(QUEUE_NAME);
        } catch (HornetQException exception) {
            System.out.println("producer failed");
            exception.printStackTrace();
        }
        return producer;
    }

    @Provides
    @Singleton
    ClientConsumer createConsumer(ClientSession session) {
        ClientConsumer consumer = null;
        try {
            consumer = session.createConsumer(QUEUE_NAME);
        } catch (HornetQException exception) {
            System.out.println("consumer failed");
            exception.printStackTrace();
        }
        return consumer;
    }



}
