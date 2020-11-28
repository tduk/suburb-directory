package com.tarasduk.auspost.suburb.tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.tarasduk.auspost.suburb.model.Suburb;
import com.tarasduk.auspost.suburb.repository.SuburbRepository;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class InitialDataSeed implements CommandLineRunner {
  @Autowired
  private SuburbRepository repository;

  @Override
  public void run(String... args) throws Exception {
    if (repository.count() == 0) {
      log.debug("Database is empty, seeding data.");
      seedData();
    } else {
      log.debug("Database is not empty, skip seeding data.");
    }
  }

  private void seedData() {
    repository.save(new Suburb("Melbourne", "3000"));
    repository.save(new Suburb("Sydney", "2000"));
    repository.save(new Suburb("Dandenong", "3001"));
    repository.save(new Suburb("St Kilda", "3002"));
    repository.save(new Suburb("BlackRock", "3002"));
  }

}
