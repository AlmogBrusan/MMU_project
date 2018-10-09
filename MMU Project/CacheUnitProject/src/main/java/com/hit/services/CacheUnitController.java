package com.hit.services;

import com.hit.dm.DataModel;

public class CacheUnitController<T>{
	
	private CacheUnitService<T> cacheUnitService;
	
	public CacheUnitController() 
	{
		cacheUnitService = new CacheUnitService<T>();
	}
	

	//delete
	public boolean delete(DataModel<T>[] dataModels)
	{
		return cacheUnitService.delete(dataModels);
	}
	//get
	public DataModel<T>[] get(DataModel<T>[] dataModels)
	{
		return cacheUnitService.get(dataModels);
	}
	
	//update
	public boolean update(DataModel<T>[] dataModels) 
	{
		return cacheUnitService.update(dataModels);
	}
	
	//statistics 
	public String statistics() 
	{
		return cacheUnitService.getStatistics();
	}
}
