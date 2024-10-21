package com.retail.dynamic_pricer_service.infrastructure.apis.rest.mappers;

import com.retail.dynamic_pricer_service.domain.model.Price;
import com.retail.dynamic_pricer_service.infrastructure.apis.rest.model.GetPriceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PriceMapper {

    PriceMapper INSTANCE = Mappers.getMapper(PriceMapper.class);

    GetPriceResponse toApi(Price price);

}