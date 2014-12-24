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

import com.xhb.conf.PathConfig;

public class WordSegment {

	private static final String basedir = System.getProperty("NLPDemo", "data");
	private static WordSegment instance = null;

	private List<String> stopWords;

	private WordSegment() throws IOException {

		System.out.println("user.dir:" + basedir);
		stopWords = new ArrayList<String>();
		loadStopWords(PathConfig.StopWordsPath);

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

		//System.out.println("stopWordsSet:" + stopWords);
	}

	public HashMap<String, Integer> wordSegmentByString(String sentence) {
		List<Term> terms = ToAnalysis.parse(sentence);
		HashMap<String, Integer> wordMap = new HashMap<>();
		String word = "";
		

		for (Term term : terms) {

			word = term.getName();
			if (word.length() > 1) {
				Integer cnt = wordMap.get(word);
				wordMap.put(word, cnt==null?1:cnt+1);				
			}
		}
		return wordMap;
	}

	public HashMap<String, Integer> wordSegmentAndRemoveStopWords(
			String sentence) {

		List<Term> terms = ToAnalysis.parse(sentence);
		//System.out.println(terms);
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
		sentence = "乒乓球拍卖玩了!";
		sentence = "系统能够通过增加服务器节点对系统的计算和存储能力进行扩容。支持在线扩展，不中断当前系统的运行，性能线性提升。可扩展节点数量达到百个节点以上规模；";
		WordSegment wordSeg = WordSegment.getInstance();
		HashMap<String, Integer> wordMap = wordSeg.wordSegmentAndRemoveStopWords(sentence);
		System.out.println(wordMap);
	}
}
