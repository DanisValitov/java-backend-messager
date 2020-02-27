package com.valitov.jwtdemo.entities;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "messages")
public class MyMessage implements Comparable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String content;

    private String sender;

    private String receiver;

    private Timestamp date;

    public MyMessage() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }



    @Override
    public String toString() {
        return "MyMessage{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", date=" + date +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        return getDate().compareTo(((MyMessage)o).getDate());
    }
}
