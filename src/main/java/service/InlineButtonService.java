package service;

import data.constants.ApplicationConstants;
import data.enums.Language;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import util.LocaleUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class InlineButtonService {

    private static class InlineButtonServiceLoader {
        private static final InlineButtonService INSTANCE = new InlineButtonService();
    }

    private InlineButtonService() {
        if (InlineButtonService.InlineButtonServiceLoader.INSTANCE != null) {
            throw new IllegalStateException("Already instantiated");
        }
    }

    public static InlineButtonService getInstance() {
        return InlineButtonService.InlineButtonServiceLoader.INSTANCE;
    }

    public InlineKeyboardMarkup generateInlineButtons(Language language, String... keys) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        for (String key : keys) {
            InlineKeyboardButton inlineBtn = new InlineKeyboardButton();
            inlineBtn.setText(LocaleUtils.getMessageByKey(key, language));
            inlineBtn.setCallbackData(key);
            rowInline.add(inlineBtn);
        }

        rowsInline.add(rowInline);
        markup.setKeyboard(rowsInline);
        return markup;
    }
}
