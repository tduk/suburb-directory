package com.tarasduk.auspost.suburb.service;

import java.util.List;
import com.tarasduk.auspost.suburb.model.Suburb;

public interface SuburbService {
  List<Suburb> getSuburbsByPostcode(String postCode);

  Suburb getPostcodeBySuburbName(String name);
  
  void createSuburb(Suburb suburb);
}
