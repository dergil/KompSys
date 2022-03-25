package com.kbe.frontend.controller;

import com.github.dergil.kompsys.dto.car.*;
import com.github.dergil.kompsys.dto.car.tax.CarTaxCalculateView;
import com.github.dergil.kompsys.dto.car.tax.CarTaxRequest;
import com.kbe.frontend.factory.UriFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Controller
@RequestMapping("/car")
public class CarController {
  private final RestTemplate restTemplate = new RestTemplate();

  @GetMapping
  public String get_manage_view(Model model) {
    URIBuilder builder = UriFactory.buildUri("/api/v1/car/all", null);
    List<CarView> carViewList = Objects.requireNonNull(restTemplate.getForObject(builder.toString(), CarViewList.class)).getCarViews();

    assert carViewList != null;
    Map<Long, String> carMap = new HashMap<>();
    for (CarView carView :
            carViewList) {
      carMap.put(carView.getId(), carView.getName());
    }
    model.addAttribute("carMap", carMap);
    return "manage_car";
  }

  @PostMapping(params = {"name"})
  public String create(@RequestParam(value = "name") String name,
                       @RequestParam(value = "price", required = false) Double price,
                       @RequestParam(value = "milesPerGallon", required = false) float milesPerGallon,
                       @RequestParam(value = "cylinders", required = false) int cylinders,
                       @RequestParam(value = "displacement", required = false) int displacement,
                       @RequestParam(value = "horsepower", required = false) int horsepower,
                       @RequestParam(value = "weightInPounds", required = false) int weightInPounds,
                       @RequestParam(value = "acceleration", required = false) float acceleration,
                       @RequestParam(value = "year", required = false) String year,
                       @RequestParam(value = "origin", required = false) String origin,
                       Model model) {
    URIBuilder builder = UriFactory.buildUri("/api/v1/car", null);
    log.info("create");
    LocalDate yearLocalDate = LocalDate.parse(year);
    CreateCarRequest createCarRequest = new CreateCarRequest(name, price, milesPerGallon, cylinders, displacement, horsepower, weightInPounds, acceleration, yearLocalDate, origin);
    HttpEntity<CreateCarRequest> r = new HttpEntity<>(createCarRequest);

    ResponseEntity<CarView> foo = restTemplate.exchange(builder.toString(), HttpMethod.POST, r, CarView.class);

    model.addAttribute("carViewList", foo.getBody());

    return "car_get_all";
  }

  @GetMapping(params = {"id"})
  public String get(@RequestParam long id, Model model) {
    URIBuilder builder = UriFactory.buildUri("/api/v1/car", null);
    CarView carView = restTemplate.getForObject(builder.addParameter("id", String.valueOf(id)).toString(), CarView.class);

    model.addAttribute("carViewList", carView);
    model.addAttribute("response", "Fetched");
    return "car_get_all";
  }

  @GetMapping("/all")
  public String getAll(Model model) {
    URIBuilder builder = UriFactory.buildUri("/api/v1/car/all", null);
    List<CarView> carViewList = Objects.requireNonNull(restTemplate.getForObject(builder.toString(), CarViewList.class)).getCarViews();

    assert carViewList != null;
    model.addAttribute("carViewList", carViewList);
    model.addAttribute("response", "Fetched");
    return "car_get_all";
  }

  @PostMapping(params = {"id", "name", "price", "milesPerGallon", "cylinders", "displacement", "horsepower", "weightInPounds", "acceleration", "year", "origin"})
  public String update(@RequestParam(value = "id") long id,
                       @RequestParam(value = "name", required = false) String name,
                       @RequestParam(value = "price", required = false) Double price,
                       @RequestParam(value = "milesPerGallon", required = false) float milesPerGallon,
                       @RequestParam(value = "cylinders", required = false) int cylinders,
                       @RequestParam(value = "displacement", required = false) int displacement,
                       @RequestParam(value = "horsepower", required = false) int horsepower,
                       @RequestParam(value = "weightInPounds", required = false) int weightInPounds,
                       @RequestParam(value = "acceleration", required = false) float acceleration,
                       @RequestParam(value = "LocalDate", required = false) LocalDate year,
                       @RequestParam(value = "String", required = false) String origin,
                       Model model) {

    URIBuilder builder = UriFactory.buildUri("/api/v1/car", null);

    EditCarRequest editCarRequest = new EditCarRequest(id, name, price, milesPerGallon, cylinders, displacement, horsepower, weightInPounds, acceleration, year, origin);
    HttpEntity<EditCarRequest> r = new HttpEntity<>(editCarRequest);
    ResponseEntity<CarView> foo = restTemplate.exchange(builder.toString(), HttpMethod.PUT, r, CarView.class);

    model.addAttribute("taxViewList", foo.getBody());
    model.addAttribute("response", "Updated");
    return "car_get_all";
  }

  @PostMapping(params = {"idToDelete"})
  public String delete(@RequestParam long idToDelete, Model model) {
    DeleteCarRequest deleteCarRequest = new DeleteCarRequest(idToDelete);
    URIBuilder builder = UriFactory.buildUri("/api/v1/car", null);
    HttpEntity<DeleteCarRequest> r = new HttpEntity<>(deleteCarRequest);

    ResponseEntity<CarView> foo = restTemplate.exchange(builder.toString(), HttpMethod.DELETE, r, CarView.class);

    model.addAttribute("carViewList", foo.getBody());
    model.addAttribute("response", "Deleted");
    return "car_get_all";
  }

  @GetMapping(params = {"calculateId"}, value = "/tax")
  public String tax(@RequestParam @Valid Long calculateId, HttpServletRequest httpRequest, Model model) {
    CarTaxRequest carTaxRequest = new CarTaxRequest();
    carTaxRequest.setId(calculateId);
    String ip = Objects.requireNonNull(httpRequest.getRemoteAddr());
    carTaxRequest.setIpAddress(ip);
    log.info(ip);

    URIBuilder builderCalc = UriFactory.buildUri("/api/v1/car/tax", null);
    CarTaxCalculateView carTaxCalculateView = restTemplate.getForObject(builderCalc.addParameter("id", String.valueOf(calculateId)).toString(), CarTaxCalculateView.class);

    model.addAttribute("carTaxCalculateView", carTaxCalculateView);
    model.addAttribute("response", "Calculated...");
    return "calculated_car_tax";
  }
}
