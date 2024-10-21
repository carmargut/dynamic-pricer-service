package com.retail.dynamic_pricer_service.apis.rest.mappers;

import com.retail.dynamic_pricer_service.apis.rest.model.GetPriceResponse;
import com.retail.dynamic_pricer_service.model.Price;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceMapper {

    GetPriceResponse toApi(Price price);

}