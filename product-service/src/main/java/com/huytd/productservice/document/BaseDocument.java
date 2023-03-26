package com.huytd.productservice.document;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class BaseDocument {
    @Id
    private String id;

    @Field(name = "is_deleted")
    @Builder.Default
    private Boolean isDeleted = Boolean.FALSE;

    @Field(name = "created_at", targetType = FieldType.INT64)
    @Builder.Default
    private Long createdAt = System.currentTimeMillis();

    @Field(name = "updated_at", targetType = FieldType.INT64)
    @Builder.Default
    private Long updatedAt = System.currentTimeMillis();
}
