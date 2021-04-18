

## Identifying Phases of Flight for the NGAFID

   This project identified phases of flight for the NGAFID. Phase of flight refers to a period within a flight.
   It will identify which phase or potentially subphase an aircraft is in at a giventime


**How to run the files from the NGAFID repository to generate results**


 Language used : JAVA

 **Software Requirements** 

 - JDK : Java
 - Text Editor

  **Setting Up TestData**
   
   Get test data from Links[git hub link](https://github.com/saumya161992/ngafid) to download csv files.These CSV files will be read into the java code of ProcessFlightFile and henceforth will be used to identify the current pgase of the aircraft
  





**Environment Settings**
  Installation instruction for windows

1. Download JDK 13. Links[Goto Java SE download site](https://www.oracle.com/java/technologies/javase-downloads.html) 
2. Install JDK. Run the downloaded installer which installs both JDK and JRE.
3. Include JDK's "bin" directory in the path
  
    ```
   Variable name  : PATH

   Variable value : c:\Program Files\Java\jdk-13.0.{x}\bin;[do not delete exiting entries...]
   
   ```
  Installation instruction for MACOS

1. Download JDK 13. Links[ Goto Java SE download site ](https://www.oracle.com/java/technologies/javase-downloads.html) 
2.  Install JDK/JRE
    - Double-click the downloaded Disk Image (DMG) file. Follow the screen instructions to install JDK/JRE.
    - Eject the DMG file.
    - To verify your installation, open a "Terminal" and issue these commands. 

__to complile code__


```JAVA
 
  javac *.java

```
__to run code__

```
 - execute ProcessFlightFile class by specifying the location of the testdate followed by example csv flight file(inside any testfile) and enter type 1 or 2 or 3 or 4for phase type.

 - java ProcessFlightFile ~/saumya_ngafid/ngafid/Validationfiles/initialclimbAnnotations.txt 3
 - java ProcessFlightFile ~/saumya_ngafid/ngafid/Validationfiles/Testing.txt 3



```

__to execute testcases__

```
 - Download validation text files from [git hub link](https://github.com/saumya161992/ngafid/tree/master/Validationfiles).

 - execute ProcessFlightFile class by specifying the location of the testdate followed by example manualannotations flightfile and enter type 2 or 3 for phase type.

 - for executing test cases for transition from standing to taxi follow the below syntax:
  
    java FlightAnnotations ~/saumya_ngafid/ngafid/Validationfiles/TaxiAnnotations.txt


- for executing test cases to detect multiple takeoffs follow the below syntax:
 
    java ProcessFlightFile ~/saumya_ngafid/ngafid/Validationfiles/TakeoffAnnotations.txt 2


- for executing test cases to detect transition from takeoff to initialclimb follow the below syntax:
 
   java ProcessFlightFile ~/saumya_ngafid/ngafid/Validationfiles/initialclimbAnnotations.txt 3


 ```


   **Dependencies**

    Packages which are imported:
 
    - java.util.ArrayvList;
    - java.util.Arrays;
    - java.math.RoundingMode;
    - java.text.DecimalFormati;
    - java.io.BufferedReader;
    - java.io.BufferedWriter;
    - java.io.File;
    - java.io.FileReader;
    - java.io.FileWriter;
    - java.io.IOException;
    - java.util.Scanner;
    - java.util.Collections;
    - java.util.List;
    - java.util.HashMap;
    - java.util.stream.Collectors;


**Domain and Architecture Diagrams** 

     Updated domain and architecture diagrams(including ingestion, cleaning and modelling layers) can be downloaded from Links[git hub link](https://github.com/saumya161992/ngafid/tree/master/Daigrams)
