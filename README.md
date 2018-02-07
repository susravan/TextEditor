#### Disclaimer:
The basic starter code is taken from Coursera website - https://www.coursera.org/learn/data-structures-optimizing-performance
and implemented the features of it.

Starter Code and GUI Application for Course 2 in the Java Programming: Object-Oriented Design of Data Structures Specialization:
Data structures: Measuring and Optimizing Performance
https://www.coursera.org/learn/data-structures-optimizing-performance
Authored by UCSD MOOC Team:
Mia Minnes, Christine Alvarado, Leo Porter, Alec Brickman and Adam Setters
Date: 10/29/2015
Coursera code provides the skeletal files like the JavaFX UI, grading files and some of the JUNITs for testing the code.

# Modern Text Editor

This repository contains the code of a modern text editor implemented in Java. It has following features, and they are explained in detail below.

1. Flesh Kincaid readability score
2. Benchmarking various ways of implementing dictionary
3. Markov text generator
3. Autocomplete using trie data structure
4. Spelling suggestions using one letter mutations
5. Edit distance - Word path between source to target words

### Flesh Kincaid readability score:
Flesch score determines how easy or difficult it is to read a text. Given a piece of writing, it calculates the number of sentences, words, and syllables in it and uses following formula to calculate the score.

I have used regex to calculate the components of the formula.

### Dictionary Implementation:
Given a list of words, we can store those words in a dictionary in several ways ranging from a simple linked list to a complex data structures such as tries.
Following is the analysis when the dictionary is implemented using both linked list and binary search tree. The operations are to look for a word for no. of trials times, once the dictionary is loaded.
Time unit - 10^-6 sec

No. of trials | Linked list dictionary access time | BST dictionary access time
---------------- | --------------------- | --------------
5000 | 44800 | 382
5500 | 36902 | 86
6000 | 33480 | 71
6500 | 57074 | 67
7000 | 33225 | 68
7500 | 35395 | 61
8000 | 37035 | 64
8500 | 51811 | 67
9000 | 223225 | 144
9500 | 91263 | 65
10000 | 105828 | 159
10500 | 88433 | 81
11000 | 113633 | 87
11500 | 160594 | 90
12000 | 64688 | 56
12500 | 65033 | 57
13000 | 67063 | 56
13500 | 68225 | 61
14000 | 60139 | 64
14500 | 94949 | 73

As we can observe, BST dictionary is at least three order of magnitude efficient than linked list dictionary.

### Markov text generator:
Based on the text frequency in the input text, probabilities are two words appearing consequently is calculated. These probabilities are used to generate new text of specified length. Though this is not a sophisticated algorithm, the results are pretty good.

### Autocomplete:
Implementing a dictionary in trie not only saves the memory but also reduces the searching time drastically to O(L) where L is the length of the string to search. Thus in general, trie implementations are used for autocomplete suggestions on the fly. If a word types is not in the dictionary, it is highlighted red in the application.

### Spelling suggestions:
This is a standard feature in most of the modern text editors. This feature can be implemented following the below procedure:
1. Get all one letter mutations (includes insertions, deletions, and substitutions) from the current word
2. If the current number of suggestions is not met, recurse over each of the mutations

These suggestions can be improved and adapted to the user's language by taking user's words usage into account. This work is slated for future.

### Edit distance:
The app also has a feature called edit distance which takes two words and gives you the steps on how the first word can be converted to second word making only one mutation at a time and the resulting all intermediate words are in the dictionary. If no such transformation is found, it results in no of steps: -1.
Note that currently it shows only the steps to take from one word to another, but it is not optimized on the no. of steps. In future, the code will be updated to reflect this improvement.
