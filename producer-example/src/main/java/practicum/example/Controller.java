package practicum.example;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class Controller {
    private final KafkaProducer<String, String> kafkaProducer;

    @GetMapping
    public ResponseEntity<String> testKafka(
            @RequestParam("topic") String topic,
            @RequestParam("message") String message
    ) {
        log.info("Sending message to topic");
        kafkaProducer.send(new ProducerRecord<>(topic, message));
        return ResponseEntity.ok("sent");
    }
}
