Hello :)

My name is Vasil and below I am going to summarize my understanding of the final task and my solution.

My understanding of the task is as follows: my application should read a CSV file with number of references. Each reference has 4 fields: employee id, project id and two of them are dates for start and end of the period in which the employee was (or is still) working on the project.

The application should calculate the maximum period for whichever pair of employees working together on one or more projects.

The application should return the following calculated information: the two employee ids, the total number of days working together and the individual contribution of each project. Also for reading the CSV file no external library should be used and the date pointing out the end of the project period can be NULL meaning the employee is still working on this project and for the calculation NULL can be replaced with the current date.

There is also another requirement that date fields should support more than one formats or preferably all formats. As I am not sure what "extra points will be given if all date formats are supported" I will limit my application to only 6 formats although more can be added.

=========================================

The application have the following endpoints:

/import POST

/backup PUT

/findProjectIds GET

/calculateAll GET

/calculateMax GET

/deleteAll DELETE

/add POST

/delete/{id} DELETE

/get/{id} GET

/update/{id} PUT

==================================================

The application can be started with /import command that will upload the correct references from EmployeeReferenceList.csv file in resources folder. However, one should update the path to the file located in the utility package in the file Constants. Also the path to backup file can be updated. application.properties should be updated as well with the corresponding database settings.

Once /import is successful the data is uploaded to database and one can calculate the maximum shared days or to calculate all the shared days for any pair of employees.

Also one can add new or update existing reference in the database or get a reference or delete one. Also deletion of all references in the database is possible but it is recommended to backup the base before that.

There are a few validations but mainly only unique references can be saved to database and take part in the calculations. If one tries to import a non-unique reference from the CSV file to database it will be ignored.

===================================================

The above summary does not have the ambition to entirely describe the application but I will be more than happy to give you more details once you decide to invite me for an interview :)

Kind regards,
Vasil 

