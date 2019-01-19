package com.madison.Bathyscape.core;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class GameAssets {

public final AssetManager manager=new AssetManager();

public void loadSubStorageImages() {
	manager.load("data/sub.png",Texture.class);
	manager.load("data/sub2.png",Texture.class);
	manager.load("data/backarrow.png",Texture.class);
	manager.load("data/subbutton.png",Texture.class);
	manager.load("data/toolbutton.png",Texture.class);
	manager.load("data/equipmentbutton.png",Texture.class);
	manager.load("data/playbutton.png",Texture.class);
	manager.load("data/BathyscapeTheme.mp3",Music.class);
	manager.load("data/arm1.png",Texture.class);
	loadBasicArmImages();
	loadMagneticArmImages();
	loadChopstickArmImages();
	loadHatImages();
	loadPhaserArmImages();
	loadSpudGunArmImages();
}



public void loadMenuImages() {

	
	//menu images
	manager.load("data/menutext.png",Texture.class);
	manager.load("data/menutitle.png",Texture.class);
	manager.load("data/play.png",Texture.class);
	manager.load("data/settingsicon.png",Texture.class);
	manager.load("data/trophyicon.png",Texture.class);
	manager.load("data/scalebutton.png",Texture.class);
	manager.load("data/soundbutton.png",Texture.class);
	manager.load("data/musicbutton.png",Texture.class);
	manager.load("data/awardsheet.png",Texture.class);

}

public void loadLevel1Images() {
	manager.load("data/mine.png",Texture.class);
	manager.load("data/chain.png",Texture.class);
	manager.load("data/explosion.png",Texture.class);
	manager.load("data/anchor.png",Texture.class);
	manager.load("data/evilsub.png",Texture.class);
	manager.load("data/torpedo.png",Texture.class);
	manager.load("data/evilprop.png",Texture.class);
	manager.load("data/launchbutton.png",Texture.class);
	manager.load("data/ship.png",Texture.class);
	manager.load("data/drum.png",Texture.class);
	loadFishImages();
	loadSquidImages();
	loadFuguImages();
	
}
public void unloadLevel1Images() {
	manager.unload("data/mine.png");
	manager.unload("data/chain.png");
	manager.unload("data/sardine.png");
	manager.unload("data/explosion.png");
	manager.unload("data/anchor.png");
}
public void loadLevel1Sounds() {
	manager.load("data/boom.mp3",Sound.class);
	manager.load("data/Underwater Collision.mp3",Sound.class);
	manager.load("data/motor.mp3",Sound.class);
	manager.load("data/BathyscapeTheme.mp3",Music.class);
	manager.load("data/inflate.mp3",Sound.class);
	
}
public void unloadLevel1Sounds() {
	manager.unload("data/boom.mp3");
	manager.unload("data/Underwater Collision.mp3");
	manager.unload("data/BathyscapeTheme.mp3");
	manager.unload("data/motor.mp3");
	manager.unload("data/inflate.mp3");
}

public void loadSandwichSubmersibleImages() {

	manager.load("data/sub.png",Texture.class);
	manager.load("data/bubble.png",Texture.class);
	manager.load("data/subpropeller.png",Texture.class);
	manager.load("data/walkbutton.png",Texture.class);
	manager.load("data/walkbutton2.png",Texture.class);
	manager.load("data/launchbutton.png",Texture.class);
	manager.load("data/menuicon.png",Texture.class);
	
}
public void unloadSandwichSubmersibleImages() {

	manager.unload("data/sub.png");
	manager.unload("data/bubble.png");
	manager.unload("data/subpropeller.png");
	manager.unload("data/walkbutton.png");
	manager.unload("data/walkbutton2.png");
	manager.unload("data/menuicon.png");
	
}

public void loadAlvinSubmersibleImages() {

	manager.load("data/sub2.png",Texture.class);
	manager.load("data/bubble.png",Texture.class);
	manager.load("data/subpropeller2.png",Texture.class);
	manager.load("data/walkbutton.png",Texture.class);
	manager.load("data/walkbutton2.png",Texture.class);
	manager.load("data/menuicon.png",Texture.class);
	manager.load("data/launchbutton.png",Texture.class);
	
}

public void loadInGameMenuImages() {
	manager.load("data/menu.png",Texture.class);
	manager.load("data/back.png",Texture.class);
	
}

public void loadSharkImages() {
	manager.load("data/sharksheet.png",Texture.class);
}
public void loadFishImages() {
	manager.load("data/fishsheet.png",Texture.class);
	manager.load("data/mahimahi.png",Texture.class);
	manager.load("data/marlinsheet.png",Texture.class);
	manager.load("data/anglerfishsheet.png",Texture.class);
	manager.load("data/sardine.png",Texture.class);
	manager.load("data/blobfishsheet.png",Texture.class);
}
public void loadFuguImages() {
	manager.load("data/fugusheet.png",Texture.class);
	manager.load("data/fuguexplode.png",Texture.class);
	manager.load("data/inflate.mp3",Sound.class);
}
public void loadBasicArmImages() {
	manager.load("data/arm2.png",Texture.class);
}
public void loadMagneticArmImages() {
	manager.load("data/magarm2.png",Texture.class);
}
public void loadChopstickArmImages() {
	manager.load("data/arm3.png",Texture.class);
	manager.load("data/chopstick.png",Texture.class);
}
public void loadPhaserArmImages() {
	manager.load("data/phaserarm2.png",Texture.class);
	manager.load("data/laser.png",Texture.class);
	manager.load("data/laser.mp3",Sound.class);
	
}
public void loadSpudGunArmImages() {
	manager.load("data/spudgun.png",Texture.class);
	manager.load("data/potato.png",Texture.class);
	manager.load("data/spudgun.mp3",Sound.class);
	
}
public void loadSquidImages() {
	manager.load("data/squidbody.png",Texture.class);
	manager.load("data/tentaclesheet.png",Texture.class);
}
public void loadHatImages() {
	manager.load("data/santahat.png",Texture.class);
}

}
