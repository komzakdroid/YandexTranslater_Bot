package com.company.komzak;

import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import com.company.komzak.service.*;

public class TranslatorBot extends TelegramLongPollingBot {

    private TranslatorService translatorService = new TranslatorServiceImpl();

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage()) {
                Message message = update.getMessage();

                if (message.hasText()) {
                    String text = message.getText();

                    if (text.equals("/start")) {
                        execute(translatorService.showAllLanguages(update));
                    } else {
                        execute(translatorService.showTranslate(update));
                    }
                }
            } else if (update.hasCallbackQuery()) {
                execute(translatorService.writeText(update));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "yandexTr_bot";
    }

    @Override
    public String getBotToken() {
        return "1669648979:AAGq5KvFjhIsiaYZs2qIEpwpaFomQA3oSr8";
    }
}
