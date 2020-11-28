package com.tarasduk.auspost.suburb.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.tarasduk.auspost.suburb.exception.NotFoundException;
import com.tarasduk.auspost.suburb.model.PostCodeResponse;
import com.tarasduk.auspost.suburb.model.Suburb;
import com.tarasduk.auspost.suburb.model.SuburbResponse;
import com.tarasduk.auspost.suburb.service.SuburbService;

@ExtendWith(MockitoExtension.class)
class SuburbControllerTest {

  @Mock
  private SuburbService service;
  @InjectMocks
  private SuburbController controller;

  @Test
  void shouldReturnSuburbResponseAnd200() {
    when(service.getSuburbsByPostcode(eq("3000")))
        .thenReturn(List.of(new Suburb("1", "1"), new Suburb("2", "2")));

    ResponseEntity<SuburbResponse> response = controller.getSuburb("3000");

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(2, response.getBody().getSuburbs().size());
    verify(service).getSuburbsByPostcode("3000");
  }

  @Test
  void shouldReturnPostcodeAnd200() throws NotFoundException {
    when(service.getPostcodeBySuburbName("Melbourne")).thenReturn(new Suburb("Melbourne", "3000"));
    ResponseEntity<PostCodeResponse> response = controller.getPostcode("Melbourne");

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("3000", response.getBody().getPostCode());
    verify(service).getPostcodeBySuburbName("Melbourne");

  }

  @Test
  void shoudThrowResponseStatuException() {
    when(service.getPostcodeBySuburbName("Melbourne")).thenReturn(null);
    NotFoundException ex =
        assertThrows(NotFoundException.class, () -> controller.getPostcode("Melbourne"));
    assertEquals("Suburb 'Melbourne' is not found", ex.getMessage());
  }

  @Test
  void shouldCreateSuburb() {
    ResponseEntity<Object> response = controller.create(new Suburb());
    assertNotNull(response);
    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    verify(service).createSuburb(any());
  }
}
