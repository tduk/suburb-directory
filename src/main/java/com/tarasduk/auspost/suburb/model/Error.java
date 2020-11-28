package com.tarasduk.auspost.suburb.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Error {
  private String code;
  private String message;
}
