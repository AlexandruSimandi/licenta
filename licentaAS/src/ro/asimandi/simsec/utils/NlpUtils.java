package ro.asimandi.simsec.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;

public class NlpUtils {
	
	public static String getSentiment(CoreMap sentence){
			return sentence.get(SentimentCoreAnnotations.SentimentClass.class);
	}
	
	public static List<CoreMap> getSentences(String post){
		 Properties props = new Properties();
		 props.setProperty("annotators", "tokenize, ssplit, pos, lemma, parse,sentiment");
		 StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		
		 Annotation annotation = pipeline.process(post);
		 List<CoreMap> sentences =
		 annotation.get(CoreAnnotations.SentencesAnnotation.class);
		 for (CoreMap sentence : sentences) {
			String sentiment = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
			System.out.println(sentiment + "\t" + sentence);
		}
		 
		 return sentences;
	}
	
//	public static String[] getSentencePOS(String sentence){
//		MaxentTagger tagger = new MaxentTagger("lib/tagger/stanford-postagger-full-2015-04-20/models/english-caseless-left3words-distsim.tagger");
//		
//		String[] tokens = sentence.split(" ");
//	    
//	    for(int i=0;i<tokens.length;i++){
//	        tokens[i] = tagger.tagString(tokens[i]);
//	    }
//	    
//	    return tokens;
//	}
	
}
