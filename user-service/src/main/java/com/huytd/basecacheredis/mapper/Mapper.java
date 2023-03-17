package com.huytd.basecacheredis.mapper;


public interface Mapper<T, S> {
     T toDto(S entity);
}
