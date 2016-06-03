package com.dvoss;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Random;

import static com.badlogic.gdx.Gdx.input;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	TextureRegion down, up, stand, right, left, tree, zombie_u, zombie_d, zombie_r, zombie_l, zombie_s, jelly1, jelly2;
	float x, y, xv, yv, zom_x, zom_y, zom_xv, zom_yv, jel_y, jel_x, jel_yv, jel_xv;
	BitmapFont font;


	static final float MAX_VELOCITY = 100;
	static final float DECELERATION = 0.5f;

	static final int WIDTH = 16;
	static final int HEIGHT = 16;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		Texture tiles = new Texture("tiles.png");
		TextureRegion[][] treeGrid = TextureRegion.split(tiles, WIDTH / 2, HEIGHT / 2);
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
		zombie_s = grid[6][6];
		zombie_u = grid[6][5];
		zombie_d = grid[6][4];
		zombie_r = grid[6][7];
		zombie_l = new TextureRegion(zombie_r);
		zombie_l.flip(true, false);
		jelly1 = grid[7][4];
		jelly2 = grid[7][5];
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

		TextureRegion z_img;
		if (zom_yv < 0) {
			z_img = zombie_d;
		}
		else if (zom_yv > 0) {
			z_img = zombie_u;
		}
		else if (zom_xv > 0) {
			z_img = zombie_r;
		}
		else if (zom_xv < 0) {
			z_img = zombie_l;
		}
		else z_img = zombie_s;

		if (zom_x < 0) {
			zom_x = 600;
		}
		if (zom_x > 600) {
			zom_x = 0;
		}
		if (zom_y < 0) {
			zom_y = 600;
		}
		if (zom_y > 600) {
			zom_y = 0;
		}

		moveJelly();

		TextureRegion j_img;
		if (jel_yv > 0) {
			j_img = jelly1;
		}
		else {
			j_img = jelly2;
		}

		if (jel_x < 0) {
			jel_x = 600;
		}
		if (jel_x > 600) {
			jel_x = 0;
		}
		if (jel_y < 0) {
			jel_y = 600;
		}
		if (jel_y > 600) {
			jel_y = 0;
		}

		Sound sound1 = Gdx.audio.newSound(Gdx.files.internal("death.wav"));
		if ((Math.abs(x - zom_x) < 10) && (Math.abs(y - zom_y) < 10)) {
			sound1.play(1.0f);
		}

		Sound sound2 = Gdx.audio.newSound(Gdx.files.internal("pickup.wav"));
		if ((Math.abs(x - 300) < 8) && (Math.abs(y - 300) < 8) || (Math.abs(x - 195) < 8) && (Math.abs(y - 453) < 8)
				|| (Math.abs(x - 547) < 8) && (Math.abs(y - 234) < 8) || (Math.abs(x - 222) < 8) && (Math.abs(y - 123)
				< 8) || (Math.abs(x - 432) < 8) && (Math.abs(y - 564) < 8) || (Math.abs(x - 100) < 8) && (Math.abs(y
				- 80) < 8) || (Math.abs(x - 80) < 8) && (Math.abs(y - 300) < 8) || (Math.abs(x - 520) < 8) &&
				(Math.abs(y - 80) < 8)) {
			sound2.play(1.0f);
		}

		Sound sound3 = Gdx.audio.newSound(Gdx.files.internal("playerhurt.wav"));
		if ((Math.abs(x - jel_x) < 10) && (Math.abs(y - jel_y) < 10)) {
			sound3.play(1.0f);
		}

		BitmapFont font = new BitmapFont();
		CharSequence obj = "Run through the trees to power up, and avoid the dangerous Blob and deadly Zombie!";

		Gdx.gl.glClearColor(0, 1, 0.4f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		font.draw(batch, obj, 25, 25);
		batch.draw(img, x, y, WIDTH * 2, HEIGHT * 2);
		batch.draw(z_img, zom_x, zom_y, WIDTH * 2, HEIGHT * 2);
		batch.draw(j_img, jel_x, jel_y, WIDTH * 2, HEIGHT * 2);
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
		if (input.isKeyPressed(Input.Keys.UP)) {
			yv = MAX_VELOCITY;
		}
		else if (input.isKeyPressed(Input.Keys.DOWN)) {
			yv = -MAX_VELOCITY;
		}
		if (input.isKeyPressed(Input.Keys.RIGHT)) {
			xv = MAX_VELOCITY;
		}
		else if (input.isKeyPressed((Input.Keys.LEFT))) {
			xv = -MAX_VELOCITY;
		}
		if (input.isKeyPressed(Input.Keys.SPACE)) {
			if (input.isKeyPressed(Input.Keys.UP)) {
				yv = MAX_VELOCITY * 2;
			}
			else if (input.isKeyPressed(Input.Keys.DOWN)) {
				yv = -MAX_VELOCITY * 2;
			}
			if (input.isKeyPressed(Input.Keys.RIGHT)) {
				xv = MAX_VELOCITY * 2;
			}
			else if (input.isKeyPressed((Input.Keys.LEFT))) {
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
		zom_y += zom_yv * delta;
		zom_x += zom_xv * delta;

		Random random = new Random();

		if (random.nextBoolean()) {
			zom_yv = MAX_VELOCITY/1.5f;
		}
		else if (!random.nextBoolean()) {
			zom_yv = -MAX_VELOCITY/1.5f;
		}
		if (random.nextBoolean()) {
			zom_xv = MAX_VELOCITY/1.5f;
		}
		else if (!random.nextBoolean()) {
			zom_xv = -MAX_VELOCITY/1.5f;
		}
	}

	public void moveJelly() {

		float delta = Gdx.graphics.getDeltaTime();
		jel_y += jel_yv * delta;
		jel_x += jel_xv * delta;

		Random random = new Random();

		if (random.nextBoolean()) {
			jel_yv = MAX_VELOCITY;
		}
		else if (!random.nextBoolean()) {
			jel_yv = -MAX_VELOCITY;
		}
		if (random.nextBoolean()) {
			jel_xv = MAX_VELOCITY;
		}
		else if (!random.nextBoolean()) {
			jel_xv = -MAX_VELOCITY;
		}
	}
}
