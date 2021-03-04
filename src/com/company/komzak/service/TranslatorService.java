package com.company.komzak.service;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

public interface TranslatorService {

    SendMessage showAllLanguages(Update update);

    SendMessage writeText(Update update);

    SendMessage showTranslate(Update update);
}
