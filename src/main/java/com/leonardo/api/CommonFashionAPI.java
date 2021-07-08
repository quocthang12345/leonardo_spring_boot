package com.leonardo.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leonardo.document.CommonFashionDocs;
import com.leonardo.document.dto.CommonFashionDTO;
import com.leonardo.service.IFashionService;

@RestController
@RequestMapping("/api")
public class CommonFashionAPI {
	
	@Autowired
	private IFashionService fashionService;
	
	@GetMapping(
			produces = {
					MediaType.APPLICATION_JSON_VALUE
			},
			path = {"/getCollectionFashion/{model}/{type}"}
	)
	public List<CommonFashionDocs> findValueItemFashion(@PathVariable(value = "model", required = true) String model,@PathVariable(value = "type", required = true) String type ){
		return fashionService.findByTypeAndModel(type,model);
	}
	
	@GetMapping(
			produces = {
					MediaType.APPLICATION_JSON_VALUE
			},
			path = {"/getCollectionFashion/{type}"}
	)
	public List<CommonFashionDocs> findListValueFashion(@PathVariable(value = "type", required = true) String type){
		return fashionService.findByType(type);
	}
	
	@PostMapping(
			produces = {
					MediaType.APPLICATION_JSON_VALUE
			},
			consumes = {
					MediaType.APPLICATION_JSON_VALUE
			},
			path = {"/postCommonFashion"}
	)
	public ResponseEntity<String> addValueInList(@RequestBody CommonFashionDTO fashions){
		CommonFashionDocs newFashion = fashionService.addItem(fashions);
		return ((newFashion != null) ? new ResponseEntity<String>(HttpStatus.ACCEPTED) : new ResponseEntity<String>(HttpStatus.BAD_REQUEST));
		
	}

	@PostMapping(
			produces = {
					MediaType.APPLICATION_JSON_VALUE
			},
			consumes = {
					MediaType.APPLICATION_JSON_VALUE
			},
			path = {"/postMultiCommonFashion"}
	)
	public ResponseEntity<String> addMultiValueInList(@RequestBody List<CommonFashionDTO> fashions){
		Boolean isAddAll = fashionService.addListItem(fashions);
		return (isAddAll ? new ResponseEntity<String>(HttpStatus.ACCEPTED) : new ResponseEntity<String>(HttpStatus.BAD_REQUEST));	
	}
	
	@PutMapping(
			produces = {
					MediaType.APPLICATION_JSON_VALUE
			},
			consumes = {
					MediaType.APPLICATION_JSON_VALUE
			},
			path = {"/updateCommonFashion"}
	)
	public ResponseEntity<String> updateValueInList(@RequestBody CommonFashionDTO fashions){
		CommonFashionDocs newFashion = fashionService.updateItem(fashions);
		return ((newFashion != null) ? new ResponseEntity<String>(HttpStatus.ACCEPTED) : new ResponseEntity<String>(HttpStatus.BAD_REQUEST));
	}
	
	@DeleteMapping(
			produces = {
					MediaType.APPLICATION_JSON_VALUE
			},
			consumes = {
					MediaType.APPLICATION_JSON_VALUE
			},
			path = {"/deleteCommonFashion/{anotherName}"}
	)
	public ResponseEntity<String> deleteValueInList(@PathVariable(value = "anotherName",required = true) String anotherName){
		Boolean isDeleted = fashionService.deleteByAnotherName(anotherName);
		return ((isDeleted) ? new ResponseEntity<String>(HttpStatus.ACCEPTED) : new ResponseEntity<String>(HttpStatus.BAD_REQUEST));
		
	}
}
