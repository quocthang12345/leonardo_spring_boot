package com.leonardo.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.leonardo.document.CommonFashionDocs;
import com.leonardo.document.dto.CommonFashionDTO;

public interface IFashionService {
	List<CommonFashionDocs> findAllFashion(Integer page);
	CommonFashionDocs addOrUpdateItem(CommonFashionDTO fashionDto);
	List<CommonFashionDocs> findByTypeAndAnotherName(String type,String anotherName);
	List<CommonFashionDocs> findByTypeAndModel(String type, String model);
	Boolean deleteById(String[] ids);
	void deleteByAnotherName(String anotherName);
	Boolean addListItem(List<CommonFashionDTO> listFashions);
}
