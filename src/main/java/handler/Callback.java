package handler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Callback {

    boolean hasCallback(String callback);

    SendMessage handleCallback(Update update);
}
