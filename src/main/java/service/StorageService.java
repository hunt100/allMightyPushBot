package service;

import data.dto.ChatSurvey;
import data.enums.LeisureType;

import java.util.ArrayList;
import java.util.List;

public class StorageService {
    private List<ChatSurvey> chatStorage = new ArrayList<>();

    private static class StorageServiceLoader {
        private static final StorageService INSTANCE = new StorageService();
    }

    private StorageService() {
        if (StorageServiceLoader.INSTANCE != null) {
            throw new IllegalStateException("Already instantiated");
        }
    }

    public static StorageService getInstance() {
        return StorageServiceLoader.INSTANCE;
    }

    public ChatSurvey addNewSurvey(Long chatId, LeisureType type) {
        if (chatStorage.stream().anyMatch(chatObj -> chatObj.getChatId().equals(chatId))) {
            ChatSurvey gulamba = findSurveyByChatId(chatId, type);
            chatStorage.remove(gulamba);
        }
        ChatSurvey gulamba = new ChatSurvey(chatId, type);
        chatStorage.add(gulamba);
        return gulamba;
    }

    public ChatSurvey findSurveyByChatId(Long chatId, LeisureType type) {
        return chatStorage
                .stream()
                .filter(chatObj -> chatObj.getChatId().equals(chatId) && type.equals(chatObj.getType()))
                .findFirst()
                .orElse(null);
    }
}
