ComponentSearch
=============

Usage
----------
If the jar file is up to date, just download it, otherwise download all the code and run compile.sh  
Then run the command line:  
`java -cp "ComponentSearch.jar" main.Main {options}`  
  
Options:  
`-n <filename>` The path for the file containing the input nodes  
`-e <filename>` The path for the file containing the input edges
`-b <basename>` The basename for the output files
`-N x` Set to x the compressed nodes filter parameter. The compressed nodes filter parameter is used 
to delete compressed nodes containing only x or less input nodes.  
`-E x` Set to x the compressed edges filter parameter. The compressed edges filter parameter is used 
to delete compressed edges representing only x or less input edges from the original graph.  
`-oc` Set true for the optimization component flag. If the oc flag is true, the algorithm will try
to move input nodes from hubs to strings of compressed nodes. For a given input node, if there is only 
one neighboring string of compressed nodes then the input node is assigned to the string, otherwise 
nothing append.
