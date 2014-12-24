package com.xhb.docs;

public class TermDoc {

	private Integer docId;
	private String docType;
	private Integer freq;
	
	public TermDoc(Integer docId, String docType, Integer freq){
		this.docId = docId;
		this.docType = docType;
		this.freq = freq;
	}

	public Integer getDocId() {
		return docId;
	}

	public void setDocId(Integer docId) {
		this.docId = docId;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public Integer getFreq() {
		return freq;
	}

	public void setFreq(Integer freq) {
		this.freq = freq;
	}
}
