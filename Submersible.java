package com.madison.Bathyscape.core;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import box2dLight.ConeLight;

public class Submersible {
	boolean subhit;
	String subname;
    Sound motorsound;
    boolean ismotorplaying;
    Label scorelabel;

	public Sprite getSubShopSprite(){
		return null;
	}

	public float getDepth() {
		// TODO Auto-generated method stub
		return 0;
	}
	public Body getBody(){
		return null;
		
	}
	public void draw(){
		
	}
	public void addScore(int i){
		
	}
	public int getScore(){
		return 0;
	}
	public Stage getStage(){
		return null;
	}
	public void activateArm(Fish fish){
		
	}
public void activateArm(Sardine fish){
		
	}
public void activateArm(Squid squid){
	
}
	public Body getArm2Body(){
		return null;
	}
	public Sprite getArm2Sprite(){
		return null;
	}
	public Sprite getSubSprite(){
		return null;
	}
	public void hit(int hitpoints){
		
	}
	public int getStrength() {
		return 0;
	}
	public float getTopSpeed() {
		return 0;
	}
	public void restoreAngle(){
		
	}
	public void sink(){}
	public float getHealth(){
		return 0;}

public String getSubName() {
	return subname;}

public Sprite getPropeller() {
	return null;
}
public void dispose() {
	
}
public Tool getArm() {
	return null;
}
public boolean isLocked() {
	return false;
}
public int getUnlockCost() {
	return 0;
	
}
public Label getScoreLabel() {
	return scorelabel;
}
public Image getMenuButton() {
	return null;
}
public Label getHealthLabel() {
	return null;
}
public ConeLight getFrontLight() {
	return null;
}

}