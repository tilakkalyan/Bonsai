package com.bonsai.common;

import android.graphics.Bitmap;

public class Item {
	private long id;
	private String englishName;
	private String scientificName;
	private Bitmap image;
	private String description;
	private String date;
	private String raasi;
	private String rishi;
	private String planet;
	private String star;
	private String teluguName;
	private String kannadaName;
	private String sanskritRaaga;
	private String englishRaaga;
	private String style;
	private String origin;
	
	
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getSanskritRaaga() {
		return sanskritRaaga;
	}
	public void setSanskritRaaga(String sanskritRaaga) {
		this.sanskritRaaga = sanskritRaaga;
	}
	public String getEnglishRaaga() {
		return englishRaaga;
	}
	public void setEnglishRaaga(String englishRaaga) {
		this.englishRaaga = englishRaaga;
	}
	public String getKannadaName() {
		return kannadaName;
	}
	public void setKannadaName(String kannadaName) {
		this.kannadaName = kannadaName;
	}
	public String getTeluguName() {
		return teluguName;
	}
	public void setTeluguName(String teluguName) {
		this.teluguName = teluguName;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public Item(){
	}
	
	public Item(Bitmap image, String text){
		this.image = image;
		this.englishName = text;
	}
	
	public Item(String date, String text){
		this.date = date;
		this.englishName = text;
	}
	
	public Bitmap getImage() {
		return image;
	}
	public void setImage(Bitmap image) {
		this.image = image;
	}
	public String getRaasi() {
		return raasi;
	}
	public void setRaasi(String raasi) {
		this.raasi = raasi;
	}
	public String getRishi() {
		return rishi;
	}
	public void setRishi(String rishi) {
		this.rishi = rishi;
	}
	public String getPlanet() {
		return planet;
	}
	public void setPlanet(String planet) {
		this.planet = planet;
	}
	public String getStar() {
		return star;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setStar(String star) {
		this.star = star;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getEnglishName() {
		return englishName;
	}
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	public String getScientificName() {
		return scientificName;
	}
	public void setScientificName(String scientificName) {
		this.scientificName = scientificName;
	}
	

}
