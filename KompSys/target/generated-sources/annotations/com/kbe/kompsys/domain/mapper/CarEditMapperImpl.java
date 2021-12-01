package com.kbe.kompsys.domain.mapper;

import com.kbe.kompsys.domain.dto.EditCarRequest;
import com.kbe.kompsys.domain.model.Car;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-12-01T19:57:35+0100",
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

        car.setName( request.getName() );
        if ( request.getPrice() != null ) {
            car.setPrice( request.getPrice().intValue() );
        }
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

    @Override
    public void update(EditCarRequest request, Car car) {
        if ( request == null ) {
            return;
        }

        if ( request.getName() != null ) {
            car.setName( request.getName() );
        }
        if ( request.getPrice() != null ) {
            car.setPrice( request.getPrice().intValue() );
        }
        car.setMilesPerGallon( (int) request.getMilesPerGallon() );
        car.setCylinders( request.getCylinders() );
        car.setDisplacement( request.getDisplacement() );
        car.setHorsepower( request.getHorsepower() );
        car.setWeightInPounds( request.getWeightInPounds() );
        car.setAcceleration( (int) request.getAcceleration() );
        if ( request.getYear() != null ) {
            car.setYear( LocalDateTime.ofInstant( request.getYear().toInstant(), ZoneOffset.UTC ).toLocalDate() );
        }
        if ( request.getOrigin() != null ) {
            car.setOrigin( request.getOrigin() );
        }
    }
}
