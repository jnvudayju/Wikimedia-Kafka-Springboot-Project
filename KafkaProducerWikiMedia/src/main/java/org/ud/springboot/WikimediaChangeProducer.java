package org.ud.springboot;

import com.launchdarkly.eventsource.EventSource;
import com.launchdarkly.eventsource.background.BackgroundEventHandler;
import com.launchdarkly.eventsource.background.BackgroundEventSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.concurrent.TimeUnit;

@Service
public class WikimediaChangeProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(WikimediaChangeProducer.class);

    private KafkaTemplate<String, String> kafkaTemplate;

    public WikimediaChangeProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage() throws InterruptedException {
        String topic = "wikimedia_recentchange";

        // Use the WikimediaChangesHandler as the BackgroundEventHandler
        WikimediaChangesHandler eventHandler = new WikimediaChangesHandler(kafkaTemplate, topic);

        String url = "https://stream.wikimedia.org/v2/stream/recentchange";

        EventSource.Builder eventSourceBuilder = new EventSource.Builder(URI.create(url));



        // Use BackgroundEventSource.Builder to build the event source
        BackgroundEventSource.Builder builder = new BackgroundEventSource.Builder(eventHandler, eventSourceBuilder);

        // Build and start the BackgroundEventSource
        BackgroundEventSource eventSource = builder.build();
        eventSource.start();

        TimeUnit.MINUTES.sleep(10);

        LOGGER.info("Event source started, streaming data from Wikimedia");


    }

}
