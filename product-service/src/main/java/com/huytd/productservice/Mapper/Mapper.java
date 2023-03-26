package com.huytd.productservice.Mapper;


public interface Mapper<T, S, V> {
     T toDto(S document);

     S toDocument(V request);
}
