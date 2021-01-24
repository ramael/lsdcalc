# lsdcalc
SpringBoot + Tests + Swagger

## Old British money calculator (�sd)
Prima del 15 febbraio 1971 la Gran Bretagna aveva un sistema di moneta risalente alle conquiste normanne del 1066.
In base a questo sistema la sterlina (pound, � o p) era divisa in 20 scellini (shillings, s) o 240 pennies (pence, d), Ogni scellino era diviso in 12 pennies.

### Esempio di operazioni
* 5p 17s 8d + 3p 4s 10d   = 9p 2s 6d
* 5p 17s 8d - 3p 4s 10d   = 2p 12s 10d
* 3p 4s 10d - 5p 17s 8d   = - 2p 12s 10d
* 5p 17s 8d * 2           = 11p 15 s 4d
* 5p 17s 8d * -2          = - 11p 15 s 4d
* 5p 17s 8d / 0           = null
* 5p 17s 8d / 3           = 1p 19s 2d (2p)
* 18p 16s 1d / 15         = 1p 5s 0d (1s 1d)

## Installation
Unpack `lsdcalc-*-bin.zip` in a temporary folder  
Rename `lsdcalc-*.jar` to `lsdcalc.jar`
Create lsdcalc installation folder (ex: C:\lsdcalc). In this document we will call this folder {LSDCALC_HOME}.
- Inside {LSDCALC_HOME} folder create 'config' folder
- Copy lsdcalc.jar in {LSDCALC_HOME} folder
- Copy batch file lsdcalc_start.cmd in {LSDCALC_HOME} folder
- Copy application.properties in {LSDCALC_HOME}\config
- Update application.properties with the corrent information (server port, paths, ...)
- Copy logback.xml in {LSDCALC_HOME}\config
- Update logback.xml with the correct information (path)

Eventually we will have the following folder structure:
{LSDCALC_HOME}
* lsdcalc.jar
* lsdcalc_start.cmd
* config
	* application.properties
	* logback.xml

### Start the server
* open Command Prompt
* run the following command: lsdcalc_start.cmd
* play with it
    * the server will start on the port defined in application.properties
    * visit the following address in your browser (http://localhost:<port\>/) to test the service with swagger
* stop it (Ctrl+C)

