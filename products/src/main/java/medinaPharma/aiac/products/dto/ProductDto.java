package medinaPharma.aiac.products.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Integer id;

    @NotBlank(message = "la categorie est obligatoire !")
    private String categorie;

    @NotBlank(message = "le nom est obligatoire !")
    private String nom;

    @NotNull(message = "le prix est obligatoire !")
    private BigDecimal prix;

    @NotNull(message = "la quantité du stock est obligatoire !")
    private double quantite_stock;

    @NotBlank(message = "la description est obligatoire !")
    private String description;

    @NotBlank(message = "le type est obligatoire !")
    private String type;

}
