package com.madison.Bathyscape.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class FishManager {
	Array<Fish> fishes;
	long starttime;
	boolean boo;
	boolean squidactive;
	long lastshark=0;
	long lastsquid=0;
	SubAbyss game;
	private int anglerfishcount;
	long lastevilsub;
	long lasttrashdrum;
public FishManager(Array<Fish> fishes, Submersible sub, SubAbyss game){
	this.fishes=fishes;
	this.game=game;
	lastevilsub=TimeUtils.millis()+MathUtils.random(30000L,60000L);
	lasttrashdrum=TimeUtils.millis()+MathUtils.random(3000L,10000L);
	this.starttime=TimeUtils.millis();
}
public boolean Fugu(){
	
	int random=MathUtils.random(0,500);
if(random==1&&fishes.size<10){
	return true;
}
else{
	return false;
	}
	
	
	
}
public boolean Tuna(){
	if(MathUtils.random(0,50)==1&&!squidactive&&game.level.sub.getDepth()<=160){
		return true;
	}
	else{
		return false;
		}
			
}
public boolean Marlin(){
	if(MathUtils.random(0,1000)==1&&game.level.sub.getDepth()<=140){
		return true;
	}
	else{
		return false;
		}
		
}
public boolean Shark(){

	
if(TimeUtils.millis()>(lastshark+6000)&&TimeUtils.timeSinceMillis(starttime)>5000&&game.level.sub.getDepth()<=160){
	lastshark=TimeUtils.millis();
	if(MathUtils.random(0,4)==1) {
		return true;
	}
	else {return false;}
}
else{ return false;
}

		
}
public boolean MahiMahi(){
	if(MathUtils.random(0,50)==1){
		return true;
	}
	else{
		return false;
		}
			
}
public boolean Squid() {
	if(((TimeUtils.timeSinceMillis(lastsquid)>30000)&&!squidactive)&&game.level.sub.getDepth()>100) {
		squidactive=true;
		lastsquid=TimeUtils.millis();
		return true;
	}
	else { 
	if(Gdx.input.isKeyPressed(Keys.R)) {
		squidactive=false;}
	
	return false;}
}
public boolean Blobfish() {
	
	if(game.level.sub.getDepth()>160) {
		int random=MathUtils.random(0,400);
		if(random==1){
			return true;
		}
	}
	
	
	
	
	return false;
}





public boolean AnglerFish() {
	if(game.level.sub.getDepth()>160) {
	int random=MathUtils.random(0,700);
	if(random==1){
		return true;
	}
	else{
		return false;
		}
	}
	else {
		return false;
	}
}
public void setAnglerFishcount(int anglerfishcount) {
	this.anglerfishcount=anglerfishcount;
	
}
public void resetSquid() {
	squidactive=false;
}
public boolean EvilSub() {
	long duration=120000;
	if(TimeUtils.timeSinceMillis(lastevilsub)>duration||Gdx.input.isKeyPressed(Keys.EQUALS)) {
		lastevilsub=TimeUtils.millis();
	return true;	
	}
	else return false;
}
public boolean TrashDrum() {
	long duration=10000;
	if(TimeUtils.timeSinceMillis(lasttrashdrum)>duration||Gdx.input.isKeyPressed(Keys.EQUALS)) {
		if(MathUtils.randomBoolean()) {
		lasttrashdrum=TimeUtils.millis();
	return true;	}
		else return false;
	}
	else return false;
}
}
