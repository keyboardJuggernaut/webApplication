package it.polimi.parkingService.webApplication.messaging.models;

import java.util.ArrayList;
import java.util.List;

public class Forum {
    List<Message> messages;

    public void addMessage(Message message){
        if(messages == null){
            messages = new ArrayList<>();
        }
        messages.add(message);
        message.setForum(this);
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
