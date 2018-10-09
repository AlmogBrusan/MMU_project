package com.hit.dao;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import com.hit.dm.DataModel;

public class DaoFileImpl<T> implements IDao<Long, DataModel<T>> {
	public String filePath;
	private HashMap<Long, DataModel<T>> data;

	public DaoFileImpl(String filePath) {
		this.filePath = filePath;
		File file = new File(filePath);
		if (!file.exists()) {
			data = new HashMap<>();
			for (long i = 1; i <= 200; i++) {  // Change capacity for DataModels, now it's 200.
				data.put(i, new DataModel<>(i, null));
				try {
					ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath));
					oos.writeObject(data);
					oos.flush();
					oos.close();
					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			try {
				FileInputStream fin = new FileInputStream(filePath);
				ObjectInputStream oin = new ObjectInputStream(fin);
				data = (HashMap<Long, DataModel<T>>)oin.readObject();
				oin.close();
				fin.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} 
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public void save(DataModel<T> t) throws Exception {
		try {
			FileInputStream fin = new FileInputStream(filePath);
			ObjectInputStream oin = new ObjectInputStream(fin);
			data = (HashMap<Long, DataModel<T>>)oin.readObject();
			oin.close();
			fin.close();

			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath));

			data.put(t.getDataModelId(), t);
			oos.writeObject(data);
			oos.flush();
			oos.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public void delete(DataModel<T> t) throws Exception {
		
		try {
			FileInputStream fin = new FileInputStream(filePath);
			ObjectInputStream oin = new ObjectInputStream(fin);
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath));
			
			data = (HashMap<Long, DataModel<T>>) oin.readObject();
			data.put(t.getDataModelId(), null);
			oos.writeObject(data);
			oos.flush();
			
			oin.close();
			fin.close();
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public DataModel<T> find(Long id) {
		try {
			FileInputStream fin = new FileInputStream(filePath);
			ObjectInputStream oin = new ObjectInputStream(fin);
			
			data = (HashMap<Long, DataModel<T>>) oin.readObject();
			
			oin.close();
			fin.close();

			return data.get(id);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
