package io.vladprotchenko.ensstartercore.model.kafka.constant;

public final class KafkaConstants {

    public static final String USER_DELETION_EVENTS = "user-deletion-events";
    public static final String ACCOUNTING_DELETION_GROUP = "user-deletion-group";

    private KafkaConstants() {
        throw new IllegalStateException("Utility class");
    }
}
