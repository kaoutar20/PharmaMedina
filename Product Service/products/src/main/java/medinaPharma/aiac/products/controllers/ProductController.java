package medinaPharma.aiac.products.controllers;

import jakarta.validation.Valid;
import medinaPharma.aiac.products.dto.ProductDto;
import medinaPharma.aiac.products.service.facade.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {
    private ProductService productService;
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping("")
    public ResponseEntity<List<ProductDto>> getProducts(){
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable("id") Integer id){
        return ResponseEntity.ok(productService.findById(id));
    }

    @GetMapping("/name/{Nom}")
    public ResponseEntity<ProductDto> getProductByNom(@PathVariable("Nom") String Nom){
        return ResponseEntity.ok(productService.findByNom(Nom));
    }

    @GetMapping("/PrixSup/{Prix}")
    public ResponseEntity<List<ProductDto>> getProduitGreaterThan(@PathVariable("Prix") BigDecimal prix){
        return ResponseEntity.ok(productService.findByPrixGreaterThan(prix));
    }

    @GetMapping("/PrixInf/{Prix}")
    public ResponseEntity<List<ProductDto>> getProduitLessThan(@PathVariable("Prix") BigDecimal prix){
        return ResponseEntity.ok(productService.findByPrixLessThan(prix));
    }

    @PostMapping("")
    public ResponseEntity<ProductDto> save(@Valid @RequestBody() ProductDto productDto){
        ProductDto saved = productService.save(productDto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id){
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> update(@RequestBody() ProductDto productDto, @PathVariable("id") Integer id){
        ProductDto updated = productService.update(productDto, id);
        return ResponseEntity.accepted().body(updated);
    }
}
