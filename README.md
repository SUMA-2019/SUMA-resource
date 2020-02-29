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

# Using SUMA
- There are two ways to use SUMA: first, run the [SUMA source code](https://github.com/SUMA-2019/SUMA-resource), and second, run [SUMA.jar](https://github.com/SUMA-2019/SUMA) by command line.
## How to run SUMA source code？
#### Runtime environment: Java 8
#### 1. Git clone https://github.com/SUMA-2019/SUMA-resource.git;
#### 2. Run the main class testSUMARunTest.java (src/main/java/com/tju/suma/test/testSUMARunTest.java);
#### 3. Load all the jars under the lib. 

- For instance, in IntelliJ IDEA, you can load in the following way: Project Structure -> Libraries -> new project library -> Java;
#### 4. Modify parameters:
- `pathTBox: the ontology path (*.owl)`
    
- `pathABox: the RDF data path (*.nt/ttl)`
    
- `n: the step of materialization`
    
- `pathExtendedABox: the path of extended RDF data (*.nt)`
          
- `isQueryByJena: whether to run the jena query, which defaults to true`
		   
- `initIsRoleWriting(true): whether to apply role rewriting algorithm, which defaults to true`
          
- `queryPath: the SPARQL query path (.sparql)`
          
- `answerPath: the answer path`

### A running example:
- After you complete the first three steps, you can run the main class testSUMARunTest.java directly. The default path in the main class is our running example. It points to src/main/java/com/tju/suma/data, where includes standard.sparql (query file), univ-bench-dl.owl (ontology file), and uobm1.nt (RDF file).

## How to run SUMA.jar？
- There are two ways to run SUMA.jar: first, directly run [SUMA.jar](https://github.com/SUMA-2019/SUMA) by command line, and second, regenerate the jar from the SUMA source. 
#### Runtime environment: Java 8 
#### 1. Git clone https://github.com/SUMA-2019/SUMA.git;
#### 2. Command is of the following form :
- java -jar SUMA.jar ONTOLOGY_PATH DATA_PATH NEW_DATA_PATH
- For example: java -jar SUMA.jar [uobm.owl](https://github.com/SUMA-2019/SUMA) [uobm1.nt](https://github.com/SUMA-2019/SUMA) [uobm1_new.nt](https://github.com/SUMA-2019/SUMA)

### Datasets:
- [LUBM data generator](http://swat.cse.lehigh.edu/projects/lubm/);
- [UOBM data generator](http://www.cs.ox.ac.uk/isg/tools/UOBMGenerator/);
- [Ontologies](https://github.com/SUMA-2019/SUMA) used at experiment of SUMA at DASFAA'20;
- [Queries](https://github.com/SUMA-2019/SUMA) used at experiment of SUMA at DASFAA'20;
- [DBPedia+ ontology, data and query](http://www.cs.ox.ac.uk/isg/tools/PAGOdA/).

### Other systems:
- [PAGOdA](http://www.cs.ox.ac.uk/isg/tools/PAGOdA/): PAGOdA exploits a hybrid approach to answering conjunctive queries over OWL 2 ontologies that combines a datalog reasoner with a fully-fledged OWL 2 reasoner in order to provide scalable "pay as you go" performance. 
- [RDF3x](https://github.com/gh-rdf3x/gh-rdf3x): RDF3x is a efficient SPARQL query engine. 


  
 
