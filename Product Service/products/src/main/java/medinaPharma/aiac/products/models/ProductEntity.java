package medinaPharma.aiac.products.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Data
@Table(name="products")
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(nullable = false)
    private String nom;
    @Column(nullable = false)
    private String categorie;
    @Column(nullable = false)
    private BigDecimal prix;
    @Column(nullable = false)
    private double quantite_stock;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String type;

    @Lob
    @Column(name = "image_produit", nullable = true)
    private byte[] image;
    public byte[] getImageProduit() { return image; }
    public void setImageProduit(byte[] imageProduit) { this.image = imageProduit; }

}
