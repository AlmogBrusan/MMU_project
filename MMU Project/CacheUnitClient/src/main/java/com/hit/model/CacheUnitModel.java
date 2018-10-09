package com.hit.model;

import java.util.Observable;

public class CacheUnitModel extends Observable implements Model{

	private CacheUnitClient client;
	
	public CacheUnitModel() {
		client=new CacheUnitClient();
	}
	
	@Override
	public <T> void updateModelData(T t) {
		String answer=client.send((String)t);
		setChanged();
		notifyObservers(answer);
	}



}
