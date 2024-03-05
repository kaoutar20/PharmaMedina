package medinaPharma.aiac.products.Producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import medinaPharma.aiac.products.dto.ProductDto;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProductProducer {

    private final AmqpTemplate amqpTemplate;
    private final ObjectMapper objectMapper; // Ajout de ObjectMapper

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routingkey}")
    private String routingkey;

    public ProductProducer(AmqpTemplate amqpTemplate, ObjectMapper objectMapper) {
        this.amqpTemplate = amqpTemplate;
        this.objectMapper = objectMapper; // Injection de ObjectMapper
    }

    public void sendProductMessage(ProductDto productDto) {
        try {
            String jsonProduct = objectMapper.writeValueAsString(productDto); // Conversion en JSON
            amqpTemplate.convertAndSend(exchange, routingkey, jsonProduct);
            System.out.println("Produit envoyé avec succès à RabbitMQ: " + jsonProduct);
        } catch (JsonProcessingException e) {
            e.printStackTrace(); // Gérer l'erreur de conversion en JSON
        }
    }
}
