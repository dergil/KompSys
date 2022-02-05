package com.kbe.kompsys;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.kbe.kompsys.domain.model.Car;

import java.io.IOException;
import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

public class CustomCarDeserializer extends StdDeserializer<Car> {
    public CustomCarDeserializer() {
        this(null);
    }

    public CustomCarDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Car deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        Car car = new Car();
        ObjectCodec codec = parser.getCodec();
        JsonNode node = codec.readTree(parser);
        car.setName(node.get("Name").asText());
        car.setPrice(node.get("Price").asInt());
        car.setMilesPerGallon(node.get("Miles_per_Gallon").asInt());
        car.setCylinders(node.get("Cylinders").asInt());
        car.setDisplacement(node.get("Displacement").asInt());
        car.setHorsepower(node.get("Horsepower").asInt());
        car.setWeightInPounds(node.get("Weight_in_lbs").asInt());
        car.setAcceleration(node.get("Acceleration").asInt());
        car.setYear((node.get("Year").asText()));
        car.setOrigin(node.get("Origin").asText());
        return car;
    }
}
