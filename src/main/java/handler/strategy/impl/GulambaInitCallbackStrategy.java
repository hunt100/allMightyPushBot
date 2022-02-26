package handler.strategy.impl;

import data.constants.ApplicationConstants;
import data.dto.ChatSurvey;
import data.dto.Decision;
import data.dto.MessageInfo;
import data.dto.User;
import data.enums.Language;
import data.enums.LeisureType;
import data.enums.LocationHost;
import handler.strategy.CallbackStrategy;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import service.InlineButtonService;
import service.StorageService;
import util.LocaleUtils;

public class GulambaInitCallbackStrategy implements CallbackStrategy {

    private final MessageInfo info;
    private final boolean accepted;
    private final InlineButtonService inlineButtonService = InlineButtonService.getInstance();
    private final Logger log = LoggerFactory.getLogger(GulambaInitCallbackStrategy.class);

    public GulambaInitCallbackStrategy(MessageInfo info, boolean accepted) {
        this.info = info;
        this.accepted = accepted;
    }

    @Override
    public SendMessage execute(Update update) {
        SendMessage sendMessage = new SendMessage();
        String msg = accepted ? getGulambaPositiveMessage() : getGulambaNegativeMessage();

        Long chatId = info.getChatId();
        User currentUser = info.getAuthor();
        ChatSurvey survey = StorageService.getInstance().findSurveyByChatId(chatId, LeisureType.WALK);
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

        long usersAgreeCount = survey.getDecisions().stream().filter(d -> BooleanUtils.isTrue(d.getAgree())).count();
        sendMessage.setText(String.format(msg, currentUser.getFirstName(), usersAgreeCount));
        if (accepted) {
            sendMessage.setReplyMarkup(inlineButtonService.generateInlineButtons(Language.RU,
                    ApplicationConstants.Commands.Gulamba.TO_DANILA,
                    ApplicationConstants.Commands.Gulamba.TO_VLADIMIR,
                    ApplicationConstants.Commands.Gulamba.TO_ANVAR));
        } else {
            sendMessage.setReplyMarkup(inlineButtonService.generateInlineButtons(Language.RU,
                    ApplicationConstants.Commands.Gulamba.BTN_YES,
                    ApplicationConstants.Commands.Gulamba.BTN_NO));
        }
        sendMessage.setChatId(chatId.toString());

        return sendMessage;
    }

    private String getGulambaPositiveMessage() {
        return LocaleUtils.getMessageByKey("gulambaPositive", Language.RU);
    }

    private String getGulambaNegativeMessage() {
        return LocaleUtils.getMessageByKey("gulambaNegative", Language.RU);
    }
}
