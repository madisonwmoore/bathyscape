package com.madison.Bathyscape.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

import box2dLight.ConeLight;
import box2dLight.PointLight;

public class MirSubmersible extends Submersible {
	RevoluteJointDef sawswing,armswing;
	Stage stage;
	Table table;
	private float depth=150f;
	int health=80;
	int score=0;
	Tool toolarm;
	String subname="U.S.S Sandwich";
	Pixmap pixmap;
	Image menu,forwardwalk;
	long starttime;
	Joint Armjoint,Armjoint2;
	Skin skin;
	boolean ismotorplaying;
	float topspeed=2.0f;
	long lightonfor;
	boolean islaunchpressed=false;
	Image launch;
	int strength=100;
	ConeLight dl;
	PointLight pl,pl2,pl3,pl4;
	Texture forwardwalkbutton,sideway,menuicon;
	float joystick,statetime;
	Sprite forwardsprite,backwardsprite,upsprite,downsprite,menusprite;
	Texture subtext,bubble,proptext,arm1,arm2,sublighttext,text;
	OrthographicCamera camera;
	Body subbody,armbody,arm2body;
	Label scorelabel,depthlabel,healthlabel;
	long lastlight;
	boolean subhit;
	Batch batch;
	SubAbyss game;
	BitmapFont font12;
	Sprite subshopsprite;
	private Array<Body> bodies = new Array<Body>();
	Fixture subfix,armfix,arm2fix;
	Sprite subsprite,bubblesprite,leftsprite,rightsprite,propeller,arm1sprite,arm2sprite,sublight;
	World world;
	boolean lighton=false;
	boolean right,upboo,downboo;
	boolean backward;
	boolean launcherequipped;
	Sprite launchsprite;
	public MirSubmersible(SubAbyss game,AssetManager assman){
		game.assman.manager.finishLoading();
		game.assman.manager.load("data/sub4.png",Texture.class);
		game.assman.manager.load("data/subpropeller.png",Texture.class);
		game.assman.manager.finishLoading();
		
		text=game.assman.manager.get("data/sub4.png",Texture.class);
		text.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		if(assman.update()) {
		subshopsprite=new Sprite(text);
		propeller=new Sprite(game.assman.manager.get("data/subpropeller2.png",Texture.class));}
	}
	public MirSubmersible(Batch batch, World world,float statetime, OrthographicCamera camera, AssetManager assman, final SubAbyss game){

		this.camera=camera;
		this.batch=batch;
		this.world=world;
		this.game=game;
		this.statetime=statetime;
		game.assman.loadSandwichSubmersibleImages();
		
		game.assman.manager.finishLoading();
		
		if(game.storage.getChosenTool().getToolName().equals("Spud Gun")||game.storage.getChosenTool().getToolName().equals("Laser Arm")) {
			launcherequipped=true;
		}
		
		
		subtext=game.assman.manager.get("data/sub4.png",Texture.class);

		bubble=game.assman.manager.get("data/bubble.png",Texture.class);
		proptext=game.assman.manager.get("data/subpropeller2.png",Texture.class);
		forwardwalkbutton=game.assman.manager.get("data/walkbutton.png",Texture.class);
		sideway=game.assman.manager.get("data/walkbutton2.png",Texture.class);
		menuicon=game.assman.manager.get("data/menuicon.png",Texture.class);
		
		subtext.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		subsprite=new Sprite(subtext);

		bubblesprite=new Sprite(bubble);
		propeller=new Sprite(proptext);

		backwardsprite = new Sprite(sideway);
		forwardsprite = new Sprite(sideway);
		upsprite = new Sprite(forwardwalkbutton);
		downsprite=new Sprite(forwardwalkbutton);
		menusprite = new Sprite(menuicon);
		
		if(launcherequipped) {
		 launchsprite = new Sprite(game.assman.manager.get("data/launchbutton.png",Texture.class));
		}

		BodyDef subbd = new BodyDef();
		subbd.type = BodyType.DynamicBody;
	    subbd.position.set(0f,75f);

	    
	    PolygonShape subbox = new PolygonShape();

	    subbox.setAsBox((subsprite.getWidth()/2)*SubAbyss.WORLD_TO_BOX,(subsprite.getHeight()/4)*SubAbyss.WORLD_TO_BOX);
	    float[] subverts= {-0.7f,-0.4f,-1.3f,0.1f,-0.75f,0.35f,0f,0.55f,1.00f,0.5f,1.2f,0.26f,0.9f,-0.4f};
	    subbox.set(subverts);
	    
	    FixtureDef subfd = new FixtureDef();
	    subfd.shape = subbox;
		subfd.friction=2f;
		subfd.density=0.1f;
		subfd.restitution = 0f;
		
		PolygonShape subbox2 = new PolygonShape();
		float[] verticies=new float[] {-1.22f,0.5f,-0.5f,0.5f,-0.5f,-0.4f,-1.22f,-0.4f};
		subbox2.set(verticies); 
	    FixtureDef subfdhat = new FixtureDef();
		 subfdhat.shape = subbox2;
		 subfdhat.friction=1f;
		 subfdhat.density=0.1f;
		 subfdhat.restitution = 0f;

		this.subbody =world.createBody(subbd);

		subfix = subbody.createFixture(subfd);
		subfix = subbody.createFixture(subfdhat);
		subbody.setUserData(subsprite);
		subbox.dispose();
		subbox2.dispose();
		subbody.setFixedRotation(true);
		subbody.setGravityScale(0f);
		
		if(game.storage.getChosenTool().getToolName().equals("Basic Arm")) {
		toolarm=new BasicArm(80f*SubAbyss.WORLD_TO_BOX, -10f*SubAbyss.WORLD_TO_BOX, subbody, game);	
		}
		if(game.storage.getChosenTool().getToolName().equals("Chopstick Arm")) {
			toolarm=new ChopstickArm(80f*SubAbyss.WORLD_TO_BOX, -10f*SubAbyss.WORLD_TO_BOX, subbody, game);	
			}
		if(game.storage.getChosenTool().getToolName().equals("Magnetic Arm")) {
		toolarm=new MagneticArm(80f*SubAbyss.WORLD_TO_BOX, -10f*SubAbyss.WORLD_TO_BOX, subbody, game);
		}
		if(game.storage.getChosenTool().getToolName().equals("Laser Arm")) {
		toolarm=new Phaser(80f*SubAbyss.WORLD_TO_BOX, -10f*SubAbyss.WORLD_TO_BOX, subbody, game);
		}
		if(game.storage.getChosenTool().getToolName().equals("Spud Gun")) {
			toolarm=new SpudGun(80f*SubAbyss.WORLD_TO_BOX, -10f*SubAbyss.WORLD_TO_BOX, subbody, game);
			}
		 	

		
		FreeTypeFontGenerator generator=new FreeTypeFontGenerator(Gdx.files.internal("data/theboldfont.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 32;
		font12 = generator.generateFont(parameter); // font size 12 pixels
		generator.dispose();
		
		dl=new ConeLight(game.level.rayhandler, 20, Color.TEAL, 6f, 0, 0, 9f, 30f);
		dl.attachToBody(subbody,(subsprite.getWidth()/4+30f)*game.WORLD_TO_BOX,-5f*game.WORLD_TO_BOX);
		dl.setActive(false);
		
		pl=new PointLight(game.level.rayhandler, 20, Color.GOLDENROD, 0.3f,0,0);
		pl.attachToBody(subbody,(subsprite.getWidth()/4-32f)*game.WORLD_TO_BOX,-2*game.WORLD_TO_BOX);
		
		pl2=new PointLight(game.level.rayhandler, 20, Color.GOLDENROD, 0.3f,0,0);
		pl2.attachToBody(subbody,(subsprite.getWidth()/4-82f)*game.WORLD_TO_BOX,-2*game.WORLD_TO_BOX);
		
//		pl3=new PointLight(game.level.rayhandler, 20, Color.GOLDENROD, 0.3f,0,0);
//		pl3.attachToBody(subbody,(subsprite.getWidth()/4-42f)*game.WORLD_TO_BOX,50*game.WORLD_TO_BOX);
		
		pl4=new PointLight(game.level.rayhandler, 20, Color.RED, 1.8f,0,0);
		pl4.attachToBody(subbody,(-subsprite.getWidth()/2)*game.WORLD_TO_BOX,0);
		
		dl.setXray(true);
		pl.setXray(true);
		pl2.setXray(true);
//		pl3.setXray(true);
		
		
		
		stage=new Stage();

		skin=new Skin();
		pixmap = new Pixmap(1, 1, Format.RGBA8888);
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
		labelstyle.font = font12;
		
		labelstyle.fontColor =Color.WHITE;
		
		
		skin.add("default", textButtonStyle);
		skin.add("default",labelstyle);

		table=new Table();
		Table lr=new Table();
		Table ud = new Table();
		table.setFillParent(true);
		stage.addActor(table);
		forwardsprite.flip(true, false);
		

		
		upsprite.flip(false,true);
		Image backwardwalk = new Image(backwardsprite);
		 forwardwalk = new Image(forwardsprite);
		Image down = new Image(upsprite);
		Image up = new Image(downsprite);
		 menu =new Image(menusprite);

		 if(launcherequipped) {
			 launch = new Image(launchsprite);
		 }
		 
		 
		scorelabel=new Label("Score: ", skin);	
		depthlabel=new Label("Depth: ",skin);
		healthlabel=new Label("Health: ",skin);

		
		
		
		table.add(scorelabel).left().top().pad(50f);
		table.right().top().add(healthlabel).pad(50f);

		table.add(menu).top().right().pad(50f);
		table.row();
		table.add(depthlabel).colspan(3).right().pad(50f).expand();
		table.row();
		ud.add(up).pad(50f).right().row();
		ud.add(down).right().pad(50f);
		if(launcherequipped) {
			lr.add(launch).bottom().center();
			lr.row();
		}
		lr.add(backwardwalk).pad(50f);
		lr.add(forwardwalk).pad(50f).left();
		table.add(lr).bottom().left();

		table.add(ud).right().colspan(3);

		backwardwalk.setTouchable(Touchable.enabled);
		forwardwalk.setTouchable(Touchable.enabled);
		up.setTouchable(Touchable.enabled);
		down.setTouchable(Touchable.enabled);
		
		backwardwalk.addListener(new ClickListener(){

			public boolean touchDown(InputEvent event, float x, float y,
		            int pointer, int button){
				
				backward=true;
				return true;
			}
			public void touchUp(InputEvent event, float x, float y,
		            int pointer, int button){
				
				backward=false;
				
			}
			
		});
		forwardwalk.addListener(new ClickListener(){

			public boolean touchDown(InputEvent event, float x, float y,
		            int pointer, int button){
				
				right=true;
				return true;
			}
			public void touchUp(InputEvent event, float x, float y,
		            int pointer, int button){
				
				right=false;
				
			}
			
		});
		down.addListener(new ClickListener(){

			public boolean touchDown(InputEvent event, float x, float y,
		            int pointer, int button){
				
				downboo=true;
				return true;
			}
			public void touchUp(InputEvent event, float x, float y,
		            int pointer, int button){
				
				downboo=false;
				
			}
			
		});
		
		up.addListener(new ClickListener(){

			public boolean touchDown(InputEvent event, float x, float y,
		            int pointer, int button){
				
				upboo=true;
				return true;
			}
			public void touchUp(InputEvent event, float x, float y,
		            int pointer, int button){
				
				upboo=false;
				
			}
			
		});
		
		menu.addListener(new ClickListener(){

			public boolean touchDown(InputEvent event, float x, float y,
		            int pointer, int button){
				if(!game.level.paused){    	
					game.level.paused=true;}
				game.level.ingamemenu.showMenu();
				
				return true;
			}
			public void touchUp(InputEvent event, float x, float y,
		            int pointer, int button){
				
				
				
			}
			
		});
		if(launch!=null) {
			launch.addListener(new ClickListener(){

				public boolean touchDown(InputEvent event, float x, float y,
			            int pointer, int button){
				if(toolarm.launch(true)) {
					islaunchpressed=true;}
					return true;
				}
				public void touchUp(InputEvent event, float x, float y,
			            int pointer, int button){
					islaunchpressed=false;
					
					
				}
				
			});
			
			}


		//table.setDebug(true); 
		Gdx.input.setInputProcessor(stage);
		skin.dispose();
		pixmap.dispose();
			
	}
	public void draw(){
		if(!game.level.paused&&game.assman.manager.update()) {
		
				//Gdx.input.setInputProcessor(stage);
			}
			world.getBodies(bodies);
		if(TimeUtils.millis()>(lastlight+2000)){
	   	 lastlight=TimeUtils.millis();
	   		lighton=true;
	   	}
	   	else{lighton=false;}
		if(launcherequipped) {	
			if(islaunchpressed||Gdx.input.isKeyPressed(Keys.SPACE)) {
				launch.setColor(Color.RED);
			}  
			
			else {launch.setColor(Color.WHITE);
			}}
		if(depth>160||Gdx.input.isKeyPressed(Keys.L)){
	    	dl.setActive(true);
	    	pl.setActive(true);
	    	pl2.setActive(true);
	    	//pl3.setActive(true);
	    	toolarm.getArm2Sprite().setColor(Color.BLACK);
	    	toolarm.getArm1Sprite().setColor(Color.BLACK);
	    
	    	
	    if(lighton) {
	    	pl4.setActive(true);
	    }
	    else {pl4.setActive(false);}
	    	
	    }
	    else {
	    	dl.setActive(false);
	    	pl.setActive(false);
	    	pl2.setActive(false);
	    //	pl3.setActive(false);
	    	pl4.setActive(false);
	    	toolarm.getArm2Sprite().setColor(Color.WHITE);
	    	toolarm.getArm1Sprite().setColor(Color.WHITE);
	    }
	    

		if(!subhit){
		 propeller.setPosition((subbody.getPosition().x*SubAbyss.BOX_WORLD_TO-148f), subbody.getPosition().y*SubAbyss.BOX_WORLD_TO-23f);
		}
		
		else{
			propeller.translate(-6f,8f);
			
		}
		if(subbody.getLinearVelocity().x>0) {
			subbody.applyForceToCenter(new Vector2(-0.2f*subbody.getLinearVelocity().x*subbody.getLinearVelocity().x,0), false);
		}
		if(subbody.getLinearVelocity().x<0) {
			subbody.applyForceToCenter(new Vector2(0.2f*subbody.getLinearVelocity().x*subbody.getLinearVelocity().x,0), false);
		}
		if(subbody.getLinearVelocity().y<0) {
			subbody.applyForceToCenter(new Vector2(0,0.2f*subbody.getLinearVelocity().y*subbody.getLinearVelocity().y), false);
		}
		if(subbody.getLinearVelocity().y>0) {
			subbody.applyForceToCenter(new Vector2(0,-0.2f*subbody.getLinearVelocity().y*subbody.getLinearVelocity().y), false);
		}
		if(depth<1f) {
			if(subbody.getLinearVelocity().y<0) {
				subbody.applyForceToCenter(new Vector2(0,20.2f*subbody.getLinearVelocity().y*subbody.getLinearVelocity().y), false);
			}
		}
		  
		 if(Gdx.input.isKeyPressed(Keys.RIGHT)||right||joystick>0.65){
			
			 bubblesprite.setScale(-MathUtils.random(0.2f,1f));
			 Level1.bubbles.add(new Bubble(new Sprite(bubblesprite),game, 1f,world,batch,(subbody.getPosition().x*SubAbyss.BOX_WORLD_TO)-200f,subbody.getPosition().y*SubAbyss.BOX_WORLD_TO-5f,1f,false));
			if(subbody.getLinearVelocity().x<4.0f){
			   subbody.applyForceToCenter(new Vector2 (4.9f,0), true);}
			else{
			   subbody.setLinearVelocity(4.0f, subbody.getLinearVelocity().y);}
			 
			
			   if(propeller.getRotation()<0f){
				   propeller.rotate(2f);}
			   
		 if(propeller.getRotation()>0f){
			   propeller.rotate(-2f);
			   }

		   }
		 
		  
		   if(Gdx.input.isKeyPressed(Keys.DOWN)||downboo){
			   bubblesprite.setScale(-MathUtils.random(0.2f,1f));
			   Level1.bubbles.add(new Bubble(new Sprite(bubblesprite),game, 1f,world,batch,(subbody.getPosition().x*SubAbyss.BOX_WORLD_TO)-200f,subbody.getPosition().y*SubAbyss.BOX_WORLD_TO-5f,1f,false));
			   
			   
			 
			 if(subbody.getLinearVelocity().y>-4.0f){
				 subbody.applyForceToCenter(new Vector2 (0,-4.9f), true);
			 }
			 else{
				 subbody.setLinearVelocity(subbody.getLinearVelocity().x,-4.0f);
			 }
			   if(propeller.getRotation()>-30f){
				   propeller.rotate(-2f);}
			
		   }
		   if((Gdx.input.isKeyPressed(Keys.UP)||upboo)&&depth>0){
			   bubblesprite.setScale(-MathUtils.random(0.2f,1f));
			   Level1.bubbles.add(new Bubble(new Sprite(bubblesprite),game, 1f,world,batch,(subbody.getPosition().x*SubAbyss.BOX_WORLD_TO)-200f,subbody.getPosition().y*SubAbyss.BOX_WORLD_TO-5f,1f,false));
			   bubblesprite.setScale(-MathUtils.random(0f,1f));
			   if(subbody.getLinearVelocity().y<3.0f){
			   subbody.applyForceToCenter(new Vector2 (0,4.9f), true);
			   }
			   else{
				   subbody.setLinearVelocity(subbody.getLinearVelocity().x, 4.0f);
			   }
			
		   if(propeller.getRotation()<30f){
			   propeller.rotate(2f);}
		 
		   }
		   
		   if(depth<=0) {
			   subbody.setGravityScale(10f);
		
			   
			   toolarm.getArm1Body().setGravityScale(0f);
			   toolarm.getArm2Body().setGravityScale(0f);
			   
		   }
		   else{subbody.setGravityScale(0f);

		   toolarm.getArm1Body().setGravityScale(0f);
		   toolarm.getArm2Body().setGravityScale(0f);
		   
		   }
		   
		  
		   if(Gdx.input.isKeyPressed(Keys.LEFT)||backward||joystick<-0.65){
			   bubblesprite.setScale(-MathUtils.random(0.2f,1f));
			   Level1.bubbles.add(new Bubble(new Sprite(bubblesprite),game, 1f,world,batch,(subbody.getPosition().x*SubAbyss.BOX_WORLD_TO)-200f,subbody.getPosition().y*SubAbyss.BOX_WORLD_TO-5f,1f,false));
			   if(subbody.getLinearVelocity().x>-4.0f){
				   subbody.applyForceToCenter(new Vector2 (-4.9f,0), true);}
				else{
				   subbody.setLinearVelocity(-3.0f, subbody.getLinearVelocity().y);}
			   if(propeller.getRotation()>0f){
				   propeller.rotate(-2f);
				   }
			   if(propeller.getRotation()<0f){
				   propeller.rotate(2f);
				   }
		
			   }
			  

		   			toolarm.draw();
		   			
				   subsprite.setPosition(subbody.getPosition().x*SubAbyss.BOX_WORLD_TO-subsprite.getWidth()/2f,subbody.getPosition().y*SubAbyss.BOX_WORLD_TO-subsprite.getHeight()/2f);
				   subsprite.setRotation(subbody.getAngle()*MathUtils.radiansToDegrees);			  
				   subsprite.draw(batch);
				   
				    for(int h=0;h<Level1.bubbles.size;h++){
				    	Level1.bubbles.get(h).draw();
				    }
				    
		//   menu.setSize(health, 100);
		   
		   propeller.draw(batch);
		 depth=(subbody.getPosition().y*-2)+150;
		  
		depthlabel.setText("Depth: "+(int)depth+" m.");
		

		 healthlabel.setText(("Health: "+(int)(((double)health/(double)strength)*100)+"%"));
		if(subbody.getPosition().x>camera.viewportWidth/2*game.WORLD_TO_BOX) {subbody.setLinearVelocity(0f,subbody.getLinearVelocity().y );
		subbody.setTransform(camera.viewportWidth/2*game.WORLD_TO_BOX, subbody.getPosition().y, 0);}
		if(subbody.getPosition().x<-camera.viewportWidth/2*game.WORLD_TO_BOX) {subbody.setLinearVelocity(0f,subbody.getLinearVelocity().y );
		subbody.setTransform(-camera.viewportWidth/2*game.WORLD_TO_BOX, subbody.getPosition().y, 0);
		}
		

	}



public String getSubName() {
	return "Mir 2";
}
public Sprite getPropeller() {
	return propeller;
}
public void addScore(int addedscore){
	score=score+addedscore;
	scorelabel.setText("Score: "+"$"+score);
}

public int getScore(){
	return score;
}

public void dispose(){

	
	if(text!=null) {
		text.dispose();
	}
	stage.clear();
	stage.dispose();
	font12.dispose();
	
}
public float getDepth(){
	return depth;
}
public Body getBody(){
	return subbody;
	}
public void hit(int hitpoints){
	
	subsprite.setColor(Color.WHITE);
	toolarm.setToolColor(Color.WHITE);
	//arm1sprite.setColor(Color.WHITE);
	propeller.setColor(Color.WHITE);

	Timer.schedule(new Task(){
        @Override
        public void run() {
            subsprite.setColor(Color.RED);
            toolarm.setToolColor(Color.RED);
            //arm2sprite.setColor(Color.RED);
           // arm1sprite.setColor(Color.RED);
            propeller.setColor(Color.RED);
            Timer.schedule(new Task(){
                @Override
                public void run() {
                    subsprite.setColor(Color.WHITE);
                    toolarm.setToolColor(Color.WHITE);
                 //   arm2sprite.setColor(Color.WHITE);
                   // arm1sprite.setColor(Color.WHITE);
                    propeller.setColor(Color.WHITE);
                }
            }
            , .2f        //    (delay)
            , .4f,2    //    (seconds)
        );
        }
    }
    , 0f        //    (delay)
    , .4f,2    //    (seconds)
);
	subsprite.setColor(Color.WHITE);
	toolarm.setToolColor(Color.WHITE);
	//arm2sprite.setColor(Color.WHITE);
//	arm1sprite.setColor(Color.WHITE);
	propeller.setColor(Color.WHITE);

	health-=hitpoints;
	if(health<0){
		health=0;
	}

}

public Body getArm2Body(){
	//return arm2body;
	return toolarm.getArm2Body();
}
public Sprite getArm2Sprite(){
	return toolarm.getArm2Sprite();
}
public Stage getStage(){
	return stage;
}
public Sprite getSubSprite(){
	return subsprite;
	}

public Sprite getSubShopSprite(){
	return subshopsprite;
}
public void activateArm(Sardine ts){

	toolarm.activateArm(ts);
}
public void activateArm(Squid sq){
	
	toolarm.activateArm(sq);
}
public void activateArm(Fish tf){

	toolarm.activateArm(tf);

}
public Tool getArm() {
	return toolarm;
}

public void restoreAngle(){
	
	

while(true){
	if(!world.isLocked()){
subbody.setTransform(subbody.getPosition(), 2*3.14f);	
break;
	}
}

	subbody.setAngularVelocity(0f);		
	subbody.setFixedRotation(true);
	
		
	
	}
private float normalizeAngle(float angle){
	angle =  angle % 360; 

	// force it to be the positive remainder, so that 0 <= angle < 360  
	angle = (angle + 360) % 360;  

	// force into the minimum absolute value residue class, so that -180 < angle <= 180  
	if (angle > 180) { 
	    angle -= 360;}
	
	return angle;
}
public void sink(){
	subhit=true;

	getPropeller().setOrigin(subbody.getPosition().x, subbody.getPosition().x);
	getPropeller().setRotation(getBody().getAngle()*MathUtils.radiansToDegrees);
	subbody.applyForceToCenter(0, -20f, true);
}
public float getHealth(){
	return health;
	}
public Label getHealthLabel() {
	return healthlabel;
}
	
public Label getScoreLabel() {
	return scorelabel;
}
public Image getMenuButton() {
	return menu;
}
public int getUnlockCost() {
	return 20000;
}
public int getStrength() {
	return health;
}
public float getTopSpeed() {
	return 3.9f;
}
}
