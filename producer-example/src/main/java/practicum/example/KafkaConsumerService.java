package practicum.example;

import java.time.Duration;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
    private final KafkaConsumer<String, String> kafkaConsumer;

    public KafkaConsumerService(KafkaConsumer<String, String> kafkaConsumer) {
        this.kafkaConsumer = kafkaConsumer;
    }

    @Scheduled(fixedRate = 5000) // Выполнять каждые 5 секунд
    public void consumeMessages() {
        ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(1000));
        records.forEach(record -> System.out.println("Received message: " + record.value()));
    }
}