rem set JAVA_HOME = "E:\Programe\AdoptOpenJDK\jdk-11.0.6.10-hotspot"
rem set PATH = .;%JAVA_HOME%/bin;%PATH%
cd C:\robocode
javac -verbose -encoding UTF-8 -classpath libs\robocode.jar;C:\robocode\robots C:\robocode\robots\eu\ase\jrobot\MyFirstRobot.java
javac -verbose -encoding UTF-8 -classpath libs\robocode.jar;C:\robocode\robots C:\robocode\robots\eu\ase\jrobot\MySecondRobot.java
javac -verbose -encoding UTF-8 -classpath libs\robocode.jar;C:\robocode\robots C:\robocode\robots\eu\ase\jrobot\MyThirdRobot.java
javac -verbose -encoding UTF-8 -classpath libs\robocode.jar;C:\robocode\robots C:\robocode\robots\eu\ase\jrobot\MyFourthRobot.java
javac -verbose -encoding UTF-8 -classpath libs\robocode.jar;C:\robocode\robots C:\robocode\robots\eu\ase\jrobot\MyFifthRobot.java
javac -verbose -encoding UTF-8 -classpath libs\robocode.jar;C:\robocode\robots C:\robocode\robots\eu\ase\jrobot\MySixthRobot.java
javac -verbose -encoding UTF-8 -classpath libs\robocode.jar;C:\robocode\robots C:\robocode\robots\eu\ase\jrobot\MySeventhRobot.java
cd C:\robocode
java -Xmx512M -cp libs/robocode.jar -XX:+IgnoreUnrecognizedVMOptions "--add-opens=java.base/sun.net.www.protocol.jar=ALL-UNNAMED" "--add-opens=java.base/java.lang.reflect=ALL-UNNAMED" "--add-opens=java.desktop/javax.swing.text=ALL-UNNAMED" "--add-opens=java.desktop/sun.awt=ALL-UNNAMED" robocode.Robocode -battle "C:\robocode\battles\TheBattle.battle"
pause