package com.kbe.kompsys.domain.mapper;

import com.kbe.kompsys.domain.dto.CarView;
import com.kbe.kompsys.domain.model.Car;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-28T11:59:28+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.13 (Oracle Corporation)"
)
@Component
public class CarViewMapperImpl implements CarViewMapper {

    @Override
    public CarView toCarView(Car car) {
        if ( car == null ) {
            return null;
        }

        CarView carView = new CarView();

        carView.setId( car.getId() );
        carView.setName( car.getName() );
        carView.setPrice( car.getPrice() );
        carView.setMilesPerGallon( car.getMilesPerGallon() );
        carView.setCylinders( car.getCylinders() );
        carView.setDisplacement( car.getDisplacement() );
        carView.setHorsepower( car.getHorsepower() );
        carView.setWeightInPounds( car.getWeightInPounds() );
        carView.setAcceleration( car.getAcceleration() );
        carView.setYear( car.getYear() );
        carView.setOrigin( car.getOrigin() );

        return carView;
    }
}
