package ro.asimandi.simsec.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.io.IOUtils;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.sequences.DocumentReaderAndWriter;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.Triple;

public class Test {

	public static void main(String[] args) throws ClassCastException, ClassNotFoundException, IOException {

		// String sentence = new Scanner(System.in).nextLine();

		 String text = "like a boss";
		 Properties props = new Properties();
		 props.setProperty("annotators", "tokenize, ssplit, pos, lemma, parse,sentiment");
		 StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		
		 Annotation annotation = pipeline.process(text);
		 List<CoreMap> sentences =
		 annotation.get(CoreAnnotations.SentencesAnnotation.class);
		 for (CoreMap sentence : sentences) {
			String sentiment = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
			System.out.println(sentiment + "\t" + sentence);
		}
		
	    // Initialize the tagger
//	    MaxentTagger tagger = new MaxentTagger("lib/tagger/stanford-postagger-full-2015-04-20/models/english-caseless-left3words-distsim.tagger");
//
//	    // The sample string
//	    String sample = "Sometimes i just want to kill my boss, he is such a pain in the ass";
//	    String[] tokens = sample.split(" ");
//	    
//	    for(int i=0;i<tokens.length;i++){
//	        String tagged = tagger.tagString(tokens[i]);
//	         System.out.println(tagged);
//	    }


	}

}
