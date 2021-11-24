package com.kbe.kompsys.domain.mapper;

import com.kbe.kompsys.domain.dto.TaxResponse;
import com.kbe.kompsys.domain.model.Car;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-18T22:22:13+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.1 (N/A)"
)
@Component
public class CarTaxResponseMapperImpl implements CarTaxResponseMapper {

    @Override
    public TaxResponse create(Car car) {
        if ( car == null ) {
            return null;
        }

        TaxResponse taxResponse = new TaxResponse();

        return taxResponse;
    }
}
