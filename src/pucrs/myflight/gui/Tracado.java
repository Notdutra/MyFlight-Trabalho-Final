package pucrs.myflight.gui;

import java.awt.Color;
import java.util.ArrayList;

import org.jxmapviewer.viewer.GeoPosition;

import pucrs.myflight.modelo.Geo;

public class Tracado {
	
	private ArrayList<Geo> pontos;
	private Color cor;
	private String label;
	private int width;
	
	public Tracado() {
		pontos = new ArrayList<>();
		this.cor = Color.BLACK;
		this.width = 1;
		this.label = "";
	}
	
	public void clear() {
		pontos.clear();
	}
	
	public int size() {
		return pontos.size();
	}
	
	public void addPonto(Geo pos) {
		pontos.add(pos);
	}
	
	public ArrayList<Geo> getPontos() {
		return pontos;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setCor(Color cor) {
		this.cor = cor;
	}
	
	public Color getCor() { 
		return cor;
	}
}
