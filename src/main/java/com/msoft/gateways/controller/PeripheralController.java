package com.msoft.gateways.controller;

import java.util.List;

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

import com.msoft.gateways.dto.GenericResponseDTO;
import com.msoft.gateways.dto.PeripheralDTO;
import com.msoft.gateways.exception.MessageResponseError;
import com.msoft.gateways.model.Peripheral;
import com.msoft.gateways.service.PeripheralService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/v1/peripheral")
@RestController
@RequiredArgsConstructor
public class PeripheralController {
    
    private final PeripheralService peripheralService;
    
    @Operation(summary = "Get peripheral by Id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Peripheral found", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = PeripheralDTO.class)) }),
			@ApiResponse(responseCode = "400", description = "Id of peripheral not supplied", content = @Content),
			@ApiResponse(responseCode = "404", description = "Peripheral not found", content = {
					@Content(mediaType = "application/json",
							schema = @Schema(implementation = MessageResponseError.class)) })
	})
	@GetMapping("/{id}")
	public ResponseEntity<PeripheralDTO> peripheralById(@PathVariable Long id) {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(peripheralService.findPeripheralById(id));
	}

	@Operation(summary = "Get a list of all peripherals")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "List of peripherals", content = {
					@Content(mediaType = "application/json",
							array = @ArraySchema(schema = @Schema(implementation = PeripheralDTO.class))) })
			
	})
	@GetMapping
	public ResponseEntity<List<PeripheralDTO>> listPeripheral() {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(peripheralService.findAllPeripherals());
	}

	@Operation(summary = "Add peripheral")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Peripheral added", content = {
					@Content(mediaType = "application/json",
							schema = @Schema(implementation = GenericResponseDTO.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid fields", content = @Content)
	})
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public ResponseEntity<GenericResponseDTO> addPeripheral(@RequestBody Peripheral peripheral) {
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new GenericResponseDTO(
						"Peripheral added successfully",
						peripheralService.addPeripheral(peripheral), 201));
	}

	@Operation(summary = "Update peripheral by id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Peripheral updated", content = {
					@Content(mediaType = "application/json",
							schema = @Schema(implementation = GenericResponseDTO.class)) }),
			@ApiResponse(responseCode = "400", description = "Id of peripheral not supplied", content = @Content),
			@ApiResponse(responseCode = "404",
					description = "Peripheral not found", content = {
					@Content(mediaType = "application/json",
							schema = @Schema(implementation = MessageResponseError.class)) })
	})
	@PutMapping("/{id}")
	public ResponseEntity<GenericResponseDTO> setPeripheral(@PathVariable Long id,
			@RequestBody Peripheral peripheral) {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(new GenericResponseDTO(
						"Peripheral updated successfully",
						peripheralService.updatePeripheral(id, peripheral), 200));
	}

	@Operation(summary = "Delete peripheral by Id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Delete peripheral successfully", content = {
					@Content(mediaType = "application/json",
							schema = @Schema(implementation = GenericResponseDTO.class)) }),
			@ApiResponse(responseCode = "400", description = "Peripheral id not supplied", content = @Content),
			@ApiResponse(responseCode = "404", description = "Peripheral not found", content = {
					@Content(mediaType = "application/json",
							schema = @Schema(implementation = MessageResponseError.class)) })
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<GenericResponseDTO> deletePeripheral(@PathVariable Long id) {
		peripheralService.deletePeripheral(id);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(new GenericResponseDTO(
						"Peripheral deleted successfully",
						id, 200));
	}

}
