package com.gpmc.servletAPI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
 * Servlet implementation class requestReport
 */
@WebServlet("/requestReport")
public class requestReport extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public requestReport() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String topicName = request.getParameter("topicName");

		StasticReport_Overall report = new StasticReport_Overall(topicName);
		try {
			if (report.generateReportData() == true) { // report has been created in fixed folder
				response.getWriter().write("generate report successful");
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	class StasticReport_Overall {

		private List moveList;
		private List turnIDList;
		private List teamNameList;
		private List userIDList;
		private List typeList;
		private List contentList;
		private String topicName;
		private String teamAName;
		private String teamAFilePath;
		private String teamBName;
		private String teamBFilePath;
		private int maxTurn;

		public StasticReport_Overall(String topicName) {
			this.topicName = topicName;
			moveList = new ArrayList<String>();
			turnIDList = new ArrayList<String>();
			teamNameList = new ArrayList<String>();
			userIDList = new ArrayList<String>();
			typeList = new ArrayList<String>();
			contentList = new ArrayList<String>();
		}

		/*
		 * receive topic name and return the first team's name to start the turn
		 */
		public String findFirstTeam() throws DocumentException {

			String path = "Topic.xml";
			org.dom4j.Document topicDoc = new SAXReader().read(new File(xmlUtil.getTopicFilePath(topicName, "Topic")));
			String xPath = "//topic[title='" + topicName + "']";
			// first check null
			if (!topicDoc.valueOf(xPath).equals("")) {
				Element ele = (Element) topicDoc.selectSingleNode(xPath);
				if (!ele.valueOf("//status").equals("end")) {
					return null; // topic at pending or processing status, can not generate a stastic report
				}
				maxTurn = Integer.parseInt(ele.valueOf("//maxTurn"));
				teamAName = ele.valueOf("//teamA");
				teamBName = ele.valueOf("//teamB");
				teamAFilePath = xmlUtil.getTopicFilePath(topicName, "Move_" + teamAName);
				teamBFilePath = xmlUtil.getTopicFilePath(topicName, "Move_" + teamBName);
				org.dom4j.Document docA = new SAXReader().read(new File(teamAFilePath));
				int t = Integer.parseInt(docA.valueOf("//move[@id='1']/turnID"));
				if (t == 1) {
					return teamAName;
				} else
					return teamBName;

			} else {

				return null;
			}
		}

		public boolean generateReportData() throws DocumentException, FileNotFoundException {

			String firstTeamName = findFirstTeam();
			org.dom4j.Document doc1, doc2;
			if (teamAName.equals(firstTeamName)) {
				doc1 = new SAXReader().read(new File(teamAFilePath));
				doc2 = new SAXReader().read(new File(teamBFilePath));
			} else {
				doc1 = new SAXReader().read(new File(teamBFilePath));
				doc2 = new SAXReader().read(new File(teamAFilePath));
			}

			for (int i = 1; i <= maxTurn; i++) {
				String xpath = "//move[turnID='" + i + "']";
				String teamName;
				System.out.println(xpath);
				List<Node> node;
				if (i % 2 == 1) {
					node = doc1.selectNodes(xpath);
					teamName = teamAName;
				} else {
					node = doc2.selectNodes(xpath);
					teamName = teamBName;
				}
				for (int j = 0; j < node.size(); j++) {
					moveList.add(String.valueOf(j));
					turnIDList.add(String.valueOf(i));
					teamNameList.add(teamName);
					userIDList.add(node.get(j).valueOf("createUserID"));
					typeList.add(node.get(j).valueOf("type"));
					contentList.add(node.get(j).valueOf("textBody"));
				}
			}
			new GenerateOverallReport(moveList, turnIDList, teamNameList, userIDList, typeList, contentList, topicName,
					teamAName, teamBName).getReport();
			return true;
		}
		// public static void main(String args[]) throws DocumentException,
		// FileNotFoundException {
		//
		// StasticReport_Overall sta = new StasticReport_Overall("Is Abortion is
		// wrong?");
		// sta.generateReportData();
		// }
	}
	class GenerateOverallReport{
		private List moveList;
		private List turnIDList;
		private List teamNameList;
		private List userIDList;
		private List typeList;
		private List contentList;
		private String topicName;
		private String teamAName;
		private String teamBName;
		private List nameList;
		
		public GenerateOverallReport(List t1, List t2, List t3, List t4, List t5, List t6, String topicName,String teamAName,String teamBName) {
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
}
