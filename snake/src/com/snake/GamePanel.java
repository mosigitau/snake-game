package com.snake;

import java.awt.*;
import java.awt.event.*;


import javax.swing.*;

import java.util.Random;
import java.util.random.*;

public class GamePanel extends JPanel  implements ActionListener {
	
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HIGHT = 600;
	static final int UNIT_SIZE = 25;
	static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HIGHT) / UNIT_SIZE;
	static final int DELAY = 75;
	final int x[] = new int[GAME_UNITS];
	final int y[] = new int[GAME_UNITS];
	int bodyParts = 6;
	int applesEaten;
	int appleX;
	int appleY;
	
	char direction = 'R';
	boolean running = false;
	
	Timer timer;
	Random random;
	
	
	GamePanel(){
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
		
	}
	
	public void startGame() {
		newApple();
		running = true;
		timer = new Timer(DELAY, this);
		timer.start();
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
		
	}
	
	public void draw(Graphics g) {
		if(running) {
			//this will draw lines 
		/*
		for(int i = 0; i < SCREEN_HIGHT/UNIT_SIZE; i++) {
			g.drawLine(i*UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HIGHT);
			g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
		}  */
			
		g.setColor(Color.red);
		g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
		
		for(int i = 0; i < bodyParts; i++) {
			if(i == 0) {
				g.setColor(Color.green);
				g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
			}else {
				//g.setColor(new Color(45,180,0));
				g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
				g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
			}
			
			g.setColor(Color.red);
			g.setFont(new Font("Ink Free", Font.BOLD, 30));
			FontMetrics mentrics = getFontMetrics(getFont());
			g.drawString("SCORE: " + applesEaten, (SCREEN_WIDTH - mentrics.stringWidth("SCORE: " + applesEaten))/3, g.getFont().getSize());

		}
		}else {
			gameOver(g);
		}
		
	}
	public void newApple() {
		appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE)) * UNIT_SIZE;
		appleY = random.nextInt((int)(SCREEN_HIGHT/UNIT_SIZE)) * UNIT_SIZE;
		
	}
	public void move() {
		for(int i = bodyParts; i > 0; i--) {
			x[i] = x[i - 1];
			y[i] = y[i - 1];
		
		}
		switch(direction) {
		case 'U':
			y[0] =  y[0] - UNIT_SIZE;
			break;
		case 'D':
			y[0] =  y[0] + UNIT_SIZE;
			break;
		case 'L':
			x[0] =  x[0] - UNIT_SIZE;
			break;
		case 'R':
			x[0] =  x[0] + UNIT_SIZE;
			break;	
			
			
		
		
		}
		
	}
	public void checkApple() {
		if((x[0] == appleX) && (y[0] == appleY)) {
			bodyParts++;
			applesEaten++;
			newApple();
		}
	}
	public void checkCollisions() {
		
		for(int i = bodyParts; i > 0; i--) {
			//check if head collides with body
			if((x[0] == x[i]) && (y[0] == y[i])) {
				running = false;
			}
		}
		//check if head touches left border
		if(x[0] < 0) {
			running  = false;
		}
		//check if head touches right border
		if(x[0] > SCREEN_WIDTH) {
			running  = false;
		}
		//check if the head touches the top border
		if(y[0] < 0) {
			running  = false;
		}
		//check if the head touches bottom border 
		if(y[0] > SCREEN_HIGHT) {
			running  = false;
		}
		if(!running) {
			timer.stop();
		}
		
	}
	public void gameOver(Graphics g) {
		
		//score
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 30));
		FontMetrics mentrics1 = getFontMetrics(getFont());
		g.drawString("SCORE: " + applesEaten, (SCREEN_WIDTH - mentrics1.stringWidth("SCORE: " + applesEaten))/3, g.getFont().getSize());
		//game over text
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 55));
		FontMetrics mentrics2 = getFontMetrics(getFont());
		g.drawString("GAME OVER", (SCREEN_WIDTH - mentrics2.stringWidth("GAME OVER"))/3, SCREEN_HIGHT/2);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
        if(running) {
        	move();
        	checkApple();
        	checkCollisions();
        }
        repaint();

		
	}
	public class MyKeyAdapter extends KeyAdapter {
		@Override
		public void keyPressed (KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction != 'D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction != 'U') {
					direction = 'D';
				}
				break;
			}
			
		}
	}
	
	

}
