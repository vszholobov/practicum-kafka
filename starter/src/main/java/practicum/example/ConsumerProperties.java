package practicum.example;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "practicum.kafka.consumer")
public record ConsumerProperties(
        boolean enabled,
        String bootstrapServers,
        String groupId,
        String topic,
        String offsetReset
) {
}
