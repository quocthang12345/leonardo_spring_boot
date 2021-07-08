package com.leonardo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leonardo.convert.CommonFashionConvert;
import com.leonardo.document.CommonFashionDocs;
import com.leonardo.document.dto.CommonFashionDTO;
import com.leonardo.repository.FashionRepository;
import com.leonardo.service.IFashionService;

@Service
public class FashionService implements IFashionService {
	
	@Autowired
	private FashionRepository fashionRepo;
	
	@Autowired
	private CommonFashionConvert fashionConvert;

	@Override
	public List<CommonFashionDocs> findAllFashion() {
		return fashionRepo.findAll();
	}

	@Override
	public CommonFashionDocs addItem(CommonFashionDTO fashionDto) {
		CommonFashionDocs newFashion = fashionConvert.toDocs(fashionDto);
		return Optional.ofNullable(newFashion).map(item -> fashionRepo.save(item)).orElse(null);
	}

	@Override
	public CommonFashionDocs updateItem(CommonFashionDTO fashionDto) {
		CommonFashionDocs newFashion = fashionConvert.toDocs(fashionDto);
		return Optional.ofNullable(newFashion).map(item -> fashionRepo.save(item)).orElse(null);
		
	}


	@Override
	public List<CommonFashionDocs> findByTypeAndModel(String type, String model) {
		return Optional.ofNullable(fashionRepo.findByTypeAndModel(type, model)).orElse(null);
	}

	@Override
	public Boolean deleteById(String[] ids) {
		return Optional.ofNullable(ids).map(listId -> {  
			for(String idDelete : listId) {
				fashionRepo.deleteById(idDelete);
			}
			return true;
		}).orElse(false);
	}

	@Override
	public List<CommonFashionDocs> findByType(String type) {
		return Optional.ofNullable(type).map(value -> fashionRepo.findByType(value)).orElse(null);
	}

	@Override
	public Boolean deleteByAnotherName(String anotherName) {
		return Optional.ofNullable(anotherName).map(value -> {
			return fashionRepo.deleteByAnotherName(value);
			}).orElse(false);
	}

	@Override
	public Boolean addListItem(List<CommonFashionDTO> listFashionsDto) {
		List<CommonFashionDocs> listResult = new ArrayList<CommonFashionDocs>();
		for(CommonFashionDTO fashionConverter : listFashionsDto) {
			listResult.add(fashionConvert.toDocs(fashionConverter));
		}
		return Optional.ofNullable(listResult).map(item ->{
			fashionRepo.saveAll(item);
			return true;
		}).orElse(false);
	}
}




