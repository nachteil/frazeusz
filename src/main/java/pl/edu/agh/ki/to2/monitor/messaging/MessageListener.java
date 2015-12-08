package pl.edu.agh.ki.to2.monitor.messaging;

import javax.inject.Inject;

public class MessageListener {

    private final MessageQueue messageQueue;

    @Inject
    public MessageListener(MessageQueue messageQueue) {
        this.messageQueue = messageQueue;
    }

    private void processMessages() {
        while(true) {

        }
    }

    public void startService() {
        new Thread(this::processMessages).start();
    }

}
