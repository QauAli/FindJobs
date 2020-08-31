package Models;

public class Conversation {
    int id, applicant_id, advertiser_id;
    String name;

    public Conversation(int id, int creator_id, int pursuer_id, String name) {
        this.id = id;
        this.applicant_id = creator_id;
        this.advertiser_id = pursuer_id;
        this.name = name;
    }
    public Conversation() {

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getApplicant_id() {
        return applicant_id;
    }

    public int getAdvertiser_id() {
        return advertiser_id;
    }

}
