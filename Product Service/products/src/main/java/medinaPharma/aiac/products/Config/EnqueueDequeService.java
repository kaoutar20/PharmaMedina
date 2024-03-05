package medinaPharma.aiac.products.Config;

import medinaPharma.aiac.products.dto.ProductDto;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EnqueueDequeService {

    private final AmqpTemplate amqpTemplate;

    @Value("${rabbitmq.exchange}")
    String exchange;

    @Value("${rabbitmq.routingkey}")
    private String routingkey;

    public EnqueueDequeService(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public void publishProduct(ProductDto productDto){
        amqpTemplate.convertAndSend(exchange, routingkey, productDto);
    }
}
