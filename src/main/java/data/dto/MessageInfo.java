package data.dto;

public class MessageInfo {

    private Long chatId;
    private User author;

    public MessageInfo(Long chatId, User author) {
        this.chatId = chatId;
        this.author = author;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
