package com.xhb.docs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.xhb.nlp.WordSegment;
import com.xhb.performance.TimeAnalysis;



public class Documents {
	
	private ArrayList<Document> docs;			//文档集合
	private Map<String, Integer> docToIndex;	//文档Id
	private String docsPath;					//语料库路径
	private WordSegment wordSeg = null;			//分词器
	private Integer docNums;					//文档数量
	private Integer wordNums;					//词数量
	private Map<String, List<TermDoc>> wordMap; //词项文档特征
	private Map<String, String> docTypeMap;	//文档类型词典
	
	public Documents() throws IOException {
		docs = new ArrayList<>();
		docToIndex = new HashMap<String, Integer>();
		wordMap = new HashMap<String, List<TermDoc>>();
		docTypeMap = new HashMap<String, String>();
		wordSeg = WordSegment.getInstance();
	}
	
	/*
	 * 初始化文档类型词典
	 */
	private void initDocTypeMap()
	{
		
		docTypeMap.put("C000007", "汽车");
		docTypeMap.put("C000008", "财经");
		docTypeMap.put("C000010", "IT");
		docTypeMap.put("C000013", "健康");
		docTypeMap.put("C000014", "体育");
		docTypeMap.put("C000016", "旅游");
		docTypeMap.put("C000020", "教育");
		docTypeMap.put("C000022", "招聘");
		docTypeMap.put("C000023", "文化");
		docTypeMap.put("C000024", "军事");
	}
	
	public void getDocumentFiles(String docsPath) throws IOException{
		
		this.docsPath = docsPath;
		
		for(File docFile : new File(this.docsPath).listFiles()) {			
			
			if(docFile.isDirectory()){
				getDocumentFiles(docFile.getPath());
			}
			
			String docType = docFile.getParentFile().getName();
			String docPath = docFile.getPath();
			String docName = docFile.getName();
			//System.out.println("Parent:" + docType +" Name:" + docName);
			if(docFile.isFile()){
				Document doc = new Document(docPath, docName, docType);
				docs.add(doc);
				docToIndex.put(doc.getDocName(), docs.size()-1);
				
				HashMap<String, Integer> words = doc.getWordMap();
				Iterator iter = words.keySet().iterator();
				
				while(iter.hasNext()){
					
					String word = (String) iter.next();
					Integer wordFreq = words.get(word);
					Integer docId = docToIndex.get(doc.getDocName());
					TermDoc termDoc = new TermDoc(docId, doc.getDocType(), wordFreq);
					List<TermDoc> termDocs = wordMap.get(word);
					if(termDocs == null){						
						termDocs = new ArrayList<TermDoc>();
					}
					termDocs.add(termDoc);
					wordMap.put(word, termDocs);	
					
				}				
			}
		}
		
		//System.out.println(wordMap);
		this.setWordNums(wordMap.size());
		this.setDocNums(docs.size());
	}
	

	public void docsSummary(){
		System.out.println("文档数量:"+ this.getDocNums());
		System.out.println("单词数量:"+ this.getWordNums());
	}
	
	public class Document {
		
		private String docName;
		private String docType;
		private HashMap<String, Integer> wordMap;
		  
		
		public Document(String docPath, String docName, String docType) throws IOException{			
			this.setDocType(docType);
			this.setDocName(docName);
			this.docWordSegment(docPath);
		}
		
		private void docWordSegment(String filePath) throws IOException{
			 
			 BufferedReader readBufferReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath),"gbk"));
			 StringBuffer textBuffer = new StringBuffer();
			 String line = "";
			 while((line=readBufferReader.readLine())!=null)
			 {		
				 line = line.trim();
				 textBuffer.append(line);
			 }
			 readBufferReader.close();
			 
			 String text = textBuffer.toString();
			 setWordMap(wordSeg.wordSegmentAndRemoveStopWords(text));
			 //System.out.println("words:"+ words);
			 
		}
		
		public String getDocName() {
			return docName;
		}

		public void setDocName(String docName) {
			this.docName = docName;
		}

		public String getDocType() {
			return docType;
		}

		public void setDocType(String docType) {
			this.docType = docType;
		}

		public HashMap<String, Integer> getWordMap() {
			return wordMap;
		}

		public void setWordMap(HashMap<String, Integer> wordMap) {
			this.wordMap = wordMap;
		}
	}
	
	public Integer getDocNums() {
		return docNums;
	}

	public void setDocNums(Integer docNums) {
		this.docNums = docNums;
	}

	public Integer getWordNums() {
		return wordNums;
	}

	public void setWordNums(Integer wordNums) {
		this.wordNums = wordNums;
	}
	
	public Map<String, List<TermDoc>> getWordMap() {
		return wordMap;
	}

	public void setWordMap(Map<String, List<TermDoc>> wordMap) {
		this.wordMap = wordMap;
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		TimeAnalysis ta = new TimeAnalysis();
		String docsPath = "D:/自然语言/语料/SogouC.reduced/Reduced";
		Documents docs = new Documents();
		docs.getDocumentFiles(docsPath);
		docs.docsSummary();
		ta.spendTime();
	}
}
