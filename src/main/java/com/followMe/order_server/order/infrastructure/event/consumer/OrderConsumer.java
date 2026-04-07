package com.followMe.order_server.order.infrastructure.event.consumer;

import com.followMe.common.event.inbox.Inbox;
import com.followMe.common.event.inbox.InboxRepository;
import com.followMe.order_server.order.application.OrderService;
import com.followMe.order_server.order.application.dto.request.OrderUpdateStateRequest;
import com.followMe.order_server.order.domain.OrderState;
import com.followMe.order_server.order.infrastructure.event.DeliveryCompleted;
import com.followMe.order_server.order.infrastructure.event.DeliveryCompleted.Payload;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderConsumer {

    private final InboxRepository inboxRepository;
    private OrderService orderService;

    @Transactional
    @KafkaListener(topics = "DeliveryCompleted", groupId = "delivery-service")
    public void consume(DeliveryCompleted event) {
        if (inboxRepository.existsById(UUID.fromString(event.getEventId()))) {
            return;
        }
        orderService.updateStatus(((Payload) event.getPayload()).orderId(),
                new OrderUpdateStateRequest(OrderState.COMPLETED.name()));

        inboxRepository.save(
                Inbox.builder().id(UUID.fromString(event.getEventId())).messageGroup("DeliveryCompleted").build());
    }
}
