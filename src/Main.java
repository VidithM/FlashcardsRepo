
import java.io.*;
import java.util.*;

public class Main {

	static File flashFile;
	static PrintWriter writer;
	static ArrayList<Flashcard> flashList;
	static int numCards;
	
	static final String TITLE_IDENT = "-{}/**&#@@";
	
	public static void main(String[] args) throws IOException {
		
		flashList = new ArrayList<Flashcard>();
		
		flashFile = new File("cards.txt");
		writer = new PrintWriter(new FileWriter(flashFile, true));
		
		print("Welcome to Flashcard Manager (authored by Vidith Madhu)\nv1.1");
		
		updateFlashList();
		numCards =flashList.size();
		
		menu();
}

	
	static void newFlashcard() throws IOException {
		
		int numQs;
		
		Scanner in = new Scanner(System.in);
		
		print("Enter the number of questions in this flashcard set: ");
		
		while(true) {
			try {
				numQs = in.nextInt();
				break;
			} catch (InputMismatchException j) {
				print("Invalid input, try again.");
				in.nextLine();
			}
		}
		
		numCards++;
		
		flashList.add(new Flashcard(numQs, false, null, numCards));
		
	}
	
	static void getFlashcard(int n) throws IOException{
		
		Scanner in = new Scanner(System.in);
		
		if(n >= flashList.size() || n < 0) {
			System.out.println("Card does not exist");
			
		} else {
		
		Flashcard chosen = flashList.get(n);
		int cnt = 0;
		
		for(String idx : chosen.getQs()) {
			System.out.println(idx);
			System.out.println("Press enter to reveal the answer...");
			in.nextLine();
			System.out.println(chosen.getAns().get(cnt) + "\n");
			cnt++;
		}
	}
		menu();
	}
	
	static void print (String s) {
		System.out.println(s);
	}
	
	static void menu()  throws IOException {
		
		
		
		Scanner in = new Scanner(System.in);
		String inStuff;
		boolean exit = false;
		print("\nI would like to... (A) Create a new flashcard, (B) Review an existing flashcard, (C) Delete a flashcard, OR (D) Exit");
		
	while(true) {
		
		inStuff = in.nextLine();
		if(inStuff.equals("A")) {
			break;
		} else if (inStuff.equals("B")){
			break;	
		} else if (inStuff.equals("C")) {
			break;
		} else if(inStuff.equals("D")) {
			exit = true;
			break;
		} else {
			System.out.println("Invalid input, try again");
		}
				
	}
	
	if(exit) {
		return;
	}
	
	if(inStuff.equals("A")) {
		
		newFlashcard();
		menu();
		
	} else if (inStuff.equals("C")){
		delFlashcard();
	} else {
		
		if(numCards > 0) {
			
			System.out.println("Select a card to review by typing its corresponding number: ");
			int cnt = 1;
			for(Flashcard idx : flashList) {
				System.out.println(Integer.toString(cnt) + ". " +  idx);
				cnt++;
			}
			
			int idxGet;
			
		while(true) {
			try {
			idxGet = in.nextInt();
			break;
			}
			catch(InputMismatchException j) {
				System.out.println("Invalid input, try again!");
				in.nextLine();
			}
		
		}
			getFlashcard(idxGet - 1);
			
		} else {
			System.out.println("You haven't created any cards yet...\n");
			menu();
		}
		
	}
	}
	
	static void updateFlashList() throws IOException {
		
		
		Scanner fileScan = new Scanner(flashFile);
		String block = "";
		boolean blockWrite = false;
		
		if(fileScan.hasNextLine()) {
		fileScan.nextLine();
		} else return;
		
		while(fileScan.hasNextLine()) {
			String ln = fileScan.nextLine();
			
			
			if(ln.contains(TITLE_IDENT)) {
				
				blockWrite = true;
			} else if (ln.equals("")) {
				blockWrite = false;
				if(!block.equals("")) {
				flashList.add(new Flashcard(1, true, block, -1)); //If creating card from pre-existing data: pass numQs as 1!!!!
				}
				block = "";
			}
			
			if(blockWrite) {
				block += (ln + "\n");
			}
			
		}
		if(!block.equals("")) {
		flashList.add(new Flashcard(1, true, block, -1));
		}
	}
	
	static void delFlashcard() throws IOException {
		
		if(flashList.size() == 0) {
			System.out.println("You haven't created any cards yet...");
			menu();
		} else {
		
		Scanner fileScan = new Scanner(flashFile);
		Scanner in = new Scanner(System.in);
		String block= "";
		
		while(fileScan.hasNextLine()) {
			block += fileScan.nextLine() + "\n";
		}
		
		System.out.println("Select a flashcard to delete: \n");
		int cnt = 1;
		for(Flashcard idx : flashList) {
			System.out.println(cnt + ". " + idx);
			cnt++;
		}
		cnt--;
		int delCard;
		
		while(true) {
			try {
				
			delCard = in.nextInt();
			
			if(delCard >= 1 && delCard <= cnt) {
			break;
			
			} else {
				System.out.println("Card does not exist, try again");
			}
			
			} catch (InputMismatchException j) {
				System.out.println("Invalid input, try again");
				in.nextLine();
			}
		}
		
		writer = new PrintWriter(new FileWriter(flashFile, false));
		in = new Scanner(block);
		
		boolean inDelBlock = false;
		
		while(in.hasNextLine()) {
			String ln = in.nextLine();
			inDelBlock = false;
			
			if(ln.contains(TITLE_IDENT) && ln.contains(flashList.get(delCard - 1).toString())) {
				inDelBlock = true;
				while(in.hasNextLine()){
					ln = in.nextLine();
					if(ln.equals("")) {
						break;
					}
				}
				
			}
			if(!inDelBlock || in.hasNextLine()) {
			writer.println(ln);
			writer.flush();
			}
			
		}
		
		writer = new PrintWriter(new FileWriter(flashFile, true));
		flashList = new ArrayList<Flashcard>();
		updateFlashList();
		numCards =flashList.size();
		menu();
		
	}
	}

}
