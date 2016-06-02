package com.dvoss;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	TextureRegion down, up, stand, right, left;
	float x, y, xv, yv;

	static final float MAX_VELOCITY = 100;
	static final float DECELERATION = 0.5f;

	static final int WIDTH = 18;
	static final int HEIGHT = 26;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		Texture tiles = new Texture("tiles.png");
		TextureRegion[][] grid = TextureRegion.split(tiles, 16, 16);
		down = grid[6][0];
		up = grid[6][1];
		stand = grid[6][2];
		right = grid[6][3];
		left = new TextureRegion(right);
		left.flip(true, false);
	}

	@Override
	public void render () {
		move();

		TextureRegion img;
		if (yv < 0) {
			img = down;
		}
		else if (yv > 0) {
			img = up;
		}
		else if (xv > 0) {
			img = right;
		}
		else if (xv < 0) {
			img = left;
		}
		else {
			img = stand;
		}

		Gdx.gl.glClearColor(0, 1, 0.4f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, x, y, WIDTH * 3, HEIGHT * 3);
		batch.end();
	}

	public void move() {
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			yv = MAX_VELOCITY;
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			yv = -MAX_VELOCITY;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			xv = MAX_VELOCITY;
		}
		else if (Gdx.input.isKeyPressed((Input.Keys.LEFT))) {
			xv = -MAX_VELOCITY;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
				yv = MAX_VELOCITY * 2;
			}
			else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
				yv = -MAX_VELOCITY * 2;
			}
			if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
				xv = MAX_VELOCITY * 2;
			}
			else if (Gdx.input.isKeyPressed((Input.Keys.LEFT))) {
				xv = -MAX_VELOCITY * 2;
			}
		}

		float delta = Gdx.graphics.getDeltaTime();
		y += yv * delta;
		x += xv * delta;

		yv = decelerate(yv);
		xv = decelerate(xv);
	}

	public float decelerate(float velocity) {
		velocity *= DECELERATION;
		if (Math.abs(velocity) < 1) {
			velocity = 0;
		}
		return velocity;
	}
}
