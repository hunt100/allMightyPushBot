package handler;

import data.constants.ApplicationConstants.Commands.Gulamba;
import data.dto.MessageInfo;
import data.enums.Language;
import data.enums.LeisureType;
import data.enums.LocationHost;
import handler.strategy.impl.GulambaHostCallbackStrategy;
import handler.strategy.impl.GulambaInitCallbackStrategy;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import service.InlineButtonService;
import service.StorageService;
import util.LocaleUtils;

import java.util.List;

public class GulambaCommand extends AbstractCommand implements Callback {

    private final InlineButtonService inlineButtonService = InlineButtonService.getInstance();

    @Override
    public String getPattern() {
        return Gulamba.PATTERN;
    }

    @Override
    public SendMessage handle(Update update) {
        SendMessage message = new SendMessage();
        message.setReplyMarkup(inlineButtonService.generateInlineButtons(Language.RU, Gulamba.BTN_YES, Gulamba.BTN_NO));
        message.setChatId(getChatId(update).toString());
        message.setText(getStartingIgrambaMessage(getMessageAuthor(update).getFirstName()));
        StorageService.getInstance().addNewSurvey(getChatId(update), LeisureType.WALK);
        return message;
    }

    @Override
    public boolean hasCallback(String callback) {
        return List.of(
                        Gulamba.BTN_YES,
                        Gulamba.BTN_NO,
                        Gulamba.TO_DANILA,
                        Gulamba.TO_VLADIMIR,
                        Gulamba.TO_ANVAR)
                .contains(callback);
    }

    @Override
    public SendMessage handleCallback(Update update) {
        SendMessage sendMessage = new SendMessage();
        String callbackResult = update.getCallbackQuery().getData();
        MessageInfo info = new MessageInfo(getChatId(update), getMessageAuthor(update));
        switch (callbackResult) {
            case Gulamba.BTN_YES: sendMessage = new GulambaInitCallbackStrategy(info, true).execute(update); break;
            case Gulamba.BTN_NO: sendMessage = new GulambaInitCallbackStrategy(info, false).execute(update); break;
            case Gulamba.TO_DANILA: sendMessage = new GulambaHostCallbackStrategy(info, LocationHost.DANILA).execute(update); break;
            case Gulamba.TO_VLADIMIR: sendMessage = new GulambaHostCallbackStrategy(info, LocationHost.VLADIMIR).execute(update); break;
            case Gulamba.TO_ANVAR: sendMessage = new GulambaHostCallbackStrategy(info, LocationHost.ANVAR).execute(update); break;
        }

        return sendMessage;
    }

    private String getStartingIgrambaMessage(String firstName) {
        return String.format(LocaleUtils.getMessageByKey("gulambaStart", Language.RU), firstName);
    }
}
