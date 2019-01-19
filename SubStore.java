package com.madison.Bathyscape.core;

import java.text.NumberFormat;
import java.util.Locale;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class SubStore implements Screen {
	//indicates selection mode 0 is sub, 1 is tool, and 2 is propulsion
	int mode;
	Viewport tableviewport,fv;
	Stage stage;
	SubAbyss game;
	BitmapFont font12,font13;
	Container subcontainer;
	Table table;
	SpriteBatch batch;
	int count=0;
	String bankaccount;
	boolean next=false;
	long laston;
	Sprite subbody,propeller,arm1,arm2,hat,lockbutton,unlockbutton,sub;
	Label subname,speedlabel, healthlabel,currencylabel;
	OrthographicCamera camera;
	Array<Submersible> subbodyarray;
	SpriteDrawable lockeddrawable, unlockeddrawable, playdrawable;
	Image subbutton,toolbutton,equipmentbutton;
	TextureAtlas atlas;
	Image playbutton=null;
		
	
	public SubStore(SubAbyss game, AssetManager manager){
	this.game=game;
	//NumberFormat format;

	}
	@Override
	public void show() {
	
		// TODO Auto-generated method stub
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
		final NumberFormat format = NumberFormat.getNumberInstance(Locale.US);
		bankaccount=format.format(game.keeper.getCurrency());
		subname=new Label(game.storage.getChosenSub().getSubName(), labelstyle);
		healthlabel=new Label("HEALTH:"+game.storage.getChosenSub().getStrength(), detaillabelstyle);
		speedlabel=new Label("TOP SPEED: "+game.storage.getChosenSub().getTopSpeed(), detaillabelstyle);
		currencylabel=new Label("$"+bankaccount, detaillabelstyle);
		
		//subname.setText(game.storage.getChosenSub().getSubName());
		
		
		//messagetable.add(stack).center();
		
		camera = new OrthographicCamera();
	//	camera.zoom = SubAbyss.WORLD_TO_BOX;
		float scale=game.keeper.getScale();
		//scale=2f;
		tableviewport=new ExtendViewport(1920/1.5f,1080/1f);
		fv=new FillViewport(1920/scale,1080/scale,camera);
	//	tableviewport.apply();
 		
		camera.position.set(camera.viewportWidth *.5f, camera.viewportHeight * .5f, 0f);
		camera.viewportHeight=Gdx.graphics.getHeight();
		camera.viewportWidth=Gdx.graphics.getWidth();
	//	camera.zoom = SubAbyss.WORLD_TO_BOX;
		
		camera.update();
		
		propeller=game.storage.getChosenSub().getPropeller();
		
		game.assman.manager.load("data/lockedbutton.png",Texture.class);
	//	game.assman.manager.load("data/walkbutton.png", Texture.class);
		game.assman.manager.load("data/walkbutton2.png", Texture.class);
		
		game.assman.manager.finishLoading();
		stage=new Stage(tableviewport);
	
		//stage=new Stage();
		subbody=game.storage.getChosenSub().getSubShopSprite();
		subbodyarray=game.storage.getSubArray();
		arm1=game.storage.getChosenTool().getArm1Sprite();
		arm2=game.storage.getChosenTool().getArm2Sprite();
		hat=game.storage.getChosenEquipment().getHatSprite();
		table=new Table();
		table.setFillParent(true);
		stage.addActor(table);
		Sprite right=new Sprite(game.assman.manager.get("data/walkbutton2.png",Texture.class));
		Sprite left=new Sprite(game.assman.manager.get("data/walkbutton2.png",Texture.class));
		Sprite back=new Sprite(game.assman.manager.get("data/backarrow.png",Texture.class));
		 sub=new Sprite(game.assman.manager.get("data/subbutton.png",Texture.class));
		Sprite tool=new Sprite(game.assman.manager.get("data/toolbutton.png",Texture.class));
		Sprite equipment=new Sprite(game.assman.manager.get("data/equipmentbutton.png",Texture.class));
		Sprite play=new Sprite(game.assman.manager.get("data/playbutton.png",Texture.class));
		lockbutton=new Sprite(game.assman.manager.get("data/lockedbutton.png",Texture.class));
		right.flip(true,false);
		lockeddrawable=new SpriteDrawable(lockbutton);
		playdrawable=new SpriteDrawable(play);
		batch=new SpriteBatch();
		batch.setProjectionMatrix(camera.combined);
		Image walk1=new Image(right);
		Image backarrow=new Image(back);
		Image walk2=new Image(left);
		 subbutton=new Image(sub);
		 toolbutton=new Image(tool);
		 equipmentbutton=new Image(equipment);
		 playbutton=new Image(play);
		Table sidetable = new Table();
		sidetable.add(subbutton).pad(25f);
		sidetable.row();
		sidetable.add(toolbutton).pad(25f);
		sidetable.row();
		sidetable.add(equipmentbutton).pad(25f);
		//table.setPosition(camera.viewportWidth, camera.viewportHeight);
	//	table.center();
		table.setFillParent(true);
		table.top();
		 subcontainer = new Container();
		subcontainer.setName("subcontainer");
		table.add(backarrow).expandX().colspan(3).top().left().pad(25f);
		table.add(currencylabel).expandX().colspan(3).top().right().pad(25f);
		table.row();
		//table.add(subbutton).expandX().top().colspan(9).right().pad(25f);
		//table.row();
		//table.add(toolbutton).expandX().top().colspan(9).right().pad(25f);
		//table.row();
		table.add().colspan(2);
		table.add(subcontainer).colspan(2).center().height(subbody.getHeight()).width(subbody.getWidth()).align(Align.center);
		table.add(sidetable).colspan(2).right();
		//table.add(equipmentbutton).expandX().colspan(9).right().pad(25f);
		
		table.row();
//		table.bottom();
//		
		table.add(subname).expandX().colspan(6).center().bottom().pad(30f).uniform();
		table.row();
		table.add(speedlabel).expandX().colspan(6).center().center().pad(10f).uniform();
		table.row();
		table.add(healthlabel).expandX().colspan(6).center().center().pad(10f).uniform();
//	
		
		
		table.row();
//	
		table.add(walk2).expandX().colspan(2).left().bottom().pad(45f);
		table.add(playbutton).colspan(2).center().pad(45f);
		table.add(walk1).expandX().colspan(2).right().bottom().pad(45f);
	
	//	table.setDebug(true); 
		
		walk1.addListener(new ClickListener(){

			public boolean touchDown(InputEvent event, float x, float y,
		            int pointer, int button){
				
				if(mode==0) {
					game.storage.getNextSub();
					//TODO: Implement flashing selection buttons 
					
					//subbutton.setColor(Color.CLEAR);
					if (!game.storage.getChosenSub().isLocked()) {
					playbutton.setDrawable(playdrawable);
					subname.setText(game.storage.getChosenSub().getSubName());
					healthlabel.setText("HEALTH:"+game.storage.getChosenSub().getStrength());
					speedlabel.setText("TOP SPEED: "+game.storage.getChosenSub().getTopSpeed());}
				else {
					playbutton.setDrawable(lockeddrawable);
					subname.setText(game.storage.getChosenSub().getSubName());
					NumberFormat format = NumberFormat.getNumberInstance(Locale.US);
					String cost = format.format(game.storage.getChosenSub().getUnlockCost());
					healthlabel.setText("Cost: $"+cost);
					speedlabel.setText("LOCKED");
					
				}
				}
				if(mode==1) {
					game.storage.getNextTool();	
					if(!game.storage.getChosenTool().isLocked()) {
						if(!game.storage.getChosenSub().isLocked()&&!game.storage.getChosenTool().isLocked()) {
							playbutton.setDrawable(playdrawable);	
							}
							else {playbutton.setDrawable(lockeddrawable);}
						subname.setText(game.storage.getChosenTool().getToolName());
					healthlabel.setText(game.storage.getChosenTool().getToolDescription());
					speedlabel.setText("");
					}
					else {
						playbutton.setDrawable(lockeddrawable);
						subname.setText(game.storage.getChosenTool().getToolName());
						NumberFormat format = NumberFormat.getNumberInstance(Locale.US);
						String cost = format.format(game.storage.getChosenTool().getUnlockCost());
						healthlabel.setText("Cost: $"+cost);
						speedlabel.setText("LOCKED");
					}
					
				}
					if(mode==2) {
						game.storage.getNextEquipment();	
						subname.setText(game.storage.getChosenEquipment().getEquipmentName());
						healthlabel.setText(game.storage.getChosenEquipment().getEquipmentDescription());
						speedlabel.setText("");
					}
					subbody=game.storage.getChosenSub().getSubShopSprite();
					propeller=game.storage.getChosenSub().getPropeller();
					arm1=game.storage.getChosenTool().getArm1Sprite();
					arm2=game.storage.getChosenTool().getArm2Sprite();
					hat=game.storage.getChosenEquipment().getHatSprite();
				//	subname.setText(game.storage.getChosenSub().getSubName());
				//	healthlabel.setText("HEALTH:"+game.storage.getChosenSub().getStrength());
				//	speedlabel.setText("TOP SPEED: "+game.storage.getChosenSub().getTopSpeed());
				return true;
			}
			public void touchUp(InputEvent event, float x, float y,
		            int pointer, int button){
				
				
			}
			
		});
	
		
		walk2.addListener(new ClickListener(){

			public boolean touchDown(InputEvent event, float x, float y,
		            int pointer, int button){
				if(mode==0) {
				game.storage.getLastSub();
				if (!game.storage.getChosenSub().isLocked()) {
					if(!game.storage.getChosenSub().isLocked()&&!game.storage.getChosenTool().isLocked()) {
					playbutton.setDrawable(playdrawable);	
					}
					else {playbutton.setDrawable(lockeddrawable);}
					
					subname.setText(game.storage.getChosenSub().getSubName());
					healthlabel.setText("HEALTH:"+game.storage.getChosenSub().getStrength());
					speedlabel.setText("TOP SPEED: "+game.storage.getChosenSub().getTopSpeed());
					//playbutton.setDrawable(new SpriteDrawable(lockbutton));
					}
				else {
					playbutton.setDrawable(lockeddrawable);
					subname.setText(game.storage.getChosenSub().getSubName());
					NumberFormat format = NumberFormat.getNumberInstance(Locale.US);
					String cost = format.format(game.storage.getChosenSub().getUnlockCost());
					healthlabel.setText("Cost: $"+cost);
					speedlabel.setText("LOCKED");}
				}
				if(mode==1) {
					game.storage.getLastTool();	
					if(!game.storage.getChosenTool().isLocked()) {
						if(!game.storage.getChosenSub().isLocked()&&!game.storage.getChosenTool().isLocked()) {
							playbutton.setDrawable(playdrawable);	
							}
							else {playbutton.setDrawable(lockeddrawable);}
						subname.setText(game.storage.getChosenTool().getToolName());
					healthlabel.setText(game.storage.getChosenTool().getToolDescription());
					speedlabel.setText("");
					}
					else {
						playbutton.setDrawable(lockeddrawable);
						subname.setText(game.storage.getChosenTool().getToolName());
						NumberFormat format = NumberFormat.getNumberInstance(Locale.US);
						String cost = format.format(game.storage.getChosenTool().getUnlockCost());
						healthlabel.setText("Cost: $"+cost);
						speedlabel.setText("LOCKED");
					}
					
				}
				if(mode==2) {
					game.storage.getLastEquipment();	
					subname.setText(game.storage.getChosenEquipment().getEquipmentName());
					healthlabel.setText(game.storage.getChosenEquipment().getEquipmentDescription());
					speedlabel.setText("");
				}
				subbody=game.storage.getChosenSub().getSubShopSprite();
				//subcontainer.setHeight(subbody.getHeight());
				
				propeller=game.storage.getChosenSub().getPropeller();
				arm1=game.storage.getChosenTool().getArm1Sprite();
				arm2=game.storage.getChosenTool().getArm2Sprite();
				hat=game.storage.getChosenEquipment().getHatSprite();
			//	subname.setText(game.storage.getChosenSub().getSubName());
				//healthlabel.setText("HEALTH:"+game.storage.getChosenSub().getStrength());
				//speedlabel.setText("TOP SPEED: "+game.storage.getChosenSub().getTopSpeed());
				return true;
			}
			public void touchUp(InputEvent event, float x, float y,
		            int pointer, int button){
				
				
			}
			
		});
		
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
		
		subbutton.addListener(new ClickListener(){

			public boolean touchDown(InputEvent event, float x, float y,
		            int pointer, int button){
				if(mode==0) {
					game.storage.getNextSub();
					//TODO: Implement flashing selection buttons 
					
					//subbutton.setColor(Color.CLEAR);
					if (!game.storage.getChosenSub().isLocked()) {
					playbutton.setDrawable(playdrawable);
					subname.setText(game.storage.getChosenSub().getSubName());
					healthlabel.setText("HEALTH:"+game.storage.getChosenSub().getStrength());
					speedlabel.setText("TOP SPEED: "+game.storage.getChosenSub().getTopSpeed());}
				else {
					playbutton.setDrawable(lockeddrawable);
					subname.setText(game.storage.getChosenSub().getSubName());
					NumberFormat format = NumberFormat.getNumberInstance(Locale.US);
					String cost = format.format(game.storage.getChosenSub().getUnlockCost());
					healthlabel.setText("Cost: $"+cost);
					speedlabel.setText("LOCKED");
					
				}
					subbody=game.storage.getChosenSub().getSubShopSprite();
					propeller=game.storage.getChosenSub().getPropeller();
					arm1=game.storage.getChosenTool().getArm1Sprite();
					arm2=game.storage.getChosenTool().getArm2Sprite();
					hat=game.storage.getChosenEquipment().getHatSprite();
				}
				
				
				
				
				mode=0;
				if (!game.storage.getChosenSub().isLocked()) {
					playbutton.setDrawable(playdrawable);
					subname.setText(game.storage.getChosenSub().getSubName());
					healthlabel.setText("HEALTH:"+game.storage.getChosenSub().getStrength());
					speedlabel.setText("TOP SPEED: "+game.storage.getChosenSub().getTopSpeed());
					//playbutton.setDrawable(new SpriteDrawable(lockbutton));
					}
				else {
					playbutton.setDrawable(lockeddrawable);
					subname.setText(game.storage.getChosenSub().getSubName());
					NumberFormat format = NumberFormat.getNumberInstance(Locale.US);
					String cost = format.format(game.storage.getChosenSub().getUnlockCost());
					healthlabel.setText("Cost: $"+cost);
					speedlabel.setText("LOCKED");}
		
				return true;
			}
			public void touchUp(InputEvent event, float x, float y,
		            int pointer, int button){
				
				
			}
			
		});
		
		toolbutton.addListener(new ClickListener(){

			public boolean touchDown(InputEvent event, float x, float y,
		            int pointer, int button){
				if(mode==1) {
					game.storage.getNextTool();	
					if(!game.storage.getChosenTool().isLocked()) {
						if(!game.storage.getChosenSub().isLocked()&&!game.storage.getChosenTool().isLocked()) {
							playbutton.setDrawable(playdrawable);	
							}
							else {playbutton.setDrawable(lockeddrawable);}
						subname.setText(game.storage.getChosenTool().getToolName());
					healthlabel.setText(game.storage.getChosenTool().getToolDescription());
					speedlabel.setText("");
					}
					else {
						playbutton.setDrawable(lockeddrawable);
						subname.setText(game.storage.getChosenTool().getToolName());
						NumberFormat format = NumberFormat.getNumberInstance(Locale.US);
						String cost = format.format(game.storage.getChosenTool().getUnlockCost());
						healthlabel.setText("Cost: $"+cost);
						speedlabel.setText("LOCKED");
					}
					
				}
				mode=1;
				if(!game.storage.getChosenTool().isLocked()) {
					if(!game.storage.getChosenSub().isLocked()&&!game.storage.getChosenTool().isLocked()) {
						playbutton.setDrawable(playdrawable);}
					else {
						playbutton.setDrawable(lockeddrawable);
					}
					
					subname.setText(game.storage.getChosenTool().getToolName());
					healthlabel.setText(game.storage.getChosenTool().getToolDescription());
					speedlabel.setText("");
				}
				else {
						subname.setText(game.storage.getChosenTool().getToolName());
						NumberFormat format = NumberFormat.getNumberInstance(Locale.US);
						String cost = format.format(game.storage.getChosenTool().getUnlockCost());
						healthlabel.setText("Cost: $"+cost);
						speedlabel.setText("LOCKED");
				}
				
				subbody=game.storage.getChosenSub().getSubShopSprite();
				//subcontainer.setHeight(subbody.getHeight());
				
				propeller=game.storage.getChosenSub().getPropeller();
				arm1=game.storage.getChosenTool().getArm1Sprite();
				arm2=game.storage.getChosenTool().getArm2Sprite();
				hat=game.storage.getChosenEquipment().getHatSprite();
				return true;
			}
			public void touchUp(InputEvent event, float x, float y,
		            int pointer, int button){
				
				
			}
			
		});
		
		equipmentbutton.addListener(new ClickListener(){

			public boolean touchDown(InputEvent event, float x, float y,
		            int pointer, int button){
				if(mode==2) {
					game.storage.getNextEquipment();	
					subname.setText(game.storage.getChosenEquipment().getEquipmentName());
					healthlabel.setText(game.storage.getChosenEquipment().getEquipmentDescription());
					speedlabel.setText("");
				}
				mode=2;
				subname.setText(game.storage.getChosenEquipment().getEquipmentName());
				healthlabel.setText(game.storage.getChosenEquipment().getEquipmentDescription());
				speedlabel.setText("");
				subbody=game.storage.getChosenSub().getSubShopSprite();
				propeller=game.storage.getChosenSub().getPropeller();
				arm1=game.storage.getChosenTool().getArm1Sprite();
				arm2=game.storage.getChosenTool().getArm2Sprite();
				hat=game.storage.getChosenEquipment().getHatSprite();
				return true;
				
			}
			public void touchUp(InputEvent event, float x, float y,
		            int pointer, int button){
				
				
			}
			
		});
		playbutton.addListener(new ClickListener(){

			public boolean touchDown(InputEvent event, float x, float y,
		            int pointer, int button){
				if(!game.storage.getChosenSub().isLocked()&&!game.storage.getChosenTool().isLocked()) {
				game.level=new Level1(game,game.menuscreen);
				game.setScreen(game.level);}
				
				else if(mode==0&&game.storage.getChosenSub().getUnlockCost()<=game.keeper.getCurrency()&&game.storage.getChosenSub().isLocked()) {
					String cost = format.format(game.storage.getChosenSub().getUnlockCost());
					Skin s = new Skin((Gdx.files.internal("data/bathyscapeskin.json")));
				//	s.addRegions(atlas);
				
					Dialog d=new Dialog("CONFIRM UNLOCK",s , "default") {
					    protected void result (Object object) {
					        System.out.println("Chosen: " + object);
					        if(object.equals(true)) {
					        	System.out.println("banana");
					        	game.keeper.deductCurrency(game.storage.getChosenSub().getUnlockCost());
					        	if(game.storage.getChosenSub().getSubName().equals("Alvin")) {
					        	game.keeper.unlockAlvin();}
					        	if(game.storage.getChosenSub().getSubName().equals("DSV Shinkai 6500")) {
					        		game.keeper.unlockShinkai();}
					        	subname.setText(game.storage.getChosenSub().getSubName());
								healthlabel.setText("HEALTH:"+game.storage.getChosenSub().getStrength());
								speedlabel.setText("TOP SPEED: "+game.storage.getChosenSub().getTopSpeed());
							  	if(!game.storage.getChosenSub().isLocked()&&!game.storage.getChosenTool().isLocked()) {
						        	playbutton.setDrawable(playdrawable);}
					        	bankaccount=format.format(game.keeper.getCurrency());
					        	currencylabel.setText("$"+bankaccount);
			
					        }
					        else {
					        	//d.hide();
					        }
					        
					        
					    }
					}.text("UNLOCK SUB FOR $" + cost+"?").button("YES", true).button("NO", false).key(Keys.ENTER, true)
					    .key(Keys.ESCAPE, false).show(stage);
		
				
		
				}
				else if(mode==0&&game.storage.getChosenSub().getUnlockCost()>game.keeper.getCurrency()&&game.storage.getChosenSub().isLocked()) {
					String cost = format.format(game.storage.getChosenSub().getUnlockCost());
					Skin s = new Skin((Gdx.files.internal("data/bathyscapeskin.json")));
				//	s.addRegions(atlas);
				
					Dialog d=new Dialog("INSUFFICENT FUNDS",s , "default") {
					    protected void result (Object object) {
					        System.out.println("Chosen: " + object);
					        if(object.equals(true)) {
					        
					        	
			
					        }
					        else {
					        	//d.hide();
					        }
					        
					        
					    }
					}.text("YOU DON'T HAVE ENOUGH MONEY \nTO UNLOCK THIS ITEM").button("OK", true).key(Keys.ENTER, true)
					    .key(Keys.ESCAPE, false).show(stage);
		
				
		
				}
				else if(mode==0&&!game.storage.getChosenSub().isLocked()&&game.storage.getChosenTool().isLocked()) {
				//	String cost = format.format(game.storage.getChosenSub().getUnlockCost());
					Skin s = new Skin((Gdx.files.internal("data/bathyscapeskin.json")));
				//	s.addRegions(atlas);
					
					Dialog d=new Dialog("SELECTED TOOL IS LOCKED",s , "default") {
					    protected void result (Object object) {
					        System.out.println("Chosen: " + object);
					        if(object.equals(true)) {
					        	
			
					        }
					        else {
					        	//d.hide();
					        }
					        
					        
					    }
					}.text("THE SELECTED TOOL IS LOCKED. \nSELECT A DIFFERENT TOOL TO PLAY." ).button("OK", true).key(Keys.ENTER, true)
					    .key(Keys.ESCAPE, false).show(stage);
		
				
		
				}
				else if(mode==1&&(game.storage.getChosenTool().getUnlockCost()<=game.keeper.getCurrency())&&game.storage.getChosenTool().isLocked()) {
					String cost = format.format(game.storage.getChosenTool().getUnlockCost());
					Skin s = new Skin((Gdx.files.internal("data/bathyscapeskin.json")));
				//	s.addRegions(atlas);
					
					Dialog d=new Dialog("CONFIRM UNLOCK",s , "default") {
					    protected void result (Object object) {
					        System.out.println("Chosen: " + object);
					        if(object.equals(true)) {
					        	System.out.println("banana");
					        	game.keeper.deductCurrency(game.storage.getChosenTool().getUnlockCost());
					        	if(game.storage.getChosenTool().getToolName().equals("Magnetic Arm")) {
					        	game.keeper.unlockMagneticTool();}
					        	if(game.storage.getChosenTool().getToolName().equals("Laser Arm")) {
						        	game.keeper.unlockPhaserTool();}
					        	if(!game.storage.getChosenSub().isLocked()&&!game.storage.getChosenTool().isLocked()) {
					        	playbutton.setDrawable(playdrawable);}
					        	subname.setText(game.storage.getChosenTool().getToolName());
								healthlabel.setText(game.storage.getChosenTool().getToolDescription());
								speedlabel.setText("");
					        	bankaccount=format.format(game.keeper.getCurrency());
					        	currencylabel.setText("$"+bankaccount);
					        	//game.keeper.unlockAlvin();
					        }
					        else {
					        	//d.hide();
					        }
					        
					        
					    }
					}.text("UNLOCK TOOL FOR $" + cost+"?").button("YES", true).button("NO", false).key(Keys.ENTER, true)
					    .key(Keys.ESCAPE, false).show(stage);
		
				
		
				}
				else if(mode==1&&game.storage.getChosenTool().getUnlockCost()>game.keeper.getCurrency()&&game.storage.getChosenTool().isLocked()) {
				
					Skin s = new Skin((Gdx.files.internal("data/bathyscapeskin.json")));
				//	s.addRegions(atlas);
				
					Dialog d=new Dialog("INSUFFICENT FUNDS",s , "default") {
					    protected void result (Object object) {
					        System.out.println("Chosen: " + object);
					        if(object.equals(true)) {
					        
					        	
			
					        }
					        else {
					        	//d.hide();
					        }
					        
					        
					    }
					}.text("YOU DON'T HAVE ENOUGH MONEY \nTO UNLOCK THIS ITEM").button("OK", true).key(Keys.ENTER, true)
					    .key(Keys.ESCAPE, false).show(stage);
		
				
		
				}
				else if(mode==1&&!game.storage.getChosenTool().isLocked()&&game.storage.getChosenSub().isLocked()) {
					Skin s = new Skin((Gdx.files.internal("data/bathyscapeskin.json")));
					Dialog d=new Dialog("SELECTED SUB IS LOCKED",s , "default") {
					    protected void result (Object object) {
					    //    System.out.println("Chosen: " + object);
					        if(object.equals(true)) {
					        	
					        	//game.keeper.unlockAlvin();
					        }
					        else {
					        	//d.hide();
					        }
					        
					        
					    }
					}.text("THE SELECTED SUB IS LOCKED. \nSELECT A DIFFERENT SUB TO PLAY.").button("OK", true).key(Keys.ENTER, true)
					    .key(Keys.ESCAPE, false).show(stage);
				}
				else if(mode==2&&game.storage.getChosenSub().isLocked()) {
					Skin s = new Skin((Gdx.files.internal("data/bathyscapeskin.json")));
					Dialog d=new Dialog("SELECTED SUB IS LOCKED",s , "default") {
					    protected void result (Object object) {
					    //    System.out.println("Chosen: " + object);
					        if(object.equals(true)) {
					        	
					        	//game.keeper.unlockAlvin();
					        }
					        else {
					        	//d.hide();
					        }
					        
					        
					    }
					}.text("THE SELECTED SUB IS LOCKED. \nSELECT A DIFFERENT SUB TO PLAY.").button("OK", true).key(Keys.ENTER, true)
					    .key(Keys.ESCAPE, false).show(stage);
				}
				else if(mode==2&&game.storage.getChosenTool().isLocked()) {
					//String cost = format.format(game.storage.getChosenSub().getUnlockCost());
					Skin s = new Skin((Gdx.files.internal("data/bathyscapeskin.json")));
				//	s.addRegions(atlas);
					
					Dialog d=new Dialog("SELECTED TOOL IS LOCKED",s , "default") {
					    protected void result (Object object) {
					        
					        if(object.equals(true)) {
					        	
			
					        }
					        else {
					        	//d.hide();
					        }
					        
					        
					    }
					}.text("THE SELECTED TOOL IS LOCKED. \nSELECT A DIFFERENT TOOL TO PLAY." ).button("OK", true).key(Keys.ENTER, true)
					    .key(Keys.ESCAPE, false).show(stage);
		
				
		
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
//		camera.zoom = SubAbyss.WORLD_TO_BOX;
		fv.apply();
	//	batch.setProjectionMatrix(camera.combined.scl(game.WORLD_TO_BOX));    
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		subbody.setPosition(0-subbody.getWidth()/2, -subbody.getHeight()/2);
		Vector2 coords=new Vector2(subcontainer.getX(),subcontainer.getY());
		Vector2 pos=subcontainer.localToStageCoordinates(new Vector2(0,0));
		
	
		subbody.setPosition(0-subbody.getWidth()/2,0+subbody.getHeight()/2);
		
//		if(Gdx.input.isKeyPressed(Keys.L)) {game.keeper.lockAlvin();
//		game.keeper.lockShinkai();
//		game.keeper.lockMagneticTool();
//		}
		if(game.storage.getChosenSub().isLocked()) {
			playbutton.setDrawable(lockeddrawable);
			subbody.setColor(Color.BLACK);
			propeller.setColor(Color.BLACK);
			arm1.setColor(Color.BLACK);
			arm2.setColor(Color.BLACK);
			if(hat!=null) {
				hat.setColor(Color.BLACK);
				
			}
		}
		else {
			subbody.setColor(Color.WHITE);
			propeller.setColor(Color.WHITE);
			arm1.setColor(Color.WHITE);
			arm2.setColor(Color.WHITE);
			if(hat!=null) {
				hat.setColor(Color.WHITE);
				
			}
			if(game.storage.getChosenTool().isLocked()) {
				playbutton.setDrawable(lockeddrawable);
				arm1.setColor(Color.BLACK);
				arm2.setColor(Color.BLACK);
			}else {
				arm1.setColor(Color.WHITE);
				arm2.setColor(Color.WHITE);
			}
		}
		subbody.draw(batch);
		
		if(game.storage.getChosenSub().getSubName().equals("U.S.S Sandwich")) {
		propeller.setPosition(subbody.getX()-14f, 0-32f+subbody.getHeight());
		float x=game.storage.getChosenSub().getSubShopSprite().getWidth()/2;
		arm1.setPosition(x-45f, -20f+subbody.getHeight());
		arm1.setRotation(25);
		arm2.setPosition(arm1.getOriginX()+arm1.getWidth()-5f,arm1.getOriginY()-25f+subbody.getHeight());
		if(hat!=null) {
		hat.setPosition(-26f, 0+58f+subbody.getHeight());}
		}
		if(game.storage.getChosenSub().getSubName().equals("Alvin")) {
			propeller.setPosition(subbody.getX()-18f, 0-35f+subbody.getHeight());
			float x=game.storage.getChosenSub().getSubShopSprite().getWidth()/2;
			arm1.setPosition(x-55f, -45f+subbody.getHeight());
			arm1.setRotation(15);
			arm2.setPosition(arm1.getX()+arm1.getWidth()-24f,arm1.getOriginY()-53f+subbody.getHeight());
			if(hat!=null) {
				hat.setPosition(20f, 0+62f+subbody.getHeight());}
				
			}
		if(game.storage.getChosenSub().getSubName().equals("DSV Shinkai 6500")) {
			propeller.setPosition(subbody.getX()-16f, 0-40f+subbody.getHeight());
			float x=game.storage.getChosenSub().getSubShopSprite().getWidth()/2;
			arm1.setPosition(x-70f, -45f+subbody.getHeight());
			arm1.setRotation(15);
			arm2.setPosition(arm1.getOriginX()+arm1.getWidth()+2f,arm1.getOriginY()-53f+subbody.getHeight());
			if(hat!=null) {
				hat.setPosition(55f, 44f+subbody.getHeight());
				hat.setRotation(1f);
			}
			}
		if(game.storage.getChosenSub().getSubName().equals("Mir 2")) {
			propeller.setPosition(subbody.getX()-20f, 0-26f+subbody.getHeight());
			float x=game.storage.getChosenSub().getSubShopSprite().getWidth()/2;
			arm1.setPosition(x-70f, -35f+subbody.getHeight());
			arm1.setRotation(15);
			
				arm2.setPosition(arm1.getOriginX()+arm1.getWidth()+2f-10f,arm1.getOriginY()-43f+subbody.getHeight());
		
			if(hat!=null) {
				hat.setPosition(20f, 54f+subbody.getHeight());
				hat.setRotation(1f);
			}
			}
		
		if(game.storage.getChosenTool().getToolName().equals("Laser Arm")||game.storage.getChosenTool().getToolName().equals("Spud Gun")){
			arm1.draw(batch);
			arm2.draw(batch);
			
		}
		else {
		arm2.draw(batch);
		arm1.draw(batch);
		}
		
		
		subbody.draw(batch);
		propeller.draw(batch);
		if(hat!=null) {
		hat.draw(batch);}
		batch.end();
	
		  tableviewport.apply();
		 stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		    stage.draw();     
		
		if(Gdx.input.isKeyPressed(Keys.ESCAPE)||Gdx.input.isKeyPressed(Keys.BACK)){
			dispose();
			game.setScreen(game.menuscreen);
		}
		

		
		if(mode==0) {
				subbutton.setColor(1, 1, 1,1f);
				toolbutton.setColor(1, 1, 1, 0.3f);
				equipmentbutton.setColor(1, 1, 1, 0.3f);
			}
	
		else if(mode==1) {
			
			subbutton.setColor(1, 1, 1,0.3f);
			toolbutton.setColor(1, 1, 1, 1f);
			equipmentbutton.setColor(1, 1, 1, 0.3f);}

		else if(mode==2) {
			subbutton.setColor(1, 1, 1,0.3f);
			toolbutton.setColor(1, 1, 1, 0.3f);
			equipmentbutton.setColor(1, 1, 1, 1f);
			
		}
		
		
		camera.update();
	}

	@Override
	public void resize(int width, int height) {
	
		tableviewport.update(width,height,true);
		fv.update(width,height,false);
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
		stage.dispose();
	}


}
