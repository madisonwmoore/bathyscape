package com.madison.Bathyscape.core;

import java.util.LinkedList;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class SubStorage {
Array<Submersible> subarray;
Music theme;
//AssetManager manager;
LinkedList<Submersible> sublist;
LinkedList<Tool> toollist;
LinkedList<Equipment> equipmentlist;
SubAbyss game;
int chosenbody=0;
int currentpossition=0;
int currenttoolpossition=0;
int currentequipmentpossition=0;
public SubStorage(AssetManager manager, SubAbyss game){
	//this.manager=manager;
	//this.game=game;
	game.assman.loadSubStorageImages();
	
	game.assman.manager.finishLoading();
//	manager.load("sub.png",Texture.class);
	//manager.load("sub2.png",Texture.class);
	//manager.update();
	//manager.finishLoading();
	sublist=new LinkedList<Submersible>();
	toollist=new LinkedList<Tool>();
	equipmentlist=new LinkedList<Equipment>();
	//subarray=new Array<Submersible>();
	sublist.add(new SandwichSubmersible(game,game.assman.manager));
	sublist.add(new AlvinSubmersible(game));
	sublist.add(new ShinkaiSubmersible(game, game.assman.manager));
	sublist.add(new MirSubmersible(game, game.assman.manager));
	
	toollist.add(new BasicArm(game));
	toollist.add(new ChopstickArm(game));
	toollist.add(new MagneticArm(game));
	toollist.add(new Phaser(game));
	toollist.add(new SpudGun(game));
	
	equipmentlist.add(new Equipment());
	equipmentlist.add(new Hat("Santa Hat", game, false));
	
	theme=game.assman.manager.get("data/BathyscapeTheme.mp3",Music.class);
}

public Submersible getChosenSub(){
	
	return sublist.get(currentpossition);
	
}

public Tool getChosenTool() {
	return toollist.get(currenttoolpossition);
}

public Equipment getChosenEquipment() {
	return equipmentlist.get(currentequipmentpossition);
}

public void setChosenSub(int choice){
	currentpossition=choice;
}
public void setChosenTool(int choice){
	currenttoolpossition=choice;
}
public void setChosenEquipment(int choice){
	currentequipmentpossition=choice;
}
public Array<Submersible> getSubArray (){
	return subarray;
}
public Submersible CreateSub(Batch batch, World world,float statetime, OrthographicCamera camera, AssetManager assman){
	return new SandwichSubmersible(batch,world,statetime,camera,assman, game);


}
public boolean addSub(Submersible newsub) {
	sublist.add(newsub);
	
	return true;
}
public Submersible getNextSub() {
	currentpossition++;
	if(currentpossition<sublist.size()) {
	return sublist.get(currentpossition);}
	else {
		currentpossition=0;
		return sublist.get(currentpossition);
		}
}
public Submersible getLastSub() {
	currentpossition--;
	if(currentpossition>=0) {
	return sublist.get(currentpossition);}
	else {
		currentpossition=sublist.size()-1;
		return sublist.get(currentpossition);
		}
}
public Tool getNextTool() {
	currenttoolpossition++;
	if(currenttoolpossition<toollist.size()) {
	return toollist.get(currenttoolpossition);}
	else {
		currenttoolpossition=0;
		return toollist.get(currenttoolpossition);
		}
}
public Tool getLastTool() {
	currenttoolpossition--;
	if(currenttoolpossition>=0) {
	return toollist.get(currenttoolpossition);}
	else {
		currenttoolpossition=toollist.size()-1;
		return toollist.get(currenttoolpossition);
		}
}
public Equipment getNextEquipment() {
	currentequipmentpossition++;
	if(currentequipmentpossition<equipmentlist.size()) {
	return equipmentlist.get(currentequipmentpossition);}
	else {
		currentequipmentpossition=0;
		return equipmentlist.get(currentequipmentpossition);
		}
}
public Equipment getLastEquipment() {
	currentequipmentpossition--;
	if(currentequipmentpossition>=0) {
	return equipmentlist.get(currentequipmentpossition);}
	else {
		currentequipmentpossition=equipmentlist.size()-1;
		return equipmentlist.get(currentequipmentpossition);
		}
}
public void dispose() {
	equipmentlist.clear();
	toollist.clear();
	sublist.clear();
}


}
