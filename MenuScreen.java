package com.madison.Bathyscape.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MenuScreen  implements Screen {

	Stage stage;
	
	Table table;
	Skin skin;
	Viewport viewport;
	SpriteBatch batch;
	Sprite textsprite,play,setting,trophy,title;
	Texture texttext,marlintext,customtext;
	Image playbutton,custombutton,settingbutton,trophybutton,menutitle;
	SubAbyss game;
	long lastrun;
	ExtendViewport fv;
	boolean faded;
	OrthographicCamera camera;
	Image text,playimage;
//	ShapeRenderer shaperenderer;
public	AssetManager manager;
	MenuScreen menu;


	
	public MenuScreen (SubAbyss game){
        this.game = game;
        menu=this;
		manager=new AssetManager(new InternalFileHandleResolver());
       
}



	@Override
	public void show() {
		// TODO Auto-generated method stub
	//	viewport=new FillViewport(1920,1080);
		game.assman.loadMenuImages();
		camera=new OrthographicCamera();
		camera.viewportHeight=Gdx.graphics.getHeight();
		camera.viewportWidth=Gdx.graphics.getWidth();
		camera.position.set(camera.viewportWidth *.5f, camera.viewportHeight * .5f, 0f);
		fv=new ExtendViewport(1920,1080,camera);
		game.assman.manager.finishLoading();
		stage=new Stage(fv);
		texttext=game.assman.manager.get("data/menutext.png",Texture.class);

		
		Texture playtext=game.assman.manager.get("data/play.png",Texture.class);
		Texture settingstext=game.assman.manager.get("data/settingsicon.png",Texture.class);
		Texture trophytext=game.assman.manager.get("data/trophyicon.png",Texture.class);
		Texture titletext=game.assman.manager.get("data/menutitle.png",Texture.class);
		
		textsprite=new Sprite(texttext);
		play=new Sprite(playtext);
		setting=new Sprite(settingstext);
		trophy=new Sprite(trophytext);
		title=new Sprite(titletext);
		
		
		skin=new Skin();
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("white", new Texture(pixmap));

	
		skin.add("default", new BitmapFont());

		
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
		textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
		textButtonStyle.font = skin.getFont("default");
		
		
		LabelStyle labelstyle = new LabelStyle();
	
		
		labelstyle.fontColor =Color.WHITE;
		
		
		skin.add("default", textButtonStyle);
		skin.add("default",labelstyle);
		
		
	//	shaperenderer=new ShapeRenderer();
	//	tableviewport=new ExtendViewport(1920,1080);
		table=new Table();
		table.setFillParent(true);
		stage.addActor(table);
	
		 playimage = new Image(play);
		 settingbutton=new Image(setting);
		 trophybutton=new Image(trophy);
		 menutitle=new Image(title);
		 table.top();
		// table.add(settingbutton).expandX().colspan(3).top().right().pad(70f);
		// table.row();
	//	table.add().height(320f).row();
		table.center().add(menutitle).colspan(2).padTop(90f).padBottom(30f);
		table.row();
		table.center().add(playimage).colspan(2).expandY().padTop(70f);
		table.row();
		 table.center().add(settingbutton).padBottom(100f);
		 table.center().add(trophybutton).padBottom(100f);
	
	//table.debug();

		Gdx.input.setCatchBackKey(true);
		batch=new SpriteBatch();
		batch.setProjectionMatrix(camera.combined);
		playimage.setTouchable(Touchable.enabled);

		
		playimage.addListener(new ClickListener(){

			public boolean touchDown(InputEvent event, float x, float y,
		            int pointer, int button){

				game.substore=new SubStore(game, null);
				
					game.setScreen(game.substore);
				return true;
			}
			public void touchUp(InputEvent event, float x, float y,
		            int pointer, int button){
				
			
				
			}
			
		});
		
		settingbutton.addListener(new ClickListener(){

			public boolean touchDown(InputEvent event, float x, float y,
		            int pointer, int button){

				 game.setScreen(game.settings);
				return true;
			}
			public void touchUp(InputEvent event, float x, float y,
		            int pointer, int button){
				
			
				
			}
			
		});
		
		trophybutton.addListener(new ClickListener(){

			public boolean touchDown(InputEvent event, float x, float y,
		            int pointer, int button){

				 game.setScreen(game.awards);
				return true;
			}
			public void touchUp(InputEvent event, float x, float y,
		            int pointer, int button){
				
			
				
			}
			
		});

		
		
		Gdx.input.setInputProcessor(stage);
		skin.dispose();
		pixmap.dispose();
		
	}



	@Override
	public void render(float delta) {
		
		// TODO Auto-generated method stub
		Gdx.input.setInputProcessor(stage);
		 Gdx.gl.glClearColor(23/255f,114/255f,155/255f,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	
		fv.apply();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		textsprite.setPosition(camera.position.x-textsprite.getWidth()/2, camera.position.y-textsprite.getHeight()/2);
		textsprite.draw(batch);
		batch.end();
		
		if(TimeUtils.timeSinceMillis(lastrun)>1500f) {
			lastrun=TimeUtils.millis();
		if(faded) {	
		
			playimage.addAction(Actions.fadeIn(1.6f));
			faded=false;
		}
		else {
			
			playimage.addAction(Actions.fadeOut(1.6f));
			faded=true;
		}
		}
	//	viewport.apply();
		 stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		    stage.draw();
		    
//		   if(Gdx.input.isKeyPressed(Keys.S)) {
//			   game.setScreen(game.settings);
//		   }
		
		
		if (Gdx.input.isKeyPressed(Keys.ESCAPE)||Gdx.input.isButtonPressed(Buttons.BACK)){
			if(game.level.collide!=null) {
			game.level.collide.dispose();}
			if(game.level.boom!=null) {
			game.level.boom.dispose();}
		//	if(game.level.theme!=null) {
				//game.level.theme.dispose();
		//	}
			if(game.level.armsound!=null) {
				game.level.armsound.dispose();
			}
		dispose();
	    	Gdx.app.exit();
	    }
		
		camera.update();
	}



	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
//		viewport.update(width, height);
		fv.update(width, height, true);
		stage.getViewport().update(width, height,true);
	//fv.
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
//		game.level.theme.dispose();
//		game.level.armsound.dispose();
//		game.level.boom.dispose();
//		game.level.collide.dispose();
		game.assman.manager.clear();
		game.assman.manager.dispose();
		game.storage.dispose();
		game.settings.dispose();
	}

}
