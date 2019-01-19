package com.madison.Bathyscape.core;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

public class Tool {
String type;
String description;
boolean armrunning;
public Tool() {}
public Tool(float x, float y, SubAbyss game) {
	
}
public Body getArm1Body() {
	return null;}
public Body getArm2Body() {
	return null;
}
public Sprite getArm2Sprite() {
	return null;
}
public Sprite getArm1Sprite() {
	return null;
}
public boolean fishOn(Fish fish) {
	return false;
}
public String getType() {
	return type;
}
public void draw() {
	
}
public void setToolColor(Color color) {
	
}
public void activateArm(Fish tf) {
	
}
public void activateArm(Sardine ts) {
	
}
public void activateArm(Squid ts) {
	
}
public String getToolName() {
	return type;
	
}
public String getToolDescription() {
	return description;
	
}
public boolean isArmRunning() {
	return armrunning;
}
public boolean isMagnetizing() {
	return false;
}
public void setMagnetizing(boolean v) {
	
}
public boolean isLocked() {
	return false;
}
public int getUnlockCost() {
	return 0;
}
public void clearProjectiles() {
	
}
public boolean launch(boolean t) {
return false;	

}
}
