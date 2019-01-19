package com.madison.Bathyscape.core;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class InGameMenu {
Table table,messagetable;
Texture menutext,backtext, messageboardtext;
Image back,menu;
Image messageboard, award;
Stage stage;
Game game;
Label message,submessage;
BitmapFont font12,font13;
Container<Image> c;
LabelStyle labelstyle;

public InGameMenu(final Stage stage, final SubAbyss game, final Submersible sub){
this.stage=stage;
table=new Table();
game.assman.loadInGameMenuImages();
game.assman.manager.finishLoading();
//messageboardtext=game.assman.manager.get("data/messageboard.png",Texture.class);
menutext=game.assman.manager.get("data/menu.png",Texture.class);
backtext=game.assman.manager.get("data/back.png",Texture.class);
//messageboard=new Image(messageboardtext);
c =new Container<Image>(messageboard).center();
c.setFillParent(true);
c.setVisible(false);
stage.addActor(c);
messagetable=new Table();
this.game=game;

FreeTypeFontGenerator generator=new FreeTypeFontGenerator(Gdx.files.internal("data/theboldfont.ttf"));
FreeTypeFontParameter parameter = new FreeTypeFontParameter();
parameter.size = 50;
font12 = generator.generateFont(parameter); // font size 12 pixels
//generator.dispose();

 labelstyle = new LabelStyle();
labelstyle.font = font12;

labelstyle.fontColor =Color.WHITE;


//FreeTypeFontGenerator generator=new FreeTypeFontGenerator(Gdx.files.internal("data/theboldfont.ttf"));
//FreeTypeFontParameter parameter = new FreeTypeFontParameter();
parameter.size = 40;
font13 = generator.generateFont(parameter); // font size 12 pixels
generator.dispose();

LabelStyle labelstyle2 = new LabelStyle();
labelstyle2.font = font13;

labelstyle2.fontColor =Color.WHITE;


table.setFillParent(true);
messagetable.setFillParent(true);


menu = new Image(menutext);
back=new Image(backtext);



award=new Image();


//messagetable.add(stack).center();

message=new Label(null, labelstyle);
submessage=new Label(null, labelstyle2);

table.add(menu).center();
table.add().row();
table.add(back);
stage.addActor(table);
stage.addActor(messagetable);
menu.setTouchable(Touchable.enabled);
back.setTouchable(Touchable.enabled);
table.setVisible(false);
messagetable.setVisible(false);
menu.addListener(new ClickListener(){

	public boolean touchDown(InputEvent event, float x, float y,
            int pointer, int button){
		game.keeper.writeHighScore(sub.getScore());
		ArrayList<Award> al=game.awardmanager.updateAwards();
			game.keeper.addCurency(sub.getScore()); 
	
			game.keeper.addMahiMahiCount(game.level.mahimahicount);
			game.keeper.addMarlinCount(game.level.marlincount);
			game.keeper.addTunaCount(game.level.tunacount);
			game.keeper.addAnglerFishCount(game.level.anglerfishcountcatch);
			game.keeper.addBlobFishCount(game.level.blobfishcount);
			game.keeper.addFuguCount(game.level.fugucount);
			if(sub.getArm().getToolName().equals("Chopstick Arm")) {
				game.keeper.addChopstickKills(game.level.fugucount+game.level.marlincount+game.level.tunacount+game.level.mahimahicount+game.level.anglerfishcountcatch);
			}
			if(sub.getArm().getToolName().equals("Laser Arm")) {
				game.keeper.addLaserKills(game.level.fugucount+game.level.marlincount+game.level.tunacount+game.level.mahimahicount+game.level.anglerfishcountcatch);
			}
			if(sub.getArm().getToolName().equals("Spud Gun")) {
				game.keeper.addSpudGunKills(game.level.fugucount+game.level.marlincount+game.level.tunacount+game.level.mahimahicount+game.level.anglerfishcountcatch);
			}
	
		if(al.size()>0) {
		showAward(al);	
		}
		else if(game.keeper.writeHighScore(sub.getScore())) {
			showMesssage("New High Score!","Score: $"+sub.getScore());
		}
		else {

		showMesssage("Keep on fishing","Score: $"+sub.getScore());}

		Timer.schedule(new Task(){
             @Override
             public void run() {
             //	game.keeper.writeHighScore(sub.getScore());
             //	game.keeper.addCurency(sub.getScore());       	            
             //	game.keeper.addNumberRuns();
             //	game.awardmanager.updateAwards();
           //  	game.level=new Level1(game,menu);
             //	game.setScreen(game.level);
            	 game.keeper.addCurency(sub.getScore());
         		game.keeper.addNumberRuns();
              	game.awardmanager.updateAwards();
         		game.assman.unloadLevel1Sounds();
         		game.assman.unloadLevel1Images();
         		game.setScreen(game.menuscreen);
             }
		}
         , 3f        //    (delay)
            //    (seconds)
     );
		
	
	

	
//		game.keeper.addCurency(sub.getScore());
//		game.keeper.addNumberRuns();
//     	game.awardmanager.updateAwards();
//		game.assman.unloadLevel1Sounds();
//		game.assman.unloadLevel1Images();
//		game.setScreen(game.menuscreen);
		
		return true;
	}
	public void touchUp(InputEvent event, float x, float y,
            int pointer, int button){
		
		
		
	}
	
});

back.addListener(new ClickListener(){

	public boolean touchDown(InputEvent event, float x, float y,
            int pointer, int button){
		
		table.setVisible(false);
		Gdx.input.setInputProcessor(game.level.sub.getStage());
		game.level.paused=false;
		return true;
	}
	public void touchUp(InputEvent event, float x, float y,
            int pointer, int button){
		
		
		
	}
	
});

//Gdx.input.setInputProcessor(stage);
table.setVisible(false);
}
public Table getTable(){
return table;
}
public void showMenu(){
	Gdx.input.setInputProcessor(stage);
	c.setVisible(false);
	table.setVisible(true);
	messagetable.setVisible(false);
	table.setVisible(true);
}
public void showMesssage(String text,String submessagetext){
	Gdx.input.setInputProcessor(stage);
	message.setText(text);
	submessage.setText(submessagetext);
//	messageboard.addAction(Actions.fadeOut(5f));
	messagetable.add(message).center();
	messagetable.row();
	messagetable.add(submessage).center();
	//FadeOutAction f = new FadeOutAction();
	messagetable.addAction(Actions.fadeOut(5f));
	table.setVisible(false);
	messagetable.setVisible(true);
	//c.setVisible(true);
	if(Gdx.input.isKeyPressed(Keys.B)){
		showMenu();
	}

	
}
public void showMesssage(String text, float durationtime){
	
	Gdx.input.setInputProcessor(stage);
	message.setText(text);
	messageboard.addAction(Actions.fadeOut(durationtime));
	messagetable.add(message).center();
	//FadeOutAction f = new FadeOutAction();
messagetable.addAction(Actions.fadeOut(durationtime));
	table.setVisible(false);
	messagetable.setVisible(true);

	 Timer.schedule(new Task(){
         @Override
         public void run() {
        	 messagetable.setVisible(false);
        	
        	}
     }
     , 4f        //    (delay)
        //    (seconds)
 );

	//c.setVisible(true);
	if(Gdx.input.isKeyPressed(Keys.B)){
		showMenu();
	}

	
}

public void showAward(ArrayList<Award> awardlist){
	final ArrayList<Award> al=awardlist;
	Gdx.input.setInputProcessor(stage);
	long start;

		
		
		if(awardlist.size()>0) {
			for(int i=0;i<al.size();i++) {
			
			start=TimeUtils.millis();
		
		
	                	messagetable.add(new Image(al.get(i).getDrawable()));
	        			messagetable.add(new Label("New Award Unlocked!",labelstyle)).center();
	               	 	message.setText("New Award Unlocked!");
	               	 	table.setVisible(false);
	               	 	messagetable.row();
//			messagetable.add(new Image(al.get(i).getDrawable()));
//			messagetable.add(message).center();
//       	 	message.setText("New Award Unlocked!");
//       	 	table.setVisible(false);
       	 	messagetable.setVisible(true);
       	 	
       	 	
       	 	
       	 	
       	 	
	       messagetable.row();
			}
		}
	        	 
	        
	
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}



