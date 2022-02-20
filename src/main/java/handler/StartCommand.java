package handler;

import data.enums.Language;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import util.LocaleUtils;
import util.PropertyUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class StartCommand implements Command {

    private final String PATTERN = "/start";

    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.getMessage().getChatId();
        String firstName = update.getMessage().getFrom().getFirstName();
        String text = update.getMessage().getText();

        SendMessage message = new SendMessage();
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        InlineKeyboardButton inlineButton = new InlineKeyboardButton("Idu gulat");
        inlineButton.setCallbackData("update_msg_text");
        rowInline.add(inlineButton);
        rowInline.add(inlineButton);

        rowsInline.add(rowInline);

        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);
        message.setChatId(chatId.toString());
        message.setText(String.format(LocaleUtils.getMessageByKey("gulambaStart", Language.RU), firstName));
        return message;
    }

    @Override
    public boolean matches(String message) {
        return message.startsWith(PATTERN);
    }
}
