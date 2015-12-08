package mfes_mastermind.ui;

import mfes_mastermind.Championship;
import mfes_mastermind.Championship.ChampionshipEntry;
import mfes_mastermind.Game;
import mfes_mastermind.GameUtils.Settings;
import mfes_mastermind.Team;

import org.overture.codegen.runtime.Utils;
import org.overture.codegen.runtime.VDMSeq;

public class ChampionshipUI extends UI {

	Championship championship;

	public void run() {
		clear();
		Settings settings = inputSettings();

		this.championship = new Championship(settings);

		while (running) {
			System.out.println(championship);
			System.out.println(" - (c)reate (t)eam");
			System.out.println(" - (c)reate (p)layer");
			System.out.println(" - (r)emove (t)eam");
			System.out.println(" - (r)emove (p)layer");
			System.out.println(" - (n)ew (g)ame");
			System.out.println(" - (d)emo (g)ame");
			System.out.println(" - (r)anking");
			System.out.println(" - (q)uit");
			System.out.print("Enter an option (ct, cp, rt, rp, ng, dg, r, q): ");

			switch (scanner.next()) {
			// New Team
			case "ct": createTeam(); break;
			
			// New Player
			case "cp": createPlayer(); break;
			
			// Remove Team
			case "rt": removeTeam(); break;
						
			// Remove Player
			case "rp": removePlayer(); break;
			
			// New Game
			case "ng": newGame(); break;
			
			// New Game
			case "dg": demoGame(); break;
			
			// Ranking
			case "r": showRanking(); break;

			// Exit
			case "q": running = false; flash("Goodbye"); break;
			
			// Default
			default: flash("Invalid option!");
			}
		}
	}

	private Settings inputSettings() {
		System.out.print("Use default settings (y/n)?: ");
		if (!scanner.next().equals("n")) {
			clear();
			return null;
		}
		
		try {
			System.out.print("Sequence length: ");
			long seq_length = Long.parseLong(scanner.next());
			System.out.print("Number of colors: ");
			long num_colors = Long.parseLong(scanner.next());
			System.out.print("Number of tries: ");
			long num_tries = Long.parseLong(scanner.next());
			System.out.print("Allow duplicates (y/n): ");
			boolean allow_dups = scanner.next().equals("y");
			
			if (!(
				(seq_length <= num_colors || allow_dups) 
				&& num_colors > 1
				&& num_tries > 0
				&& seq_length > 0				
			))
				throw new IllegalArgumentException();
			
			clear();
			return new Settings(seq_length, num_colors, num_tries, allow_dups);
		} catch (IllegalArgumentException e) {
			flash("Invalid settings. Using defaults");
			return null;
		}		
	}

	private void createTeam() {
		System.out.print("\tEnter a team name: ");
		String name = scanner.next();
		
		try {
			championship.findTeam(name);
			flash("Team already exists!");
			return;
		} catch (RuntimeException e) {}
		
		championship.createTeam(name);
		flash("Team " + name + " created!");
	}

	private void createPlayer() {
		System.out.print("\tEnter a team name: ");
		String team_name = scanner.next(); Team team;
		
		try {
			team = championship.findTeam(team_name);
		} catch (RuntimeException e) {
			flash("Team " + team_name + " does not exist!");
			return;
		}
		
		System.out.print("\tEnter a player name: ");
		String player = scanner.next();
		
		if (player.equals("")) {
			flash("Player name can't be empty!");
			return;
		}
		if (team.getPlayers().contains(player)) {
			flash("Team " + team_name + " already has a player named " + player + "!");
			return;
		}
		
		championship.createPlayer(team_name, player);
		flash("Player " + player + " created in team " + team_name + "!");
	}
	
	
	private void removeTeam() {
		System.out.print("\tEnter a team name: ");
		String name = scanner.next();
		
		try {
			championship.findTeam(name);
		} catch (RuntimeException e) {
			flash("Team doesn't exist!");
		}
		
		championship.removeTeam(name);
		flash("Team " + name + " removed!");		
	}
	
	private void removePlayer() {
		System.out.print("\tEnter a team name: ");
		String team_name = scanner.next(); Team team;
		
		try {
			team = championship.findTeam(team_name);
		} catch (RuntimeException e) {
			flash("Team " + team_name + " does not exist!");
			return;
		}
		
		System.out.print("\tEnter a player name: ");
		String player = scanner.next();
		
		if (!team.getPlayers().contains(player)) {
			flash("Team " + team_name + " doesn't have a player named " + player + "!");
			return;
		}
		
		championship.removePlayer(team_name, player);
		flash("Player " + player + " removed from team " + team_name + "!");		
	}

	
	
	private void newGame() {
		System.out.print("\tEnter a team name: ");
		String team_name = scanner.next(); Team team;
		
		try {
			team = championship.findTeam(team_name);
		} catch (RuntimeException e) {
			flash("Team " + team_name + " does not exist!");
			return;
		}
		
		System.out.print("\tEnter a player name: ");
		String player = scanner.next();
		
		if (!team.getPlayers().contains(player)) {
			flash("Team " + team_name + " has no player named " + player + "!");
			return;
		}
		
		Game game = championship.newGame(team_name, player);
		new GameUI(game).play();
	}
	
	private void demoGame() {
		Game game = new Game(championship.getSettings(), null, "demo");
		new GameUI(game).play();
	}
	
	private void showRanking() {
		clear();
		System.out.println("Ranking:");
		VDMSeq ranking = championship.getResults();
		if (ranking.size() == 0) System.out.println("<empty>");
		for(int i = 1; i <= ranking.size(); i++) {
			ChampionshipEntry rank_entry = (ChampionshipEntry) Utils.get(ranking, i);
			System.out.println(i + "º - " + rank_entry.team + " - Average: " + rank_entry.average_team_points + " - Num.Games: " + rank_entry.num_games);
		}	
		System.out.println();
	}
}
