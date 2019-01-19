package com.madison.Bathyscape.core;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.utils.Array;
//import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;

public class Anchor extends Trap {
Body anchorbody;  
Texture anchortext;
Sprite anchorsprite;
Sprite chainsprite;
private Array<Body> bodies=new Array<Body>();
Batch batch;
World world;
public Anchor(SubAbyss game,Batch batch,AssetManager assman, World world,float x, float y) {
	this.batch=batch;
	this.world=world;
	
	//Create Sprite

	anchorsprite=new Sprite(game.assman.manager.get("data/anchor.png",Texture.class));
	chainsprite=new Sprite(game.assman.manager.get("data/chain.png",Texture.class));
	float[] anchorverts= {-0.6f,-.4f,0.6f,-.4f,0.3f,-.6f,0,-.68f,-0.3f,-.6f};
	
	BodyDef anchorbd = new BodyDef();
	anchorbd.type = BodyType.DynamicBody;
	anchorbd.position.set(x,y);
   
	PolygonShape anchorbox=new PolygonShape();
	anchorbox.set(anchorverts);
	
	PolygonShape anchorbox2=new PolygonShape();
	anchorbox2.setAsBox(0.1f, 0.5f);
	
	//CircleShape anchorbox = new CircleShape();
    //anchorbox.setRadius(anchorsprite.getWidth()/2*game.WORLD_TO_BOX);
   
   
    FixtureDef anchorfd = new FixtureDef();
    anchorfd.shape = anchorbox;
    anchorfd.friction=3f;
    anchorfd.density=5f;
    anchorfd.restitution = 0.0f;
    anchorfd.isSensor=false;
    
    FixtureDef anchorfd2 = new FixtureDef();
    anchorfd2.shape = anchorbox2;
    anchorfd2.friction=3f;
    anchorfd2.density=5f;
    anchorfd2.restitution = 0.0f;
    anchorfd2.isSensor=false;
    
	anchorbody =world.createBody(anchorbd);
	
	Fixture anchorfix = anchorbody.createFixture(anchorfd);
	 Fixture anchorfix2 = anchorbody.createFixture(anchorfd2);
	anchorfix.setUserData("anchor");
	anchorfix2.setUserData("anchor");
	anchorbody.setUserData(anchorsprite);

	anchorbox.dispose();
	 anchorbox2.dispose();
	
	//TODO  add chains
	BodyDef chainbd=new BodyDef();
	chainbd.type=BodyType.DynamicBody;
	PolygonShape chainbox =new PolygonShape();
	chainbox.setAsBox(chainsprite.getWidth()/2*game.WORLD_TO_BOX, chainsprite.getHeight()/2*game.WORLD_TO_BOX);
	FixtureDef chainfix =new FixtureDef();
	chainfix.shape=chainbox;
	chainfix.density=2f;
	  
	 for(int i = 0 ; i<5;i++){
		 
    	 chainbd.position.set((x)*SubAbyss.WORLD_TO_BOX,anchorbody.getPosition().y);
    	Body b=world.createBody(chainbd);
    	b.createFixture(chainfix);
    	b.setUserData(chainsprite);
    	b.setFixedRotation(false);
    	
	bodies.add(b);
     }
	
	 DistanceJointDef cj =new DistanceJointDef();
     cj.collideConnected=false;
     cj.dampingRatio=0;
     for(int i=0;i<bodies.size-1;i++){
    	 cj.bodyA=bodies.get(i);
    	 cj.localAnchorA.set(0, anchorsprite.getHeight()/6f*SubAbyss.WORLD_TO_BOX);
    	 cj.localAnchorB.set(0, -anchorsprite.getHeight()/6f*SubAbyss.WORLD_TO_BOX);
    	 cj.bodyB=bodies.get(i+1);
    	 cj.length=((4f)*SubAbyss.WORLD_TO_BOX);
    	 
    	 world.createJoint(cj);
         
     }
	anchorbody.setLinearVelocity(0f, -3f);
	cj.bodyB=bodies.get(0);
	cj.bodyA=anchorbody;
	cj.localAnchorA.set(0,anchorsprite.getHeight()/2*SubAbyss.WORLD_TO_BOX);
	world.createJoint(cj);
}	
public void draw() {
	if(anchorbody.getLinearVelocity().x>0) {
		anchorbody.applyForceToCenter(new Vector2(-0.2f*anchorbody.getLinearVelocity().x*anchorbody.getLinearVelocity().x,0), false);
	}
	if(anchorbody.getLinearVelocity().x<0) {
		anchorbody.applyForceToCenter(new Vector2(0.2f*anchorbody.getLinearVelocity().x*anchorbody.getLinearVelocity().x,0), false);
	}
	if(anchorbody.getLinearVelocity().y<0) {
		anchorbody.applyForceToCenter(new Vector2(0,0.2f*anchorbody.getLinearVelocity().y*anchorbody.getLinearVelocity().y), false);
	}
	if(anchorbody.getLinearVelocity().y>0) {
		anchorbody.applyForceToCenter(new Vector2(0,-0.2f*anchorbody.getLinearVelocity().y*anchorbody.getLinearVelocity().y), false);
	}
	anchorsprite.setPosition(anchorbody.getPosition().x*SubAbyss.BOX_WORLD_TO-anchorsprite.getWidth()/2, anchorbody.getPosition().y*SubAbyss.BOX_WORLD_TO-anchorsprite.getHeight()/2);
	anchorsprite.setRotation(anchorbody.getAngle()*MathUtils.radiansToDegrees);
	anchorsprite.draw(batch);
	for(Body i : bodies) {
		((Sprite) i.getUserData()).setPosition(i.getPosition().x*SubAbyss.BOX_WORLD_TO-chainsprite.getWidth()/2, i.getPosition().y*SubAbyss.BOX_WORLD_TO-chainsprite.getHeight()/2);
		((Sprite) i.getUserData()).setRotation(i.getAngle()*MathUtils.radiansToDegrees);
		((Sprite) i.getUserData()).draw(batch);
	}
}
public Body getBody() {
	return anchorbody;
}
public void dispose() {
	world.destroyBody(anchorbody);
	for(Body i:bodies) {
		world.destroyBody(i);
	}
}
	
}
