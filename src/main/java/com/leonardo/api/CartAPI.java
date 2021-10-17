package com.leonardo.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leonardo.document.CommonFashionDocs;
import com.leonardo.service.ICartService;

@RestController
@RequestMapping("/api")
public class CartAPI {
	
	@Autowired
	private ICartService cartService;
	
	@GetMapping(
			produces = {
					MediaType.APPLICATION_JSON_VALUE
			},
			path = {"/getCarts/{anotherName}","/getCarts"}
	)
	public List<CommonFashionDocs> findListValue(@PathVariable(value = "anotherName", required = false) String anotherName){
		List<CommonFashionDocs> result = cartService.findValueCart(anotherName);
		return result;
	}
	
	@PostMapping(
			produces = {
					MediaType.APPLICATION_JSON_VALUE
			},
			path = {"/postCarts/{anotherName}"}	
	)
	public ResponseEntity<String> InsertValue(@PathVariable(required = true, value = "anotherName")String anotherName){
			Boolean isInsert = cartService.addItemInCart(anotherName);
			return isInsert ?  new ResponseEntity<String>(HttpStatus.ACCEPTED) : new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping(
			produces = {
					MediaType.APPLICATION_JSON_VALUE
			},
			path = {"/deleteCarts/{anotherName}","/deleteCarts"}	
	)
	public ResponseEntity<String> DeleteValue(@PathVariable(required = true, value = "anotherName") String anotherName){
		Boolean isDeleted = cartService.deleteItemCart(anotherName);
		return isDeleted ?  new ResponseEntity<String>(HttpStatus.ACCEPTED) : new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
	}
}
