

## How to execute code at your local

1. Install Java version java 13.
2. Goto my below git hub link to download csv files and place them in downloads folder.
3. Download csv flight files from Data folder.


**Setting up TestData**


**Environment Settings**
#####Installation instruction for windows

1. Download JDK 13. Links[Goto Java SE download site](https://www.oracle.com/java/technologies/javase-downloads.html) 
2. Install JDK. Run the downloaded installer which installs both JDK and JRE.
3. Include JDK's "bin" directory in the path
  
    ```
   Variable name  : PATH

   Variable value : c:\Program Files\Java\jdk-13.0.{x}\bin;[do not delete exiting entries...]
   
   ```
#####Installation instruction for MACOS

1. Download JDK 13. Links[Goto Java SE download site](https://www.oracle.com/java/technologies/javase-downloads.html) 
2.  Install JDK/JRE
    1. Double-click the downloaded Disk Image (DMG) file. Follow the screen instructions to install JDK/JRE.
    1. Eject the DMG file.
    1. To verify your installation, open a "Terminal" and issue these commands. 

__to comple__


```JAVA
javac *.java

```
__torun__

```

java ProcessFlightFile ~/Downloads/flight_0.csv

```
































