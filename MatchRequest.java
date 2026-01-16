package com.mycompany.oop;
public class MatchRequest {
    private String sender;
    private String receiver;
    private String status; // "Pending", "Accepted", "Rejected"

    public MatchRequest(String sender, String receiver, String status) {
        this.sender = sender;
        this.receiver = receiver;
        this.status = status;
    }

    public String getSender() { return sender; }
    public String getReceiver() { return receiver; }
    public String getStatus() { return status; }

    @Override
    public String toString() {
        return sender + "," + receiver + "," + status;
    }
}