import java.io.*;
import java.util.*;

public class Flashcard {

	private Scanner in;
	private String title;
	
	List<String> questions;
	List<String> answers;
	
	public Flashcard(int numQs, boolean exists, String block, int numCards) throws IOException {
		
		answers = new ArrayList<String>();
		questions = new ArrayList<String>();
		
		if(!exists) {
			
		in = new Scanner(System.in);	
		
		System.out.println("Enter title for flashcard set: ");		
		title = in.nextLine();
		
		
		for(int qOrA = 0; qOrA < 2; qOrA++) {
			
			for(int a = 1; a <= numQs; a++) {
				
				if(qOrA == 0) {
					
				System.out.println("Write question " + a + ": ");
				
				questions.add(in.nextLine());
				
				
				} else {
					
				System.out.println("Write answer to question " + a + ": ");
				
				answers.add(in.nextLine());
				
			}
				
		}
	}
		
		PrintWriter flashWriter = new PrintWriter(new FileWriter (new File("cards.txt"), true));
		flashWriter.println();
		flashWriter.println("Set <<" + title + ">> -{}/**&#@@ " + numCards);
		
		int cnt = 1;
		for(String idx : questions) {
			
			flashWriter.println(cnt + " ques: " + idx);
			cnt++;
		}
		
		cnt = 1;
		for(String idx : answers) {
			
			flashWriter.println(cnt + " ans: " + idx);
			cnt++;
		}
		
		flashWriter.flush();
	
		
	} else if (exists) {
			
			in = new Scanner(block);
			
			while(in.hasNextLine()) {
				String ln = in.nextLine();
				
				
				if(ln.contains("ques:")) {
					questions.add(ln.substring(8));
					
				} else if (ln.contains("ans:")) {
					answers.add(ln.substring(7));
				} else if(ln.contains("<<")){
					title = ln.substring(ln.indexOf("<") + 2, ln.indexOf(">"));
					
				}
			}
				
		}
					
	}
	
	
	@Override
	public String toString() {
		return this.title;
	}
	
	public List<String> getQs(){
		return questions;
	}
	
	public List<String> getAns(){
		return answers;
	}
	
	
	
}
