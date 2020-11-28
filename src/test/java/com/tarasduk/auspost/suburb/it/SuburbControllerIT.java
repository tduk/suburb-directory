package com.tarasduk.auspost.suburb.it;



import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.tarasduk.auspost.suburb.controller.SuburbController;
import com.tarasduk.auspost.suburb.model.Suburb;
import com.tarasduk.auspost.suburb.service.SuburbService;


@WebMvcTest(SuburbController.class)
public class SuburbControllerIT {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private SuburbService service;

  @Test
  public void shouldReturnSuburbs() throws Exception {
    List<Suburb> suburbs = List.of(
        new Suburb("Melbourne", "3000"), 
        new Suburb("PointCook", "3030")
    );

    when(service.getSuburbsByPostcode(anyString())).thenReturn(suburbs);

    mvc.perform(get("/api/suburbs?postcode=3000").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.suburbs", hasSize(2)))
            .andExpect(jsonPath("$.suburbs[0].name", is("Melbourne")));
  }

  @Test
  public void shouldReturnPostcode() throws Exception {
    when(service.getPostcodeBySuburbName(anyString())).thenReturn(new Suburb("Melbourne", "3000"));

    mvc.perform(get("/api/suburbs/Melbourne/postcode").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.postCode", is("3000")));
  }

}
