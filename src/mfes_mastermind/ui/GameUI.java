package mfes_mastermind.ui;

import mfes_mastermind.Game;
import mfes_mastermind.GameUtils;
import mfes_mastermind.GameUtils.Feedback;
import mfes_mastermind.GameUtils.Settings;

import org.overture.codegen.runtime.Utils;
import org.overture.codegen.runtime.VDMSeq;

public class GameUI extends UI{

	Game game;

	public GameUI(Game game) {
		super();
		this.game = game;
	}

	@SuppressWarnings("unchecked")
	public void play() {
		clear();
		while(running) {
			Settings settings = game.getSettings();
			System.out.println("Sequence Length: " + settings.sequenceLength);
			System.out.println("Number of Colors: " + settings.numberColors);
			if (settings.allowDuplicates) System.out.println("Allows duplicates");
			System.out.println("Number of Tries: " + settings.numberTries);
			System.out.println("(DEMO) Answer: " + game.getAnswer());
			
			if (game.getGuesses().size() > 0) 
				System.out.println("\nGuesses: ");			
			for(int i = 1; i <= game.getGuesses().size(); i++) {
				VDMSeq guess = (VDMSeq) Utils.get(game.getGuesses(),i);
				Feedback feedback = (Feedback) Utils.get(game.getFeedbacks(),i);
				System.out.println(i + " - " + guess + " - " +
						feedback.correct + " red, " +
						feedback.misplaced + " white.");				
			}
			
			System.out.print("\nEnter a guess ou (q)uit: ");

			String input = scanner.nextLine();
			
			if (input.equals("q")) 
				running = false;
			else try {
				VDMSeq guess = new VDMSeq();
				for(String num : input.split(" "))
				    guess.add(Integer.parseInt(num));
				
				if (!GameUtils.validGuessForSettings(guess, game.getSettings()))
					throw new IllegalArgumentException();
				
				game.makeGuess(guess);
				running = !game.over();
				clear();
			} catch (IllegalArgumentException e) {
				flash("Invalid guess!");
			}	
			
		}
		
		if (game.won())	flash("Game won in " + game.getNeededTries() + " move(s)");
		else if (game.lost()) flash("Game lost");
		else flash("Game canceled");
	}

}
