package com.followMe.order_server.order.infrastructure.event;
import com.followMe.common.event.BaseEvent;
import java.util.UUID;
import lombok.Getter;

@Getter
public class DeliveryCompleted extends BaseEvent {

    private static final String domain = "DELIVERY";

    public record Payload(UUID orderId) {}

    private DeliveryCompleted(UUID deliveryId, Payload payload) {
        super(domain, deliveryId, payload);
    }
}
