package com.leonardo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
	public List<CommonFashionDocs> findAllFashion(Integer page) {
		return Optional.ofNullable(page).map(item -> fashionRepo.findAll(PageRequest.of(item, 8, Sort.Direction.DESC, "_id")).getContent()).orElse(fashionRepo.findAll());
	}

	@Override
	public CommonFashionDocs addOrUpdateItem(CommonFashionDTO fashionDto) {
		CommonFashionDocs newFashion = fashionConvert.toDocs(fashionDto);
		return Optional.ofNullable(newFashion).map(item -> fashionRepo.save(item)).orElse(null);
	}


	@Override
	public List<CommonFashionDocs> findByTypeAndModel(String type, String model) {
		return Optional.ofNullable(fashionRepo.findByTypeAndModel(type, model)).orElse(null);
	}

	@Override
	@Transactional
	public Boolean deleteById(String[] ids) {
		return Optional.ofNullable(ids).map(listId -> {  
			for(String idDelete : listId) {
				fashionRepo.deleteById(idDelete);
			}
			return true;
		}).orElse(false);
	}

	@Override
	public List<CommonFashionDocs> findByTypeAndAnotherName(String type, String anotherName) {
		return (anotherName != null ? (fashionRepo.findByTypeAndAnotherName(type, anotherName)):(Optional.ofNullable(type).map(value -> fashionRepo.findByType(value)).orElse(null)));
	}

	@Override
	@Transactional
	public void deleteByAnotherName(String anotherName) {
		fashionRepo.deleteByAnotherName(anotherName);
	}

	@Override
	@Transactional
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




