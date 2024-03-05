package medinaPharma.aiac.products.service.impl;

import medinaPharma.aiac.products.Producer.ProductProducer;
import medinaPharma.aiac.products.dao.ProductDao;
import medinaPharma.aiac.products.dto.ProductDto;
import medinaPharma.aiac.products.exception.handler.EntityNotFoundException;
import medinaPharma.aiac.products.models.ProductEntity;
import medinaPharma.aiac.products.service.facade.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;
    private final ModelMapper modelMapper;
    private final ProductProducer productProducer;

    public ProductServiceImpl(ProductDao productDao, ModelMapper modelMapper, ProductProducer productProducer) {
        this.productDao = productDao;
        this.modelMapper = modelMapper;
        this.productProducer = productProducer;
    }

    @Override
    public ProductDto save(ProductDto productDto) {
        ProductEntity productEntity=modelMapper.map(productDto, ProductEntity.class);
        ProductEntity saved = productDao.save(productEntity);
        return modelMapper.map(saved, ProductDto.class);
    }

    @Override
    public ProductDto findById(Integer id) {
        Optional<ProductEntity> productEntityOptional = productDao.findById(id);
        if(productEntityOptional.isPresent()){
            ProductEntity productEntity = productEntityOptional.get();
            ProductDto productDto = modelMapper.map(productEntity, ProductDto.class);
            productProducer.sendProductMessage(productDto); // Envoi du produit à RabbitMQ
            return productDto;
        } else {
            throw new EntityNotFoundException("Product Not Found");
        }
    }

    @Override
    public List<ProductDto> findAll() {
        return productDao.findAll()
                .stream()
                .map(productEntity -> modelMapper.map(productEntity, ProductDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto findByNom(String nom) {
        ProductEntity productEntity=productDao.findByNom(nom);
        if(productEntity == null) return null;
        return modelMapper.map(productEntity, ProductDto.class);
    }

    @Override
    public List<ProductDto> findByPrixGreaterThan(BigDecimal prix) {
        List<ProductEntity> productEntities = productDao.findByPrixGreaterThan(prix);
        return productEntities
                .stream()
                .map(productEntity -> modelMapper.map(productEntity, ProductDto.class))
                .collect(Collectors.toList());

    }

    @Override
    public List<ProductDto> findByPrixLessThan(BigDecimal prix) {
        List<ProductEntity> productEntities = productDao.findByPrixLessThan(prix);
        return productEntities
                .stream()
                .map(productEntity -> modelMapper.map(productEntity, ProductDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        productDao.deleteById(id);
    }

    @Override
    public ProductDto update(ProductDto productDto, Integer id) {
        Optional<ProductEntity> productEntityOptional = productDao.findById(id);
        if(productEntityOptional.isPresent()){
            ProductEntity productEntity = modelMapper.map(productDto, ProductEntity.class);
            productEntity.setId(id);
            ProductEntity  updated = productDao.save(productEntity);
            return modelMapper.map(updated, ProductDto.class);
        }else{
            throw new EntityNotFoundException("Product Not Found");
        }
    }

}


