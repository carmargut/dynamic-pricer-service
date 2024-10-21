package com.retail.dynamic_pricer_service.infrastructure.apis.rest.mappers;

import com.retail.dynamic_pricer_service.domain.model.Price;
import com.retail.dynamic_pricer_service.infrastructure.apis.rest.model.GetPriceResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceMapper {

    GetPriceResponse toApi(Price price);

}