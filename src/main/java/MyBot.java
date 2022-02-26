import handler.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import util.PropertyUtils;

import java.util.List;
import java.util.Properties;

public class MyBot extends TelegramLongPollingBot {

    private static final Logger log = LoggerFactory.getLogger(MyBot.class);

    private final String botUsername;
    private final String botToken;
    private final List<Command> commands;

    public MyBot() {
        super();
        Properties properties = PropertyUtils.getProperties();
        this.botUsername = properties.getProperty("bot.name");
        this.botToken = System.getenv("BOT_TOKEN");
        this.commands = List.of(
                new GulambaCommand(),
                new IgrambaCommand());
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage message = null;
        try {
            if (update.hasMessage() && update.getMessage().hasText()) {
                for (Command command : commands) {
                    if (command.matches(update.getMessage().getText())){
                        message = command.handle(update);
                        break;
                    }
                }
            }
            if (update.hasCallbackQuery()) {
                for (Command command : commands) {
                    if (command instanceof Callback) {
                        Callback callbackCommand = (Callback) command;
                        if (callbackCommand.hasCallback(update.getCallbackQuery().getData())) {
                            message = callbackCommand.handleCallback(update);
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("Unexpected exception appear: ", e);
        }

        if (message != null) {
            executeMessage(message);
        }
    }

    private void executeMessage(SendMessage message) {
        try {
            log.trace("Execute msg: {}", message.toString());
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Exception appear during execution: ", e);
        }
    }
}
