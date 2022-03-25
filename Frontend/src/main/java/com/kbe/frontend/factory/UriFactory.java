package com.kbe.frontend.factory;


import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;

import java.util.List;

public class UriFactory {

  private static final String HOST = "localhost:8082";

  private UriFactory() {
  }

  public static URIBuilder buildUri(String path, List<NameValuePair> parameters) {

    URIBuilder builder = new URIBuilder()
            .setScheme("http")
            .setHost(HOST)
            .setPath(path);
    if (parameters != null) {
      builder.addParameters(parameters);
    }
    return builder;
  }

}
