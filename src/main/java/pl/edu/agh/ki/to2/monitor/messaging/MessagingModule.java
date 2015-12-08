package pl.edu.agh.ki.to2.monitor.messaging;

import dagger.Module;
import pl.edu.agh.ki.to2.monitor.Monitor;
import pl.edu.agh.ki.to2.monitor.messaging.hornet.HornetModule;

@Module(
        includes = HornetModule.class,
        injects = Monitor.class)
public class MessagingModule {
}
