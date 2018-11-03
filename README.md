Project name: MasterTracker
Application specification: A mobile app that extract data  ( for this project, data for cycling workout) and display data graphically.


OUR GOALS:

1. EXTRACT .TCX FILE FROM ENDOMONDO
2. PARSE TCX INTO XML
3. DISPLAY CONTENTS

Group Project - Deliverable 1.1

The name of our mobile app is Master Tracker.
Application specification: 
An android app that extract data  ( for this project, data for cycling workout) and display data graphically. We develop the application using android studio.

Data source:
The data source for our mobile application is Endomondo database . we created an account on endomondo and logged data for cycling(sport) workout.
The data we create for each cycling workout is date, distance (how many km/mile we did cycling), duration of workout and average rate of heart beat. We will display these data in form of table and graph in first phase of the project.
We can get data of our workouts one by one from endomondo website in .TCX and .GPX format.
#####Note: Login Details for Endomondo: bobendo354@gmail.com Password: concordia354


How we obtain data:
To connect our application to endomondo in order to get cycling workout data, we use an unofficial API called Endo2Java. This unofficial API is a open source API can be found on GitHub with the address (https://github.com/MoOmEeN/endo2java ) and we use this API as a library in our application that give us the functionality of logging into an account already created on endomondo and extract extract the data we need.
Using this API, we can extract basic data of all the workouts, data of single workout (for this project we only focus on cycling sport) and data of the account we created.

Data schema of the extracted data:
We extract data from endomondo in .TCX format which is Training Center XML file. 
Then we parse the .TCX into .XML and read the file, display data and show it graphically. 

 


 

