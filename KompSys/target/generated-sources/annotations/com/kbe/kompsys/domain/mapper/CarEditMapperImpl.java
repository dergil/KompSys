package com.kbe.kompsys.domain.mapper;

import com.kbe.kompsys.domain.dto.EditCarRequest;
import com.kbe.kompsys.domain.model.Car;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-18T22:22:13+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.1 (N/A)"
)
@Component
public class CarEditMapperImpl implements CarEditMapper {

    @Override
    public Car create(EditCarRequest request) {
        if ( request == null ) {
            return null;
        }

        Car car = new Car();

        return car;
    }
}
