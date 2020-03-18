#Task description

The task is to design and implement a backend service to control an appliance such as a wash machine or an oven. The API should be REST based and the state of the appliance should be persisted to any form of persistent storage. There is no need for any frontend but we expect there to be a README.md file with build directions and examples of how to invoke the REST API (e.g. curl).

The project should be implemented using Java or Node.js. Feel free to use any 3rd party library that you are comfortable with. Unit tests are expected and the assignment will be assessed based on good programming practices and design.

Please use GitHub or Bitbucket to publish your source code.

#Installation

Execut "mvn spring-boot:run" command in project root folder

#REST api description
```
curl localhost:8080/start?name={name} - start washing program with given name
curl localhost:8080/pause - pause program
curl localhost:8080/continue - continue paused program
curl localhost:8080/stop - forcibly finish program
curl localhost:8080/getState - get current state
curl localhost:8080/getAll - get all available programs
curl localhost:8080/getByName?name={name} - get program with given name
curl localhost:8080/getByMaterial?material={material} - get all programs for given material
curl -H "Content-Type: application/json" 
-X POST -d 
{
\"name\":\"{name}\", 
\"material\":\"{material}\", 
\"temperature\":{temperature}, 
\"duration\":{duration}, 
\"spinning_speed\":{spinning_speed}
} 
localhost:8080/save - save new program
curl -X DELETE localhost:8080/delete?name={name} - delete program with given name
curl -H "Content-Type: application/json" 
-X POST -d 
{
\"name\":\"{name}\", 
\"material\":\"{material}\", 
\"temperature\":{temperature}, 
\"duration\":{duration}, 
\"spinning_speed\":{spinning_speed}
} 
localhost:8080/update?name={name} - update program with given parameters
```
