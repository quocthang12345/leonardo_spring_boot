package com.leonardo.service;

import java.util.List;

import com.leonardo.document.CartDocs;

public interface ICartService {
	
	List<CartDocs> findValueCart(String anotherName);

	boolean addItemInCart(String anotherName);

	boolean deleteItemCart(String anotherName);

}
