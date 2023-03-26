package com.huytd.productservice.document;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;

@Document(collection = "product")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class Product extends BaseDocument {
    @Field(name = "name")
    private String name;

    @Field(name = "description")
    private String description;

    @Field(name = "price", targetType = FieldType.DECIMAL128)
    private BigDecimal price;

    @Field(name = "image")
    private String image;

    @Field(name = "quantity_in_inventory")
    @Builder.Default
    private Long quantityInInventory = 0L;

    @Field(name = "category_id")
    private String categoryId;
}
