package com.madison.Bathyscape.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;

public class Shark extends Fish {
	
	SpriteBatch batch;
	World world;
	int aggresivness=0;
	float statetime;
	String sharktype;
	Texture sharksheet;
	int FRAME_COLS;
	int FRAME_ROWS;
	TextureRegion [] swimframe;
	Animation<TextureRegion> swimanimation;
	Sprite currentframe;
	Body fishbody;
	//private Array<Body> bodies = new Array<Body>();
	boolean killable;
	boolean caught =false;
	SubAbyss game;
	
	public Shark (SpriteBatch batch, World world, float statetime,float x, float y,String sharktype,SubAbyss game){
	//	super();
		this.batch=batch;
		this.sharktype=sharktype;
		this.game=game;
		this.statetime=statetime;
		this.world=world;
		killable=false;
		game.assman.loadSharkImages();
		game.assman.manager.finishLoading();
		sharksheet = game.assman.manager.get("data/sharksheet.png",Texture.class);
	
		FRAME_COLS=4;
		FRAME_ROWS=4;
	
		TextureRegion[][] tmp = TextureRegion.split(sharksheet, sharksheet.getWidth() / 
				FRAME_COLS, sharksheet.getHeight() / FRAME_ROWS);                                
			swimframe = new TextureRegion[FRAME_COLS * FRAME_ROWS];
			int index1 = 0;
			for (int i = 0; i < FRAME_ROWS; i++) {
			                for (int j = 0; j < FRAME_COLS; j++) {
			                        swimframe[index1++] = tmp[i][j];
			                									 }	                
												}
		swimanimation = new Animation<TextureRegion>(0.08f, swimframe);
		
		swimanimation.setPlayMode(PlayMode.LOOP);
	     TextureRegion text=swimanimation.getKeyFrame(statetime, true);
	        currentframe = new Sprite(text);   
	        float[] sharkverts= {-1.35f,-0.13f,-1.45f,0.15f,0,0.35f,1,0,0,-0.25f};
	        
	        BodyDef fishbd=new BodyDef();
	        fishbd.type=BodyType.KinematicBody;
	        fishbd.position.set(x,y);
	        PolygonShape fishbox = new PolygonShape();
	       fishbox.set(sharkverts);
	    //    fishbox.setAsBox((currentframe.getRegionWidth())*SubAbyss.WORLD_TO_BOX/6,(currentframe.getRegionHeight())*SubAbyss.WORLD_TO_BOX/6);
	        
	        
	        FixtureDef sharkfd = new FixtureDef();
	        sharkfd.shape = fishbox;
			sharkfd.friction=3f;
			sharkfd.density=0.001f;
			sharkfd.restitution = 0f;
			sharkfd.isSensor=true;
			this.fishbody =world.createBody(fishbd); 
			Fixture fishfix = fishbody.createFixture(sharkfd);
			fishfix.setUserData("Shark");
			fishbody.setUserData(currentframe);
			fishbox.dispose();
		//	fishbody.;
	        fishbody.setLinearVelocity(-5.5f, 0f);
	        statetime += Gdx.graphics.getDeltaTime(); 
	   fishbody.setGravityScale(0f);
																									}
	
	public void draw(){
		if(!game.level.paused) {
		statetime += Gdx.graphics.getDeltaTime(); 
		if(game.level.level==1) {aggresivness=1;}
		if(game.level.level==2) {aggresivness=2;}
		currentframe.setRegion(swimanimation.getKeyFrame(statetime, true));  }
		if(aggresivness==1&&fishbody.getPosition().x>game.level.camera.viewportWidth/4*game.WORLD_TO_BOX) {
			fishbody.setLinearVelocity(game.level.sub.getBody().getPosition().sub(fishbody.getPosition()).nor().scl(6f));
		}
		if(aggresivness>1&&fishbody.getPosition().x>0) {
			fishbody.setLinearVelocity(game.level.sub.getBody().getPosition().sub(fishbody.getPosition()).nor().scl(6f));
		}
		swimanimation.setPlayMode(PlayMode.LOOP);
		
		fishbody.setUserData(currentframe);
		
		fishbody.setTransform(fishbody.getPosition().x, fishbody.getPosition().y, ((fishbody.getLinearVelocity().angle()+180)*MathUtils.degreesToRadians));
		
		 currentframe.setPosition(fishbody.getPosition().x*SubAbyss.BOX_WORLD_TO-currentframe.getWidth()/2, fishbody.getPosition().y*SubAbyss.BOX_WORLD_TO-currentframe.getHeight()/2);
		   currentframe.setRotation(fishbody.getAngle()*MathUtils.radiansToDegrees);
		
		
//		 Sprite sprite = (Sprite) fishbody.getUserData();
//		   sprite.setPosition(fishbody.getPosition().x*SubAbyss.BOX_WORLD_TO-sprite.getWidth()/2, fishbody.getPosition().y*SubAbyss.BOX_WORLD_TO-sprite.getHeight()/2);
//		   sprite.setRotation(fishbody.getAngle()*MathUtils.radiansToDegrees);
		   currentframe.draw(batch);
		
		
		

	}
	
				 
	public boolean getKill(){
	return killable;
						}
	public Body getBody(){
		return fishbody;
	}
	public void dispose(){
		//sharksheet.dispose();
	}
	public String getFishType(){
		return "shark";
	}
	public Sprite getCurrentFrame(){
		return currentframe;
	}
	public boolean getCaught(){
		return false;
	}
	public void setCaught (boolean caught){
		// do Nothing
	}
}
  