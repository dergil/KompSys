package com.kbe.kompsys.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

@Entity
@Data
@EqualsAndHashCode
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private int price;
    private double milesPerGallon;
    private int cylinders;
    private int displacement;
    private int horsepower;
    private int weightInPounds;
    private double acceleration;
    private LocalDate year;
    private String origin;
    private String hash;

    //https://www.baeldung.com/jpa-entity-lifecycle-events
    @PostPersist
    @PostUpdate
    @PostRemove
    private void afterChangeInDatabase() throws IOException {
        try {
            File myObj = new File("update_necessary.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());

                try {
                    FileWriter myWriter = new FileWriter("update_necessary.txt");
                    myWriter.write("1");
                    myWriter.close();
                    System.out.println("Successfully wrote to the file.");
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }

            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        // todo fire Job to message queue updating csv in 5minutes

    }
}



