package practicum.example;

import java.util.Optional;

import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaCloser {
    /**
     * Qualifier нужна на случай, если кроме как в стартере еще будут созданы бины KafkaConsumer
     * Аннотация добавится в конструктор благодаря настройкам в lombok.config
     */
    @Qualifier("kafkaConsumer")
    private final Optional<KafkaConsumer<String, String>> kafkaConsumer;
    @Qualifier("kafkaProducer")
    private final Optional<KafkaProducer<String, String>> kafkaProducer;

    /**
     * Закрывает consumer и producer при остановке приложения
     * Обрабатывает ошибки закрытия
     */
    @PreDestroy
    public void shutdown() {
        log.info("Closing kafka beans...");
        try {
            kafkaConsumer.ifPresent(KafkaConsumer::close);
        } catch (Exception e) {
            log.error("Error occurred while closing kafka consumer", e);
        }
        try {
            kafkaProducer.ifPresent(KafkaProducer::close);
        } catch (Exception e) {
            log.error("Error occurred while closing kafka producer", e);
        }
        log.info("Kafka beans closed");
    }
}
