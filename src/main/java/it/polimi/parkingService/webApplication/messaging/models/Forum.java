package it.polimi.parkingService.webApplication.messaging.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="forum")
public class Forum {

    @Column(name="name")
    @Id
    private String name;

    @OneToMany(
            mappedBy = "forum",
            fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    private List<Message> messages;

    public Forum(String name) {
        this.name = name;
    }

    public Forum(){}

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
