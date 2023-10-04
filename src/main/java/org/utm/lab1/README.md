# University Management System

This project is University Management System that allows managing faculties and students. The project requires Java 17 to run.

## Operations

- **Create New Faculty**: To create a new faculty, use the following command:
````
nf/<faculty name>/<faculty abbreviation>/<field>
````
- **Display Faculties**: To display all faculties, use the command:
````
df
````
- **Create New Student**: To create a new student, use the following command:
````
ns/<faculty abbreviation>/<first name>/<last name>/<email>/<date-of-birth (dd-MM-yyyy)>
````
- **Display Faculties of a Field**: To display all faculties of a specific field, use the command:
````
dff/<field>
````
- **Search Faculty for a Student**: To search which faculty a student belongs to, use the command:
````
ss/<email>
````
- **Batch Graduate Students**: To batch graduate students from a file named `batch_enrollment.txt`, use the command:
````
bes
````
- **Batch Graduate Students**: To batch graduate students from a file named `batch_enrollment.txt`, use the command:
````
bgs
````
- **Graduate Student**: To graduate a student, use the command:
````
gs/<email>
````
- **Display Enrolled Students**: To display enrolled students of a specific faculty, use the command:
````
de/<faculty abbreviation>
````
- **Display Graduated Students**: To display graduated students of a specific faculty, use the command:
````
dg/<faculty abbreviation>
````
- **Check Student Belonging to Faculty**: To check if a student belongs to a specific faculty, use the command:
````
bf/<faculty abbreviation>/<email>
````
## How to Run

1. Make sure you have Java 17 installed on your system
2. Clone this repository to your local machine
3. Open IDE to run the code