package com.hit.services;

import com.hit.algorithm.IAlgoCache;
import com.hit.algorithm.LRUAlgoCacheImpl;
import com.hit.algorithm.MRUAlgoCacheImpl;
import com.hit.algorithm.RandomAlgoCacheImpl;
import com.hit.algorithm.SecondChance;
import com.hit.dao.DaoFileImpl;
import com.hit.dao.IDao;
import com.hit.dm.DataModel;
import com.hit.memory.CacheUnit;

public class CacheUnitService<T> {

	private IAlgoCache<Long, DataModel<T>> algo;
	private IDao<Long, DataModel<T>> dao;
	private CacheUnit<T> cache;
	private int capacity;
	private int swaps;
	private static int requestsNumber=0;
	private String algorithm;

	public CacheUnitService() {
		
		capacity = 15;
		algo = new LRUAlgoCacheImpl<>(capacity);
	    dao= new DaoFileImpl<>("file1.txt");
		cache = new CacheUnit<>(algo, dao);
	}

	public boolean update(DataModel<T>[] dataModels) {
		requestsNumber++;
		Long[] ids = new Long[dataModels.length];
		for (int i = 0; i < dataModels.length; i++) {
			ids[i] = dataModels[i].getDataModelId();
		}
		try {
			DataModel<T>[] tempDM = (DataModel<T>[]) cache.getDataModels(ids);
			for (int i = 0; i < dataModels.length; i++) {
				tempDM[i].setContent(dataModels[i].getContent());
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean delete(DataModel<T>[] dataModels) {
		requestsNumber++;
		Long[] ids = new Long[dataModels.length];
		for (int i = 0; i < dataModels.length; i++) {
			ids[i] = dataModels[i].getDataModelId();
		}
		try {
			DataModel<T>[] tempDM = (DataModel<T>[]) cache.getDataModels(ids);
			for (int i = 0; i < dataModels.length; i++) {
				tempDM[i].setContent(null);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	public DataModel<T>[] get(DataModel<T>[] dataModels) {
		requestsNumber++;
		Long[] ids = new Long[dataModels.length];
		for (int i = 0; i < dataModels.length; i++) {
			ids[i] = dataModels[i].getDataModelId();
		}
		try {
			DataModel<T>[] tempDM = (DataModel<T>[]) cache.getDataModels(ids);
			
			for (int i = 0; i < dataModels.length; i++) {
				dataModels[i].setContent(tempDM[i].getContent());
			}
			return dataModels;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getStatistics() {
		swaps = cache.getSwaps();

		if (algo instanceof LRUAlgoCacheImpl)
			algorithm = "LRU";
		else if (algo instanceof MRUAlgoCacheImpl)
			algorithm = "MRU";
		else if (algo instanceof SecondChance)
			algorithm = "Second Chance";
		else if (algo instanceof RandomAlgoCacheImpl)
			algorithm = "Random";

		String statistics = "Capacity: " + capacity + ",Algorithm Used: " + algorithm + ",Num of DataModels: 200"
				+ ",Num of requests: " + requestsNumber + ",Num of DataModel swaps: " + swaps;

		return statistics;
	}

}
