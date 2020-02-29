# SUMA 1.0.0

## Introduction
SUMA is an efficient and scalable query answering system. It develops a partial materialization-based approach to extending RDF data. SUMA supports the OWL 2 DL ontology.

## Versions
This documentation is for SUMA 1.0.0.

## Main features
- Produces efficient materialization by low complexity materialization algorithm
- Builds three types index for data and rules
- Provides reasoning API
- Can integrate off-the-shelf efficient SPARQL query engines
- Supports [OWL 2 DL](https://www.w3.org/TR/owl2-syntax/) ontologies
- Supports [SPARQL](https://www.w3.org/TR/sparql11-query/) queries

# Getting started

## How to run SUMA source code？
#### Runtime environment: Java 8
#### 1. git clone https://github.com/qinxiaoyu123/uobm-gOWL.git;
#### 2. run the main class testSUMARunTest.java at package path: src/main/java/com/tju/suma/test;
#### 3. load all the jars in the lib. In IntelliJ IDEA, you can load in the following way: Project Structure -> Libraries -> new project library -> Java;
#### 4. modify parameters:
- `pathTBox: the ontology path (*.owl)`
    
- `pathABox: the RDF data path (*.nt/ttl)`
    
- `n: the step of materialization`
    
- `pathExtendedABox: the path of extended RDF data (*.nt)`
          
- `isQueryByJena: whether to run the jena query, which defaults to true`
		   
- `initIsRoleWriting(true): whether to apply role rewriting algorithm, which defaults to true`
          
- `queryPath: the SPARQL query path (.sparql)`
          
- `answerPath: the answer path`

###
  
 
