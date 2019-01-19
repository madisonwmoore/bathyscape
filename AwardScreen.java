package com.madison.Bathyscape.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Scaling;

public class AwardScreen implements Screen {
SubAbyss game;
BitmapFont font12,font13;
Stage stage;
Table table;
TextureRegion [] awardframe;
AwardManager awardmanager;
SpriteDrawable lockeddrawable,a0drawable,a1drawable,a2drawable,a3drawable,a4drawable,a5drawable,a6drawable,a7drawable,a8drawable,a9drawable,a10drawable,a11drawable,a12drawable,a13drawable,a14drawable;
Image awardimage,awardimage1,awardimage2,awardimage3,awardimage4,awardimage5,awardimage6,awardimage7,awardimage8,awardimage9,awardimage10,awardimage11,awardimage12,awardimage13,awardimage14;
Label namelabel,detaillabel;	
public AwardScreen(SubAbyss game) {
		this.game=game;
		this.awardmanager=game.awardmanager;
	}
	
	@Override
	public void show() {
		FreeTypeFontGenerator generator=new FreeTypeFontGenerator(Gdx.files.internal("data/theboldfont.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		FreeTypeFontParameter parameter2 = new FreeTypeFontParameter();
		parameter.size = 50;
		font12 = generator.generateFont(parameter); // font size 12 pixels
		parameter2.size=30;
		font13=generator.generateFont(parameter2); 
		generator.dispose();
		
		LabelStyle labelstyle = new LabelStyle();
		labelstyle.font = font12;
		labelstyle.fontColor =Color.WHITE;
		
		LabelStyle detaillabelstyle = new LabelStyle();
		detaillabelstyle.font = font13;
		detaillabelstyle.fontColor =Color.WHITE;
		
		//scrollpanestyle.background=s.getDrawable("default");
		
	//	scrollpane=(game.)
		
		game.assman.manager.load("data/walkbutton2.png", Texture.class);
		game.assman.manager.finishLoading();
		Sprite right=new Sprite(game.assman.manager.get("data/walkbutton2.png",Texture.class));
		Sprite left=new Sprite(game.assman.manager.get("data/walkbutton2.png",Texture.class));
		Sprite back=new Sprite(game.assman.manager.get("data/backarrow.png",Texture.class));
//		Texture awardtext=game.assman.manager.get("data/awardsheet.png",Texture.class);
//		
//		int FRAME_COLS=4;
//		int	FRAME_ROWS=4;
//		TextureRegion[][] tmp = TextureRegion.split(awardtext, awardtext.getWidth() / 
//				FRAME_COLS, awardtext.getHeight() / FRAME_ROWS);                                
//				        awardframe = new TextureRegion[FRAME_COLS * FRAME_ROWS];
//				        int index1 = 0;
//				        for (int i = 0; i < FRAME_ROWS; i++) {
//				                for (int j = 0; j < FRAME_COLS; j++) {
//				                        awardframe[index1++] = tmp[i][j];
//				                }
//				        }
//		
		a0drawable=new SpriteDrawable(new Sprite(game.awardmanager.getAwardTexture(0)));
		a1drawable=new SpriteDrawable(new Sprite(game.awardmanager.getAwardTexture(1)));
		a2drawable=new SpriteDrawable(new Sprite(game.awardmanager.getAwardTexture(2)));
		a3drawable=new SpriteDrawable(new Sprite(game.awardmanager.getAwardTexture(3)));
		a4drawable=new SpriteDrawable(new Sprite(game.awardmanager.getAwardTexture(4)));
		a5drawable=new SpriteDrawable(new Sprite(game.awardmanager.getAwardTexture(5)));
		a6drawable=new SpriteDrawable(new Sprite(game.awardmanager.getAwardTexture(6)));
		a7drawable=new SpriteDrawable(new Sprite(game.awardmanager.getAwardTexture(7)));
		a8drawable=new SpriteDrawable(new Sprite(game.awardmanager.getAwardTexture(8)));
		a9drawable=new SpriteDrawable(new Sprite(game.awardmanager.getAwardTexture(9)));
		a10drawable=new SpriteDrawable(new Sprite(game.awardmanager.getAwardTexture(10)));
		a11drawable=new SpriteDrawable(new Sprite(game.awardmanager.getAwardTexture(11)));
		a12drawable=new SpriteDrawable(new Sprite(game.awardmanager.getAwardTexture(12)));
		a13drawable=new SpriteDrawable(new Sprite(game.awardmanager.getAwardTexture(13)));
		a14drawable=new SpriteDrawable(new Sprite(game.awardmanager.getAwardTexture(14)));
		lockeddrawable=new SpriteDrawable(new Sprite(game.awardmanager.getAwardTexture(15)));
		
		
		right.flip(true,false);
		
		Image rightarrow=new Image(right);
		Image backarrow=new Image(back);
		Image leftarrow=new Image(left);
		if(!game.awardmanager.getAward(0).isLocked()){
		 awardimage=new Image(a0drawable);}
		else { awardimage=new Image(lockeddrawable);}
		if(!game.awardmanager.getAward(1).isLocked()){
			 awardimage1=new Image(a1drawable);}
			else { awardimage1=new Image(lockeddrawable);}
		if(!game.awardmanager.getAward(2).isLocked()){
			 awardimage2=new Image(a2drawable);}
			else { awardimage2=new Image(lockeddrawable);}
		if(!game.awardmanager.getAward(3).isLocked()){
			 awardimage3=new Image(a3drawable);}
			else { awardimage3=new Image(lockeddrawable);}
		if(!game.awardmanager.getAward(4).isLocked()){
			 awardimage4=new Image(a4drawable);}
			else { awardimage4=new Image(lockeddrawable);}
		if(!game.awardmanager.getAward(5).isLocked()){
			 awardimage5=new Image(a5drawable);}
			else { awardimage5=new Image(lockeddrawable);}
		if(!game.awardmanager.getAward(6).isLocked()){
			 awardimage6=new Image(a6drawable);}
			else { awardimage6=new Image(lockeddrawable);}
		if(!game.awardmanager.getAward(7).isLocked()){
			 awardimage7=new Image(a7drawable);}
			else { awardimage7=new Image(lockeddrawable);}
		if(!game.awardmanager.getAward(8).isLocked()){
			 awardimage8=new Image(a8drawable);}
			else { awardimage8=new Image(lockeddrawable);}
		if(!game.awardmanager.getAward(9).isLocked()){
			 awardimage9=new Image(a9drawable);}
			else { awardimage9=new Image(lockeddrawable);}
		if(!game.awardmanager.getAward(10).isLocked()){
			 awardimage10=new Image(a10drawable);}
			else { awardimage10=new Image(lockeddrawable);}
		if(!game.awardmanager.getAward(11).isLocked()){
			 awardimage11=new Image(a11drawable);}
			else { awardimage11=new Image(lockeddrawable);}
		if(!game.awardmanager.getAward(12).isLocked()){
			 awardimage12=new Image(a12drawable);}
			else { awardimage12=new Image(lockeddrawable);}
		if(!game.awardmanager.getAward(13).isLocked()){
			 awardimage13=new Image(a13drawable);}
			else { awardimage13=new Image(lockeddrawable);}
		if(!game.awardmanager.getAward(14).isLocked()){
			 awardimage14=new Image(a14drawable);}
			else { awardimage14=new Image(lockeddrawable);}

//		Image awardimage1=new Image(game.awardmanager.getAwardTexture(1));
//		Image awardimage2=new Image(game.awardmanager.getAwardTexture(2));
//		Image awardimage3=new Image(game.awardmanager.getAwardTexture(3));
//		Image awardimage4=new Image(game.awardmanager.getAwardTexture(4));
//		Image awardimage5=new Image(game.awardmanager.getAwardTexture(5));
//		Image awardimage6=new Image(game.awardmanager.getAwardTexture(6));
//		Image awardimage7=new Image(game.awardmanager.getAwardTexture(7));
//		Image awardimage8=new Image(game.awardmanager.getAwardTexture(8));
//		Image awardimage9=new Image(game.awardmanager.getAwardTexture(9));
//		Image awardimage10=new Image(game.awardmanager.getAwardTexture(10));
//		Image awardimage11=new Image(game.awardmanager.getAwardTexture(11));
//		Image awardimage12=new Image(game.awardmanager.getAwardTexture(12));
//		Image awardimage13=new Image(game.awardmanager.getAwardTexture(13));
//		Image awardimage14=new Image(game.awardmanager.getAwardTexture(14));
//		Image awardimage15=new Image(game.awardmanager.getAwardTexture(15));
		
		
		namelabel=new Label("", labelstyle);
		detaillabel=new Label("", detaillabelstyle);
		
		stage=new Stage();
		table=new Table();
		Table scrolltable=new Table();
		Skin s = new Skin((Gdx.files.internal("data/bathyscapeskin.json")));
		ScrollPane scrollpane=new ScrollPane(scrolltable,s);
	//	table.debug();
		stage.addActor(table);
		boolean enable_x = true;
		boolean enable_y = true;
		//scrollpane.setForceScroll(enable_x,enable_y);
		backarrow.setScaling(Scaling.fill);
		table.setFillParent(true);
		table.top();
		table.top().left().add(backarrow).left().expandX().colspan(3).pad(25f).height(backarrow.getHeight());
		table.row();
		
		scrolltable.add(awardimage);
		scrolltable.add(awardimage1);
		scrolltable.add(awardimage2);
		scrolltable.row();
		scrolltable.add(awardimage3);
		scrolltable.add(awardimage4);
		scrolltable.add(awardimage5);
		scrolltable.row();
		scrolltable.add(awardimage6);
		scrolltable.add(awardimage7);
		scrolltable.add(awardimage8);
		scrolltable.row();
		scrolltable.add(awardimage9);
		scrolltable.add(awardimage10);
		scrolltable.add(awardimage11);
		scrolltable.row();
		scrolltable.add(awardimage12);
		scrolltable.add(awardimage13);
		scrolltable.add(awardimage14);
		//scrollpane.setScaleY(0.9f);
		scrollpane.setHeight(20f);
		scrollpane.setWidget(scrolltable);
		table.center().add(scrollpane).colspan(3).fillX();
	//	scrollpane.setFillParent(true);
		table.row();
		table.center().add(namelabel).colspan(3).pad(25f);
		table.row();
		table.center().add(detaillabel).colspan(3).pad(25f);
		
	//	
		//table.row().pad(25f);
	
//			table.add().expandX().colspan(1).left().bottom().pad(45f);
//			table.add().colspan(1).center().pad(45f);
//			table.add().expandX().colspan(1).right().bottom().pad(45f);
		
			
			

			backarrow.addListener(new ClickListener(){

				public boolean touchDown(InputEvent event, float x, float y,
			            int pointer, int button){
					
						game.setScreen(game.menuscreen);
					return true;
				}
				public void touchUp(InputEvent event, float x, float y,
			            int pointer, int button){
					
					
				}
				
			});
			
			
			awardimage.addListener(new ClickListener(){

				public boolean touchDown(InputEvent event, float x, float y,
			            int pointer, int button){
					if(!game.awardmanager.getAward(0).isLocked()) {
							namelabel.setText("Get Hooked");
							detaillabel.setText("Take your first dive");}
					else {
						namelabel.setText("LOCKED");
						detaillabel.setText("Take your first dive");
					}
					return true;
				}
				public void touchUp(InputEvent event, float x, float y,
			            int pointer, int button){
					
					
				}
				
			});
			awardimage1.addListener(new ClickListener(){

				public boolean touchDown(InputEvent event, float x, float y,
			            int pointer, int button){
					if(!game.awardmanager.getAward(1).isLocked()) {
							namelabel.setText("Spud Gunner");
							detaillabel.setText("Get 100 kills with the spud gun.");}
					else {
						namelabel.setText("LOCKED");
						detaillabel.setText("Get 100 kills with the spud gun.");
					}
					return true;
				}
				public void touchUp(InputEvent event, float x, float y,
			            int pointer, int button){
				}
				
			});
			
			awardimage2.addListener(new ClickListener(){

				public boolean touchDown(InputEvent event, float x, float y,
			            int pointer, int button){
					if(!game.awardmanager.getAward(2).isLocked()) {
							namelabel.setText("Fortunate Sub");
							detaillabel.setText("Amass a fortune of $50,000");}
					else {
						namelabel.setText("LOCKED");
						detaillabel.setText("Amass a fortune of $50,000");
					}
					return true;
				}
				public void touchUp(InputEvent event, float x, float y,
			            int pointer, int button){
					
					
				}
				
			});
			awardimage3.addListener(new ClickListener(){

				public boolean touchDown(InputEvent event, float x, float y,
			            int pointer, int button){
					if(!game.awardmanager.getAward(3).isLocked()) {
							namelabel.setText("Fish Killer");
							detaillabel.setText("Catch 100 fish");}
					else {
						namelabel.setText("LOCKED");
						detaillabel.setText("catch 100 fish");
					}
					return true;
				}
				public void touchUp(InputEvent event, float x, float y,
			            int pointer, int button){
					
					
				}
				
			});
			awardimage4.addListener(new ClickListener(){

				public boolean touchDown(InputEvent event, float x, float y,
			            int pointer, int button){
					if(!game.awardmanager.getAward(4).isLocked()) {
							namelabel.setText("Su(b)shi Chef");
							detaillabel.setText("Catch 100 fish with the chopstick arm");}
					else {
						namelabel.setText("LOCKED");
						detaillabel.setText("Catch 100 fish with the chopstick arm");
					}
					return true;
				}
				public void touchUp(InputEvent event, float x, float y,
			            int pointer, int button){
					
					
				}
				
			});
			
			awardimage5.addListener(new ClickListener(){

				public boolean touchDown(InputEvent event, float x, float y,
			            int pointer, int button){
					if(!game.awardmanager.getAward(5).isLocked()) {
							namelabel.setText("Master Blaster");
							detaillabel.setText("Catch 100 fish with the Laser Arm");}
					else {
						namelabel.setText("LOCKED");
						detaillabel.setText("Catch 100 fish with the Laser Arm");
					}
					return true;
				}
				public void touchUp(InputEvent event, float x, float y,
			            int pointer, int button){
					
					
				}
				
			});
			
			awardimage6.addListener(new ClickListener(){

				public boolean touchDown(InputEvent event, float x, float y,
			            int pointer, int button){
					if(!game.awardmanager.getAward(6).isLocked()) {
							namelabel.setText("King Mackerel");
							detaillabel.setText("Earn a highscore over $10,000");}
					else {
						namelabel.setText("LOCKED");
						detaillabel.setText("Earn a highscore over $10,000");
					}
					return true;
				}
				public void touchUp(InputEvent event, float x, float y,
			            int pointer, int button){
					
					
				}
				
			});
			awardimage7.addListener(new ClickListener(){

				public boolean touchDown(InputEvent event, float x, float y,
			            int pointer, int button){
					if(!game.awardmanager.getAward(7).isLocked()) {
							namelabel.setText("Ghost Ship");
							detaillabel.setText("Die 100 times");}
					else {
						namelabel.setText("LOCKED");
						detaillabel.setText("MYSTERY");
					}
					return true;
				}
				public void touchUp(InputEvent event, float x, float y,
			            int pointer, int button){
					
					
				}
				
			});
			awardimage8.addListener(new ClickListener(){

				public boolean touchDown(InputEvent event, float x, float y,
			            int pointer, int button){
					if(!game.awardmanager.getAward(8).isLocked()) {
							namelabel.setText("fin can");
							detaillabel.setText("Catch 500 Tuna");}
					else {
						namelabel.setText("LOCKED");
						detaillabel.setText("Catch 500 Tuna");
					}
					return true;
				}
				public void touchUp(InputEvent event, float x, float y,
			            int pointer, int button){
					
					
				}
				
			});
			awardimage9.addListener(new ClickListener(){

				public boolean touchDown(InputEvent event, float x, float y,
			            int pointer, int button){
					if(!game.awardmanager.getAward(9).isLocked()) {
							namelabel.setText("");
							detaillabel.setText("");}
					else {
						namelabel.setText("LOCKED");
						detaillabel.setText("mystery");
					}
					return true;
				}
				public void touchUp(InputEvent event, float x, float y,
			            int pointer, int button){
					
					
				}
				
			});
			awardimage10.addListener(new ClickListener(){

				public boolean touchDown(InputEvent event, float x, float y,
			            int pointer, int button){
					if(!game.awardmanager.getAward(10).isLocked()) {
							namelabel.setText("");
							detaillabel.setText("");}
					else {
						namelabel.setText("LOCKED");
						detaillabel.setText("mystery");
					}
					return true;
				}
				public void touchUp(InputEvent event, float x, float y,
			            int pointer, int button){
					
					
				}
				
			});
			awardimage11.addListener(new ClickListener(){

				public boolean touchDown(InputEvent event, float x, float y,
			            int pointer, int button){
					if(!game.awardmanager.getAward(11).isLocked()) {
							namelabel.setText("");
							detaillabel.setText("");}
					else {
						namelabel.setText("LOCKED");
						detaillabel.setText("mystery");
					}
					return true;
				}
				public void touchUp(InputEvent event, float x, float y,
			            int pointer, int button){
					
					
				}
				
			});
			awardimage12.addListener(new ClickListener(){

				public boolean touchDown(InputEvent event, float x, float y,
			            int pointer, int button){
					if(!game.awardmanager.getAward(12).isLocked()) {
							namelabel.setText("");
							detaillabel.setText("");}
					else {
						namelabel.setText("LOCKED");
						detaillabel.setText("mystery");
					}
					return true;
				}
				public void touchUp(InputEvent event, float x, float y,
			            int pointer, int button){
					
					
				}
				
			});
			awardimage13.addListener(new ClickListener(){

				public boolean touchDown(InputEvent event, float x, float y,
			            int pointer, int button){
					if(!game.awardmanager.getAward(13).isLocked()) {
							namelabel.setText("");
							detaillabel.setText("");}
					else {
						namelabel.setText("LOCKED");
						detaillabel.setText("mystery");
					}
					return true;
				}
				public void touchUp(InputEvent event, float x, float y,
			            int pointer, int button){
					
					
				}
				
			});
			awardimage14.addListener(new ClickListener(){

				public boolean touchDown(InputEvent event, float x, float y,
			            int pointer, int button){
					if(!game.awardmanager.getAward(14).isLocked()) {
							namelabel.setText("");
							detaillabel.setText("");}
					else {
						namelabel.setText("LOCKED");
						detaillabel.setText("mystery");
					}
					return true;
				}
				public void touchUp(InputEvent event, float x, float y,
			            int pointer, int button){
					
					
				}
				
			});
			
			
			Gdx.input.setInputProcessor(stage);
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(20/255f,98/255f,120/255f,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
	    stage.draw();
	    
	    if(game.awardmanager.getAward(0).isLocked()) {
	    	awardimage.setDrawable(lockeddrawable);
	    }
	    else {
	    	awardimage.setDrawable(a0drawable);
	    }
	    
	    if(game.awardmanager.getAward(1).isLocked()) {
	    	awardimage1.setDrawable(lockeddrawable);
	    }
	    else {
	    	awardimage1.setDrawable(a1drawable);
	    }
	    
	    if(game.awardmanager.getAward(2).isLocked()) {
	    	awardimage2.setDrawable(lockeddrawable);
	    }
	    else {
	    	awardimage2.setDrawable(a2drawable);
	    }
	    if(game.awardmanager.getAward(3).isLocked()) {
	    	awardimage3.setDrawable(lockeddrawable);
	    }
	    else {
	    	awardimage3.setDrawable(a3drawable);
	    }
	    if(game.awardmanager.getAward(4).isLocked()) {
	    	awardimage4.setDrawable(lockeddrawable);
	    }
	    else {
	    	awardimage4.setDrawable(a4drawable);
	    }
	    if(game.awardmanager.getAward(5).isLocked()) {
	    	awardimage5.setDrawable(lockeddrawable);
	    }
	    else {
	    	awardimage5.setDrawable(a5drawable);
	    }
	    if(game.awardmanager.getAward(6).isLocked()) {
	    	awardimage6.setDrawable(lockeddrawable);
	    }
	    else {
	    	awardimage6.setDrawable(a6drawable);
	    }
	    if(game.awardmanager.getAward(7).isLocked()) {
	    	awardimage7.setDrawable(lockeddrawable);
	    }
	    else {
	    	awardimage7.setDrawable(a7drawable);
	    }
	    if(game.awardmanager.getAward(8).isLocked()) {
	    	awardimage8.setDrawable(lockeddrawable);
	    }
	    else {
	    	awardimage8.setDrawable(a8drawable);
	    }
	    if(game.awardmanager.getAward(9).isLocked()) {
	    	awardimage9.setDrawable(lockeddrawable);
	    }
	    else {
	    	awardimage9.setDrawable(a9drawable);
	    }
	    if(game.awardmanager.getAward(10).isLocked()) {
	    	awardimage10.setDrawable(lockeddrawable);
	    }
	    else {
	    	awardimage10.setDrawable(a10drawable);
	    }
	    if(game.awardmanager.getAward(11).isLocked()) {
	    	awardimage11.setDrawable(lockeddrawable);
	    }
	    else {
	    	awardimage11.setDrawable(a11drawable);
	    }
	    if(game.awardmanager.getAward(12).isLocked()) {
	    	awardimage12.setDrawable(lockeddrawable);
	    }
	    else {
	    	awardimage12.setDrawable(a12drawable);
	    }
	    if(game.awardmanager.getAward(13).isLocked()) {
	    	awardimage13.setDrawable(lockeddrawable);
	    }
	    else {
	    	awardimage13.setDrawable(a13drawable);
	    }
	    if(game.awardmanager.getAward(14).isLocked()) {
	    	awardimage14.setDrawable(lockeddrawable);
	    }
	    else {
	    	awardimage14.setDrawable(a14drawable);
	    }
//	    if(game.awardmanager.getAward(15).isLocked()) {
//	    	awardimage2.setDrawable(lockeddrawable);
//	    }
//	    else {
//	    	awardimage2.setDrawable(a2drawable);
//	    }
		
		if(Gdx.input.isKeyPressed(Keys.U)) {
			game.awardmanager.getAward(0).setLocked(false);
			game.awardmanager.getAward(1).setLocked(false);
			game.awardmanager.getAward(2).setLocked(false);
			game.awardmanager.getAward(3).setLocked(false);
			game.awardmanager.getAward(4).setLocked(false);
			game.awardmanager.getAward(5).setLocked(false);
			game.awardmanager.getAward(6).setLocked(false);
			game.awardmanager.getAward(7).setLocked(false);
			game.awardmanager.getAward(8).setLocked(false);
			game.awardmanager.getAward(9).setLocked(false);
			game.awardmanager.getAward(10).setLocked(false);
			game.awardmanager.getAward(11).setLocked(false);
			game.awardmanager.getAward(12).setLocked(false);
			game.awardmanager.getAward(13).setLocked(false);
			game.awardmanager.getAward(14).setLocked(false);
			
		}
		if(Gdx.input.isKeyPressed(Keys.L)) {
			game.awardmanager.getAward(0).setLocked(true);
			game.awardmanager.getAward(1).setLocked(true);
			game.awardmanager.getAward(2).setLocked(true);
			game.awardmanager.getAward(3).setLocked(true);
			game.awardmanager.getAward(4).setLocked(true);
			game.awardmanager.getAward(5).setLocked(true);
			game.awardmanager.getAward(6).setLocked(true);
			game.awardmanager.getAward(7).setLocked(true);
			game.awardmanager.getAward(8).setLocked(true);
			game.awardmanager.getAward(9).setLocked(true);
			game.awardmanager.getAward(10).setLocked(true);
			game.awardmanager.getAward(11).setLocked(true);
			game.awardmanager.getAward(12).setLocked(true);
			game.awardmanager.getAward(13).setLocked(true);
			game.awardmanager.getAward(14).setLocked(true);
		//	awardimage.setDrawable(lockeddrawable);
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
