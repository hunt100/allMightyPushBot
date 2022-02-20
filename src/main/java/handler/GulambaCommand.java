package handler;

import data.constants.ApplicationConstants;
import data.dto.ChatSurvey;
import data.dto.Decision;
import data.dto.User;
import data.enums.Language;
import data.enums.LeisureType;
import data.enums.LocationHost;
import org.apache.commons.lang3.BooleanUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import service.StorageService;
import util.LocaleUtils;

import java.util.ArrayList;
import java.util.List;

public class GulambaCommand extends AbstractCommand implements Callback {

    @Override
    public String getPattern() {
        return ApplicationConstants.Commands.Gulamba.PATTERN;
    }

    @Override
    public SendMessage handle(Update update) {
        SendMessage message = new SendMessage();
        message.setReplyMarkup(generateInlineButtons());
        message.setChatId(getChatId(update).toString());
        message.setText(getStartingIgrambaMessage(getMessageAuthor(update).getFirstName()));
        StorageService.getInstance().addNewSurvey(getChatId(update), LeisureType.WALK);
        return message;
    }

    private InlineKeyboardMarkup generateInlineButtons() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        InlineKeyboardButton inlineYesButton = new InlineKeyboardButton();
        inlineYesButton.setText(getPositiveBtnText());
        inlineYesButton.setCallbackData(ApplicationConstants.Commands.Gulamba.CALLBACK_BTN_YES);

        InlineKeyboardButton inlineNoButton = new InlineKeyboardButton();
        inlineNoButton.setText(getNegativeBtnText());
        inlineNoButton.setCallbackData(ApplicationConstants.Commands.Gulamba.CALLBACK_BTN_NO);

        rowInline.add(inlineYesButton);
        rowInline.add(inlineNoButton);

        rowsInline.add(rowInline);
        markup.setKeyboard(rowsInline);
        return markup;
    }

    @Override
    public boolean hasCallback(String callback) {
        return List.of(
                ApplicationConstants.Commands.Gulamba.CALLBACK_BTN_YES,
                ApplicationConstants.Commands.Gulamba.CALLBACK_BTN_NO)
                .contains(callback);
    }

    @Override
    public SendMessage handleCallback(Update update) {
        SendMessage sendMessage = new SendMessage();
        boolean accepted = ApplicationConstants.Commands.Gulamba.CALLBACK_BTN_YES.equals(update.getCallbackQuery().getData());
        String msg = accepted ? getGulambaPositiveMessage() : getGulambaNegativeMessage();

        Long chatId = getChatId(update);
        ChatSurvey survey = StorageService.getInstance().findSurveyByChatId(chatId, LeisureType.WALK);
        User currentUser = getMessageAuthor(update);
        Decision decision = new Decision();
        decision.setUser(currentUser);
        decision.setAgree(accepted);
        decision.setHost(LocationHost.GENERAL);
        int oldValue = survey.getDecisions().size();
        survey.getDecisions().add(decision);
        boolean sizeChanged = oldValue != survey.getDecisions().size();

        if (!sizeChanged) {
            return null;
        }

        long usersAgreeCount = survey.getDecisions().stream().filter(d -> BooleanUtils.isTrue(d.getAgree())).count();
        sendMessage.setText(String.format(msg, currentUser.getFirstName(), usersAgreeCount));
        sendMessage.setReplyMarkup(generateInlineButtons());
        sendMessage.setChatId(getChatId(update).toString());
        return sendMessage;
    }

    private String getPositiveBtnText() {
        return LocaleUtils.getMessageByKey("gulambaBtnYes", Language.RU);
    }

    private String getNegativeBtnText() {
        return LocaleUtils.getMessageByKey("gulambaBtnNo", Language.RU);
    }

    private String getStartingIgrambaMessage(String firstName) {
        return String.format(LocaleUtils.getMessageByKey("gulambaStart", Language.RU), firstName);
    }

    private String getGulambaPositiveMessage() {
        return LocaleUtils.getMessageByKey("gulambaPositive", Language.RU);
    }

    private String getGulambaNegativeMessage() {
        return LocaleUtils.getMessageByKey("gulambaNegative", Language.RU);
    }
}
