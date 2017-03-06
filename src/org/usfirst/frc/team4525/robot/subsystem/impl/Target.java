package org.usfirst.frc.team4525.robot.subsystem.impl;

public class Target{
	private double centerX; // center x
	private double centerY; // center y
	private double width;
	private double height;
	private double upperLeftX;
	private double upperLeftY;
	private double lowerRightX;
	private double lowerRightY;
	private double imageWidth;
	private double imageHeight;
	private double updated;
	
	
	public double getImageHeight() {
		return imageHeight;
	}
	public void setImageHeight(double imageHeight) {
		this.imageHeight = imageHeight;
	}
	
	public double getImageWidth() {
		return imageWidth;
	}
	
	public void setImageWidth(double imageWidth) {
		this.imageWidth = imageWidth;
	}
	
	public double getUpdated() {
		return updated;
	}
	
	public void setUpdated(double updated) {
		this.updated = updated;
	}
	
	public double getCenterX() {
		return centerX;
	}
	public void setCenterX(double centerX) {
		this.centerX = centerX;
	}
	public double getCenterY() {
		return centerY;
	}
	public void setCenterY(double centerY) {
		this.centerY = centerY;
	}
	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		this.width = width;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public double getUpperLeftX() {
		return upperLeftX;
	}
	public void setUpperLeftX(double upperLeftX) {
		this.upperLeftX = upperLeftX;
	}
	public double getUpperLeftY() {
		return upperLeftY;
	}
	public void setUpperLeftY(double upperLeftY) {
		this.upperLeftY = upperLeftY;
	}
	public double getLowerRightX() {
		return lowerRightX;
	}
	public void setLowerRightX(double lowerRightX) {
		this.lowerRightX = lowerRightX;
	}
	public double getLowerRightY() {
		return lowerRightY;
	}
	public void setLowerRightY(double lowerRightY) {
		this.lowerRightY = lowerRightY;
	}
	
	
	
}