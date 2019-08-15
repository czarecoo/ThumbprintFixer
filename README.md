# ThumbprintFixer
This app inserts colons to colonless thumbprint.

## How to use Application
Type url or some text into textbox. (app will automaticly paste any string you have saved in your clipboard on start).

![main app window1](https://www.dropbox.com/s/679pf0i9p567zwn/1.png?raw=1)


Click on "fix thumbprint" button.

![main app window2](https://www.dropbox.com/s/6ldsc4cbq0jtvpu/2.png?raw=1)


You can see that result matches real page's thumbprint.

![main app window3](https://www.dropbox.com/s/3l23rquq2tl90ij/3.png?raw=1)

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
