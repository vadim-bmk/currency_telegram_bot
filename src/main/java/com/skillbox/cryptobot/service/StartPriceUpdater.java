package com.skillbox.cryptobot.service;

import com.skillbox.cryptobot.client.BinanceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
public class StartPriceUpdater {

    private final AtomicReference<Double> price = new AtomicReference<>();
    private final BinanceClient client;

    public StartPriceUpdater(BinanceClient client) {
        this.client = client;
    }

    @Scheduled(fixedRateString = "#{@scheduleProperties.bitcoinFetchRate.toMillis()}")
    public void updatePrice() {
        try {
            double newPrice = client.getBitcoinPrice();
            price.set(newPrice);
            log.info("Цена BTC обновлена: {}", newPrice);
        } catch (IOException e) {
            log.error("Ошибка при обновлении цены BTC: {}", e.getMessage());
        }
    }

    public Double getCachedPrice() {
        return price.get();
    }
}