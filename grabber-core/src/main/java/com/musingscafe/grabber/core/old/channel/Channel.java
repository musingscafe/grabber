package com.musingscafe.grabber.core.old.channel;

import com.musingscafe.grabber.core.old.connectors.Connector;
import com.musingscafe.grabber.core.old.consumers.Consumer;
import com.musingscafe.grabber.core.old.message.GrabberMessage;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * Created by ayadav on 11/15/16.
 */
public class Channel {

    private String channelIdentifier;
    private List<Consumer> consumers;
    private Connector connector;
    private ChannelExecutionContext channelExecutionContext;
    private ChannelExecutor channelExecutor;
    private boolean executeSelf;
    private boolean isComplete;


    public List<Consumer> getConsumers() {
        return consumers;
    }

    public void setConsumers(List<Consumer> consumers) {
        this.consumers = consumers;
    }

    public String getChannelIdentifier() {
        return channelIdentifier;
    }

    public void setChannelIdentifier(String channelIdentifier) {
        this.channelIdentifier = channelIdentifier;
    }


    public ChannelExecutionContext getChannelExecutionContext() {
        return channelExecutionContext;
    }

    public void setChannelExecutionContext(ChannelExecutionContext channelExecutionContext) {
        this.channelExecutionContext = channelExecutionContext;
    }

    public void write(GrabberMessage message){

        if(!isComplete){
            validate();
        }

        channelExecutor.write(message);
    }

    public Connector getConnector() {
        return connector;
    }

    public void setConnector(Connector connector) {
        this.connector = connector;
    }

    public ChannelExecutor getChannelExecutor() {
        return channelExecutor;
    }

    public void setChannelExecutor(ChannelExecutor channelExecutor) {
        this.channelExecutor = channelExecutor;
    }

    private void validate(){
        assert (consumers != null);
        assert (consumers.size() > 0);
        assert (connector != null);
        assert (channelExecutionContext != null);
        assert (channelExecutor != null);
        assert (!StringUtils.isEmpty(channelIdentifier));

        isComplete = true;
    }

    public boolean isExecuteSelf() {
        return executeSelf;
    }

    public void setExecuteSelf(boolean executeSelf) {
        this.executeSelf = executeSelf;
    }
}
