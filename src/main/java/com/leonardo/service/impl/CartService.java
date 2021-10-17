package com.leonardo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leonardo.document.CartDocs;
import com.leonardo.document.CommonFashionDocs;
import com.leonardo.repository.CartRepository;
import com.leonardo.repository.FashionRepository;
import com.leonardo.service.ICartService;

@Service
public class CartService implements ICartService {

	@Autowired
	private CartRepository cartRepo;
	
	@Autowired
	private FashionRepository fasionRepo;
	
	
	@Override
	public List<CommonFashionDocs> findValueCart(String anotherName) {
		List<CommonFashionDocs> listItem = new ArrayList<CommonFashionDocs>();
		List<CartDocs> listItemInCart = Optional.ofNullable(anotherName).map(value -> cartRepo.findByAnotherName(anotherName)).orElse(cartRepo.findAll());
		for(CartDocs itemInCart : listItemInCart) {
			listItem.add(fasionRepo.findOneByAnotherName(itemInCart.getAnotherName()));
		}
		return listItem;
	}

	@Override
	@Transactional
	public boolean addItemInCart(String anotherName){
		CommonFashionDocs fashion = fasionRepo.findOneByAnotherName(anotherName);
		CartDocs cart = new CartDocs(fashion.getAnotherName(),1,fashion.getType());
		return Optional.ofNullable(cart).map(item -> {
			cartRepo.insert(item);
			return true;
		}).orElse(false);
	} 

	@Override
	@Transactional
	public boolean deleteItemCart(String anotherName) {
		List<CartDocs> cart = cartRepo.findByAnotherName(anotherName);	
		return Optional.ofNullable(cart).map(listItem ->{ 
				for(CartDocs cartDelete : listItem)	{
					cartRepo.delete(cartDelete);
				}
				return true;
			}).orElse(false);
	}


}
