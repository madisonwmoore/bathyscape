package com.madison.Bathyscape.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class HighScoreKeeper {
boolean exists;
int highscore;
int currency;
boolean alvinsub=true;
boolean shinkaisub=true;
boolean magnetictool=true;
boolean phasertool=true;
boolean soundonoff=true;
int numberruns=0;
float gamescale;
int equipment;
int tunacount=0;
int marlincount=0;
int fugucount=0;
int blobfishcount=0;
int anglerfishcount=0;
int mahimahicount=0;
int spudgunkills=0;
int musicvolume=75;
int laserkills=0;
int chopstickkills=0;
boolean a0locked=true;
boolean a1locked,a2locked=true,a3locked,a4locked,a5locked=true,a6locked,a7locked,a8locked,a9locked,a10locked,a11locked,a12locked,a13locked,a14locked;
Preferences prefs;


public HighScoreKeeper(){
	 prefs = Gdx.app.getPreferences("High Score Keeper");
	 highscore=prefs.getInteger("highscore");
	 currency=prefs.getInteger("currency");
	 alvinsub=prefs.getBoolean("alvin",true);
	 shinkaisub=prefs.getBoolean("shinkai",true);
	 equipment=prefs.getInteger("equipment");
	 magnetictool=prefs.getBoolean("magnetictool",true);
	 phasertool=prefs.getBoolean("phasertool",true);
	 gamescale=prefs.getFloat("gamescale",1.3f);
	 soundonoff=prefs.getBoolean("soundonoff",true);
	 tunacount=prefs.getInteger("tunacount",0);
	 blobfishcount=prefs.getInteger("blobfishcount",0);
	 marlincount=prefs.getInteger("marlincount",0);
	 fugucount=prefs.getInteger("fugucount",0);
	 anglerfishcount=prefs.getInteger("anglerfishcount",0);
	 mahimahicount=prefs.getInteger("mahimahicount",0);
	 spudgunkills=prefs.getInteger("spudgunkills",0);
	 laserkills=prefs.getInteger("laserkills",0);
	 a0locked=prefs.getBoolean("a0locked",true);
	 a1locked=prefs.getBoolean("a1locked", true);
	 a2locked=prefs.getBoolean("a2locked", true);
	 a3locked=prefs.getBoolean("a3locked", true);
	 a4locked=prefs.getBoolean("a4locked", true);
	 a5locked=prefs.getBoolean("a5locked", true);
	 a6locked=prefs.getBoolean("a6locked", true);
	 a7locked=prefs.getBoolean("a7locked", true);
	 a8locked=prefs.getBoolean("a8locked", true);
	 a9locked=prefs.getBoolean("a9locked", true);
	 a10locked=prefs.getBoolean("a10locked", true);
	 a11locked=prefs.getBoolean("a11locked", true);
	 a12locked=prefs.getBoolean("a12locked", true);
	 a13locked=prefs.getBoolean("a13locked", true);
	 a14locked=prefs.getBoolean("a14locked", true);
	 numberruns=prefs.getInteger("numberruns",0);
	 musicvolume=prefs.getInteger("musicvolume",75);
	 prefs.flush();
}

public int getHighScore(){
	return highscore;
}
public boolean writeHighScore(int score){
if(score>highscore) {
	prefs.putInteger("highscore", score);
	prefs.flush();
	return true;
	}
return false;
}

public int getCurrency() {
	return currency;
}
public boolean addCurency(int deposit) {
	if(deposit>0) {
	currency=currency+deposit;
	prefs.putInteger("currency", currency);
	prefs.flush();
	return true;
	}
	else {
		return false;
	}
}

public boolean deductCurrency(int withdrawl) {
	if(withdrawl<currency&&withdrawl>0) {
	currency=currency-withdrawl;
	prefs.putInteger("currency", currency);
	prefs.flush();
	return true;
	}
	else {
		return false;
	}
	
}
public boolean isAlvinLocked(){
	
	return alvinsub;
	
}
public boolean isShinkaiLocked() {
return shinkaisub;
}
public boolean isSoundOn() {
	return soundonoff;
}

public boolean isMagneticToolLocked() {
	return magnetictool;
}
public boolean isPhaserToolLocked() {
	return phasertool;
}
public float getScale() {
	return gamescale;
}
public void unlockAlvin(){
	prefs.putBoolean("alvin", false);
	prefs.flush();
	alvinsub=false;
}
public void lockAlvin(){
	prefs.putBoolean("alvin", true);
	prefs.flush();
	alvinsub=true;
}
public void unlockShinkai(){
	prefs.putBoolean("shinkai", false);
	prefs.flush();
	shinkaisub=false;
}
public void lockShinkai(){
	prefs.putBoolean("shinkai", true);
	prefs.flush();
	shinkaisub=true;
}
public void unlockMagneticTool() {
	prefs.putBoolean("magnetictool", false);
	prefs.flush();
	magnetictool=false;
}
public void unlockPhaserTool() {
	prefs.putBoolean("phasertool", false);
	prefs.flush();
	phasertool=false;
}
public void lockMagneticTool() {
	prefs.putBoolean("magnetictool", true);
	prefs.flush();
	magnetictool=true;
}
public void lockPhaserTool() {
	prefs.putBoolean("phasertool", true);
	prefs.flush();
	phasertool=true;
}
public void updateScale(float newscale) {
prefs.putFloat("gamescale",newscale);
prefs.flush();
gamescale=newscale;


}
public void setSound(boolean sound) {
	prefs.putBoolean("soundonoff", sound);
	prefs.flush();
	soundonoff=sound;
	
}
public void addTunaCount(int add) {
	tunacount=tunacount+add;
	prefs.putInteger("tunacount", tunacount);
	prefs.flush();
}

public void addMarlinCount(int add) {
	marlincount=marlincount+add;
	prefs.putInteger("marlincount", marlincount);
	prefs.flush();
}
public void addMahiMahiCount(int add) {
	mahimahicount=mahimahicount+add;
	prefs.putInteger("mahimahicount", mahimahicount);
	prefs.flush();
}
public void addFuguCount(int add) {
	fugucount=fugucount+add;
	prefs.putInteger("fugucount", fugucount);
	prefs.flush();
}
public void addAnglerFishCount(int add) {
	anglerfishcount=anglerfishcount+add;
	prefs.putInteger("anglerfishcount", anglerfishcount);
	prefs.flush();
}
public void addBlobFishCount(int add) {
	blobfishcount=blobfishcount+add;
	prefs.putInteger("blobfishcount", blobfishcount);
	prefs.flush();
}

public void addLaserKills(int add) {
	laserkills=laserkills+add;
	prefs.putInteger("laserkills", laserkills);
	prefs.flush();
	
}
public void addChopstickKills(int add) {
	chopstickkills=chopstickkills+add;
	prefs.putInteger("chopstickkills", chopstickkills);
	prefs.flush();
	
}
public void addSpudGunKills(int add) {
	spudgunkills=spudgunkills+add;
	prefs.putInteger("spudgunkills", spudgunkills);
	prefs.flush();
	
}
public int getMarlinCount() {
	 
	return marlincount;
}
public int getFuguCount() {
	 
	return fugucount;
}
public int getMahiMahiCount() {
	 
	return mahimahicount;
}
public int getAnglerFishCount() {
	 
	return anglerfishcount;
}

public int getBlobFishCount() {
	 
	return blobfishcount;
}

public int getTunaCount() {
	 
	return tunacount;
}
public int getLaserKills() {
	return laserkills;
}
public int getChopstickKills() {
	return chopstickkills;
}
public int getSpudGunKills() {
	return spudgunkills;
}


public int getTotalFishCount() {
	return tunacount+mahimahicount+marlincount+fugucount+anglerfishcount+blobfishcount;
}

public int getNumberRuns() {
	return numberruns;
	
}

public void addNumberRuns() {
	numberruns++;
	prefs.putInteger("numberruns", numberruns);
	prefs.flush();
}

public boolean isAwardLocked(int awardnumber) {
	if(awardnumber==0) {
		System.out.println("a0 is" + a0locked);
		return a0locked;
	}
	if(awardnumber==1){
		return a1locked;
	}
	if(awardnumber==2){
		return a2locked;
	}
	if(awardnumber==3){
		return a3locked;
	}
	if(awardnumber==4){
		return a4locked;
	}
	if(awardnumber==5){
		return a5locked;
	}
	if(awardnumber==6){
		return a6locked;
	}
	if(awardnumber==7){
		return a7locked;
	}
	if(awardnumber==8){
		return a8locked;
	}
	if(awardnumber==9){
		return a9locked;
	}
	if(awardnumber==10){
		return a10locked;
	}
	if(awardnumber==11){
		return a11locked;
	}
	if(awardnumber==12){
		return a12locked;
	}
	if(awardnumber==13){
		return a13locked;
	}
	if(awardnumber==14){
		return a14locked;
	}
	else {
		return true;
	}
}

public void setAwardLocked(int awardnumber, boolean locked) {
	if(awardnumber==0) {
	 prefs.putBoolean("a0locked", locked);
	}
	if(awardnumber==1){
		prefs.putBoolean("a1locked", locked);
	}
	if(awardnumber==2){
		prefs.putBoolean("a2locked", locked);
	}
	if(awardnumber==3){
		prefs.putBoolean("a3locked", locked);
	}
	if(awardnumber==4){
		prefs.putBoolean("a4locked", locked);
	}
	if(awardnumber==5){
		prefs.putBoolean("a5locked", locked);
	}
	if(awardnumber==6){
		prefs.putBoolean("a6locked", locked);
	}
	if(awardnumber==7){
		prefs.putBoolean("a7locked", locked);
	}
	if(awardnumber==8){
		prefs.putBoolean("a8locked", locked);
	}
	if(awardnumber==9){
		prefs.putBoolean("a9locked", locked);
	}
	if(awardnumber==10){
		prefs.putBoolean("a10locked", locked);
	}
	if(awardnumber==11){
		prefs.putBoolean("a11locked", locked);
	}
	if(awardnumber==12){
		prefs.putBoolean("a12locked", locked);
	}
	if(awardnumber==13){
		prefs.putBoolean("a13locked", locked);
	}
	if(awardnumber==14){
		prefs.putBoolean("a14locked", locked);
	}
	prefs.flush();
	
}
public int getMusicVolume() {
	return musicvolume;
	
}
public void setMusicVolume(int volume) {
	musicvolume=volume;
	prefs.putInteger("musicvolume", volume);
	prefs.flush();
}

}
