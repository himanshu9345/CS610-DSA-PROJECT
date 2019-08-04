# CS610-DSA-PROJECT
Repository for DSA (Data Structure and Algorithms) Project

HIMANSHU PATEL cs610 2093 prp

HANDOUT 2 ADHERED; NO BUGS TO REPORT




## PROJECT 1 Huffman Coding


################# <br />
INPUT FILE <br />
#### linux command for copying the file <br />
cp xyz.pdf filename  <br />
cp abc.mp4 filename  <br />
cp pqr.jpg filename  <br />

#### windows command for copying the file <br />
copy xyz.pdf filename <br />
copy abc.mp4 filename <br />
copy pqr.jpg filename <br />
################### <br />

### How to run the project?
1. compile java file to encode the given file
-> javac henc2093.java

2. Run java file
-> java henc2093 filename

#### OUTPUT FILE
-> filename.huf

##### delete filename or it will get overwrite.
3. compile java file to decode the given file(filename.huf)
-> javac hdec2093.java

4. Run java file
-> java hdec2093 filename.huf

#### OUTPUT FILE(same folder as input file)
-> filename
###### md5sum of original file and filename is same.

##### BUG REPORT
1. small size file wont compress that much.


## PROJECT 2 HITS and PageRank implementations


## a. HITS algorithm

## How to run the project?

********************************************************
1. compile java file to run hits algorithm
-> javac hits2093.java
2. Run java file
-> java hits2093 iteration(4) initial(-1) samplegraph.txt
###### samplegraph.txt provided, given in prp handout
********************************************************
Base : 0 :A/H[0]=0.2500000/0.2500000 A/H[1]=0.2500000/0.2500000 A/H[2]=0.2500000/0.2500000 A/H[3]=0.2500000/0.2500000
Iter : 1 :A/H[0]=0.5000000/0.8164966 A/H[1]=0.5000000/0.4082483 A/H[2]=0.5000000/0.4082483 A/H[3]=0.5000000/0.0000000
Iter : 2 :A/H[0]=0.3162278/0.9428090 A/H[1]=0.3162278/0.2357023 A/H[2]=0.6324555/0.2357023 A/H[3]=0.6324555/0.0000000
Iter : 3 :A/H[0]=0.1714986/0.9847319 A/H[1]=0.1714986/0.1230915 A/H[2]=0.6859943/0.1230915 A/H[3]=0.6859943/0.0000000
Iter : 4 :A/H[0]=0.0877058/0.9961165 A/H[1]=0.0877058/0.0622573 A/H[2]=0.7016464/0.0622573 A/H[3]=0.7016464/0.0000000



## b. Google’s PageRank algorithm

## How to run the project?

********************************************************
1. compile java file to run pagerank algorthm
-> javac pgrk2093.java
2. Run java file
-> java pgrk2093 iteration(4) initial(-1) samplegraph.txt
###### samplegraph.txt provided, given in prp handout
********************************************************
Base : 0 :P[0]=0.2500000 P[1]=0.2500000 P[2]=0.2500000 P[3]=0.2500000
Iter : 1 :P[0]=0.2500000 P[1]=0.2500000 P[2]=0.1437500 P[3]=0.1437500
Iter : 2 :P[0]=0.2500000 P[1]=0.1596875 P[2]=0.1437500 P[3]=0.1437500
Iter : 3 :P[0]=0.1732344 P[1]=0.1596875 P[2]=0.1437500 P[3]=0.1437500
Iter : 4 :P[0]=0.1732344 P[1]=0.1596875 P[2]=0.1111246 P[3]=0.1111246



##### BUG REPORT: NO BUGS TO REPORT



