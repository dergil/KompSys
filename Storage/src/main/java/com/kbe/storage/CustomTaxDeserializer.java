package com.kbe.storage;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.kbe.storage.domain.model.Tax;

import java.io.IOException;

public class CustomTaxDeserializer extends StdDeserializer<Tax> {
  public CustomTaxDeserializer() {
    this(null);
  }

  public CustomTaxDeserializer(Class<?> vc) {
    super(vc);
  }

  @Override
  public Tax deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
    Tax taxView = new Tax();
    ObjectCodec codec = parser.getCodec();
    JsonNode node = codec.readTree(parser);
    taxView.setCountryCodeID(node.get("Country").textValue());
    taxView.setTax(node.get("rate").asDouble());

    return taxView;
  }
}
