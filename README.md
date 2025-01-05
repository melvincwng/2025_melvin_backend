# Coin Changer Java/Dropwizard Web Service

This Java/Dropwizard web service calculates and returns the **minimum** number of coins needed to make up a target amount.

For example:
- Target amount = $7.03
- Coin denominator = [0.01, 0.5, 1, 5, 10]
- Output by web service: [0.01, 0.01, 0.01, 1, 1, 5]

## Tech stack
- Languages & Frameworks: **Java & Dropwizard** 
- Infrastructure (Containerization & Deployment): **Docker & AWS EC2**

## Frontend app
https://github.com/melvincwng/2025_melvin_frontend (**JavaScript/React.js UI**)

## Unit Tests
Done using **JUnit**, with a total of **7** unit tests cases found in **CoinChangeResourceTest.java**

## How to run the application locally on your computer (Setup Instructions)
1. Open up any terminal (e.g. Git Bash), change into a directory of your choice, and clone this repository by entering the following command: `git clone https://github.com/melvincwng/2025_melvin_backend`
2. Open the repository in an IDE of your choice (e.g. IntelliJ IDEA) and in the terminal, enter the following commands to install required dependencies and start the app locally:
    ```
    mvn clean install package
    java -jar target/2025_melvin_backend-1.0-SNAPSHOT.jar server config.yml 
    ```
3. The app will then run on http://localhost:8080/
4. Kindly follow the [Setup Instructions for the Frontend React.js Web App over here](https://github.com/melvincwng/2025_melvin_frontend/blob/master/README.md) if you need the GUI to fetch the necessary data from the backend, or if you need to run/develop/test the frontend app locally
5. Alternatively, you can do the API testing using an **Internet Browser** or **Postman** instead
![image](https://github.com/user-attachments/assets/49b17c1e-da39-4618-a468-997b68d2802d)
![image](https://github.com/user-attachments/assets/d96d8926-86cf-457b-8dcc-c447b24b36f1)


## How to build and run this application in a Docker Container?
1. **Important Prerequisite:**
   - Please ensure Docker Desktop/Docker Engine is already installed on your machine. If you need instructions on how to install Docker, you can refer to their documentation over [here](https://docs.docker.com/desktop/)
2. Open a terminal, and change directory to where you stored the Java/Dropwizard project
3. Alternatively, open the Java/Dropwizard project in IntelliJ IDEA, and then open the integrated terminal
4. In the terminal, enter this command: `docker build -t dropwizard-app-docker .`
5. The command above builds a Docker Image named `dropwizard-app-docker` on your computer. You can change the image name to something else if you prefer, but make sure to update the name consistently in any subsequent commands.
6. If you encounter the error below, it means your Docker Desktop/Docker Engine is not running on your computer. Turn it on to resolve the error:
   ```
   ERROR: error during connect: Head "http://%2F%2F.%2Fpipe%2FdockerDesktopLinuxEngine/_ping": open //./pipe/dockerDesktopLinuxEngine: The system cannot find the file specified.
   ```
7. After building the Docker Image, to run your app inside a Docker container, enter the following command:
   ```    
   docker run -p 8080:8080 dropwizard-app-docker server config.yml
   ```
8. To stop the Docker Container, just press `Ctrl + C` in your terminal

## OPTIONAL: My general approach on how I hosted the dockerized apps on the Cloud (For ownself future reference)
1. Push your Docker images onto Docker Hub or any other container registry
2. Setup your server or compute instance from AWS or other cloud providers
3. SSH/Connect into your server/compute instance via terminal
4. Install Docker in your newly setup cloud server/compute instance
5. Pull the Docker image into your server/compute instance
6. Once pulled, run the command `docker run -d -p 8080:8080 <NAME_OF_DOCKER_IMAGE> server config.yml` to deploy & run your Dropwizard backend application on the cloud. 
7. The `-d (detached flag)` ensures that the application keeps running on the cloud, even after you exit the terminal.
8. Approach is similar for hosting frontend apps, but change port number (e.g. to 3000:3000) and can remove `server config.yml` arguments (these arguments are specifically for the Dropwizard framework only).
9. We can add environmental variables in the `docker run` command if needed via the `-e tag`
   
## Live Demo:
Example URLs:
- http://34.235.114.16:8080/api/v1/healthCheck
- http://34.235.114.16:8080/api/v1/coinChange?target=7.03&coinDenominations=0.01&coinDenominations=0.5&coinDenominations=1&coinDenominations=5&coinDenominations=10
- **Note: for the /coinChange API**, you need to pass in a valid `target` query parameter (e.g. between 0 and 10,000 inclusive) and valid `coinDenominations` query parameter (e.g. [0.01, 0.05, 0.1, 0.2, 0.5, 1.0, 2.0, 5.0, 10.0, 50.0, 100.0, 1000.0]). If you want to add more coinDenominations, you can append additional denominations (e.g., `coinDenominations=0.2&coinDenominations=2`) to the URL (see example above)

![image](https://github.com/user-attachments/assets/755a72a0-8c1f-4365-81a3-f33351d4aca0)

## Useful References:
- https://medium.com/@ashdeepupadhyay/getting-started-with-dropwizard-ec37215094d3
- https://medium.com/@ashdeepupadhyay/dockerize-the-java-dropwizard-application-1110c9cf075a

