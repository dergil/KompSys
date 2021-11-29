package com.kbe.kompsys.domain.mapper;

import com.kbe.kompsys.domain.dto.EditCarRequest;
import com.kbe.kompsys.domain.model.Car;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-28T11:59:28+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.13 (Oracle Corporation)"
)
@Component
public class CarEditMapperImpl implements CarEditMapper {

    @Override
    public Car create(EditCarRequest request) {
        if ( request == null ) {
            return null;
        }

        Car car = new Car();

        car.setId( request.getId() );
        car.setName( request.getName() );
        car.setPrice( (int) request.getPrice() );
        car.setMilesPerGallon( (int) request.getMilesPerGallon() );
        car.setCylinders( request.getCylinders() );
        car.setDisplacement( request.getDisplacement() );
        car.setHorsepower( request.getHorsepower() );
        car.setWeightInPounds( request.getWeightInPounds() );
        car.setAcceleration( (int) request.getAcceleration() );
        if ( request.getYear() != null ) {
            car.setYear( LocalDateTime.ofInstant( request.getYear().toInstant(), ZoneOffset.UTC ).toLocalDate() );
        }
        car.setOrigin( request.getOrigin() );

        return car;
    }
}
