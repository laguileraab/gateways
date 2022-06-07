package com.msoft.gateways.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.msoft.gateways.dto.GatewayDTO;
import com.msoft.gateways.dto.GenericResponseDTO;
import com.msoft.gateways.dto.PeripheralDTO;
import com.msoft.gateways.exception.MessageResponseError;
import com.msoft.gateways.model.Gateway;
import com.msoft.gateways.model.Peripheral;
import com.msoft.gateways.service.GatewayService;
import com.msoft.gateways.service.PeripheralService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/v1/gateway")
@RestController
@RequiredArgsConstructor
public class GatewayController {
    
    private final GatewayService gatewayService;
    
    @Operation(summary = "Get gateway by Id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Gateway found", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = GatewayDTO.class)) }),
			@ApiResponse(responseCode = "400", description = "Id of gateway not supplied", content = @Content),
			@ApiResponse(responseCode = "404", description = "Gateway not found", content = {
					@Content(mediaType = "application/json",
							schema = @Schema(implementation = MessageResponseError.class)) })
	})
	@GetMapping("/{id}")
	public ResponseEntity<GatewayDTO> gatewayById(@Valid @PathVariable String id) {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(gatewayService.findGatewayById(id));
	}

	@Operation(summary = "Get a list of all gateways")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "List of gateways", content = {
					@Content(mediaType = "application/json",
							array = @ArraySchema(schema = @Schema(implementation = GatewayDTO.class))) })
			
	})
	@GetMapping
	public ResponseEntity<List<GatewayDTO>> listGateway() {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(gatewayService.findAllGateways());
	}

	@Operation(summary = "Add gateway")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Gateway added", content = {
					@Content(mediaType = "application/json",
							schema = @Schema(implementation = GenericResponseDTO.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid fields", content = @Content)
	})
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public ResponseEntity<GenericResponseDTO> addGateway(@Valid @RequestBody Gateway gateway) {
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new GenericResponseDTO(
						"Gateway added successfully",
						gatewayService.addGateway(gateway), 201));
	}

	@Operation(summary = "Update gateway by id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Gateway updated", content = {
					@Content(mediaType = "application/json",
							schema = @Schema(implementation = GenericResponseDTO.class)) }),
			@ApiResponse(responseCode = "400", description = "Id of gateway not supplied", content = @Content),
			@ApiResponse(responseCode = "404",
					description = "Gateway not found", content = {
					@Content(mediaType = "application/json",
							schema = @Schema(implementation = MessageResponseError.class)) })
	})
	@PutMapping("/{id}")
	public ResponseEntity<GenericResponseDTO> setGateway(@PathVariable String id,@Valid
			@RequestBody Gateway gateway) {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(new GenericResponseDTO(
						"Gateway updated successfully",
						gatewayService.updateGateway(id, gateway), 200));
	}

	@Operation(summary = "Delete gateway by Id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Delete gateway successfully", content = {
					@Content(mediaType = "application/json",
							schema = @Schema(implementation = GenericResponseDTO.class)) }),
			@ApiResponse(responseCode = "400", description = "Gateway id not supplied", content = @Content),
			@ApiResponse(responseCode = "404", description = "Gateway not found", content = {
					@Content(mediaType = "application/json",
							schema = @Schema(implementation = MessageResponseError.class)) })
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<GenericResponseDTO> deleteGateway(@PathVariable String id) {
		gatewayService.deleteGateway(id);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(new GenericResponseDTO(
						"Gateway deleted successfully",
						id, 200));
	}
}
