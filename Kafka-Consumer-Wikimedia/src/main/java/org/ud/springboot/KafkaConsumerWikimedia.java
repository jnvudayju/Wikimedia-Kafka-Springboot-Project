package org.ud.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerWikimedia {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerWikimedia.class);


    @KafkaListener(topics = "wikimedia_recentchange", groupId = "myGroup")
    public void consumer(String eventMessage){
        LOGGER.info(String.format("message received by consumer %s", eventMessage));
    }
}
