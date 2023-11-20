package com.skilldistillery.filmquery.app;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {
	DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) {
		FilmQueryApp app = new FilmQueryApp();
		try {
//	 app.test();
			app.launch();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void test() throws SQLException {
		Film film = db.findFilmById(7);
		System.out.println(film);
	}

	private void launch() {
		Scanner input = new Scanner(System.in);

		startUserInterface(input);

		input.close();
	}

	private void startUserInterface(Scanner input) {
		String keepGoing = "Y";

		while (keepGoing.toUpperCase().equals("Y")) {
			int userSelection = 0;
			System.out.println();
			System.out.println("|----------------------------------------------|");
			System.out.println("|---  * Choose From The Following *  ----------|");
			System.out.println("|---    1. Look up a film by its id   ---------| ");
			System.out.println("|---    2. Look up a film by a search keyword -|");
			System.out.println("|---    3. Exit       -------------------------|");
			System.out.println("|----------------------------------------------|");
			System.out.println();
			userSelection = input.nextInt();
			input.nextLine();
			switch (userSelection) {

			case 1:
				System.out.println("Looks like you know your stuff! \n\nWhat if the ID of your moive? ");
				int movieID = input.nextInt();
				input.nextLine();
				try {
					Film film = db.findFilmById(movieID);
					if (film == null) {
						System.out.println("\n*****\t NO FILM FOUND!\t*****");
					} else {
						System.out.println(film);
						List<Actor> actors = db.findActorsByFilmId(movieID);
						System.out.println("\n\t\t***** These are the Actors in the film*****\n");
						for (Actor actor : actors) {
							System.out.println(actor);
							System.out.println();
						}

					}

				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;
			case 2:

				System.out.println(
						"Looks like you're having a hard time finding the right Movie ?\n\nGive me a Keyword to look it up by.");

				String keyword = input.nextLine();

				List<Film> films = db.findFilmsByKeyword(keyword);
				if (films.isEmpty()) {
					System.out.println("\n*****\t NO FILM FOUND!\t*****");
					System.out.println("\n*****\tTry another word\t*****");

				} else {
					for (Film film : films) {
						System.out.println(film);
						System.out.println();
					}
					System.out.println("\n\n***\tAll of these movies match your search!\n***\tHave at it !");
				}

				break;
			case 3:
				System.out.println("Come back with popcorn next time!\n\nGoodBye!");
				keepGoing = "n";
				break;

			default:
				System.out.println("Come on now\n\nRead the prompt!!");
			}

		}

	}

}
