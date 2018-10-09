package com.hit.dm;

import java.io.Serializable;

@SuppressWarnings("serial")
public class DataModel<T> implements Serializable{
	private Long dataModelId;
	private T content;
	
	public DataModel(Long id, T content) {
		super();
		this.dataModelId = id;
		this.content = content;
	}

	public Long getDataModelId() {
		return dataModelId;
	}
	
	public void setDataModelId(Long id) {
		this.dataModelId = id;
	}
	
	public T getContent() {
		return content;
	}
	
	public void setContent(T content) {
		this.content = content;
	}
	
	@Override
	public int hashCode() {
		return dataModelId.intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if((obj instanceof DataModel)){
			DataModel<T> object = (DataModel<T>) obj;
			return object.getDataModelId().equals(dataModelId);
		}
		return false;
	}

	@Override
	public String toString() {
		return "[" +content + "," + "]";
	}
}
	
	
