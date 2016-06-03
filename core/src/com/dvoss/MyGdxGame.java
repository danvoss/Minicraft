package com.dvoss;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Random;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	TextureRegion down, up, stand, right, left, tree, zombie;
	float x, y, xv, yv, zombiex, zombiey, zombxv, zombyv;


	static final float MAX_VELOCITY = 100;
	static final float DECELERATION = 0.5f;

	static final int WIDTH = 16;
	static final int HEIGHT = 16;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		Texture tiles = new Texture("tiles.png");
		TextureRegion[][] treeGrid = TextureRegion.split(tiles, WIDTH/2, HEIGHT/2);
		TextureRegion[][] grid = TextureRegion.split(tiles, 16, 16);
		down = grid[6][0];
		up = grid[6][1];
		stand = grid[6][2];
		right = grid[6][3];
		left = new TextureRegion(right);
		left.flip(true, false);
		tree = treeGrid[1][0];
		tree.setRegionWidth(WIDTH);
		tree.setRegionHeight(HEIGHT);
		zombie = grid[6][7];
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

		if (x < 0) {
			x = 600;
		}
		if (x > 600) {
			x = 0;
		}
		if (y < 0) {
			y = 600;
		}
		if (y > 600) {
			y = 0;
		}

		moveZombie();

		if (zombiex < 0) {
			zombiex = 600;
		}
		if (zombiex > 600) {
			zombiex = 0;
		}
		if (zombiey < 0) {
			zombiey = 600;
		}
		if (zombiey > 600) {
			zombiey = 0;
		}


		Gdx.gl.glClearColor(0, 1, 0.4f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, x, y, WIDTH * 2, HEIGHT * 2);
		batch.draw(zombie, zombiex, zombiey, WIDTH * 2, HEIGHT * 2);
		batch.draw(tree, 300, 300, WIDTH * 1.5f, HEIGHT * 1.5f);
		batch.draw(tree, 195, 453, WIDTH * 1.5f, HEIGHT * 1.5f);
		batch.draw(tree, 547, 234, WIDTH * 1.5f, HEIGHT * 1.5f);
		batch.draw(tree, 222, 123, WIDTH * 1.5f, HEIGHT * 1.5f);
		batch.draw(tree, 432, 564, WIDTH * 1.5f, HEIGHT * 1.5f);
		batch.draw(tree, 100, 80, WIDTH * 1.5f, HEIGHT * 1.5f);
		batch.draw(tree, 80, 300, WIDTH * 1.5f, HEIGHT * 1.5f);
		batch.draw(tree, 520, 80, WIDTH * 1.5f, HEIGHT * 1.5f);
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
	public void moveZombie() {

		float delta = Gdx.graphics.getDeltaTime();
		zombiey += zombyv * delta;
		zombiex += zombxv * delta;

		Random random = new Random();
		if (random.nextBoolean() == true) {
			zombyv = MAX_VELOCITY;
		}
		else if (random.nextBoolean() == false) {
			zombyv = -MAX_VELOCITY;
		}
		if (random.nextBoolean() == true) {
			zombxv = MAX_VELOCITY;
		}
		else if (random.nextBoolean() == false) {
			zombxv = -MAX_VELOCITY;
		}
	}
}
