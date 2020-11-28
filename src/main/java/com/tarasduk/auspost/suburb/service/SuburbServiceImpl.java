package com.tarasduk.auspost.suburb.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tarasduk.auspost.suburb.model.Suburb;
import com.tarasduk.auspost.suburb.repository.SuburbRepository;

@Service
public class SuburbServiceImpl implements SuburbService {
  @Autowired
  private SuburbRepository repository;

  @Override
  public List<Suburb> getSuburbsByPostcode(String postCode) {
    return repository.getSuburbsByPostCode(postCode);
  }

  @Override
  public Suburb getPostcodeBySuburbName(String name) {
    return repository.getPostCodeByName(name);

  }

  @Override
  public void createSuburb(Suburb suburb) {
    Suburb existingSuburb = repository.findById(suburb.getName()).orElse(null);
    
    if (existingSuburb != null) {
      throw new IllegalArgumentException("Suburb '" + suburb.getName() + "' already exists");
    }

    repository.save(suburb);
  }
}
