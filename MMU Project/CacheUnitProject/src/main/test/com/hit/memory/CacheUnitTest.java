package com.hit.memory;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.hit.algorithm.IAlgoCache;
import com.hit.algorithm.LRUAlgoCacheImpl;
import com.hit.dao.DaoFileImpl;
import com.hit.dao.IDao;
import com.hit.dm.DataModel;

public class CacheUnitTest {
	
	@Test
	public void getDataModelsTest() throws Exception {
		IAlgoCache<Long, DataModel<String>> algo = new LRUAlgoCacheImpl<>(5);
	    IDao<Long, DataModel<String>> dao = new DaoFileImpl<>("src\file.txt");
	    CacheUnit<String> cacheUnit = new CacheUnit<>(algo, dao);
	   
	    
	    DataModel<String> dm1 = new DataModel<>((long)1, "a");
	    DataModel<String> dm2 = new DataModel<>((long)2, "b");
	    DataModel<String> dm3 = new DataModel<>((long)3, "c");
	    
	    dao.save(dm1);
	    dao.save(dm2);
	    
	    algo.putElement((long)3, dm3);
	    
	    Long ids[] = {(long)1, (long)2};
	    cacheUnit.getDataModels(ids);
	    
	    DataModel<String> dataModel1 = algo.getElement((long)1);
	    DataModel<String> check = dao.find((long)1);
	    assertEquals(dm1, dataModel1);
	    assertEquals(null, check);
	    
	    DataModel<String> dataModel2 = algo.getElement((long)2);
	    assertEquals(dm2, dataModel2);
	    
	    DataModel<String> dataModel3 = dao.find((long)3);
	    assertEquals(dm3, dataModel3);
	   
	}
}
