package handler.strategy.impl;

import data.constants.ApplicationConstants;
import data.dto.*;
import data.enums.Language;
import data.enums.LeisureType;
import data.enums.LocationHost;
import handler.strategy.CallbackStrategy;
import org.apache.commons.lang3.BooleanUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import service.InlineButtonService;
import service.StorageService;
import util.LocaleUtils;

import java.util.Set;
import java.util.stream.Collectors;

public class GulambaHostCallbackStrategy implements CallbackStrategy {

    private final MessageInfo info;
    private final LocationHost locationHost;
    private final InlineButtonService inlineButtonService = InlineButtonService.getInstance();

    public GulambaHostCallbackStrategy(MessageInfo info, LocationHost locationHost) {
        this.info = info;
        this.locationHost = locationHost;
    }



    @Override
    public SendMessage execute(Update update) {
        SendMessage sendMessage = new SendMessage();
        String msg = LocaleUtils.getMessageByKey(locationHost.getLocationKey(), Language.RU);

        Long chatId = info.getChatId();
        ChatSurvey survey = StorageService.getInstance().findSurveyByChatId(chatId, LeisureType.WALK);
        User currentUser = info.getAuthor();

        WalkSubDecision subDecision = new WalkSubDecision(locationHost);
        subDecision.setUser(currentUser);

        boolean isPositiveDecision = survey.getDecisions().stream().anyMatch(decision -> BooleanUtils.isTrue(decision.getAgree()));
        if (!isPositiveDecision) {
            return null;
        }

        boolean isUserAlreadyVoted = survey.getWalkSubDecisions().stream().anyMatch(walkSubDecision -> currentUser.getId().equals(walkSubDecision.getUser().getId()));

        if (isUserAlreadyVoted) {
            return null;
        }

        survey.getWalkSubDecisions().add(subDecision);

        long usersToSpecificLocationCount = survey.getWalkSubDecisions().stream().filter(walkSubDecision -> locationHost.equals(walkSubDecision.getLocationHost())).count();
        sendMessage.setText(String.format(msg, currentUser.getFirstName(), usersToSpecificLocationCount));
        sendMessage.setReplyMarkup(inlineButtonService.generateInlineButtons(Language.RU,
                ApplicationConstants.Commands.Gulamba.BTN_YES,
                ApplicationConstants.Commands.Gulamba.BTN_NO));

        sendMessage.setChatId(chatId.toString());

        return sendMessage;
    }


}
