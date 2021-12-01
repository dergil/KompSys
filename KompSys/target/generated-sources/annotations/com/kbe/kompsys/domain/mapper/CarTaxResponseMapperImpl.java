package com.kbe.kompsys.domain.mapper;

import com.kbe.kompsys.domain.dto.TaxResponse;
import com.kbe.kompsys.domain.model.Car;
import java.time.ZoneOffset;
import java.util.Date;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-12-01T19:57:35+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.13 (Oracle Corporation)"
)
@Component
public class CarTaxResponseMapperImpl implements CarTaxResponseMapper {

    @Override
    public TaxResponse create(Car car) {
        if ( car == null ) {
            return null;
        }

        TaxResponse taxResponse = new TaxResponse();

        taxResponse.setId( car.getId() );
        taxResponse.setName( car.getName() );
        taxResponse.setPrice( car.getPrice() );
        taxResponse.setMilesPerGallon( car.getMilesPerGallon() );
        taxResponse.setCylinders( car.getCylinders() );
        taxResponse.setDisplacement( car.getDisplacement() );
        taxResponse.setHorsepower( car.getHorsepower() );
        taxResponse.setWeightInPounds( car.getWeightInPounds() );
        taxResponse.setAcceleration( car.getAcceleration() );
        if ( car.getYear() != null ) {
            taxResponse.setYear( Date.from( car.getYear().atStartOfDay( ZoneOffset.UTC ).toInstant() ) );
        }
        taxResponse.setOrigin( car.getOrigin() );

        return taxResponse;
    }
}
