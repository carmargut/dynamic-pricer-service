package com.retail.dynamic_pricer_service.apis.rest.mappers;

import com.retail.dynamic_pricer_service.apis.rest.model.GetPriceRequest;
import com.retail.dynamic_pricer_service.model.PriceRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceRequestMapper {

    PriceRequest toDomain(GetPriceRequest request);

}