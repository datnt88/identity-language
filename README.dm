Author: Nguyen Tien Dat
Email: dtnguyen88@outlook.com

This source implement two techniques: Trigram and Small Word
to solve a problem of language indentity.

The reference literature:
COMPARING TWO LANGUAGE IDENTIFICATION SCHEMES
by Gregory Grefenstette. Xerox Research Centre Europe

http://www.academia.edu/375397/Comparing_Two_Language_Identification_Schemes

========================================================
For implementation:

source:       	src/
project file: 	pom.xml
traning corpus	dataset/

To build the model for new lanuage:
./target/build-model.jar -c <corpus> -l <language>

note: there current are 6 language models:
English, Geman, French, Spanish, Czech and Vietnamese 

To indendity the language of a given document
./target/indentity-language.jar -d <document> -t <techique>


