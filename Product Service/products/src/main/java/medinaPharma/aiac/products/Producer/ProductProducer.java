package medinaPharma.aiac.products.Producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import medinaPharma.aiac.products.dto.ProductDto;
import medinaPharma.aiac.products.exception.handler.EntityNotFoundException;
import medinaPharma.aiac.products.service.facade.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductProducer {

    @Autowired
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    public String getProductJsonById(Integer productId) {
        try {
            ProductDto product = productService.findById(productId);
            return objectMapper.writeValueAsString(product);
        } catch (EntityNotFoundException e) {
            System.out.println("Le produit avec l'ID " + productId + " n'a pas été trouvé.");
            return null;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

}
