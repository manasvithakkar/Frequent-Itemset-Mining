# Frequent-Itemset-Mining
A pattern mining algorithm based on Apriori algorithm that mines k+ frequent itemsets given a minimum support count.

This project has been implemented in Java Eclipse.
This program takes four command line agruments and outputs the result in the file mentioned in command line:
The command line arguments are
1. Minimum support
2. k
3. Name_of_input_file
4. Name_of_output_file

The input file should be present in the same location as of the .java file.
To run the project in Eclipse, create a new java prooject and in the class 
paste this "DApriori.java" file. Name the class DApriori
Go to the Run option and choose run configurations and in the Arguments tab 
enter the above four command line arguments.

For example :
Java Eclipse->Run->Run Configurations->Arguments->Program Arguments

This code takes the following commandline arguments:
minimum_support_count k input_file output_file

Example :
5 3 transactionDB.txt Output_5_3.txt

The output for the above example looks like :
A2J96R1J6MDMEV AWE8HU0AZKASV A3UIATN5XW74NQ (5)
AWE8HU0AZKASV A3Y9BX5AS769T A3UIATN5XW74NQ (5)
AWE8HU0AZKASV A3PXX92YUMGMBG A3UIATN5XW74NQ (5)
AYFQ8ML2PYZ1D A2S9IDC1IZH7WN A9TJYY7P2R280 (5)
