package handler;

import data.dto.User;
import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class AbstractCommand implements Command {

    private final String pattern = StringUtils.EMPTY;

    public String getPattern() {
        return pattern;
    }

    public Long getChatId(Update update) {
        return getMessage(update).getChatId();
    }

    public String getText(Update update) {
        return update.getMessage().getText();
    }

    public User getMessageAuthor(Update update) {
        User user = new User();
        user.setId(getFrom(update).getId());
        user.setFirstName(getFrom(update).getFirstName());
        user.setLastName(getFrom(update).getLastName());
        user.setUsername(getFrom(update).getUserName());

        return user;
    }

    private Message getMessage(Update update) {
        return update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
    }

    private org.telegram.telegrambots.meta.api.objects.User getFrom(Update update) {
        return update.hasMessage() ? update.getMessage().getFrom() : update.getCallbackQuery().getFrom();
    }

    @Override
    public boolean matches(String message) {
        return message.startsWith(getPattern());
    }
}
