package handler;

import data.constants.ApplicationConstants.Commands.Igramba;
import data.dto.ChatSurvey;
import data.dto.Decision;
import data.dto.User;
import data.enums.Language;
import data.enums.LeisureType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import service.InlineButtonService;
import service.StorageService;
import util.LocaleUtils;

import java.util.List;

// TODO: 26.02.2022 Add opportunity to check in which game we will play, analogue with Gulamba 
public class IgrambaCommand extends AbstractCommand implements Callback {

    private final Logger log = LoggerFactory.getLogger(IgrambaCommand.class);
    private final InlineButtonService inlineButtonService = InlineButtonService.getInstance();
    private final String FULL_PARTY_MSG_KEY = "igrambaFullParty";

    @Override
    public String getPattern() {
        return Igramba.PATTERN;
    }

    @Override
    public SendMessage handle(Update update) {
        SendMessage message = new SendMessage();
        message.setReplyMarkup(inlineButtonService.generateInlineButtons(Language.RU, Igramba.BTN_YES, Igramba.BTN_NO));
        message.setChatId(getChatId(update).toString());
        message.setText(getStartingIgrambaMessage(getMessageAuthor(update).getFirstName()));
        StorageService.getInstance().addNewSurvey(getChatId(update), LeisureType.PLAY);
        return message;
    }

    @Override
    public boolean hasCallback(String callback) {
        return List.of(
                Igramba.BTN_YES,
                Igramba.BTN_NO)
                .contains(callback);
    }

    @Override
    public SendMessage handleCallback(Update update) {
        SendMessage sendMessage = new SendMessage();
        boolean accepted = Igramba.BTN_YES.equals(update.getCallbackQuery().getData());
        String msg = accepted ? getIgrambaPositiveMessage() : getIgrambaNegativeMessage();

        Long chatId = getChatId(update);
        User currentUser = getMessageAuthor(update);
        ChatSurvey survey = StorageService.getInstance().findSurveyByChatId(chatId, LeisureType.PLAY);
        if (survey == null) {
            log.warn("No survey for init gulamba callback! Chat id: [{}], User login: [{}], user id: [{}]",
                    chatId,
                    currentUser.getUsername(),
                    currentUser.getId());
            return null;
        }

        Decision decision = new Decision();
        decision.setUser(currentUser);
        decision.setAgree(accepted);
        int oldValue = survey.getDecisions().size();
        survey.getDecisions().add(decision);
        boolean sizeChanged = oldValue != survey.getDecisions().size();

        if (!sizeChanged) {
            return null;
        }

        String mainMsg = String.format(msg, currentUser.getFirstName(), ++oldValue);
        if (oldValue >= 4) {
            String fullPartyMsgPart = LocaleUtils.getMessageByKey(FULL_PARTY_MSG_KEY, Language.RU);
            mainMsg += fullPartyMsgPart;
        }
        sendMessage.setText(mainMsg);
        sendMessage.setReplyMarkup(inlineButtonService.generateInlineButtons(Language.RU, Igramba.BTN_YES, Igramba.BTN_NO));
        sendMessage.setChatId(getChatId(update).toString());
        return sendMessage;
    }

    private String getStartingIgrambaMessage(String firstName) {
        return String.format(LocaleUtils.getMessageByKey("igrambaStart", Language.RU), firstName);
    }

    private String getIgrambaPositiveMessage() {
        return LocaleUtils.getMessageByKey("igrambaPositive", Language.RU);
    }

    private String getIgrambaNegativeMessage() {
        return LocaleUtils.getMessageByKey("igrambaNegative", Language.RU);
    }

}
