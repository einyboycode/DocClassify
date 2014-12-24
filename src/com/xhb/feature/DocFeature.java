package com.xhb.feature;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.xhb.com.FileUtil;
import com.xhb.conf.PathConfig;
import com.xhb.docs.Documents;
import com.xhb.docs.TermDoc;
import com.xhb.performance.TimeAnalysis;

public class DocFeature {
	
	private Map<String, List<TermDoc>> wordMap;
	private Map<String, Integer> dfeatureMap;
	
	public DocFeature(Map<String, List<TermDoc>> wordMap){
		this.wordMap = wordMap;
		dfeatureMap = new HashMap<String, Integer>();
		this.calcDocFeature();
	}
	
	private void calcDocFeature() {
		
		Iterator iter = wordMap.keySet().iterator();
		
		while(iter.hasNext()){
			String word = (String) iter.next();
			List<TermDoc> termDocs = (List<TermDoc>)wordMap.get(word);
			dfeatureMap.put(word, termDocs.size());
		}
	}
	
	public void writeDocFeature(){
		
		File file = new File(PathConfig.DocFeaturePath);
		FileUtil.writeHashMap(file, dfeatureMap);
	}
	
	public void selectFeatureWords(Integer maxFreq, Integer minFreq){
		
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		TimeAnalysis ta = new TimeAnalysis();
		String docsPath = "D:/自然语言/语料/SogouC.reduced/Reduced";
		Documents docs = new Documents();
		docs.getDocumentFiles(docsPath);
		docs.docsSummary();
		
		DocFeature docFeature = new DocFeature(docs.getWordMap());
		//docFeature.writeDocFeature();
		
		ta.spendTime();
	}
}
