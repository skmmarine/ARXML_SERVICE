package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

/**
 * Servlet implementation class My
 */
@WebServlet("/Getdata")
public class Getdata extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected static final String URI = "xmldb:exist://localhost:8080/exist/xmlrpc";
	protected static final String driver = "org.exist.xmldb.DatabaseImpl";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Getdata() {
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
		/////////////////////////////////
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		try {
			Class<?> cl = Class.forName(driver);
			Database database = (Database) cl.newInstance();
			database.setProperty("create-database", "true");
			DatabaseManager.registerDatabase(database);

			String EcuName = request.getParameter("Web2DB_ECUNAME");
			String PartName = new String("");
			//String query = "for $x in doc(\"/db/test/hihi/BOOKSTORE\")//author return $x";

			if (EcuName.equals("LockgCen") || EcuName.equals("IntrLi") || EcuName.equals("MirrAdjmt")
					|| EcuName.equals("MirrTintg") || EcuName.equals("SeatAdjmt") || EcuName.equals("WiprWshr")
					|| EcuName.equals("EntryRemKeyls") || EcuName.equals("KeyPad") || EcuName.equals("Antithft")
					|| EcuName.equals("ExtrLi") || EcuName.equals("TerminalClmpCtrl") || EcuName.equals("HornCtrl")
					|| EcuName.equals("RoofCnvtbCtrl") || EcuName.equals("DefrstCtrl") || EcuName.equals("Imob")
					|| EcuName.equals("Pase") || EcuName.equals("SnsrBody") || EcuName.equals("SeatClima"))
				PartName = new String("Body");
			/* Body_Part_Select_End */

			/* PT_Part_Select_Start */
			if (EcuName.equals("PtCoorr") || EcuName.equals("Trsm") || EcuName.equals("CmbEng")
					|| EcuName.equals("VehMtnForPt") || EcuName.equals("PtMisc"))
				PartName = new String("PT");
			/* PT_Part_Select_End */

			/* Chassis_Part_Select_Start */
			if (EcuName.equals("CrsCtrlAndAcc") || EcuName.equals("Esc") || EcuName.equals("Ssm")
					|| EcuName.equals("Epb") || EcuName.equals("Vlc") || EcuName.equals("Rsc")
					|| EcuName.equals("Steer") || EcuName.equals("SteerVehStabyCtrl")
					|| EcuName.equals("SteerDrvrAsscSys") || EcuName.equals("Susp") || EcuName.equals("DtTqDistbn")
					|| EcuName.equals("TirePMon") || EcuName.equals("ChassisSnsr") || EcuName.equals("SurrndgsSnsr"))
				PartName = new String("Chassis");
			/* Chassis_Part_Select_End */

			/* OPS_Part_Select_Start */
			if (EcuName.equals("SnsrPoolOccptPedSfty") || EcuName.equals("ActrPoolOccptPedSfty1")
					|| EcuName.equals("ActrPoolOccptPedSfty2") || EcuName.equals("ActrPoolOccptPedSfty3")
					|| EcuName.equals("SeatBltRmn") || EcuName.equals("PedCrashDetn")
					|| EcuName.equals("SysPedProtnActvn") || EcuName.equals("OccptDetn")
					|| EcuName.equals("VehCrashDetn") || EcuName.equals("OccptRestrntSysActvn"))
				PartName = new String("OPS");
			/* OPS_Part_Select_End */

			/* MTH_Part_Select_Start */
			if (EcuName.equals("BtnPan") || EcuName.equals("InterdomCtrlr"))
				PartName = new String("MTH");
			/* MTH_Part_Select_End */
			//////////////////////

			/* file method start */
			FileWriter outfile = new FileWriter("/usr/local/apache-tomcat-8.5.12/webapps/AREX/path.txt");
			outfile.write(PartName);
			outfile.append(System.getProperty("line.separator"));
			outfile.write(EcuName);
			outfile.flush();
			outfile.close();

			/* file method end */

			String query = "import module namespace xmldb=\"http://exist-db.org/xquery/xmldb\";"
					+ "\n declare default element namespace \"http://autosar.org/schema/r4.0\";"
					+ "\n declare namespace functx = \"http://www.functx.com\";"
					+ "\n declare function functx:change-element-ns-deep" + "\n ( $nodes as node()* ,"
					+ "\n    $newns as xs:string ," + "\n    $prefix as xs:string )  as node()* {"
					+ "\n  for $node in $nodes" + "\n  return if ($node instance of element())"
					+ "\n        then (element" + "\n               {QName ($newns,"
					+ "\n                          concat($prefix,"
					+ "\n                                    if ($prefix = '')"
					+ "\n                                    then ''"
					+ "\n                                    else ':',"
					+ "\n                                    local-name($node)))}" + "\n               {$node/@*,"
					+ "\n                functx:change-element-ns-deep($node/node(),"
					+ "\n                                           $newns, $prefix)})"
					+ "\n         else if ($node instance of document-node())"
					+ "\n         then functx:change-element-ns-deep($node/node(),"
					+ "\n                                           $newns, $prefix)" + "\n         else $node"
					+ "\n } ;" + "\n let $test :=doc(\"/db/test/TEST.arxml\")"
					+ "\n let $log-in := xmldb:login(\"/db\", \"admin\", \"\")"
					+ "\n let $create-collection := xmldb:create-collection(\"/db\", \"output\")"
					+ "\n let $filename := \"/TEST_" + EcuName + ".arxml\""
					+ "\n let $output-create := xmldb:store(\"/db/AUTOSAR/" + PartName + "\", $filename, $test)"
					+ "\n let $ALL :=doc(\"/db/test/AUTOSAR_MOD_AISpecificationExamples.arxml\")/AUTOSAR/AR-PACKAGES/AR-PACKAGE/AR-PACKAGES/AR-PACKAGE/AR-PACKAGES/AR-PACKAGE/ELEMENTS"
					+ "\n let $ALL_copy :=doc(\"/db/AUTOSAR/" + PartName + "/TEST_" + EcuName
					+ ".arxml\")/AUTOSAR/AR-PACKAGES/AR-PACKAGE/AR-PACKAGES/AR-PACKAGE/AR-PACKAGES/AR-PACKAGE"
					+ "\n let $SW :=$ALL/COMPOSITION-SW-COMPONENT-TYPE"
					+ "\n let $PORT_SR:= $ALL/SENDER-RECEIVER-INTERFACE"
					+ "\n let $PORT_CS := $ALL/CLIENT-SERVER-INTERFACE" + "\n let $PORT :=($PORT_SR,$PORT_CS)"
					+ "\n let $ARD := $ALL/APPLICATION-RECORD-DATA-TYPE"
					+ "\n let $AAD := $ALL/APPLICATION-ARRAY-DATA-TYPE"
					+ "\n let $APD := $ALL/APPLICATION-PRIMITIVE-DATA-TYPE" + "\n let $DATATYPE := ($APD,$ARD,$AAD)"
					+ "\n let $ECU := for $ECU in $SW[SHORT-NAME =\"" + EcuName + "\"] return $ECU"
					// +"\n let $ECU := for $ECU in $SW[SHORT-NAME = \"Imob\"]
					// return $ECU"
					+ "\n let $SUBSW :=for $SUBSW in $SW[SHORT-NAME = data($ECU/COMPONENTS/SW-COMPONENT-PROTOTYPE/SHORT-NAME)] return $SUBSW "
					+ "\n let $PARTSW :=($ECU,$SUBSW)"
					+ "\n let $PDATA := data($PARTSW/PORTS/P-PORT-PROTOTYPE/PROVIDED-INTERFACE-TREF)"
					+ "\n let $RDATA := data($PARTSW/PORTS/R-PORT-PROTOTYPE/REQUIRED-INTERFACE-TREF)"
					+ "\n let $ECU-PORT := for $ECU-PORT in $PORT[SHORT-NAME = $PDATA or SHORT-NAME = $RDATA] return $ECU-PORT"
					+ "\n let $PORTDATA := data($ECU-PORT/DATA-ELEMENTS/VARIABLE-DATA-PROTOTYPE/TYPE-TREF)"
					+ "\n let $PORT-DATATYPE := for $PORT-DATATYPE in $DATATYPE[SHORT-NAME = $PORTDATA] return $PORT-DATATYPE"
					+ "\n let $ECU_insert := functx:change-element-ns-deep($ECU, \"\", \"\")"
					+ "\n let $PORT_insert := functx:change-element-ns-deep($ECU-PORT, \"\", \"\")"
					+ "\n let $DATA_insert := functx:change-element-ns-deep($PORT-DATATYPE, \"\", \"\")"
					+ "\n let $DATATYPE_update := for $x in $DATA_insert"
					+ "\n return update insert functx:change-element-ns-deep($DATA_insert, \"\", \"\") into $ALL_copy[SHORT-NAME = \"ApplicationDataTypes_Example\"]/ELEMENTS"
					+ "\n let $PORT_update := for $x in $PORT_insert"
					+ "\n return update insert functx:change-element-ns-deep($PORT_insert, \"\", \"\") into $ALL_copy[SHORT-NAME = \"PortInterfaces_Example\"]/ELEMENTS"
					+ "\n let $ECU_update := for $x in $ECU_insert"
					+ "\n return update insert functx:change-element-ns-deep($ECU_insert, \"\", \"\") into $ALL_copy[SHORT-NAME = \"SwComponentTypes_Example\"]/ELEMENTS"
					+ "\n return doc(\"/db/AUTOSAR/" + PartName + "/TEST_" + EcuName + ".arxml\")";
			/////////////////////
			/*
			 * String query =
			 * "declare default element namespace \"http://autosar.org/schema/r4.0\";"
			 * +
			 * "\n let $ALL :=doc(\"/db/test/AUTOSAR_MOD_AISpecificationExamples.arxml\")/AUTOSAR/AR-PACKAGES/AR-PACKAGE/AR-PACKAGES/AR-PACKAGE/AR-PACKAGES/AR-PACKAGE/ELEMENTS "
			 * + "\n let $SWC :=$ALL/COMPOSITION-SW-COMPONENT-TYPE" +
			 * "\n let $PORT_SR:= $ALL/SENDER-RECEIVER-INTERFACE" +
			 * "\n let $PORT_CS := $ALL/CLIENT-SERVER-INTERFACE" +
			 * "\n let $PORT :=($PORT_SR,$PORT_CS)" +
			 * "\n let $ARD := $ALL/APPLICATION-RECORD-DATA-TYPE" +
			 * "\n let $AAD := $ALL/APPLICATION-ARRAY-DATA-TYPE" +
			 * "\n let $APD := $ALL/APPLICATION-PRIMITIVE-DATA-TYPE" +
			 * "\n let $DATATYPE := ($APD,$ARD,$AAD)" +
			 * "\n let $ECU_Main := for $ECU_Main in $SWC[SHORT-NAME =\""
			 * +Ecuname+"\"] return $ECU_Main" //+
			 * "\n let $ECU_Main := for $ECU_Main in $SWC[SHORT-NAME = \"TirePMon\"] return $ECU_Main"
			 * +
			 * "\n let $ECU_Sub :=for $ECU_Sub in $SWC[SHORT-NAME = data($ECU_Main/COMPONENTS/SW-COMPONENT-PROTOTYPE/SHORT-NAME)] return $ECU_Sub"
			 * + "\n let $ECU :=($ECU_Main,$ECU_Sub)" +
			 * "\n let $PDATA := data($ECU/PORTS/P-PORT-PROTOTYPE/PROVIDED-INTERFACE-TREF)"
			 * +
			 * "\n let $RDATA := data($ECU/PORTS/R-PORT-PROTOTYPE/REQUIRED-INTERFACE-TREF)"
			 * +
			 * "\n let $ECU-PORT := for $ECU-PORT in $PORT[SHORT-NAME = $PDATA or SHORT-NAME = $RDATA] return $ECU-PORT"
			 * +
			 * "\n let $PORTDATA := data($ECU-PORT/DATA-ELEMENTS/VARIABLE-DATA-PROTOTYPE/TYPE-TREF)"
			 * +
			 * "\n let $PORT-DATATYPE := for $PORT-DATATYPE in $DATATYPE[SHORT-NAME = $PORTDATA] return $PORT-DATATYPE"
			 * + "\n return ($ECU, $ECU-PORT, $PORT-DATATYPE) ";
			 */
			Collection col = DatabaseManager.getCollection(URI + XmldbURI.ROOT_COLLECTION);
			// get query-service
			XQueryService service = (XQueryService) col.getService("XQueryService", "1.0");

			// set pretty-printing on
			service.setProperty(OutputKeys.INDENT, "yes");
			service.setProperty(OutputKeys.ENCODING, "UTF-8");

			CompiledExpression compiled = service.compile(query);

			// execute query and get results in ResourceSet
			ResourceSet result = service.execute(compiled);

			Properties outputProperties = new Properties();
			outputProperties.setProperty(OutputKeys.INDENT, "yes");
			SAXSerializer serializer = (SAXSerializer) SerializerPool.getInstance().borrowObject(SAXSerializer.class);
			serializer.setOutput(new OutputStreamWriter(System.out), outputProperties);

			ResourceIterator i = result.getIterator();

			String value2 = "";
			while (i.hasMoreResources()) {
				org.xmldb.api.base.Resource r = i.nextResource();

				String value = (String) r.getContent();
				// value2 +=value+"<br>";
				value2 += value + EcuName;
			}

			value2 = value2.replaceAll("[&]", "&amp;").replaceAll("[<]", "&lt;").replaceAll("[>]", "&gt;");
			value2 = "<pre>" + value2;

			request.setAttribute("queryValue", value2);

			RequestDispatcher rd = request.getRequestDispatcher("/xmlmanage.jsp");
			rd.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
		// doGet(request, response);
	}
}
