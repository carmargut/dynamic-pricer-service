package com.retail.dynamic_pricer_service.infrastructure.apis.rest.mappers;

import com.retail.dynamic_pricer_service.domain.model.PriceRequest;
import com.retail.dynamic_pricer_service.infrastructure.apis.rest.model.GetPriceRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceRequestMapper {

    PriceRequest toDomain(GetPriceRequest request);

}