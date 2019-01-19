package com.madison.Bathyscape.core;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Settings implements Screen{
OrthographicCamera camera;
SubAbyss game;
Table table;
Stage stage;
Image scalebutton, soundbutton, musicbutton;
int mode;
boolean soundonoff;
Slider slider;
Skin s;
//mode 0 is scale mode 1 is sound on off
BitmapFont font12;
BitmapFont font13;
Label scalelabel;
float gamescale;
ExtendViewport tableviewport;

public Settings(SubAbyss game) {
	this.game=game;
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
	detaillabelstyle.font = font12;
	detaillabelstyle.fontColor =Color.WHITE;
	final DecimalFormat df = new DecimalFormat("#.##");
	soundonoff=game.keeper.isSoundOn();
	gamescale=game.keeper.getScale();
	
	 s = new Skin((Gdx.files.internal("data/bathyscapeskin.json")));
	
	camera=new OrthographicCamera();
	camera.viewportHeight=Gdx.graphics.getHeight();
	camera.viewportWidth=Gdx.graphics.getWidth();
	camera.update();
	
	game.assman.manager.load("data/walkbutton2.png", Texture.class);
	game.assman.manager.finishLoading();
	Sprite right=new Sprite(game.assman.manager.get("data/walkbutton2.png",Texture.class));
	Sprite left=new Sprite(game.assman.manager.get("data/walkbutton2.png",Texture.class));
	Sprite back=new Sprite(game.assman.manager.get("data/backarrow.png",Texture.class));
	Sprite scale = new Sprite(game.assman.manager.get("data/scalebutton.png",Texture.class));
	Sprite sound = new Sprite(game.assman.manager.get("data/soundbutton.png",Texture.class));
	Sprite music = new Sprite(game.assman.manager.get("data/musicbutton.png",Texture.class));
	scalelabel=new Label("Scale: "+df.format(gamescale), detaillabelstyle);
	right.flip(true,false);
	
	Image rightarrow=new Image(right);
	Image backarrow=new Image(back);
	Image leftarrow=new Image(left);
	 scalebutton=new Image(scale);
	 soundbutton=new Image(sound);
	 musicbutton=new Image(music);
	
	 slider=new Slider(0, 100, 5, false, s);
	 slider.setVisible(false);
	 slider.setValue(game.keeper.getMusicVolume());
//	slider.setWidth(300f);
	Table sidetable = new Table();
	Table guitable = new Table();

	sidetable.add(scalebutton).pad(25f);
	sidetable.row();
	sidetable.add(soundbutton).pad(25f);
	sidetable.row();
	sidetable.add(musicbutton).pad(25f);

	guitable.center().add(scalelabel).pad(20f);
	guitable.row();
	guitable.center().add(slider).width(400f);;
	
	tableviewport=new ExtendViewport(1920,1080);
	
	stage=new Stage(tableviewport);
	table=new Table();
	
	table.setFillParent(true);
	stage.addActor(table);
	table.top();
	table.add(backarrow).expandX().colspan(3).top().left().pad(25f);
	table.row();
	table.add().colspan(2);
	table.center().add(guitable).expandY().colspan(2);;
	table.right().add(sidetable).colspan(2).right();
	table.row();
	
//	
		table.add(leftarrow).expandX().colspan(1).left().bottom().pad(45f);
		table.add().colspan(2).center().pad(45f);
		table.add(rightarrow).expandX().colspan(3).right().bottom().pad(45f);
	
	backarrow.addListener(new ClickListener(){

		public boolean touchDown(InputEvent event, float x, float y,
	            int pointer, int button){
				game.keeper.updateScale(gamescale);
				game.keeper.setSound(soundonoff);
				game.setScreen(game.menuscreen);
				game.keeper.setMusicVolume(MathUtils.round(slider.getValue()));
			return true;
		}
		public void touchUp(InputEvent event, float x, float y,
	            int pointer, int button){
			
			
		}
		
	});
	leftarrow.addListener(new ClickListener(){

		public boolean touchDown(InputEvent event, float x, float y,
	            int pointer, int button){
			if(mode==0) {
			gamescale=gamescale-0.05f;
			scalelabel.setText("Scale: "+df.format(gamescale));}
			if(mode==1) {
				if(soundonoff) {
					soundonoff=false;
				scalelabel.setText("Sound: "+"Off");}
				else {
					soundonoff=true;
					scalelabel.setText("Sound: "+"On");
				}
			}
			return true;
		}
		public void touchUp(InputEvent event, float x, float y,
	            int pointer, int button){
			
			
		}
		
	});
	rightarrow.addListener(new ClickListener(){

		public boolean touchDown(InputEvent event, float x, float y,
	            int pointer, int button){
			if(mode==0) {
			gamescale=gamescale+0.05f;
			scalelabel.setText("Scale: "+df.format(gamescale));}
			if(mode==1) {
				if(soundonoff) {
					soundonoff=false;
				scalelabel.setText("Sound: "+"Off");}
				else {
					soundonoff=true;
					scalelabel.setText("Sound: "+"On");
				}}
			return true;
		}
		public void touchUp(InputEvent event, float x, float y,
	            int pointer, int button){
			
			
		}
		
	});
	scalebutton.addListener(new ClickListener(){

		public boolean touchDown(InputEvent event, float x, float y,
	            int pointer, int button){
			
			mode=0;
			slider.setVisible(false);
			scalelabel.setText("Scale: "+df.format(gamescale));
			return true;
		}
		public void touchUp(InputEvent event, float x, float y,
	            int pointer, int button){
			
			
		}
		
	});
	soundbutton.addListener(new ClickListener(){

		public boolean touchDown(InputEvent event, float x, float y,
	            int pointer, int button){
			
			mode=1;
			slider.setVisible(false);
			if(game.keeper.isSoundOn()) {
			scalelabel.setText("Sound: "+"On");}
			else {
				scalelabel.setText("Sound: "+"Off");
			}
			return true;
		}
		public void touchUp(InputEvent event, float x, float y,
	            int pointer, int button){
			
			
		}
		
	});
	musicbutton.addListener(new ClickListener(){

		public boolean touchDown(InputEvent event, float x, float y,
	            int pointer, int button){
			
			mode=2;
		//	slider.setSize(300f, 100f);
		//	slider.setWidth(1600f);
			slider.setVisible(true);
			
		//	table.add(slider);
			//if(game.keeper.isSoundOn()) {
		
		
			//final DecimalFormat ds = new DecimalFormat("#.#");
			int number=MathUtils.round(slider.getValue());
			if(game.storage.theme!=null) {
			game.storage.theme.setVolume(0.5f*(MathUtils.round(slider.getValue()/100f)));}
			scalelabel.setText("Music: "+number);
		
		//	else {
			//	scalelabel.setText("Music: "+"Off");
			//}
			return true;
		}
		public void touchUp(InputEvent event, float x, float y,
	            int pointer, int button){
			
			
		}
		
	});
//	table.setDebug(true);
	
	Gdx.input.setInputProcessor(stage);
}

@Override
public void render(float delta) {
	Gdx.gl.glClearColor(20/255f,98/255f,120/255f,1);
	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	  tableviewport.apply();
	 stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
	    stage.draw();
	//    slider.setWidth(300f);
		if(mode==0) {
			scalebutton.setColor(1, 1, 1,1f);
			soundbutton.setColor(1, 1, 1, 0.3f);
			musicbutton.setColor(1, 1, 1, 0.3f);
			
		}

	else if(mode==1) {
		
		scalebutton.setColor(1, 1, 1,0.3f);
		soundbutton.setColor(1, 1, 1, 1f);
		musicbutton.setColor(1, 1, 1, 0.3f);
		}

	else if(mode==2) {
		int number=MathUtils.round(slider.getValue());
		if(game.storage.theme!=null) {
			game.storage.theme.setVolume(0.5f*(MathUtils.round(slider.getValue())/100f));}
		//slider.setWidth(300f);
		//slider.setOriginX(150f);
		scalelabel.setText("Music: "+number);
		//scalelabel.setText("Music: "+slider.getPercent()*100);
		scalebutton.setColor(1, 1, 1,0.3f);
		soundbutton.setColor(1, 1, 1, 0.3f);
		musicbutton.setColor(1, 1, 1, 1);
		}
	
	if(game.storage.theme!=null&&game.storage.theme.isPlaying()&&!soundonoff) {
		game.storage.theme.stop();
	}
	
}

@Override
public void resize(int width, int height) {
	// TODO Auto-generated method stub
	tableviewport.update(width,height,true);
	 stage.getViewport().update(width, height, true);
	 
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
	if(s!=null) {
	s.dispose();}
}}
