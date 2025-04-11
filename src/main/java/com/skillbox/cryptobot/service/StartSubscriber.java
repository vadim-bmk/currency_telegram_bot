package com.skillbox.cryptobot.service;

import com.skillbox.cryptobot.entity.Subscriber;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@AllArgsConstructor
@Slf4j
public class StartSubscriber {
    private final TelegramService telegramService;
    private SubscriberService subscriberService;
    private CryptoCurrencyService service;
    private StartPriceUpdater startPriceUpdater;

    @Scheduled(fixedRateString = "#{@scheduleProperties.bitcoinCheckRate.toMillis()}")
    public void checkSubscribers() {
        Double price = startPriceUpdater.getCachedPrice();

        if (price == null) {
            log.warn("Цена BTC ещё не загружена");
            return;
        }

        for (Subscriber subscriber : subscriberService.getAllSubscribers()) {
            if (price > subscriber.getPrice()) {
                telegramService.sendMessage(subscriber.getId(), "Пора покупать, цена: " + price);
            }
        }
    }
}
