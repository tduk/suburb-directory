package com.tarasduk.auspost.suburb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.tarasduk.auspost.suburb.exception.NotFoundException;
import com.tarasduk.auspost.suburb.model.Error;
import com.tarasduk.auspost.suburb.model.PostCodeResponse;
import com.tarasduk.auspost.suburb.model.Suburb;
import com.tarasduk.auspost.suburb.model.SuburbResponse;
import com.tarasduk.auspost.suburb.service.SuburbService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@Tag(name = "Suburb Directory")
public class SuburbController {
  @Autowired
  private SuburbService suburbService;

  @Operation(summary = "Get suburb information by postcode",
      responses = {
          @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = SuburbResponse.class)))})
  @GetMapping("/suburbs")
  public ResponseEntity<SuburbResponse> getSuburb(
      @RequestParam(value = "postcode") String postCode) {
    return ResponseEntity.ok(new SuburbResponse(suburbService.getSuburbsByPostcode(postCode)));
  }

  @Operation(summary = "Get postcode by suburb name",
      responses = {
          @ApiResponse(responseCode = "200",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(implementation = PostCodeResponse.class))),
          @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = Error.class)))})
  @GetMapping("/suburbs/{name}/postcode")
  public ResponseEntity<PostCodeResponse> getPostcode(@PathVariable(value = "name") String suburbName)
      throws NotFoundException {
    Suburb suburb = suburbService.getPostcodeBySuburbName(suburbName);

    if (suburb == null) {
      throw new NotFoundException("Suburb '"+suburbName+"' is not found");
    }

    return ResponseEntity.ok(new PostCodeResponse(suburb.getPostCode()));
  }

  @Operation(summary = "Creates new suburb",
      security = @SecurityRequirement(name = "basicAuth"),
      responses = {@ApiResponse(responseCode = "202"),
          @ApiResponse(responseCode = "500", content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = Error.class)))})
  @PostMapping("/admin/suburbs")
  public ResponseEntity<Object> create(@RequestBody Suburb suburb) {
    suburbService.createSuburb(suburb);

    return ResponseEntity.noContent().build();
  }
}
