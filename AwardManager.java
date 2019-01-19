package com.madison.Bathyscape.core;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AwardManager {
	
TextureRegion[] awardframe;	
ArrayList<Award> awardlist;	
SubAbyss game;
public AwardManager(SubAbyss game) {
	this.game=game;
	game.assman.loadMenuImages();
	game.assman.manager.finishLoading();
	Texture awardtext=game.assman.manager.get("data/awardsheet.png",Texture.class);
	
	int FRAME_COLS=4;
	int	FRAME_ROWS=4;
	TextureRegion[][] tmp = TextureRegion.split(awardtext, awardtext.getWidth() / 
			FRAME_COLS, awardtext.getHeight() / FRAME_ROWS);                                
			        awardframe = new TextureRegion[FRAME_COLS * FRAME_ROWS];
			        int index1 = 0;
			        for (int i = 0; i < FRAME_ROWS; i++) {
			                for (int j = 0; j < FRAME_COLS; j++) {
			                        awardframe[index1++] = tmp[i][j];
			                }
			        }
			        
		awardlist=new ArrayList<Award>();
		
		for(int i=0;i<16;i++) {
		awardlist.add(new Award(game, awardframe[i], i));   }  
			        
			        
}  
public boolean  isAwardLocked(int awardnumber) {
	return game.keeper.isAwardLocked(awardnumber);
}

public Award getAward(int awardnumber ) {
	return awardlist.get(awardnumber);
}

public TextureRegion getAwardTexture(int awardnumber) {
//	if(isAwardLocked(awardnumber)) {
//		System.out.println(awardlist.get(awardnumber).isLocked());
//		System.out.println("hi");
//		return awardlist.get(15).getTexture();
//		
//		}
//else {
		System.out.println("no");
		return awardlist.get(awardnumber).getTexture();
	//}
}

public ArrayList<Award> updateAwards() {
	ArrayList<Award> earnedawards=new ArrayList<Award>();
	Award award=updateAward();
	Award award1=updateAward1();
	Award award2=updateAward2();
	Award award3=updateAward3();
	Award award4=updateAward4();
	Award award5=updateAward5();
	Award award6=updateAward6();
	Award award7=updateAward7();
	Award award8=updateAward8();
	//Award award9=updateAward9();
	//Award award10=updateAward10();
	//Award award11=updateAward11();
	//Award award12=updateAward12();
	//Award award13=updateAward13();
	//Award award14=updateAward14();
	if(award!=null) {
		earnedawards.add(award);
	}
	if(award1!=null) {
		earnedawards.add(award1);
	}
	if(award2!=null) {
		earnedawards.add(award2);
	}
	if(award3!=null) {
		earnedawards.add(award3);
	}
	if(award4!=null) {
		earnedawards.add(award4);
	}
	if(award5!=null) {
		earnedawards.add(award5);
	}
	if(award6!=null) {
		earnedawards.add(award6);
	}
	if(award7!=null) {
		earnedawards.add(award7);
	}
	if(award8!=null) {
		earnedawards.add(award8);
	}
	
	
	
	
	return earnedawards;
	
	
}
private Award updateAward(){
	
	if (game.keeper.getNumberRuns()>=0&&awardlist.get(0).isLocked()) {
		awardlist.get(0).setLocked(false);
		return awardlist.get(0);
	}
	else {
		return null;
	}
}
private Award updateAward1(){
	if (game.keeper.getSpudGunKills()>=100&&awardlist.get(1).isLocked()) {
		awardlist.get(1).setLocked(false);
		return awardlist.get(1);
	}
	else {
		return null;
	}
}
private Award updateAward2(){
	if (game.keeper.getCurrency()>50000&&awardlist.get(2).isLocked()) {
		awardlist.get(2).setLocked(false);
		return awardlist.get(2);
	}
	else {
		return null;
	}
}
private Award updateAward3(){
	if (game.keeper.getTotalFishCount()>100&&awardlist.get(3).isLocked()) {
		awardlist.get(3).setLocked(false);
		return awardlist.get(3);
	}
	else {
		return null;
	}
}

private Award updateAward4(){
	if (game.keeper.getChopstickKills()>100&&awardlist.get(4).isLocked()) {
		awardlist.get(4).setLocked(false);
		return awardlist.get(4);
	}
	else {
		return null;
	}
}
private Award updateAward5(){
	if (game.keeper.getLaserKills()>100&&awardlist.get(5).isLocked()) {
		awardlist.get(5).setLocked(false);
		return awardlist.get(5);
	}
	else {
		return null;
	}
}
private Award updateAward6(){
	if (game.keeper.getHighScore()>10000&&awardlist.get(6).isLocked()) {
		awardlist.get(6).setLocked(false);
		return awardlist.get(6);
	}
	else {
		return null;
	}
}
private Award updateAward7(){
	if (game.keeper.getNumberRuns()>=100&&awardlist.get(7).isLocked()) {
		awardlist.get(7).setLocked(false);
		return awardlist.get(7);
	}
	else {
		return null;
	}
}
private Award updateAward8(){
	if (game.keeper.getTunaCount()>500&&awardlist.get(8).isLocked()) {
		awardlist.get(8).setLocked(false);
		return awardlist.get(8);
	}
	else {
		return null;
	}
}


}
