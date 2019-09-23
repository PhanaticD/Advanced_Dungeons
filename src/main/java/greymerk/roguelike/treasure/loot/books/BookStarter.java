package greymerk.roguelike.treasure.loot.books;

import greymerk.roguelike.treasure.loot.BookBase;
import zhehe.advanceddungeons.AdvancedDungeons;

public class BookStarter extends BookBase{

	public BookStarter(){
		super("greymerk", "Roguelike Dungeons");
				
		this.addPage("Roguelike Dungeons v" + AdvancedDungeons.version + "\n"
			+ AdvancedDungeons.date + "\n\n"
			+ "Credits\n\n"
			+ "Author: Greymerk\n\n"
			+ "Bits: Drainedsoul\n\n"
			+ "Ideas: Eniko @enichan"
			);
	}
}
