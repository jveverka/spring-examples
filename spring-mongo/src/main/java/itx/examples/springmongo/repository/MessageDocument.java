package itx.examples.springmongo.repository;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class MessageDocument {

    @Id
    private String id;

    private String message;

    public String getId() {
        return id;
    }

    public MessageDocument() {
    }

    public MessageDocument(String id, String message) {
        this.id = id;
        this.message = message;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
