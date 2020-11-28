package com.tarasduk.auspost.suburb.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.tarasduk.auspost.suburb.model.Suburb;
import com.tarasduk.auspost.suburb.repository.SuburbRepository;

@ExtendWith(MockitoExtension.class)
class SuburbServiceImplTest {
  @Mock
  private SuburbRepository repository;

  @InjectMocks
  private SuburbServiceImpl service;

  @Test
  void shouldGetSuburb() {
    List<Suburb> suburbs = List.of(new Suburb("1", "1"));
    when(repository.getSuburbsByPostCode(anyString())).thenReturn(suburbs);

    List<Suburb> response = service.getSuburbsByPostcode("3000");
    assertEquals(suburbs, response);
    verify(repository).getSuburbsByPostCode("3000");
  }

  @Test
  void shouldGetPostcode() {
    when(repository.getPostCodeByName(anyString())).thenReturn(new Suburb("Melbourne", "3000"));

    Suburb response = service.getPostcodeBySuburbName("Melbourne");

    assertEquals("3000", response.getPostCode());
    verify(repository).getPostCodeByName("Melbourne");
  }

  @Test
  void shouldCreateSuburb() {
    when(repository.findById(anyString())).thenReturn(Optional.ofNullable(null));
    Suburb suburb = new Suburb("Melbourne", "3000");
    service.createSuburb(suburb);
    verify(repository).save(suburb);
    verify(repository).findById("Melbourne");
  }


  @Test
  void shouldThrowIllegalArgumentException() {
    Suburb suburb = new Suburb("Melbourne", "3000");

    when(repository.findById(anyString())).thenReturn(Optional.of(suburb));

    IllegalArgumentException ex =
        assertThrows(IllegalArgumentException.class, () -> service.createSuburb(suburb));
    
    assertEquals("Suburb 'Melbourne' already exists", ex.getMessage());
  }

}
