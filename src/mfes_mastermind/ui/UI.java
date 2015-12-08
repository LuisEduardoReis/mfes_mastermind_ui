package mfes_mastermind.ui;

import java.util.Scanner;

public class UI {

	protected Scanner scanner;
	protected boolean running;
	
	public UI() {
		this.scanner = new Scanner(System.in);
		this.running = true;
	}

	protected void clear() {
		for (int i = 0; i < 100; i++)
			System.out.println();
	}

	protected void flash(String message) {
		clear();
		System.out.println(message);
		System.out.println();
	}

}