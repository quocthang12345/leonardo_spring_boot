package com.leonardo.service;

import java.util.List;

import com.leonardo.document.CommonFashionDocs;
import com.leonardo.document.dto.CommonFashionDTO;

public interface IFashionService {
	List<CommonFashionDocs> findAllFashion();
	CommonFashionDocs addItem(CommonFashionDTO fashionDto);
	CommonFashionDocs updateItem(CommonFashionDTO fashionDto);
	List<CommonFashionDocs> findByType(String type);
	List<CommonFashionDocs> findByTypeAndModel(String type, String model);
	Boolean deleteById(String[] ids);
	Boolean deleteByAnotherName(String anotherName);
	Boolean addListItem(List<CommonFashionDTO> listFashions);
}
