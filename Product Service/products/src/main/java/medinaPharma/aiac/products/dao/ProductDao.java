package medinaPharma.aiac.products.dao;

import medinaPharma.aiac.products.models.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ProductDao extends JpaRepository<ProductEntity, Integer> {
    ProductEntity findByNom(String nom);
    List<ProductEntity> findByPrixGreaterThan(BigDecimal prix);
    List<ProductEntity> findByPrixLessThan(BigDecimal prix);
    List<ProductEntity> findByType(String type);
}
