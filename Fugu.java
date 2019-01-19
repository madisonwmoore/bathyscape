package com.madison.Bathyscape.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class Fugu extends Fish {
	SpriteBatch batch;
	World world;
	float statetime,x ,y;
	float depth;
	AssetManager assman;
	boolean caught=false;
	Texture fugusheet,fuguexplode;
	int FRAME_COLS,FRAME_COLS2;
	int FRAME_ROW,FRAME_ROW2;
	TextureRegion [] swimframe,swimframe2;
	Animation<TextureRegion> swimanimation,explodeanimation;
	Timer.Task explodetask;
	TextureRegion text2;
	Sprite currentframe;
	Body fugubody,explodebody;
	private Array<Body> bodies = new Array<Body>();
	boolean killable,exploded;
	Sprite halfexplode,fullexplode;
	Sound inflate;
	SubAbyss game;
	int hitpoints=10;
	private Task killtask;
	
	public Fugu(SpriteBatch batch,World world,float statetime, SubAbyss game, float x, float y){
	this.batch=batch;
	this.statetime=statetime;
	this.world=world;
	this.game=game;
	this.x=x;
	this.y=y;

	fugusheet = game.assman.manager.get("data/fugusheet.png",Texture.class);
	fuguexplode = game.assman.manager.get("data/fuguexplode.png",Texture.class);
	inflate=game.assman.manager.get("data/inflate.mp3",Sound.class);

	FRAME_COLS=3;
	FRAME_ROWS=1;

	TextureRegion[][] tmp = TextureRegion.split(fugusheet, fugusheet.getWidth() / 
			FRAME_COLS, fugusheet.getHeight() / FRAME_ROWS);                                
		swimframe = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		int index1 = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
		                for (int j = 0; j < FRAME_COLS; j++) {
		                        swimframe[index1++] = tmp[i][j];
		                									 }	                
											}
	swimanimation = new Animation<TextureRegion>(0.1f, swimframe);
	swimanimation.setPlayMode(PlayMode.LOOP);
     TextureRegion text=swimanimation.getKeyFrame(statetime, true);
        currentframe = new Sprite(text);   
        
   
        TextureRegion[][] explodetextregion = TextureRegion.split(fuguexplode, fuguexplode.getWidth(), fuguexplode.getHeight()/2);
        halfexplode=new Sprite(explodetextregion[1][0]);
        fullexplode=new Sprite(explodetextregion[0][0]);
        
 
 explodetask=new Timer.Task() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(!exploded&&!caught){
				explode(1);};
				
			}
			
};
        
        
        Timer.schedule(explodetask,MathUtils.random(1f,5f));    
        

        
        
        
        
        
        BodyDef fishbd=new BodyDef();
        fishbd.type=BodyType.DynamicBody;
        fishbd.position.set(x,y);
        PolygonShape fishbox = new PolygonShape();
       
        fishbox.setAsBox((currentframe.getRegionWidth())*SubAbyss.WORLD_TO_BOX/3,(currentframe.getRegionHeight())*SubAbyss.WORLD_TO_BOX/6);
        
        
        FixtureDef fishfd = new FixtureDef();
        fishfd.shape = fishbox;
		fishfd.friction=3f;
		fishfd.density=1f;
		fishfd.restitution = 0f;
		fishfd.isSensor=true;
		fugubody =world.createBody(fishbd); 
		 fugubody.createFixture(fishfd);
		fugubody.setUserData(currentframe);
		fishbox.dispose();
        fugubody.setLinearVelocity(-3.5f, 0f);
        statetime += Gdx.graphics.getDeltaTime(); 
        fugubody.setGravityScale(0f);
        
        
        
        CircleShape fishcirc = new CircleShape();	       
        fishcirc.setRadius((halfexplode.getWidth()/2*game.WORLD_TO_BOX)*0.8f);
        FixtureDef fishfd3 = new FixtureDef();
        fishfd3.shape = fishcirc;
		fishfd3.friction=3f;
		fishfd3.density=0.1f;
		fishfd3.restitution = 0f;
		fishfd3.isSensor=true;
		//explodebody =world.createBody(fishbd);
		fugubody.createFixture(fishfd3);
        fishcirc.dispose();
        //explodebody.setGravityScale(0);
        
        
        
        
}
	public void draw(){
		
			statetime += Gdx.graphics.getDeltaTime();
			
	if(exploded) {		
			if(fugubody.getLinearVelocity().x>0) {
				fugubody.applyForceToCenter(new Vector2(-0.1f*fugubody.getLinearVelocity().x*fugubody.getLinearVelocity().x,0), false);
			}
			if(fugubody.getLinearVelocity().x<0) {
				fugubody.applyForceToCenter(new Vector2(0.1f*fugubody.getLinearVelocity().x*fugubody.getLinearVelocity().x,0), false);
			}
			if(fugubody.getLinearVelocity().y<0) {
				fugubody.applyForceToCenter(new Vector2(0,0.1f*fugubody.getLinearVelocity().y*fugubody.getLinearVelocity().y), false);
			}
			if(fugubody.getLinearVelocity().y>0) {
				fugubody.applyForceToCenter(new Vector2(0,-0.1f*fugubody.getLinearVelocity().y*fugubody.getLinearVelocity().y), false);
			}
			if(depth<1f) {
				if(fugubody.getLinearVelocity().y<0) {
					fugubody.applyForceToCenter(new Vector2(0,20.2f*fugubody.getLinearVelocity().y*fugubody.getLinearVelocity().y), false);
				}
			}
			
	}
			
			
			
	if(magnetize&&!exploded) {
	
		fugubody.setLinearVelocity(game.level.sub.getArm2Body().getPosition().sub(fugubody.getPosition()).nor().scl(5f));
		if(fugubody.getPosition().dst2(game.level.sub.getBody().getPosition())<0.55f){
			caught=true;
		}
	}
		if(!exploded){
		currentframe.setRegion(swimanimation.getKeyFrame(statetime, true));

		}
		swimanimation.setPlayMode(PlayMode.LOOP);
		
		fugubody.setUserData(currentframe);
		
		if(caught){
			fugubody.setTransform(game.level.sub.getArm2Body().getPosition().x+0.9f*(MathUtils.cos(game.level.sub.getArm2Body().getAngle())*(game.level.sub.getArm2Sprite().getWidth()/2)*SubAbyss.WORLD_TO_BOX), game.level.sub.getArm2Body().getPosition().y+0.9f*(MathUtils.sin(game.level.sub.getArm2Body().getAngle())*(game.level.sub.getArm2Sprite().getWidth()/2)*SubAbyss.WORLD_TO_BOX), game.level.sub.getArm2Body().getAngle()-(MathUtils.PI/2));
			getCurrentFrame().setColor(Color.RED);
			fugubody.setUserData(currentframe);
			
		}
		 currentframe.setPosition(fugubody.getPosition().x*SubAbyss.BOX_WORLD_TO-currentframe.getWidth()/2, fugubody.getPosition().y*SubAbyss.BOX_WORLD_TO-currentframe.getHeight()/2);
		   currentframe.setRotation(fugubody.getAngle()*MathUtils.radiansToDegrees);
		currentframe.draw(batch);
	
		  depth=(fugubody.getPosition().y*-2)+150;
		  if(depth<=0) {
			  fugubody.setGravityScale(5f);
		  }
		  else {fugubody.setGravityScale(0f);}
		  
	}

	
	
	public void explode(int q){
	if(!caught&&!dying&&fugubody!=null){

		if(fugubody!=null&&fugubody.getFixtureList().size>=1) {
			fugubody.getFixtureList().peek().setSensor(false);
		}
			currentframe=halfexplode;
			fugubody.applyForceToCenter(10f, 1f, true);
	
			fugubody.applyAngularImpulse(MathUtils.random(-0.01f,0.01f), true);
			fugubody.setLinearVelocity(-1.5f, fugubody.getLinearVelocity().y);
			exploded=true;

		
		killtask=new Timer.Task() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
		if(killable=false) {
				Kill();}
				
			}};
		
			Timer.schedule(	killtask, 30f);
	}
	
	
	}
	
	
	
	
	public boolean getKill(){  
		return killable;
		}
	public void Kill(){
		explodetask.cancel();
					killable=true;	
	}
	public Body getBody(){
		return fugubody;
	}
	public void dispose(){
		if(killtask!=null) {
		killtask.cancel();}
		explodetask.cancel();
//		fugusheet.dispose();
		//inflate.dispose();
	}
	public String getFishType(){
		return "fugu";
		
	}
	public Sprite getCurrentFrame(){
		
		return currentframe;
	}
	public boolean getCaught(){
		return caught;
	}
	public void setCaught(boolean caught){
		explodetask.cancel();
		if(exploded){
			caught=false;}
		else{
		this.caught=caught;}
		
	}
	public void magnetize() {
		magnetize=true;
		
	}
	public boolean isExploded() {
		return exploded;
	}
	
	public void hit(int hit) {
	//	explode();
		Timer.schedule(new Task(){
	        @Override
	        public void run() {
	            currentframe.setColor(Color.RED);
	    
	            Timer.schedule(new Task(){
	                @Override
	                public void run() {
	                    currentframe.setColor(Color.WHITE);
	        
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
		
		hitpoints=hitpoints-hit;
		if(hitpoints<=0) {
			dying=true;
			fugubody.setAngularVelocity(-5f);
			 Timer.schedule(new Task(){
	             @Override
	             public void run() {
	              Kill();
	     
	             }
	         }
	         ,2    //    (seconds)
	     );
		}
		
	}
	
}
