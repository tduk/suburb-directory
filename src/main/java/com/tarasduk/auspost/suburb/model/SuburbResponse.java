package com.tarasduk.auspost.suburb.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SuburbResponse {
  private List<Suburb> suburbs;
}
