package com.company.komzak.service;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import com.company.komzak.models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class TranslatorServiceImpl implements TranslatorService {
    private HttpService httpService = new HttpService();
    private List<Account> accountList = new ArrayList<>();

    @Override
    public SendMessage showAllLanguages(Update update) {
        int index = -1;
        for (int i = 0; i < accountList.size(); i++) {
            if (accountList.get(i).getUser().getId().equals(update.getMessage().getFrom().getId())) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            accountList.add(new Account(update.getMessage().getFrom()));
        }
        List<String> languageList = httpService.getLanguagesText();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("\uD83D\uDE01 Select which do you want:");
        sendMessage.setChatId(update.getMessage().getChatId());

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> list = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonList = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton1;
        for (int i = 0; i < languageList.size(); i++) {
            inlineKeyboardButton1 = new InlineKeyboardButton();
            if (i != 0 && i % 5 == 0) {
                list.add(inlineKeyboardButtonList);
                inlineKeyboardButtonList = new ArrayList<>();
            }
            inlineKeyboardButton1.setText(languageList.get(i));
            inlineKeyboardButton1.setCallbackData(languageList.get(i));
            inlineKeyboardButtonList.add(inlineKeyboardButton1);
        }
        list.add(inlineKeyboardButtonList);
        inlineKeyboardMarkup.setKeyboard(list);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return sendMessage;
    }

    @Override
    public SendMessage writeText(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());
        sendMessage.setText("Enter text:");
        String language = update.getCallbackQuery().getData();

        for (int i = 0; i < accountList.size(); i++) {
            if (accountList.get(i).getUser().getId().equals(update.getCallbackQuery().getFrom().getId())) {
                accountList.get(i).setLanguage(language);
                break;
            }
        }
        return sendMessage;
    }

    @Override
    public SendMessage showTranslate(Update update) {
        int index = -1;
        for (int i = 0; i < accountList.size(); i++) {
            if (accountList.get(i).getUser().getId().equals(update.getMessage().getFrom().getId())) {
                index = i;
                break;
            }
        }
        String translate = httpService.getTranslateText(accountList.get(index).getLanguage(), update.getMessage().getText());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());
        sendMessage.setText(translate);
        return sendMessage;
    }
}
