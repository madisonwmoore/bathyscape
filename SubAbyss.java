package com.madison.Bathyscape.core;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class SubAbyss extends Game implements ApplicationListener {
	static final float WORLD_TO_BOX=0.01f;
	static final float BOX_WORLD_TO=100.0f;
	static final float SCREENWIDTH=1920;
	static final float SCREENHEIGHT=1080;
	
	AdHandler handler;
	boolean toggle;
	
	Level1 level;
	SubStore substore;
	MenuScreen menuscreen;
	HighScoreKeeper keeper;
	SubStorage storage;
	GameAssets assman;
	Settings settings;
	AwardScreen awards;
	AwardManager awardmanager;

	public SubAbyss(AdHandler handler){
		this.handler=handler;
	}
	
	public SubAbyss(){
		//this.handler=handler;
	}


	@Override
	public void create () {
		Gdx.input.setCatchBackKey(true);
		Gdx.input.setCatchMenuKey(true);
		assman=new GameAssets();
		keeper=new HighScoreKeeper();
		menuscreen=new MenuScreen(this);
		settings=new Settings(this);
		level=new Level1(this,null);
		storage=new SubStorage(menuscreen.manager,this);
		substore=new SubStore(this,menuscreen.manager);
	//	keeper=new HighScoreKeeper();
		awards=new AwardScreen(this);
		awardmanager=new AwardManager(this);
		setScreen(menuscreen);
	}
	

	
}