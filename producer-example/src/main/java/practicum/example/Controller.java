package practicum.example;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    private final KafkaProducer<String, String> kafkaProducer;

    public Controller(KafkaProducer<String, String> kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @GetMapping
    public ResponseEntity<?> testKafka() {
        System.out.println("231dda");
        kafkaProducer.send(new ProducerRecord<>("my-topic", "123124123"));
        return ResponseEntity.ok("sent");
    }
}
