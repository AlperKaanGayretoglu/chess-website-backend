package com.alpergayretoglu.chess_website_backend.model.response;

import com.alpergayretoglu.chess_website_backend.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class EntityResponse {

    private String id;
    private ZonedDateTime created;
    private ZonedDateTime updated;

    protected static <R extends EntityResponse, E extends BaseEntity> R setCommonValuesFromEntity(R response, E entity) {
        response.setId(entity.getId());
        response.setCreated(entity.getCreated());
        response.setUpdated(entity.getUpdated());
        return response;
    }

}
