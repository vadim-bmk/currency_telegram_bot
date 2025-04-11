package com.skillbox.cryptobot.bot.command;

import com.skillbox.cryptobot.service.CryptoCurrencyService;
import com.skillbox.cryptobot.service.SubscriberService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Обработка команды подписки на курс валюты
 */
@Service
@AllArgsConstructor
@Slf4j
public class SubscribeCommand implements IBotCommand {

    @Override
    public String getCommandIdentifier() {
        return "subscribe";
    }

    @Override
    public String getDescription() {
        return "Подписывает пользователя на стоимость биткоина";
    }

    private SubscriberService subscriberService;
    private CryptoCurrencyService service;

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        SendMessage answer = new SendMessage();
        answer.setChatId(message.getChatId());
        String price = message.getText().split(" ", 2)[1];

        try {
            Double priceDouble = Double.parseDouble(price);

            subscriberService.updateSubscriber(message.getFrom().getId(), priceDouble);
            GetPriceCommand getPriceCommand = new GetPriceCommand(service);
            getPriceCommand.processMessage(absSender, message, arguments);
            answer.setText("Новая подписка создана на стоимость " + priceDouble + ".");

        } catch (NumberFormatException e) {
            log.error("Error occurred in /subscribe command", e);
        }

        try {
            absSender.execute(answer);
        } catch (TelegramApiException e) {
            log.error("Error occurred in /subscribe command", e);
        }

    }


}