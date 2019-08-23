# ThumbprintFixer
This app inserts colons to colonless thumbprint.
Click [here](https://github.com/czarecoo/ThumbprintFixer/releases) to download newest version.
(GUI is windows only)

## How to use Application
Type url or some text into textbox. (app will automaticly paste any string you have saved in your clipboard on start).

![1](https://www.dropbox.com/s/679pf0i9p567zwn/1.png?raw=1)


Click on "fix thumbprint" button.

![2](https://www.dropbox.com/s/6ldsc4cbq0jtvpu/2.png?raw=1)


You can see that result matches real page's thumbprint.

![3](https://www.dropbox.com/s/3l23rquq2tl90ij/3.png?raw=1)


From version 1.1 you can setup proxy. The window will open when you click on cogwheel button.

![4](https://www.dropbox.com/s/uy9hb81ynnyyiah/4.png?raw=1)


Your proxy data will be saved in properties file located next to your jar.
![4](https://www.dropbox.com/s/iwn2r9r99lhvuov/5.png?raw=1)

You can also use the application from command line:
* If you pass 0 parameters the UI will start (java -jar jarfile)
* If you pass 1 parameter the thumbprint will be created without proxy (java -jar jarfile somesite.com).
* If you pass 3 parameters the thumbprint will be created with proxy (java -jar jarfile somesite.com someProxyServer someProxyPort).

## Installation
Type
```bash
mvn clean javafx:run
```
to run application from maven.

or type
```bash
mvn package
```
to get jar file (it will be created in target/ directory)

## Usage

```bash
double click jar file to start app's javafx gui
```
or 
```bash
java -jar ThumbprintFixer-version.jar stringToMakeIntoThumbprint
```
