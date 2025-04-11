package com.skillbox.cryptobot.schedule;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@ConfigurationProperties(prefix = "schedule")
@Data
public class ScheduleProperties {
    private Duration bitcoinFetchRate;
    private Duration bitcoinCheckRate;
}