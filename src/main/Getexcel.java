package main;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Properties;
//import org.exist.Resource;
import javax.xml.transform.OutputKeys;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.exist.util.serializer.SAXSerializer;
import org.exist.util.serializer.SerializerPool;
import org.exist.xmldb.XQueryService;
import org.exist.xmldb.XmldbURI;
import org.w3c.dom.NodeList;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.CompiledExpression;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.modules.XMLResource;

/**
 * Servlet implementation class ExcelExtract
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/ExcelExtract" })
public class Getexcel extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected static final String URI = "xmldb:exist://localhost:8080/exist/xmlrpc";
    protected static final String driver = "org.exist.xmldb.DatabaseImpl";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Getexcel() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		////////////////////////////////////////////////
		try {
	        
			
			XSSFWorkbook wb = new XSSFWorkbook();
			//cell style
			CellStyle cs = wb.createCellStyle();
			cs.setFillForegroundColor(IndexedColors.LIME.getIndex());
			cs.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
			Font f = wb.createFont();
			f.setBoldweight(Font.BOLDWEIGHT_BOLD);
			f.setFontHeightInPoints((short)12);
			cs.setFont(f);
			
			//sheet 생성 
			XSSFSheet spreadSheet1 = (XSSFSheet) wb.createSheet("Port");
			XSSFSheet spreadSheet2 = (XSSFSheet) wb.createSheet("PortInterface");
			XSSFSheet spreadSheet3= (XSSFSheet) wb.createSheet("DataType_PRIMITIVE");
			XSSFSheet spreadSheet4= (XSSFSheet) wb.createSheet("DataType_ARRAY");
			XSSFSheet spreadSheet5= (XSSFSheet) wb.createSheet("DataType_RECORD");
	
			
			//sheet1 column 크기 변경  port
			spreadSheet1.setColumnWidth((short)0,(short)(256*5));
			spreadSheet1.setColumnWidth((short)1,(short)(256*60));
			spreadSheet1.setColumnWidth((short)2,(short)(256*15));
			spreadSheet1.setColumnWidth((short)3,(short)(256*25));
			spreadSheet1.setColumnWidth((short)4,(short)(256*60));
			spreadSheet1.setColumnWidth((short)5,(short)(256*15));
			
			//sheet2 column 크기 변경 portinterface
			spreadSheet2.setColumnWidth((short)0,(short)(256*20));
			spreadSheet2.setColumnWidth((short)1,(short)(256*30));
			spreadSheet2.setColumnWidth((short)2,(short)(256*70));
			spreadSheet2.setColumnWidth((short)3,(short)(256*20));
			spreadSheet2.setColumnWidth((short)4,(short)(256*60));
			spreadSheet2.setColumnWidth((short)5,(short)(256*20));
			
			//sheet3 column 크기 변경 
			spreadSheet3.setColumnWidth((short)0,(short)(256*10));
			spreadSheet3.setColumnWidth((short)1,(short)(256*25));
			spreadSheet3.setColumnWidth((short)2,(short)(256*40));
			spreadSheet3.setColumnWidth((short)3,(short)(256*60));
			spreadSheet3.setColumnWidth((short)4,(short)(256*20));
			spreadSheet3.setColumnWidth((short)5,(short)(256*50));
			
			//sheet4 column 크기 변경 
			spreadSheet4.setColumnWidth((short)0,(short)(256*10));
			spreadSheet4.setColumnWidth((short)1,(short)(256*20));
			spreadSheet4.setColumnWidth((short)2,(short)(256*30));
			spreadSheet4.setColumnWidth((short)3,(short)(256*60));
			spreadSheet4.setColumnWidth((short)4,(short)(256*20));
	
			//sheet5 column 크기 변경 
			spreadSheet5.setColumnWidth((short)0,(short)(256*20));
			spreadSheet5.setColumnWidth((short)1,(short)(256*30));
			spreadSheet5.setColumnWidth((short)2,(short)(256*40));
			spreadSheet5.setColumnWidth((short)3,(short)(256*80));
			spreadSheet5.setColumnWidth((short)4,(short)(256*70));
			

	    	Class<?> cl = Class.forName( driver );
            Database database = (Database) cl.newInstance();
            database.setProperty( "create-database", "true" );
            DatabaseManager.registerDatabase( database );
            //String Ecuname = request.getParameter("Web2DB_ECUNAME");
            
            
          //==========================//
            // 텍스트 파일 읽기
            //==========================//
                BufferedReader infile = new BufferedReader(new FileReader("/usr/local/apache-tomcat-8.5.12/webapps/AREX/path.txt"));

                String s;
                String EcuName=new String("");
                String PartName=new String("");
                int cnt =0;
                while ((s = infile.readLine()) != null) 
                {
                    if(cnt==0) PartName=new String(s);
                    else if(cnt==1) EcuName=new String(s);
                    cnt++;
                }
                cnt=0;
                  infile.close();
            
            
            String basequery = 
            		   "declare default element namespace \"http://autosar.org/schema/r4.0\";" 
            + "\n let $ALL :=doc(\"/db/AUTOSAR/" + PartName + "/TEST_" + EcuName + ".arxml\")//AR-PACKAGES/AR-PACKAGE/AR-PACKAGES/AR-PACKAGE/AR-PACKAGES";
            //+ "\n let $ALL :=doc(\"/db/test/AUTOSAR_MOD_AISpec_Imob.arxml\")//AR-PACKAGES/AR-PACKAGE/AR-PACKAGES/AR-PACKAGE/AR-PACKAGES";
           // + "\n let $ALL :=doc(\"/db/test/AUTOSAR_MOD_AISpec_"+Ecuname+"\")//AR-PACKAGES/AR-PACKAGE/AR-PACKAGES/AR-PACKAGE/AR-PACKAGES";
            				 

            //sheet1
            String pportSname = basequery + "\n return  data(//AR-PACKAGE/ELEMENTS/COMPOSITION-SW-COMPONENT-TYPE/PORTS/P-PORT-PROTOTYPE/SHORT-NAME)";
            String pportLname = basequery + "\n return  data(//AR-PACKAGE/ELEMENTS/COMPOSITION-SW-COMPONENT-TYPE/PORTS/P-PORT-PROTOTYPE/LONG-NAME/L-4)";
            String pportDesc = basequery + "\n return  data(//AR-PACKAGE/ELEMENTS/COMPOSITION-SW-COMPONENT-TYPE/PORTS/P-PORT-PROTOTYPE/DESC/L-2)";
            String pportinterface = basequery + "\n return  data(//AR-PACKAGE/ELEMENTS/COMPOSITION-SW-COMPONENT-TYPE/PORTS/P-PORT-PROTOTYPE/PROVIDED-INTERFACE-TREF)";
            String rportSname = basequery + "\n return  data(//AR-PACKAGE/ELEMENTS/COMPOSITION-SW-COMPONENT-TYPE/PORTS/R-PORT-PROTOTYPE/SHORT-NAME)";
            String rportLname = basequery + "\n return  data(//AR-PACKAGE/ELEMENTS/COMPOSITION-SW-COMPONENT-TYPE/PORTS/R-PORT-PROTOTYPE/LONG-NAME/L-4)";
            String rportDesc = basequery + "\n return  data(//AR-PACKAGE/ELEMENTS/COMPOSITION-SW-COMPONENT-TYPE/PORTS/R-PORT-PROTOTYPE/DESC/L-2)";
            String rportinterface = basequery + "\n return  data(//AR-PACKAGE/ELEMENTS/COMPOSITION-SW-COMPONENT-TYPE/PORTS/R-PORT-PROTOTYPE/REQUIRED-INTERFACE-TREF)";
    
            //sheet2
            String portInterShortname = basequery + "\n return  data(//AR-PACKAGE/ELEMENTS/SENDER-RECEIVER-INTERFACE/SHORT-NAME)";
            String portInterLongname = basequery + "\n return  data(//AR-PACKAGE/ELEMENTS/SENDER-RECEIVER-INTERFACE/LONG-NAME/L-4)";
            String portInterDesc  = basequery + "\n return  data(//AR-PACKAGE/ELEMENTS/SENDER-RECEIVER-INTERFACE/DESC/L-2)";
            String dataelementSname = basequery + "\n return  data(//AR-PACKAGE/ELEMENTS/SENDER-RECEIVER-INTERFACE/DATA-ELEMENTS/VARIABLE-DATA-PROTOTYPE/SHORT-NAME)";
            String dataelementDesc = basequery + "\n return  data(//AR-PACKAGE/ELEMENTS/SENDER-RECEIVER-INTERFACE/DATA-ELEMENTS/VARIABLE-DATA-PROTOTYPE/DESC/L-2)";
            String dataelementType = basequery + "\n return  data(//AR-PACKAGE/ELEMENTS/SENDER-RECEIVER-INTERFACE/DATA-ELEMENTS/VARIABLE-DATA-PROTOTYPE/TYPE-TREF)";
        
            //sheet3
            String priSname = basequery + "\n return  data(//AR-PACKAGE/ELEMENTS/APPLICATION-PRIMITIVE-DATA-TYPE/SHORT-NAME)";
            String priLname = basequery + "\n return  data(//AR-PACKAGE/ELEMENTS/APPLICATION-PRIMITIVE-DATA-TYPE/LONG-NAME/L-4)";
            String priDesc  = basequery + "\n return  data(//AR-PACKAGE/ELEMENTS/APPLICATION-PRIMITIVE-DATA-TYPE/DESC/L-2)";
            String priCate = basequery + "\n return  data(//AR-PACKAGE/ELEMENTS/APPLICATION-PRIMITIVE-DATA-TYPE/CATEGORY)";
            String priResol = basequery + "\n return  data(//AR-PACKAGE/ELEMENTS/APPLICATION-PRIMITIVE-DATA-TYPE/SW-DATA-DEF-PROPS/SW-DATA-DEF-PROPS-VARIANTS/SW-DATA-DEF-PROPS-CONDITIONAL/SW-INTENDED-RESOLUTION)";
            String priUnit = basequery + "\n return  data(//AR-PACKAGE/ELEMENTS/APPLICATION-PRIMITIVE-DATA-TYPE/SW-DATA-DEF-PROPS/SW-DATA-DEF-PROPS-VARIANTS/SW-DATA-DEF-PROPS-CONDITIONAL/UNIT-REF)";
        
            
            //sheet4
            String arrSname = basequery + "\n return  data(//AR-PACKAGE/ELEMENTS/APPLICATION-ARRAY-DATA-TYPE/SHORT-NAME)";
            String arrLname = basequery + "\n return  data(//AR-PACKAGE/ELEMENTS/APPLICATION-ARRAY-DATA-TYPE/LONG-NAME/L-4)";
            String arrDesc = basequery + "\n return  data(//AR-PACKAGE/ELEMENTS/APPLICATION-ARRAY-DATA-TYPE/DESC/L-2)";
            String arrCate = basequery + "\n return  data(//AR-PACKAGE/ELEMENTS/APPLICATION-ARRAY-DATA-TYPE/CATEGORY)";
            String arrEle = basequery + "\n return  data(//AR-PACKAGE/ELEMENTS/APPLICATION-ARRAY-DATA-TYPE/ELEMENT/SHORT-NAME)"; 
            
            //sheet5
            String recSname = basequery + "\n return  data(//AR-PACKAGE/ELEMENTS/APPLICATION-RECORD-DATA-TYPE/SHORT-NAME)";
            String recLname = basequery + "\n return  data(//AR-PACKAGE/ELEMENTS/APPLICATION-RECORD-DATA-TYPE/LONG-NAME/L-4)";
            String recDesc  = basequery + "\n return  data(//AR-PACKAGE/ELEMENTS/APPLICATION-RECORD-DATA-TYPE/DESC/L-2)";
            String recCate= basequery + "\n return  data(//AR-PACKAGE/ELEMENTS/APPLICATION-RECORD-DATA-TYPE/CATEGORY)";
            String recEle = basequery + "\n return  data(//AR-PACKAGE/ELEMENTS/APPLICATION-RECORD-DATA-TYPE/ELEMENTS/APPLICATION-RECORD-ELEMENT)";
            String recEleSname = basequery + "\n return  data(//AR-PACKAGE/ELEMENTS/APPLICATION-RECORD-DATA-TYPE/APPLICATION-RECORD-ELEMENT/SHORT-NAME)";
        
 
            // get root-collection
            Collection col =
                DatabaseManager.getCollection(URI + XmldbURI.ROOT_COLLECTION);
            // get query-service
            XQueryService service =
                (XQueryService) col.getService( "XQueryService", "1.0" );
            
            // set pretty-printing on
            service.setProperty( OutputKeys.INDENT, "yes" );
            service.setProperty( OutputKeys.ENCODING, "UTF-8" );
            
            // execute query and get results in ResourceSet
           // ResourceSet ps = service.execute( compiled_ps );
            
            Properties outputProperties = new Properties();
            outputProperties.setProperty(OutputKeys.INDENT, "yes");
            SAXSerializer serializer = (SAXSerializer) SerializerPool.getInstance().borrowObject(SAXSerializer.class); 
            serializer.setOutput(new OutputStreamWriter(System.out), outputProperties);
       
            ResourceIterator pportSnamei = service.execute( service.compile( pportSname ) ).getIterator();
            ResourceIterator pportLnamei = service.execute( service.compile( pportLname )).getIterator();
            ResourceIterator pportDesci = service.execute( service.compile( pportDesc )).getIterator();
            ResourceIterator pportinterfacei = service.execute(service.compile( pportinterface ) ).getIterator();
            ResourceIterator rportSnamei = service.execute( service.compile( rportSname )).getIterator();
            ResourceIterator rportLnamei = service.execute( service.compile( rportLname) ).getIterator();
            ResourceIterator rportDesci = service.execute( service.compile( rportDesc )).getIterator();
            ResourceIterator rportinterfacei = service.execute( service.compile( rportinterface )).getIterator();
            ResourceIterator portInterShortnamei = service.execute( service.compile( portInterShortname )).getIterator();
            ResourceIterator portInterDesci = service.execute( service.compile( portInterDesc )).getIterator();
            ResourceIterator portInterLongnamei = service.execute( service.compile( portInterLongname )).getIterator();
            ResourceIterator dataelementSnamei = service.execute( service.compile( dataelementSname )).getIterator();
           // ResourceIterator dataelementDesci = service.execute( service.compile( dataelementDesc )).getIterator();
          //  ResourceIterator dataelementTypei = service.execute( service.compile( dataelementType )).getIterator();
            ResourceIterator priSnamei = service.execute( service.compile( priSname )).getIterator();
            ResourceIterator priLnamei = service.execute( service.compile( priLname )).getIterator();
            ResourceIterator priDesci = service.execute( service.compile( priDesc )).getIterator();
            ResourceIterator priCatei = service.execute( service.compile( priCate )).getIterator();
            //ResourceIterator priResoli = service.execute( service.compile( priResol )).getIterator();
            ResourceIterator priUniti = service.execute( service.compile( priUnit )).getIterator();
            ResourceIterator arrSnamei = service.execute( service.compile( arrSname )).getIterator();
            ResourceIterator arrLnamei = service.execute( service.compile(arrLname)).getIterator();
            ResourceIterator arrDesci = service.execute( service.compile( arrDesc )).getIterator();
            ResourceIterator arrCatei = service.execute( service.compile( arrCate )).getIterator();
            ResourceIterator arrElei = service.execute( service.compile(arrEle )).getIterator();
            ResourceIterator recSnamei = service.execute( service.compile(recSname)).getIterator();
            ResourceIterator recCatei = service.execute( service.compile(recCate)).getIterator();
            ResourceIterator recLnamei = service.execute( service.compile(recLname)).getIterator();
            ResourceIterator recDesci = service.execute( service.compile(recDesc)).getIterator();
            ResourceIterator recElei = service.execute( service.compile(recEle)).getIterator();
            ResourceIterator recEleSnamei = service.execute( service.compile(recEleSname)).getIterator();
          
     		//sheet1 create rows
     		XSSFRow sheet1_row0 = spreadSheet1.createRow(0);
     		//sheet1 create cell
     	    XSSFCell cell = sheet1_row0.createCell((short)0);
     	    cell.setCellValue("No");  cell.setCellStyle(cs);
     	    cell = sheet1_row0.createCell((short)1);
     	    cell.setCellValue("PortInterface");  cell.setCellStyle(cs);
     	    cell = sheet1_row0.createCell((short)2);
     	    cell.setCellValue("ShortnameofPort");  cell.setCellStyle(cs);
     	    cell = sheet1_row0.createCell((short)3);
     	    cell.setCellValue("LongnameOfPort");  cell.setCellStyle(cs);
     	    cell = sheet1_row0.createCell((short)4);
     	    cell.setCellValue("PortDescription");  cell.setCellStyle(cs);
     	    cell = sheet1_row0.createCell((short)5);
     	    cell.setCellValue("PortCategory");  cell.setCellStyle(cs);

       
           int j = 0;
            while(pportSnamei.hasMoreResources()){
            	XSSFRow sheet1_row1 = spreadSheet1.createRow(j+1);
           
	            cell = sheet1_row1.createCell((short) 0);
	            cell.setCellValue(j+1);
               
	            cell = sheet1_row1.createCell((short) 1);
				cell.setCellValue((String)pportinterfacei.nextResource().getContent());
	           	cell = sheet1_row1.createCell((short) 2);
				cell.setCellValue((String)pportSnamei.nextResource().getContent());
				
				cell = sheet1_row1.createCell((short) 3);
				cell.setCellValue((String)pportLnamei.nextResource().getContent());
				
				cell = sheet1_row1.createCell((short) 4);
				cell.setCellValue((String)pportDesci.nextResource().getContent());
			
				cell = sheet1_row1.createCell((short) 5);
				cell.setCellValue("Pport"); 
	               j++;
            }
            
            int rportNo = j+1;
            int k = 0;
            while(rportSnamei.hasMoreResources()){
 			   XSSFRow sheet1_row2 = spreadSheet1.createRow(rportNo+k);
			   
			    cell = sheet1_row2.createCell((short) 0);
				cell.setCellValue(rportNo+k);

				cell = sheet1_row2.createCell((short) 1);
				cell.setCellValue((String)rportinterfacei.nextResource().getContent());
			
				cell = sheet1_row2.createCell((short) 2);
				cell.setCellValue((String)rportSnamei.nextResource().getContent());
				
				cell = sheet1_row2.createCell((short) 3);
				cell.setCellValue((String)rportLnamei.nextResource().getContent());
				
				
				cell = sheet1_row2.createCell((short) 4);
				cell.setCellValue((String)rportDesci.nextResource().getContent());
			    
				cell = sheet1_row2.createCell((short) 5);
				cell.setCellValue("Rport");
				k++;
            }
            
       	 //sheet2 create rows
			XSSFRow row = spreadSheet2.createRow(0);
			//sheet2 create cell
		    XSSFCell cell1 = row.createCell((short)0);
		    cell1.setCellValue("PortInterface(Shortname)");  cell1.setCellStyle(cs);
		    cell1 = row.createCell((short)1);
		    cell1.setCellValue("PortInterface(Longname)");  cell1.setCellStyle(cs);
		    cell1 = row.createCell((short)2);
		    cell1.setCellValue("Description");  cell1.setCellStyle(cs);
		    cell1 = row.createCell((short)3);
		    cell1.setCellValue("Dataelement");  cell1.setCellStyle(cs);
		    cell1 = row.createCell((short)4);
		    cell1.setCellValue("Description");  cell1.setCellStyle(cs);
		    cell1 = row.createCell((short)5);
		    cell1.setCellValue("DataType");  cell1.setCellStyle(cs);
		    
		
		    /*
		         
	        while(portInterShortnamei.hasMoreResources()){
	        	System.out.println("hello");
 
	        	System.out.println((String)portInterShortnamei.nextResource().getContent());
	        	System.out.println((String)pportLnamei.nextResource().getContent());
      
            }
		     */
		
		    
		    int i = 0;
	        while(portInterShortnamei.hasMoreResources()){
            	XSSFRow row1 = spreadSheet2.createRow(i+1);
           
	            cell1 = row1.createCell((short) 0);
	            cell1.setCellValue((String)portInterShortnamei.nextResource().getContent());
               
	            cell1 = row1.createCell((short) 1);
				cell1.setCellValue((String)portInterLongnamei.nextResource().getContent());
				
	           	cell1 = row1.createCell((short) 2);
				cell1.setCellValue((String)portInterDesci.nextResource().getContent());
				
				cell1 = row1.createCell((short) 3);
				cell1.setCellValue((String)dataelementSnamei.nextResource().getContent());
				
	               i++;
            }
			   
				 //sheet3 create rows
			XSSFRow sheet3_row = spreadSheet3.createRow(0);
			//sheet3 create cell
		    XSSFCell cell2 = sheet3_row.createCell((short)0);
		    cell2.setCellValue("Category");  cell2.setCellStyle(cs);
		    cell2 = sheet3_row.createCell((short)1);
		    cell2.setCellValue("Shortname");  cell2.setCellStyle(cs);
		    cell2 = sheet3_row.createCell((short)2);
			cell2.setCellValue("Longname");  cell2.setCellStyle(cs);
		    cell2 = sheet3_row.createCell((short)3);
		    cell2.setCellValue("Description");  cell2.setCellStyle(cs);
		    cell2 = sheet3_row.createCell((short)4);
		    cell2.setCellValue("Resolution");  cell2.setCellStyle(cs);
		    cell2 = sheet3_row.createCell((short)5);
		    cell2.setCellValue("Unit");  cell2.setCellStyle(cs);
		    
		    
		    i = 0;
	        while(priSnamei.hasMoreResources()){
            	XSSFRow sheet1_row1 = spreadSheet3.createRow(i+1);
           
	            cell2 = sheet1_row1.createCell((short) 0);
	            cell2.setCellValue((String)priCatei.nextResource().getContent());
               
	            cell2 = sheet1_row1.createCell((short) 1);
				cell2.setCellValue((String)priSnamei.nextResource().getContent());
	           	cell2 = sheet1_row1.createCell((short) 2);
				cell2.setCellValue((String)priLnamei.nextResource().getContent());
				
				cell2 = sheet1_row1.createCell((short) 3);
				cell2.setCellValue((String)priDesci.nextResource().getContent());
				
				cell2 = sheet1_row1.createCell((short) 4);
				//cell.setCellValue((String)priDesci.nextResource().getContent());
			
				cell2 = sheet1_row1.createCell((short) 5);
				cell2.setCellValue((String)priUniti.nextResource().getContent()); 
				i++;
            }
		    
		    //sheet4 create rows
			XSSFRow sheet4_row = spreadSheet4.createRow(0);
			//sheet4 create cell
		    XSSFCell cell3 = sheet4_row.createCell((short)0);
		    cell3.setCellValue("Category");  cell3.setCellStyle(cs);
		    cell3 = sheet4_row.createCell((short)1);
		    cell3.setCellValue("Shortname");  cell3.setCellStyle(cs);
		    cell3 = sheet4_row.createCell((short)2);
		    cell3.setCellValue("Longname");  cell3.setCellStyle(cs);
		    cell3 = sheet4_row.createCell((short)3);
		    cell3.setCellValue("Description");  cell3.setCellStyle(cs);
		    cell3 = sheet4_row.createCell((short)4);
		    cell3.setCellValue("Element");  cell3.setCellStyle(cs);
		    
		    i = 0;
	        while(arrSnamei.hasMoreResources()){
            	XSSFRow sheet1_row1 = spreadSheet4.createRow(i+1);
           
	            cell = sheet1_row1.createCell((short) 0);
	            cell.setCellValue((String)arrCatei.nextResource().getContent());
               
	            cell = sheet1_row1.createCell((short) 1);
				cell.setCellValue((String)arrSnamei.nextResource().getContent());
				
	           	cell = sheet1_row1.createCell((short) 2);
				cell.setCellValue((String)arrLnamei.nextResource().getContent());
				
				cell = sheet1_row1.createCell((short) 3);
				cell.setCellValue((String)arrDesci.nextResource().getContent());
				
				cell = sheet1_row1.createCell((short) 4);
				cell.setCellValue((String)arrElei.nextResource().getContent());
 
	               i++;
            }
		    
		    //sheet5 create rows
		    XSSFRow sheet5_row = spreadSheet5.createRow(0);
		    //sheet5 create cell
		    XSSFCell cell4 = sheet5_row.createCell((short)0);
		    cell4.setCellValue("Category");  cell4.setCellStyle(cs);
		    cell4 = sheet5_row.createCell((short)1);
		    cell4.setCellValue("Shortname");  cell4.setCellStyle(cs);
		    cell4 = sheet5_row.createCell((short)2);
		    cell4.setCellValue("Longname");  cell4.setCellStyle(cs);
		    cell4 = sheet5_row.createCell((short)3);
		    cell4.setCellValue("Description");  cell4.setCellStyle(cs);
		    cell4 = sheet5_row.createCell((short)4);
		    cell4.setCellValue("Element");  cell4.setCellStyle(cs);
		    
		    i = 0;
	        while(recSnamei.hasMoreResources()){
            	XSSFRow sheet1_row1 = spreadSheet5.createRow(i+1);
           
	            cell = sheet1_row1.createCell((short) 0);
	            cell.setCellValue((String)recCatei.nextResource().getContent());
               
	            cell = sheet1_row1.createCell((short) 1);
				cell.setCellValue((String)recSnamei.nextResource().getContent());
	           	cell = sheet1_row1.createCell((short) 2);
				cell.setCellValue((String)recLnamei.nextResource().getContent());
				
				cell = sheet1_row1.createCell((short) 3);
				cell.setCellValue((String)recDesci.nextResource().getContent());
				
				//cell = sheet1_row1.createCell((short) 4);
				//cell.setCellValue((String)pportDesci.nextResource().getContent());
			 
	               i++;
            }
		  /*  
            //FileOutputStream output = new FileOutputStream(new File("C:/Users/JuHyeok/Workspace_new/AM_service/output1.xlsx"));
          
			   wb.write(output);
			   output.flush();
			   output.close();
			   RequestDispatcher rd = request.getRequestDispatcher("/xmlmanage.jsp");
	             rd.forward(request, response);
            */
	        response.setContentType("application/vnd.ms-excel");
	        response.setHeader("Content-Disposition", "attachment; filename=output.xlsx");
	        wb.write(response.getOutputStream());
	        response.getOutputStream().close();
	        wb.close();
	        RequestDispatcher rd = request.getRequestDispatcher("/xmlmanage.jsp");
            rd.forward(request, response);
        } catch ( Exception e ) {
            e.printStackTrace();
        }

		////////////////////////////////////////////////
		//doGet(request, response);
	}

}
