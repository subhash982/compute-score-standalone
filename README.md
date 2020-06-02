# Compute a score for list of names
This project is calculate the score for list of name and can be extended to enhance or change the algorithm to calculate the score.By default it is implementing the below algorithm. 
<br/>
<b>Compute Score By First Name:</b>  Read all the comma separate name from file and sum up all the char's int value (A=1,B=2...Z=26) and multiply the summed value with index of name after sorting.
<br/>
<b>Compute Score By Full Name:</b> Default implementation provided same as First name but can be changed accordingly. 

## Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Assumptions
- All the name should contains chars from [A-Z or a-Z] to calculate the score correctly. Ignoring any new line, tab, Carriage return and double quotes.
- If name contains small letters then it will be converted to upper case and then calculate the score.
- Performing the in-memory computation if file size is less then **50KB**. This value is configuration through properties.
- Performing computation using external sorting in chunks (each chunk of **20KB** size and configurable through properties) if file size is greater then **50KB**.
- Only implemented for first name based algorithm but can be extended for other algorithms. Possible values are **scoreByFirstName** or **scoreByFullName**. 
- Entire data should be in single line or can be splitted into multiple lines but multiple line shouldn't have different data set. Here are the some examples for valid / invalid data.
<pre>
<b>Valid Records :</b>
1) "MARY","PATRICIA" 
	Score=211
	
2) "MARY","PATR
	ICIA"  
	Score=211
	
3) "MARY",
	"PATRICIA" 
	Score =211
<b>Note:</b> Record will be converted to "MARY","PATRICIA" before processing and then score will be calculated.

<b>Invalid Records</b>
"MARY"
"PATRICIA"
Score = 134

<b>Note:</b>Record will be converted to "MARYPATRICIA" before processing and then score will be calculated.

</pre>
### Prerequisites
- Java 8 or later must be installed on your machine

### Installing / Running
Please follow the below step to run it locally
<pre>
1- Clone the repository or extract downloaded zip on your local machine.
2- Go to cloned/extracted directory (compute-score-standalone).
3- Open the command prompt and navigate to cloned/extracted and run <b>score.cmd</b> (For Windows) and <b>.\score</b>( For Linux) script.
4- Enter the valid file name (sample file added under the <b>testdata</b> directory in the repository).
5- It will also ask for the scoring algorithm also but it is optional and can be skipped by pressing enter. Default value is <b>scoreByFirstName</b>.
</pre>

### Testing Guidance
Here are some testing screenshots
![Score Calculation](/Testing_Guidance.JPG?raw=true)

## Author
* **Subhash Chand** - [GitHub Link](https://github.com/subhash982)
