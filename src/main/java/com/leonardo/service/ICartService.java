package com.leonardo.service;

import java.util.List;

import com.leonardo.document.CartDocs;
import com.leonardo.document.CommonFashionDocs;

public interface ICartService {
	
	List<CommonFashionDocs> findValueCart(String anotherName);

	boolean addItemInCart(String anotherName);

	boolean deleteItemCart(String anotherName);

}
