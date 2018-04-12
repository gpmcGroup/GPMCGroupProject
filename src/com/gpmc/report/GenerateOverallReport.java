package com.gpmc.report;

import java.io.FileNotFoundException;
import java.util.List;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

public class GenerateOverallReport {
	private List moveList;
	private List turnIDList;
	private List teamNameList;
	private List userIDList;
	private List typeList;
	private List contentList;
	private String topicName;
	
	public GenerateOverallReport(List t1, List t2, List t3, List t4, List t5, List t6, String topicName) {
		this.moveList = t1;
		this.turnIDList = t2;
		this.teamNameList = t3;
		this.userIDList = t4;
		this.typeList = t5;
		this.contentList = t6;
		this.topicName = topicName;
	}
	
	public void getReport() throws FileNotFoundException {
		PdfWriter writer = new PdfWriter("report.pdf");
		PdfDocument pdf = new PdfDocument(writer);
		Document document = new Document(pdf, PageSize.A4.rotate());
		
		Table table = new Table(new float[] {1,1,4,4,6,10}); 
		table.setWidthPercent(100);
		
		document.add(new Paragraph(topicName));
		
		table.addCell(new Cell().add(new Paragraph("Move")));
		table.addCell(new Cell().add(new Paragraph("TurnID")));
		table.addCell(new Cell().add(new Paragraph("TeamName")));
		table.addCell(new Cell().add(new Paragraph("CreateUserName")));
		table.addCell(new Cell().add(new Paragraph("Type")));
		table.addCell(new Cell().add(new Paragraph("Content")));
		for(int i = 0 ; i< moveList.size() ; i++) {
			table.addCell(new Cell().add(new Paragraph((String)moveList.get(i))));
			table.addCell(new Cell().add(new Paragraph((String)turnIDList.get(i))));
			table.addCell(new Cell().add(new Paragraph((String)teamNameList.get(i))));
			table.addCell(new Cell().add(new Paragraph((String)userIDList.get(i))));
			table.addCell(new Cell().add(new Paragraph((String)typeList.get(i))));
			table.addCell(new Cell().add(new Paragraph((String)contentList.get(i))));
		}
		
		document.add(table);
		document.close();
	}
}

