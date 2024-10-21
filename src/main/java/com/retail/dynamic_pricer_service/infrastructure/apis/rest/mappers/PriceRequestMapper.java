package com.retail.dynamic_pricer_service.infrastructure.apis.rest.mappers;

import com.retail.dynamic_pricer_service.domain.model.PriceRequest;
import com.retail.dynamic_pricer_service.infrastructure.apis.rest.model.GetPriceRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PriceRequestMapper {

    PriceRequestMapper INSTANCE = Mappers.getMapper(PriceRequestMapper.class);

    PriceRequest toDomain(GetPriceRequest request);

}