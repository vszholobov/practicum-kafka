package practicum.example;

import java.time.Duration;
import java.util.concurrent.Future;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest(classes = KafkaConfig.class, properties = {
        "practicum.kafka.consumer.enabled=true",
        "practicum.kafka.consumer.group-id=test-group",
        "practicum.kafka.consumer.offset-reset=earliest",
        "practicum.kafka.consumer.topic=test-topic",
        "practicum.kafka.producer.enabled=true",
})
@ActiveProfiles("test")
class KafkaConfigTest {
    private static final KafkaContainer kafkaContainer =
            new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));
    @Autowired(required = false)
    private KafkaConsumer<String, String> kafkaConsumer;
    @Autowired(required = false)
    private KafkaProducer<String, String> kafkaProducer;

    /**
     * Проставляет адрес кафки для продюсера и консюмера после запуска контейнера
     */
    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("practicum.kafka.consumer.bootstrap-servers", kafkaContainer::getBootstrapServers);
        registry.add("practicum.kafka.producer.bootstrap-servers", kafkaContainer::getBootstrapServers);
    }

    @BeforeAll
    static void startKafka() {
        kafkaContainer.start();
    }

    @Test
    void testKafkaMessageSendAndReceive() throws Exception {
        log.info("start testKafkaMessageSendAndReceive");
        String topic = "test-topic";
        String message = "Hello, Kafka!";

        ProducerRecord<String, String> record = new ProducerRecord<>(topic, message);
        Future<RecordMetadata> future = kafkaProducer.send(record);
        future.get();

        ConsumerRecord<String, String> receivedRecord = kafkaConsumer.poll(Duration.ofSeconds(5)).iterator().next();

        assertThat(receivedRecord.value()).isEqualTo(message);
        log.info("end testKafkaMessageSendAndReceive");
    }
}
