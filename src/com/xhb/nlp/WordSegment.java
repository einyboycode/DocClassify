package com.xhb.nlp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.ansj.util.FilterModifWord;

public class WordSegment {

	private static final String basedir = System.getProperty("NLPDemo", "data");
	private static WordSegment instance = null;

	private List<String> stopWords;

	private WordSegment() throws IOException {

		System.out.println("user.dir:" + basedir);
		stopWords = new ArrayList<String>();
		loadStopWords(basedir + "/stopword.txt");

	}

	public static WordSegment getInstance() throws IOException {

		if (instance == null) {
			instance = new WordSegment();
		}

		return instance;
	}

	public void loadStopWords(String stopWordsPath) throws IOException {

		FileReader stopWordsReader = new FileReader(stopWordsPath);
		BufferedReader stopWordsBR = new BufferedReader(stopWordsReader);
		String stopWordsLine;

		while ((stopWordsLine = stopWordsBR.readLine()) != null) {
			if (!stopWordsLine.isEmpty()) {
				stopWords.add(stopWordsLine);
				// System.out.println(stopWordsLine);
			}
		}

		FilterModifWord.insertStopWords(stopWords);

		System.out.println("stopWordsSet:" + stopWords);
	}

	public HashMap<String, Integer> wordSegmentByString(String sentence) {
		List<Term> terms = ToAnalysis.parse(sentence);
		HashMap<String, Integer> wordMap = new HashMap<>();
		String word = "";
		Integer cnt = 0;

		for (Term term : terms) {

			word = term.getName();
			if (word.length() > 1) {

				if (wordMap.get(word) == null) {
					wordMap.put(word, 1);
				} else {
					cnt = wordMap.get(word) + 1;
					wordMap.put(word, cnt);
				}
			}
		}
		return wordMap;
	}

	public HashMap<String, Integer> wordSegmentAndRemoveStopWords(
			String sentence) {

		List<Term> terms = ToAnalysis.parse(sentence);
		terms = FilterModifWord.modifResult(terms);
		HashMap<String, Integer> wordMap = new HashMap<>();
		String word = "";
		Integer cnt = 0;
		for (Term term : terms) {

			word = term.getName();
			if (word.length() > 1) {

				if (wordMap.get(word) == null) {
					wordMap.put(word, 1);
				} else {
					cnt = wordMap.get(word) + 1;
					wordMap.put(word, cnt);
				}
			}
		}
		return wordMap;
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String sentence = "可以方便地将需要的类型以集合类型保存在一个变量中.";
		WordSegment wordSeg = WordSegment.getInstance();
		HashMap<String, Integer> wordMap = wordSeg.wordSegmentAndRemoveStopWords(sentence);
		System.out.println(wordMap);
	}
}
