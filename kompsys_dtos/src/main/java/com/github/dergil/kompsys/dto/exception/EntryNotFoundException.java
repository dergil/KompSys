package com.github.dergil.kompsys.dto.exception;

public class EntryNotFoundException extends RuntimeException {

  public EntryNotFoundException(Class<?> clazz, long id) {
    super(String.format("Entity %s with id %d not found", clazz.getSimpleName(), id));
  }

  public EntryNotFoundException(Class<?> clazz, String id) {
    super(String.format("Entity %s with id %s not found", clazz.getSimpleName(), id));
  }


}
