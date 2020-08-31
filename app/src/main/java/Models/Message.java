package Models;

public class Message {
    int id, sender_id, conversation_id;
    String date_time, message;

    public Message(int id, int sender_id, int conversation_id, String date_time, String message) {
        this.id = id;
        this.sender_id = sender_id;
        this.conversation_id = conversation_id;
        this.date_time = date_time;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public int getSender_id() {
        return sender_id;
    }

    public int getConversation_id() {
        return conversation_id;
    }

    public String getDate_time() {
        return date_time;
    }

    public String getMessage() {
        return message;
    }
}
