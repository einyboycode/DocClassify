package com.xhb.docs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.xhb.nlp.WordSegment;
import com.xhb.performance.TimeAnalysis;



public class Documents {
	
	private ArrayList<Document> docs;
	private String docsPath;
	WordSegment wordSeg = null;
	
	public Documents() throws IOException {
		docs = new ArrayList<>();
		wordSeg = WordSegment.getInstance();
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
			}
		}
	}
	
	public class Document {
		
		private String docName;
		private String docType;
		private HashMap<String, Integer> wordMap;
		  
		
		public Document(String docPath, String docName, String docType) throws IOException{			
			this.docType = docType;
			this.docName = docName;
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
			 wordMap = wordSeg.wordSegmentAndRemoveStopWords(text);
			 //System.out.println("words:"+ words);
			 
		}
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		TimeAnalysis ta = new TimeAnalysis();
		String docsPath = "D:/自然语言/语料/SogouC.reduced/Reduced";
		Documents docs = new Documents();
		docs.getDocumentFiles(docsPath);
		ta.spendTime();
	}
}
