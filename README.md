# Number Generator Rest API

### Running the Application

Navigate to the root of the project via command line and execute the command to run.

```txt
$ mvn spring-boot:run
```

### API Documentation

```txt
POST- /api/generate
```

- http://localhost:8081/numbergenerator/api/generate
```Request json
{
    "goal": 10,
    "step": 2
}
```

```Response json
{
    "task": "281cbefb-b1ab-48c7-a53f-8c54f6a0833c"
}
```

```txt
GET- /api/bulk/tasks/{taskId}
```

- http://localhost:8081/numbergenerator/api/bulk/tasks/281cbefb-b1ab-48c7-a53f-8c54f6a0833c

```Response json
{
    "result": "SUCCESS"
}
```

```txt
GET- /api/tasks/{taskId}/?action=get_numlist
```

- http://localhost:8081/numbergenerator/api/tasks/281cbefb-b1ab-48c7-a53f-8c54f6a0833c/?action=get_numlist

```Response json
{
    "result": "10,8,6,4,2,0"
}
```

```txt
POST- /api/bulkGenerate
```

- http://localhost:8081/numbergenerator/api/bulkGenerate
```Request json
[
    {
        "goal": 10,
        "step": 2
    },
    {
        "goal": 100,
        "step": 3
    }
]
```

```Response json
{
    "task": "8f4be825-cb84-48a9-879e-a3724d484657"
}
```

```txt
GET- /api/bulk/tasks/{taskId}/?action=get_numlist
```

- http://localhost:8081/numbergenerator/api/bulk/tasks/8f4be825-cb84-48a9-879e-a3724d484657/?action=get_numlist

```Response json
{
    "results": [
        "10,8,6,4,2,0",
        "100,97,94,91,88,85,82,79,76,73,70,67,64,61,58,55,52,49,46,43,40,37,34,31,28,25,22,19,16,13,10,7,4,1"
    ]
}
```

### Create docker image
* We are using dockerfile-maven-plugin for this. The below command will create a docker image.
```txt
$ mvn clean package
```

* Run the image locally using below command:
```txt
$ docker run -p 8081:8081 -d  hasandocker53/number-generator:0.0.1-SNAPSHOT
```

* Push the image to docker-hub
```txt
$ docker login
$ docker push hasandocker53/number-generator:0.0.1-SNAPSHOT
```

### Deploy to GKE
* First setup a free tier account in Google Cloud.
* Set up a project and create a Workload with the name of the project.
* Deploy application to kubernetes cluster by pulling image from docker hub
```txt
$ kubectl create deployment number-generator --image=hasandocker53/number-generator:0.0.1-SNAPSHOT
```