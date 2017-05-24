package main;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.io.OutputStreamWriter;
import java.util.Properties;
import javax.xml.transform.OutputKeys;
import org.exist.util.serializer.SAXSerializer;
import org.exist.util.serializer.SerializerPool;
import org.exist.xmldb.XQueryService;
import org.exist.xmldb.XmldbURI;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.CompiledExpression;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.modules.XMLResource;

import java.io.PrintWriter;
import java.util.Enumeration;
import com.oreilly.servlet.*;
import com.oreilly.servlet.multipart.*;

/**
 * Servlet implementation class Seet
 */
@WebServlet("/FileUpload")
public class FileUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String dir = "/usr/local/apache-tomcat-8.5.12/webapps/AREX_WEB/ARXMLDATA";
	int max = 15 * 1024 * 1024; // 업로드 파일의 최대 크기 지정

	String name = "";
	String subject = "";
	String filename1 = "";

	protected static final String URI = "xmldb:exist://localhost:8080/exist/xmlrpc";

	protected static final String driver = "org.exist.xmldb.DatabaseImpl";

	protected static String readFile(String file) throws IOException {
		try (BufferedReader f = new BufferedReader(new FileReader(file))) {
			String line;
			StringBuffer xml = new StringBuffer();
			while ((line = f.readLine()) != null) {
				xml.append(line);
			}
			return xml.toString();
		}
	}

	public FileUpload() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

			MultipartRequest m = new MultipartRequest(request, dir, max, "utf-8", new DefaultFileRenamePolicy());

			Enumeration files = m.getFileNames();
			String file1 = (String) files.nextElement();
			filename1 = m.getFilesystemName(file1); // 저장될 이름이다.
			System.out.println(filename1 + "<br>");

			Class<?> cl = Class.forName(driver);
			Database database = (Database) cl.newInstance();
			database.setProperty("create-database", "true");
			DatabaseManager.registerDatabase(database);

			String ArxmlName = "";

			// 한 글자씩 화면에 출력
			for (int i = 0; i < filename1.length(); i++) {
				if (filename1.charAt(i) == '.')
					break;
				ArxmlName += filename1.charAt(i);
			}
			try {
				/* file write start */
				FileWriter outfile = new FileWriter("/usr/local/apache-tomcat-8.5.12/webapps/AREX_WEB/path.txt");
				outfile.write(ArxmlName);
				outfile.append(System.getProperty("line.separator"));
				outfile.flush();
				outfile.close();
				/* file write end */
			} catch (IOException e) {
				e.printStackTrace();

			}

			

			/*
			 * String query =
			 * "import module namespace file =\"http://exist-db.org/xquery/file\";"
			 * +
			 * "import module namespace util =\"http://exist-db.org/xquery/util\";"
			 * +
			 * "\n import module namespace xmldb=\"http://exist-db.org/xquery/xmldb\";"
			 * + "\n let $log-in := xmldb:login(\"/db\", \"admin\", \"\")" +
			 * "\n let $create-collection :=xmldb:create-collection(\"/db\", \"user\")"
			 * +
			 * "\n let $filename := '/usr/local/apache-tomcat-8.5.12/webapps/AREX/ARXMLDATA/"
			 * +filename1+"' " // + "\n let $newfile := file:read($filename) " +
			 * "\n let $newfile := <test>TEST</test> " //+
			 * "\n let $nodeset := util:parse($newfile)" //파일죽는이유 +
			 * "\n let $filename2 := \"/" + filename1 + "\"" // +
			 * "\n return xmldb:store(\"/db/test\", $filename2, $nodeset)"; +
			 * "\n return xmldb:store(\"/db/user\", $filename2, $newfile)";
			 */

			
			  String query = "let $collection-uri := '/db/user'" +
			  "\n let $directory := '"+dir+"'"
			  +"\n let $pattern := '"+filename1 +"'" 
			  +"\n let $mime-type := ()" 
			  +"\n let $preserve-structure := true()" 
			  +"\n return" 
			  +"\n xmldb:store-files-from-pattern($collection-uri, $directory, $pattern, $mime-type, $preserve-structure)" ;
			 

			// get root-collection
			Collection col = DatabaseManager.getCollection(URI + XmldbURI.ROOT_COLLECTION);
			// get query-service
			XQueryService service = (XQueryService) col.getService("XQueryService", "3.0");

			// set pretty-printing on
			service.setProperty(OutputKeys.INDENT, "yes");
			service.setProperty(OutputKeys.ENCODING, "UTF-8");

			CompiledExpression compiled = service.compile(query);

			long start = System.currentTimeMillis();

			// execute query and get results in ResourceSet
			ResourceSet result = service.execute(compiled);

			long qtime = System.currentTimeMillis() - start;
			start = System.currentTimeMillis();

			Properties outputProperties = new Properties();
			outputProperties.setProperty(OutputKeys.INDENT, "yes");
			SAXSerializer serializer = (SAXSerializer) SerializerPool.getInstance().borrowObject(SAXSerializer.class);
			serializer.setOutput(new OutputStreamWriter(System.out), outputProperties);

			ResourceIterator i = result.getIterator();
			// for ( int j = 0; j < (int) result.getSize(); j++ ) {
			// XMLResource resource = (XMLResource) result.getResource( (long) j
			// );
			// resource.getContentAsSAX(serializer);
			// }
			while (i.hasMoreResources()) {
				org.xmldb.api.base.Resource r = i.nextResource();

				String value = (String) r.getContent();
				System.out.println(value);
			}

			SerializerPool.getInstance().returnObject(serializer);
			long rtime = System.currentTimeMillis() - start;
			System.out.println("hits:          " + result.getSize());
			System.out.println("query time:    " + qtime);
			System.out.println("retrieve time: " + rtime);

			RequestDispatcher rd = request.getRequestDispatcher("/xmlmanage.jsp");
			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// doGet(request, response);
	}

}