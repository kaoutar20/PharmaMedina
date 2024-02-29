package medinaPharma.aiac.products.service.facade;

import medinaPharma.aiac.products.dto.ProductDto;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    ProductDto save(ProductDto productDto);

    ProductDto findById(Integer id);
    List<ProductDto> findAll();
    ProductDto findByNom(String nom);
    List<ProductDto> findByPrixGreaterThan(BigDecimal prix);

    List<ProductDto> findByPrixLessThan(BigDecimal prix);


    void delete(Integer id);

    ProductDto update(ProductDto productDto, Integer id);
}