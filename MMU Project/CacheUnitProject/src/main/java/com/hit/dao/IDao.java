package com.hit.dao;

import java.io.Serializable;

public interface IDao <ID extends Serializable,T> {
	void save(T t) throws Exception;
	void delete(T t) throws Exception;
	T find(ID id);
}
