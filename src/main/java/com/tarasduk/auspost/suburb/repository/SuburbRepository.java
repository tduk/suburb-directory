package com.tarasduk.auspost.suburb.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.tarasduk.auspost.suburb.model.Suburb;

@Repository
public interface SuburbRepository extends CrudRepository<Suburb, String> {
  List<Suburb> getSuburbsByPostCode(String postCode);
  Suburb getPostCodeByName(String name);
}
