package ro.asimandi.simsec.utils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;

public class WordUtils {
	

	
	public static List<String> getWorkRelatedWords() throws IOException{
		
		String[] baseWords = {"boss","office","job","work","employer"};
		String[] wrongWords = {"line"};
		
		String path = "dict";
		URL url = new URL("file", null, path);
		IDictionary dict = new Dictionary(url);
		dict.open();
		
		Set<String> workRelatedWords = new TreeSet<String>();
		
		for (String baseWord : baseWords) {
			IIndexWord idxWord = dict.getIndexWord(baseWord, POS.NOUN);
			for (IWordID wordID : idxWord.getWordIDs()) {
				wordID = idxWord.getWordIDs().get(0); // 1st meaning
				IWord word = dict.getWord(wordID);
				ISynset synset = word.getSynset();

				// iterate over words associated with the synset
				for (IWord w : synset.getWords()){
					workRelatedWords.add(w.getLemma().replace("_", " "));
				}

			}			
		}
		
		for (int i = 0; i < wrongWords.length; i++) {
			workRelatedWords.remove(wrongWords[i]);
		}
		
		List<String> workRelatedList = new ArrayList<String>();
		workRelatedList.addAll(workRelatedWords);
		return workRelatedList;
	}
	
	
	// TODO departure words
	public static List<String> getHolidayWords() throws IOException{
		
//		String[] baseWords = {"holiday","summer"};
		String[] baseWords = {"leave","go"};
		String[] wrongWords = {};
		
		String path = "dict";
		URL url = new URL("file", null, path);
		IDictionary dict = new Dictionary(url);
		dict.open();
		
		Set<String> workRelatedWords = new TreeSet<String>();
		
		for (String baseWord : baseWords) {
			IIndexWord idxWord = dict.getIndexWord(baseWord, POS.VERB);
			for (IWordID wordID : idxWord.getWordIDs()) {
				wordID = idxWord.getWordIDs().get(0); // 1st meaning
				IWord word = dict.getWord(wordID);
				ISynset synset = word.getSynset();

				// iterate over words associated with the synset
				for (IWord w : synset.getWords()){
					workRelatedWords.add(w.getLemma().replace("_", " "));
				}

			}			
		}
		
		for (int i = 0; i < wrongWords.length; i++) {
			workRelatedWords.remove(wrongWords[i]);
		}
		
		List<String> workRelatedList = new ArrayList<String>();
		workRelatedList.addAll(workRelatedWords);
		return workRelatedList;
	}
	
}
