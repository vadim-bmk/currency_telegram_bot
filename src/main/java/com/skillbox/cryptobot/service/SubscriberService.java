package com.skillbox.cryptobot.service;

import com.skillbox.cryptobot.entity.Subscriber;
import com.skillbox.cryptobot.repository.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class SubscriberService {

    private final SubscriberRepository subscriberRepository;

    @Autowired
    public SubscriberService(SubscriberRepository subscriberRepository) {
        this.subscriberRepository = subscriberRepository;
    }

    public Subscriber createNewSubscriber(Long id) {
        Optional<Subscriber> existingSubscriber = Optional.ofNullable(getSubscriber(id));
        if (existingSubscriber.isPresent()) {
            return existingSubscriber.get();
        }

        Subscriber subscriber = new Subscriber();
        subscriber.setId(id);
        log.info("Создан новый пользователь: {}", subscriber);
        return subscriberRepository.save(subscriber);
    }

    public Subscriber updateSubscriber(Long id, Double price) {
        Subscriber subscriber = getSubscriber(id);
        subscriber.setPrice(price);
        log.info("Для пользователя " + subscriber.getId() + " создана подписка " + price);

        return subscriberRepository.save(subscriber);
    }

    public Double getSubscription (Long id){
        Subscriber subscriber = getSubscriber(id);
        return subscriber.getPrice();
    }

    public void unsubscribe(Long id){
        Subscriber subscriber = getSubscriber(id);
        subscriber.setPrice(null);
        subscriberRepository.save(subscriber);
    }

    public Subscriber getSubscriber(Long id) {
        Subscriber subscriber = subscriberRepository.findById(id);
        return subscriber;
    }

    public List<Subscriber> getAllSubscribers() {
        return subscriberRepository.findAll();
    }
}