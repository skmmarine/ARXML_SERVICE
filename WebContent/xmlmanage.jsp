<%@ page language="java" contentType="text/html; charset=EUC-KR"
   pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport"
   content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<link rel="stylesheet" href="font-awesome.min.css">
<link href='https://fonts.googleapis.com/css?family=Bitter:700,400'
   rel='stylesheet' type='text/css'>
<link href='https://fonts.googleapis.com/css?family=Russo+One'
   rel='stylesheet' type='text/css'>
<link href='https://fonts.googleapis.com/css?family=BioRhyme+Expanded'
   rel='stylesheet' type='text/css'>
<link href='https://fonts.googleapis.com/css?family=Roboto+Slab:100'
   rel='stylesheet' type='text/css'>
<link href='https://fonts.googleapis.com/css?family=Patua+One'
   rel='stylesheet' type='text/css'>

<link href="CSS/xmlmanage.css" rel="stylesheet" type="text/css" />
<title>AM service</title>
</head>
<body>
   <div class="full-width background-about">
      <div class="container second-container">
         <div class="about">
            <div class="about header">
               <h2>ECU Manage</h2>
               <hr id="about-hr">
               <h5>Select the ECU you want</h5>
               <h5>1</h5>
            </div>

               <div style="border: 1px solid gold;  float: left; width: 25%; padding:5px;">
				<ul>
               <form method="post" action="main_my">
             
                  <details open>
                     <summary>Body</summary>
                
                     <button  name="Web2DB_ECUNAME" value="LockgCen" type="submit" >LockgCen</button>
                     <br>
                     <button name="Web2DB_ECUNAME" value="IntrLi" type="submit">IntrLi</button>
                     <br>
                     <button name="Web2DB_ECUNAME" value="MirrAdjmt" type="submit">MirrAdjmt</button>
                     <br>
                     <button name="Web2DB_ECUNAME" value="MirrTintg" type="submit">MirrTintg</button>
                     <br>
                     <button name="Web2DB_ECUNAME" value="SeatAdjmt" type="submit">SeatAdjmt</button>
                     <br>
                     <button name="Web2DB_ECUNAME" value="WiprWshr" type="submit">WiprWshr</button>
                     <br>
                     <button name="Web2DB_ECUNAME" value="EntryRemKeyls" type="submit">EntryRemKeyls</button>
                     <br>
                     <button name="Web2DB_ECUNAME" value="KeyPad" type="submit">KeyPad</button>
                     <br>
                     <button name="Web2DB_ECUNAME" value="Antithft" type="submit">Antithft</button>
                     <br>
                     <button name="Web2DB_ECUNAME" value="ExtrLi" type="submit">ExtrLi</button>
                     <br>
                     <button name="Web2DB_ECUNAME" value="TerminalClmpCtrl" type="submit">TerminalClmpCtrl</button>
                     <br>
                     <button name="Web2DB_ECUNAME" value="HornCtrl" type="submit">HornCtrl</button>
                     <br>
                     <button name="Web2DB_ECUNAME" value="RoofCvntbCtrl" type="submit">RoofCvntbCtrl</button>
                     <br>
                     <button name="Web2DB_ECUNAME" value="Defrstctrl" type="submit">Defrstctrl</button>
                     <br>
                     <button name="Web2DB_ECUNAME" value="Imob" type="submit">Imob</button>
                     <br>
                     <button name="Web2DB_ECUNAME" value="Pase" type="submit">Pase</button>
                     <br>
                     <button name="Web2DB_ECUNAME" value="SnsrBody" type="submit">SnsrBody</button>
                     <br>
                     <button name="Web2DB_ECUNAME" value="SeatClima" type="submit">SeatClima</button>
                 
                  
                  </details>
					
                  <details>

                     <summary>PT</summary>
				
                     <button name="Web2DB_ECUNAME" value="PtCoorr" type="submit">PtCoorr</button>
                     <br>
                     <button name="Web2DB_ECUNAME" value="Trsm" type="submit">Trsm</button>
                     <br>
                     <button name="Web2DB_ECUNAME" value="CmbEng" type="submit">CmbEng</button>
                     <br>
                     <button name="Web2DB_ECUNAME" value="VehMthnForP" type="submit">VehMtnForPt</button>
                     <br>
                     <button name="Web2DB_ECUNAME" value="TirePMon" type="submit">PtMisc</button>
				
                  </details>

                  <details>
                     <summary>Chassis</summary>

                     <button name="Web2DB_ECUNAME" value="TirePMon" type="submit">CrsCtrlAndAcc</button>
                     <br>
                     <button name="Web2DB_ECUNAME" value="Esc" type="submit">Esc</button>
                     <br>
                     <button name="Web2DB_ECUNAME" value="Ssm" type="submit">Ssm</button>
                     <br>
                     <button name="Web2DB_ECUNAME" value="Epb" type="submit">Epb</button>
                     <br>
                     <button name="Web2DB_ECUNAME" value="Vlc" type="submit">Vlc</button>
                     <br>
                     <button name="Web2DB_ECUNAME" value="Rsc" type="submit">Rsc</button>
                     <br>
                     <button name="Web2DB_ECUNAME" value="Steer" type="submit">Steer</button>
                     <br>
                     <button name="Web2DB_ECUNAME" value="SteerVehStabyCtrl" type="submit">SteerVehStabyCtrl</button>
                     <br>
                     <button name="Web2DB_ECUNAME" value="SteerDrvrAsscSys" type="submit">SteerDrvrAsscSys</button>
                     <br>
                     <button name="Web2DB_ECUNAME" value="Susp" type="submit">Susp</button>
                     <br>
                     <button name="Web2DB_ECUNAME" value="DtTqDistbn" type="submit">DtTqDistbn</button>
                     <br>
                     <button name="Web2DB_ECUNAME" value="TirePMon" type="submit">TirePMon</button>
                     <br>
                     <button name="Web2DB_ECUNAME" value="ChassisSnsr" type="submit">ChassisSnsr</button>
                     <br>
                     <button name="Web2DB_ECUNAME" value="SurrndgsSnsr" type="submit">SurrndgsSnsr</button>

                  </details>

                  <details>
                     <summary>OccptPedSfty</summary>

                     <button name="Web2DB_ECUNAME" value="SnsrPoolOccptPedSfty" type="submit">SnsrPoolOccptPedSfty</button>
                     <br>
                     <button name="Web2DB_ECUNAME" value="SnsrPoolOccptPedSfty1" type="submit">SnsrPoolOccptPedSfty1</button>
                     <br>
                     <button name="Web2DB_ECUNAME" value="SnsrPoolOccptPedSfty2" type="submit">SnsrPoolOccptPedSfty2</button>
                     <br>
                     <button name="Web2DB_ECUNAME" value="SnsrPoolOccptPedSfty3" type="submit">SnsrPoolOccptPedSfty3</button>
                     <br>
                     <button name="Web2DB_ECUNAME" value="SeatBltRmn" type="submit">SeatBltRmn</button>
                     <br>
                     <button name="Web2DB_ECUNAME" value="PedCrashDetn" type="submit">PedCrashDetn</button>
                     <br>
                     <button name="Web2DB_ECUNAME" value="SysPedProtnActvn" type="submit">SysPedProtnActvn</button>
                     <br>
                     <button name="Web2DB_ECUNAME" value="OccptDetn" type="submit">OccptDetn</button>
                     <br>
                     <button name="Web2DB_ECUNAME" value="VehCrashDetn" type="submit">VehCrashDetn</button>
                     <br>
                     <button name="Web2DB_ECUNAME" value="OccptRestrntSysActvn" type="submit">OccptRestrntSysActvn</button>

                  </details>
                  
                  <details>
                     <summary>MmedTelmHmi</summary>
                    
                        <button name="Web2DB_ECUNAME" value="BtnPan" type="submit">BtnPan</button>
                        <br>
                        <button name="Web2DB_ECUNAME" value="InterdomCtrlr" type="submit">InterdomCtrlr</button>
                  </details>
                 
                 
                  <hr>
               </form>
               </ul>
            </div>
            
            <div class="skills inline">
			<form method="post" action="main_excelextract">
                  <button name="Web2DB_ECUNAME" value="" type="submit" style="background-color:DeepSkyBlue; color:#ffffff">excel Extract</button>                  
                  <a href="http://13.124.76.77:8080/exist/apps/eXide/index.html" style="background-color:DeepSkyBlue">eXist button</a>
               </form>
               <div style="border: 1px solid gold;overflow:scroll; width:800px; height:1500px; padding:10px; background-color:black;">
				${ queryValue }
				</div> <br>
               <br>
               <br>
               <br>
               <br>
               <br>
               <br>
               <br>
               <br>
               <br>
               <br>
               <br>
               <br>
               <br>
               <br>
               <br>

            </div>
         </div>
      </div>
   </div>
</body>
</html>