# XML parsing

The purpose of this exercise is to train you to work with the JAXP API.  
The estimated workload is *120 min*.

### Description

> You are given two files:
`test.xsd` and `test.xml`
Please, proceed to their content and analyse it

1. Create java classes to store information from `test.xml`. Tesese classes must be orgranized in the hierarchy by an aggregation. There is must be a top level `container`.

2. Create a program to parse an xml content with the help of a **DOM parser**. A program take `input.xml` as an input and produces `container` with the information from the `input.xml` document. Place all the code of this program into `DOMController` class.

3. Create a program to parse an xml content with the help of a **SAX parser**. A program take `input.xml` as an input and produces `container` with the information from the `input.xml` document. Place all the code of this program into `SAXController` class.

4. Create a program to parse an xml content with the help of a **StAX parser**. A program take `input.xml` as an input and produces `container` with the information from the `input.xml` document. Place all the code of this program into `StAXController` class.

5. Create three methods to sort content of `container` by some order.

6. Create a method to save a content from `container` into an output xml file. 

7. Create a method to check if an xml document is valid against xsd document. You can use `validation API` or do it with the help of `DOM/SAX parser`.

8. Proceed to `Main` class and for every parser (DOM, SAX, StAX) do the following actions:
    * parse `input.xml` and obtain `container`
    * sort `container` in some way (use methods from 5)
    * save an information from `container` to output xml (see name convention in `Main` class)
    * validate an output xml file

> You can create new classes if you need it