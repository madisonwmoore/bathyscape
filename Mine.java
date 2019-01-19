package com.madison.Bathyscape.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;

public class Mine {
	Body minebody;
	SpriteBatch batch;
	World world;
	TextureRegion[] boomframe;
	boolean chainable;
	boolean shoulddie;
	Sprite currentFrame;
	Sprite sprite;
	float statetime;
	boolean launch=false;
	boolean explode;
	float depth;
	boolean magnetized;
	SubAbyss game;
	
	private Animation<TextureRegion> explosionanimation;
	public Mine(World world,SpriteBatch batch,Sprite sprite,Sprite chain,float x,float y,float statetime,SubAbyss game){
		BodyDef minebd = new BodyDef();
		this.world=world;
		this.sprite=sprite;
		this.batch=batch;
		this.statetime=statetime;
		this.game=game;
		Texture explosionsheet=	game.assman.manager.get("data/explosion.png",Texture.class);
		 
		 int FRAME_COLS=2;
		int FRAME_ROWS=3;
		
	TextureRegion[][] tmp = TextureRegion.split(explosionsheet, explosionsheet.getWidth() / 
	FRAME_COLS, explosionsheet.getHeight() / FRAME_ROWS);                                
	        boomframe = new TextureRegion[FRAME_COLS * FRAME_ROWS];
	        int index1 = 0;
	        for (int i = 0; i < FRAME_ROWS; i++) {
	                for (int j = 0; j < FRAME_COLS; j++) {
	                        boomframe[index1++] = tmp[i][j];
	                }
	        }
	  
	    
		minebd.type=BodyType.DynamicBody;
		minebd.position.set(x,y);
		minebody = world.createBody(minebd);
		CircleShape minecircle = new CircleShape();
		minebody.setUserData(sprite);
		
		minecircle.setRadius((sprite.getHeight()*0.01f)/2);
		FixtureDef paintfixdef = new FixtureDef();
		paintfixdef.shape=minecircle;
		
		paintfixdef.density=1.2f;
		paintfixdef.isSensor=false;
		Fixture minefix = minebody.createFixture(paintfixdef);
		minefix.setUserData("mine");
		//minebody.setLinearVelocity(0, 0f);
		minebody.setGravityScale(0f);
		minebody.setAngularDamping(3f);
		depth=(minebody.getPosition().y*-2)+150;	
	
		minecircle.dispose();
		
Timer.schedule(new Timer.Task() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				if(minebody!=null){
				minebody.setLinearVelocity(minebody.getLinearVelocity().x, 2f);
					minebody.setGravityScale(-1.75f);
				launch=true;
					}
				
			}
			@Override
			public void cancel() {
				// TODO Auto-generated method stub
			}
			}, 2f);
	
Timer.schedule(new Timer.Task() {
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		
		shoulddie=true;
	}
	@Override
	public void cancel() {
		// TODO Auto-generated method stub
	}
	}, 12f);
		
	}
	public void explode(){
		//minebody.setUserData(new Sprite(explosionanimation.getKeyFrame(statetime,true)));
		//explosionanimation.setPlayMode(PlayMode.NORMAL);
		explode=true;
	}
	public void draw(){
		
		if(magnetized) {
		minebody.setLinearVelocity(game.level.sub.getArm2Body().getPosition().sub(minebody.getPosition()).nor().scl(5f));		
		game.level.sub.getArm().setMagnetizing(false);}
	
		if(depth<=0) {
		minebody.setGravityScale(10f);
		}
		
		else {
			if(launch&&depth>1f) {
				minebody.setGravityScale(-1.75f);
			}else {
				minebody.setGravityScale(0f);
			}
		}
		if(minebody.getLinearVelocity().y>0) {
			minebody.applyForceToCenter(new Vector2(0,-0.2f*minebody.getLinearVelocity().y*minebody.getLinearVelocity().y), false);
		}
		if(depth<1f) {
			if(minebody.getLinearVelocity().y<0) {
				minebody.applyForceToCenter(new Vector2(0,20.2f*minebody.getLinearVelocity().y*minebody.getLinearVelocity().y), false);
			}
		}
		
		
		if(!explode){
		 minebody.setUserData(sprite); 
		sprite.setPosition(minebody.getPosition().x*SubAbyss.BOX_WORLD_TO-sprite.getWidth()/2, minebody.getPosition().y*SubAbyss.BOX_WORLD_TO-sprite.getHeight()/2);
		sprite.setRotation(minebody.getAngle()*MathUtils.radiansToDegrees);
		sprite.draw(batch);
		}
		else{
		    explosionanimation = new Animation<TextureRegion>(.02f, boomframe);
			  //     explosionanimation.setPlayMode(PlayMode.NORMAL);
			      currentFrame=new Sprite(explosionanimation.getKeyFrame(statetime,true));
			statetime += Gdx.graphics.getDeltaTime(); 
			explosionanimation.setPlayMode(PlayMode.LOOP);
			
		currentFrame.setRegion( explosionanimation.getKeyFrame(statetime, true));
		//minebody.setUserData(currentFrame);
		currentFrame.setPosition(minebody.getPosition().x*SubAbyss.BOX_WORLD_TO-currentFrame.getWidth()/2, minebody.getPosition().y*SubAbyss.BOX_WORLD_TO-currentFrame.getHeight()/2);
		currentFrame.setRotation(minebody.getAngle()*MathUtils.radiansToDegrees);
		currentFrame.draw(batch);
		if(explosionanimation.isAnimationFinished(statetime)){
	
			Timer.schedule(new Timer.Task() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
			shoulddie=true;
			}
			@Override
			public void cancel() {
				// TODO Auto-generated method stub
			}
			}, .12f);
			
		}
		}

		 depth=(minebody.getPosition().y*-2)+150;	
	
	}
	public void magnetize() {
		magnetized=true;
	}
	}
	

