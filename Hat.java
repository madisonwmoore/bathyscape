package com.madison.Bathyscape.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Hat extends Equipment{

Sprite hatsprite;
String hattype;
String description;
String subname;
SubAbyss game;
public Hat(String hattype,SubAbyss game, boolean anything) {
	this.hattype=hattype;
	this.game=game;
	if(hattype.equals("Santa Hat")) {
		description="A festive holiday hat";
		hatsprite=new Sprite(game.assman.manager.get("data/santahat.png",Texture.class));
		}
}
public Hat(String hattype, SubAbyss game) {
this.hattype=hattype;
this.game=game;
subname=game.level.sub.getSubName();
if(hattype.equals("Santa Hat")) {
	hatsprite=new Sprite(game.assman.manager.get("data/santahat.png",Texture.class));
	}
}	
	public void draw() {
		if(hattype.equals("Santa Hat")) {
			hatsprite.setOrigin(hatsprite.getWidth()/2+10,-hatsprite.getHeight()/4);
			if(subname.equals("U.S.S Sandwich")) {
				hatsprite.setPosition(game.level.sub.getBody().getPosition().x*game.BOX_WORLD_TO-26,game.level.sub.getBody().getPosition().y*game.BOX_WORLD_TO+58f);
			
			}
			
			if(game.level.sub.getBody().getLinearVelocity().x>0.0f) {
				if(hatsprite.getRotation()<10f) {
				hatsprite.rotate(1f);}
			}
			else if(game.level.sub.getBody().getLinearVelocity().x<-0.1f) {
				if(hatsprite.getRotation()>-4f) {
				hatsprite.rotate(-1f);}
			}
			else{
				
				if(hatsprite.getRotation()>0f) {
					hatsprite.rotate(-1f);}
				else if(hatsprite.getRotation()<0f) {
					hatsprite.rotate(1f);}
				
				
				
				//hatsprite.setRotation(0f);
				
			
			}
			if(subname.equals("Alvin")) {
				hatsprite.setPosition(game.level.sub.getBody().getPosition().x*game.BOX_WORLD_TO+20,game.level.sub.getBody().getPosition().y*game.BOX_WORLD_TO+62f);
				if(game.level.sub.getBody().getLinearVelocity().x>0.4f) {
					if(hatsprite.getRotation()<7f) {
					hatsprite.rotate(1f);}
				}
				else if(game.level.sub.getBody().getLinearVelocity().x<-0.4f) {
					if(hatsprite.getRotation()>-1f) {
					hatsprite.rotate(-1f);}
				}
				else{
					
					if(hatsprite.getRotation()>0f) {
						hatsprite.rotate(-1f);}
					else if(hatsprite.getRotation()<0f) { 
						hatsprite.rotate(1f);}
			}}
			if(subname.equals("DSV Shinkai 6500")) {
				hatsprite.setPosition(game.level.sub.getBody().getPosition().x*game.BOX_WORLD_TO+55,game.level.sub.getBody().getPosition().y*game.BOX_WORLD_TO+44f);
				hatsprite.setRotation(1f);
				if(game.level.sub.getBody().getLinearVelocity().x>0.4f) {
					if(hatsprite.getRotation()<10f) {
					hatsprite.rotate(1f);}
				}
				else if(game.level.sub.getBody().getLinearVelocity().x<-0.4f) {
					if(hatsprite.getRotation()>-10f) {
					hatsprite.rotate(-1f);}
				}
				else{
					
					if(hatsprite.getRotation()>0f) {
						hatsprite.rotate(-1f);}
					else if(hatsprite.getRotation()<0f) {
						hatsprite.rotate(1f);
			}
		}}
			if(subname.equals("Mir 2")) {
				hatsprite.setPosition(game.level.sub.getBody().getPosition().x*game.BOX_WORLD_TO+16,game.level.sub.getBody().getPosition().y*game.BOX_WORLD_TO+55f);
				if(game.level.sub.getBody().getLinearVelocity().x>0.4f) {
					if(hatsprite.getRotation()<10f) {
					hatsprite.rotate(1f);}
				}
				else if(game.level.sub.getBody().getLinearVelocity().x<-0.4f) {
					if(hatsprite.getRotation()>-8f) {
					hatsprite.rotate(-1f);}
				}
				else{
					
					if(hatsprite.getRotation()>0f) {
						hatsprite.rotate(-1f);}
					else if(hatsprite.getRotation()<0f) {
						hatsprite.rotate(1f);
			}
			}}
		hatsprite.draw(game.level.batch);
	}}
	public String getEquipmentName() {
		return hattype;
		
	}
	public String getEquipmentDescription() {
		return description;
		
	}
	public Sprite getHatSprite() {
		return hatsprite;
	}
	
}
