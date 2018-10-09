package com.hit.memory;

import java.util.concurrent.atomic.AtomicInteger;

import com.hit.algorithm.IAlgoCache;
import com.hit.dao.IDao;
import com.hit.dm.DataModel;

public class CacheUnit<T> {
	private IAlgoCache<Long, DataModel<T>> algo; // cache
	private IDao<Long, DataModel<T>> dao; // file
	private static AtomicInteger swaps = new AtomicInteger(0);

	public CacheUnit(IAlgoCache<Long, DataModel<T>> algo, IDao<Long, DataModel<T>> dao) {
		super();
		this.algo = algo;
		this.dao = dao;
		
	}

	@SuppressWarnings("unchecked")
	public DataModel<T>[] getDataModels(Long[] ids) throws Exception {
		DataModel<T>[] dm = new DataModel[ids.length];

		for (int i = 0; i < ids.length; i++) {
			// the id doesn't exist in the Cache.
			DataModel<T> temp, fromCache;
			fromCache = algo.getElement(ids[i]);
			if (fromCache == null) { 
				temp = dao.find(ids[i]);
				if (temp == null) {
				} else {
					fromCache = algo.putElement(ids[i], temp); 
					if (fromCache != null) {
						dao.save(fromCache);
						swaps.incrementAndGet();
						// if fromCache is null it means the Cache was empty.
					}
				}
			}
			dm[i] = algo.getElement(ids[i]);


		}
		return dm;
	}

	public int getSwaps() 
	{
		return swaps.intValue();
	}

}
