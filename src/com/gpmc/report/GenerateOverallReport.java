package com.gpmc.report;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.gpmc.util.xmlUtil;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

/**
 * 
 * this class store all debate history and could generate statistic report
 *
 */
public class GenerateOverallReport {
	private List<String> moveList;
	private List<String> turnIDList;
	private List<String> teamNameList;
	private List<String> userIDList;
	private List<String> typeList;
	private List<String> contentList;
	private String topicName;
	private String teamAName;
	private String teamBName;
	private List<String> nameList;
	
	public GenerateOverallReport(List<String> t1, List<String> t2, List<String> t3, List<String> t4, List<String> t5, List<String> t6, String topicName,String teamAName,String teamBName) {
		this.moveList = t1;
		this.turnIDList = t2;
		this.teamNameList = t3;
		this.userIDList = t4;
		this.typeList = t5;
		this.contentList = t6;
		this.topicName = topicName;
		this.teamAName = teamAName;
		this.teamBName = teamBName;
	}
	
	/**
	 * generate statistic report
	 * @throws FileNotFoundException
	 * @throws DocumentException
	 */
	public void getReport() throws FileNotFoundException, DocumentException {
		PdfWriter writer = new PdfWriter(xmlUtil.getTopicFilePath(topicName, "report.pdf"));
		PdfDocument pdf = new PdfDocument(writer);
		Document document = new Document(pdf, PageSize.A4.rotate());
		Table table1 = new Table(new float[] {1,1,4,4,6,10}); 
		table1.setWidthPercent(100);
		document.add(new Paragraph(topicName));
		
		table1.addCell(new Cell().add(new Paragraph("Move")));
		table1.addCell(new Cell().add(new Paragraph("TurnID")));
		table1.addCell(new Cell().add(new Paragraph("TeamName")));
		table1.addCell(new Cell().add(new Paragraph("CreateUserName")));
		table1.addCell(new Cell().add(new Paragraph("Type")));
		table1.addCell(new Cell().add(new Paragraph("Content")));
		for(int i = 0 ; i< moveList.size() ; i++) {
			table1.addCell(new Cell().add(new Paragraph((String)moveList.get(i))));
			table1.addCell(new Cell().add(new Paragraph((String)turnIDList.get(i))));
			table1.addCell(new Cell().add(new Paragraph((String)teamNameList.get(i))));
			table1.addCell(new Cell().add(new Paragraph((String)userIDList.get(i))));
			table1.addCell(new Cell().add(new Paragraph((String)typeList.get(i))));
			table1.addCell(new Cell().add(new Paragraph((String)contentList.get(i))));
		}
		
		document.add(table1);
		document.add(new Paragraph(" "));
		Table teamATable = getTeamTable(teamAName);
		document.add(new Paragraph("Team '" + teamAName + "'s Report"));
		document.add(new Paragraph(" "));
		document.add(teamATable);
		
		Table teamBTable = getTeamTable(teamBName);
		document.add(new Paragraph("Team '" + teamBName + "'s Report"));
		document.add(new Paragraph(" "));
		document.add(teamBTable);
		
		List<Table> studentTableList = getStudentsTableList();
		for(int i = 0 ; i < studentTableList.size() ;i++) {
			document.add(new Paragraph(" "));
			document.add(new Paragraph("Student --" + nameList.get(i) + "'s Report"));
			document.add(new Paragraph(" "));
			document.add(studentTableList.get(i));
		}
		document.close();
	}
	
	/**
	 * return team Table with team debate data
	 * @param teamName
	 * @return Table that contains team debate data
	 */
	public Table getTeamTable(String teamName) {
		Table table = new Table(new float[] {1,1,4,6,10});
		table.addCell(new Cell().add(new Paragraph("Move")));
		table.addCell(new Cell().add(new Paragraph("TurnID")));
		table.addCell(new Cell().add(new Paragraph("CreateUserName")));
		table.addCell(new Cell().add(new Paragraph("Type")));
		table.addCell(new Cell().add(new Paragraph("Content")));
		String teamAName = (String) teamNameList.get(0);
		for(int i = 0; i < moveList.size(); i++) {
			if(teamAName.equals(teamNameList.get(i))) {  //this is teamA's move
				table.addCell(new Cell().add(new Paragraph((String)moveList.get(i))));
				table.addCell(new Cell().add(new Paragraph((String)turnIDList.get(i))));
				table.addCell(new Cell().add(new Paragraph((String)userIDList.get(i))));
				table.addCell(new Cell().add(new Paragraph((String)typeList.get(i))));
				table.addCell(new Cell().add(new Paragraph((String)contentList.get(i))));
			}
		}
		return table;
	}
	
	/**
	 * get students' personal debate data
	 * @return Table list, contains student personal debate data
	 * @throws DocumentException
	 */
	public List<Table> getStudentsTableList() throws DocumentException{
		
		org.dom4j.Document doc = new SAXReader().read(new File(xmlUtil.Userpath));
		nameList = new ArrayList<String>();
		
		Element root = doc.getRootElement();
		List<Node> nodes = root.selectNodes("//user");
		for(int i = 0 ; i < nodes.size();i++) {
			if(nodes.get(i).valueOf("@teacher").equals("true") == false) {
				nameList.add(nodes.get(i).valueOf("username"));
			}
		}
		List<Table> tableList = new ArrayList<Table>();
		
		for(int i = 0 ; i < nameList.size() ;i++) {
			String str = (String)nameList.get(i);
			Table table = new Table(new float[] {6,10});
			table.addCell(new Cell().add(new Paragraph("Type")));
			table.addCell(new Cell().add(new Paragraph("Content")));
			for(int j = 0 ; j < moveList.size() ;j++) {
				if(str.equalsIgnoreCase((String)userIDList.get(j)) == true) {
					table.addCell(new Cell().add(new Paragraph((String)typeList.get(j))));
					table.addCell(new Cell().add(new Paragraph((String)contentList.get(j))));
				}
			}
			tableList.add(table);
		}
		return tableList;
	}
}

