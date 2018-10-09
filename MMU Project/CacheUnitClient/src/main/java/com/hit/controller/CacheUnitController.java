package com.hit.controller;

import java.util.Observable;

import com.hit.model.Model;
import com.hit.view.View;

public class CacheUnitController implements Controller{
	public View view;
	public Model model;
	
	public CacheUnitController(View view, Model model) {
		this.view = view;
		this.model = model;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof View)
			model.updateModelData((String)arg);
		
		else if (o instanceof Model)
			view.updateUIData((String)arg);
	}

}
