

## Identifying Phases of Flight for the NGAFID

   This project identified phases of flight for the NGAFID. Phase of flight refers to a period within a flight.
   It will identify which phase or potentially subphase an aircraft is in at a giventime
**How to run the files from the NGAFID repository to generate results**

 **Software Requirements** 

 - JDK :Java
 - Text Editor

  **Setting Up TestData**
   
   Get test data from Links[git hub link](https://github.com/saumya161992/ngafid) to download csv files and place them in downloads folder.These CSV files will be read into the java code of ProcessFlightFile and henceforth will be used to identify the current pgase of the aircraft
  





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

java ProcessFlightFile ~/Downloads/flight_0.csv

```
































