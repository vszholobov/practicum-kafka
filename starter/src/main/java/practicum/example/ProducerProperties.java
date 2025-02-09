package practicum.example;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "practicum.kafka.producer")
public record ProducerProperties(
        boolean enabled,
        String bootstrapServers
) {
}
