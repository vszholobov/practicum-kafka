package practicum.example;

import java.time.Duration;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {
    private final KafkaConsumer<String, String> kafkaConsumer;

    @Scheduled(fixedRate = 5000)
    public void consumeMessages() {
        ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(1000));
        records.forEach(record -> System.out.println("Received message: " + record.value()));
    }
}