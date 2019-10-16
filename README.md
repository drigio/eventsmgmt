# Event Registration

## Installation
Change MySQL username password in [application.properties](./src/main/resources/application.properties)

### SQL Insert Queries.
These queries represent a very simple database for a quickstart.
You can add your own hashed password using [Online Bcrypt](https://www.browserling.com/tools/bcrypt) tool (use only 10 rounds algorithm). Make sure you add **{bcrypt}** before inserting passwords into database. 

**Warning** : Only bcrypt passwords that begin with *"2a"* and not **"2y"** or **"2b"** will work. This is an issue with Spring's *BCryptPasswordEncoder class*. 


```sql
INSERT INTO user VALUES(1,"{bcrypt}$2a$10$8UPsstcsh57sMwIfiGE9m.GG2Vdwh8Tk7IrvmB1RO.1355ifgn3eW", "admin");

INSERT INTO role VALUES(1, "ADMIN");

INSERT INTO user_role VALUES(1,1);
```
Copy Paste and execute the above queries into your mysql workbench / mysql cli application **only after running Spring Boot application atleast once**.

Use [Postman](https://www.getpostman.com
) for sending beautiful requests easily. (For Testing purposes only)


## Usage

### Authentication

#### Sign in and receive an access token
- Request URL :- http://localhost:8080/api/auth/signin
- Request Method :- POST
- Request Body 
    ```JSON
    {
        "username" : "",
        "password" : ""
    }
    ```
- Response 
    ```JSON
    {
        "accessToken": "",
        "tokenType": "Bearer"
    }
    ```

    Use this access token whenever you need prior authentication. 
    Whenever messages like *Full authentication required to access this resource* is found send the the *accessToken* in header using the key *Authorization* and value as "Bearer \<accessToken>\"

### Admin

#### Add Admin
- Request URL :- http://localhost:8080/api/admin/admins/add
- Request Method :- POST
- Request Body (username password email Mandatory)
    ```JSON
    {
        "username" : "",
        "password" : "",
        "email" : "",
        "firstName" : "",
        "middleName" : "",
        "lastName" : "",
        "mobile" : ""
    }
    ```
- Response 
    ```JSON
    {
        "status" : "",
        "message" : ""
    }
    ```

#### Get Admin By ID
- Request URL :- http://localhost:8080/api/admin/admins/{id}
- Request Method :- GET
- Response Example
    ```JSON
    {
        "id": 3,
        "username": "admin",
        "password": "{bcrypt}$2a$10$mKnuXpp4s3gVDlyr23orCOEPVQN.InirGCImShZzLM0Q6/RL8.DNm",
        "email": "hello@hello.com",
        "firstName": "Admin",
        "middleName": "Admin",
        "lastName": "Admin",
        "mobile": "8888182818",
        "createdAt": "2019-10-05T20:30:23.000+0000",
        "lastUpdated": "2019-10-05T20:30:23.000+0000",
        "role": [
            {
                "id": 1,
                "name": "ADMIN",
                "description": "Admin Role",
                "createdAt": "2019-10-05T20:16:42.000+0000",
                "lastUpdated": "2019-10-05T20:16:42.000+0000"
            }
        ]
    }
    ```
#### Get Admin By Username
- Request URL :- http://localhost:8080/api/admin/admins/username/{username}
- Request Method :- GET

#### Get All Admins
- Request URL :- http://localhost:8080/api/admin/admins/
- Request Method :- GET

#### Delete Admin By ID
- Request URL :- http://localhost:8080/api/admin/admins/{id}
- Request Method :- DELETE
- Response 
    ```JSON
    {
        "status" : "",
        "message" : ""
    }
    ```
#### Update Admin By ID
- Request URL :- http://localhost:8080/api/admin/admins/{id}
- Request Method :- PUT
- Request Body (All Optional)
    ```JSON
    {
        "username" : "",
        "password" : "",
        "email" : "",
        "firstName" : "",
        "middleName" : "",
        "lastName" : "",
        "mobile" : ""
    }
    ```

#### Add Event

- Request URL :- http://localhost:8080/api/admin/events/add
- Request Method :- POST
- Authentication :- **Required**
- Request Body 
    ```JSON
    {
        "name" : "",
        "desc" : ""
    }
    ```
- Response 
    ```JSON
    {
        "status" : "",
        "message" : ""
    }
    ```

#### Get All Events
- Request URL :- http://localhost:8080/api/admin/events
- Request Method :- GET
- Response 
    ```JSON
    [
        {
            "id": ,
            "name": "",
            "desc": "",
            "createdAt": "2019-10-16T02:07:35.000+0000",
            "createdBy": {},
            "updatedAt": "2019-10-16T02:07:35.000+0000",
            "updatedBy": {}
        },
        {
            "id": ,
            "name": "",
            "desc": "",
            "createdAt": "2019-10-17T01:18:02.000+0000",
            "createdBy": {},
            "updatedAt": "2019-10-17T01:18:02.000+0000",
            "updatedBy": {}
        }
    ]
    ```

#### Get Event By Id
- Request URL :- http://localhost:8080/api/admin/events/{id}
- Request Method :- GET
- Response 
    ```JSON
    {
        "id": ,
        "name": "",
        "desc": "",
        "createdAt": "2019-10-16T02:07:35.000+0000",
        "createdBy": {},
        "updatedAt": "2019-10-16T02:07:35.000+0000",
        "updatedBy": {}
    }
    ```

#### Delete Event By Id
- Request URL :- http://localhost:8080/api/admin/events/{id}
- Request Method :- DELETE
- Response 
    ```JSON
    {
        "status" : "",
        "message" : ""
    }
    ```

#### Add Coordinator

- Request URL :- http://localhost:8080/api/admin/coordinators/add
- Request Method :- POST
- Request Body (username password email Mandatory)
    ```JSON
    {
        "username" : "",
        "password" : "",
        "email" : "",
        "firstName" : "",
        "middleName" : "",
        "lastName" : "",
        "mobile" : ""
    }
    ```
- Response 
    ```JSON
    {
        "status" : "",
        "message" : ""
    }
    ```

#### Get All Coordinators
- Request URL :- http://localhost:8080/api/admin/coordinators
- Request Method :- GET
- Response 
    ```JSON
    [
        {
            "id": ,
            "username": "",
            "password": "",
            "email": "",
            "firstName": "",
            "middleName": "",
            "lastName": "",
            "mobile": "",
            "createdAt": "2019-10-15T20:27:44.000+0000",
            "lastUpdated": "2019-10-15T20:27:44.000+0000",
            "role": [
                {
                    "id": ,
                    "name": "COORDINATOR",
                    "description": "Coordinator Role",
                    "createdAt": "2019-10-15T20:22:26.000+0000",
                    "lastUpdated": "2019-10-15T20:22:26.000+0000"
                }
            ],
            "event": {}
        },
        {
            "id": ,
            "username": "",
            "password": "",
            "email": "",
            "firstName": "",
            "middleName": "",
            "lastName": "",
            "mobile": "",
            "createdAt": "2019-10-15T20:27:44.000+0000",
            "lastUpdated": "2019-10-15T20:27:44.000+0000",
            "role": [
                {
                    "id": ,
                    "name": "COORDINATOR",
                    "description": "Coordinator Role",
                    "createdAt": "2019-10-15T20:22:26.000+0000",
                    "lastUpdated": "2019-10-15T20:22:26.000+0000"
                }
            ],
            "event": {}
        }
    ]
    ```

#### Get Coordinators By Id
- Request URL :- http://localhost:8080/api/admin/coordinators/{id}
- Request Method :- GET
- Response 
    ```JSON
    {
        "id": ,
        "username": "",
        "password": "",
        "email": "",
        "firstName": "",
        "middleName": "",
        "lastName": "",
        "mobile": "",
        "createdAt": "2019-10-15T20:27:44.000+0000",
        "lastUpdated": "2019-10-15T20:27:44.000+0000",
        "role": [
            {
                "id": ,
                "name": "COORDINATOR",
                "description": "Coordinator Role",
                "createdAt": "2019-10-15T20:22:26.000+0000",
                "lastUpdated": "2019-10-15T20:22:26.000+0000"
            }
        ],
        "event": {}
    }
    ```
#### Add or Update Event for Coordinator
- Request URL :- http://localhost:8080/api/admin/coordinators/event
- Request Method :- POST
- Request Body 
    ```JSON
    {
        "coordinatorId" : "",
        "eventId" : ""
    }
    ```
- Response 
    ```JSON
    {
        "status" : "",
        "message" : ""
    }
    ```

#### Delete Coordinators By Id
- Request URL :- http://localhost:8080/api/admin/coordinators/{id}
- Request Method :- DELETE
- Response
     ```JSON
    {
        "status" : "",
        "message" : ""
    }
    ```
### For Coordinator

#### Add Registrar

- Request URL :- http://localhost:8080/api/coordinator/registrars/add
- Request Method :- POST
- Request Body (username password email Mandatory)
    ```JSON
    {
        "username" : "",
        "password" : "",
        "email" : "",
        "firstName" : "",
        "middleName" : "",
        "lastName" : "",
        "mobile" : ""
    }
    ```
- Response 
    ```JSON
    {
        "status" : "",
        "message" : ""
    }
    ```

#### Get All Registrars
- Request URL :- http://localhost:8080/api/coordinators/registrars
- Request Method :- GET
- Response 
  ```JSON
    [
        {
            "id": ,
            "username": "",
            "password": "",
            "email": "",
            "firstName": "",
            "middleName": "",
            "lastName": "",
            "mobile": "",
            "createdAt": "2019-10-16T18:03:15.000+0000",
            "lastUpdated": "2019-10-16T18:03:15.000+0000",
            "role": [
                {
                    "id": ,
                    "name": "REGISTRAR",
                    "description": "Registrar Role",
                    "createdAt": "2019-10-16T15:21:46.000+0000",
                    "lastUpdated": "2019-10-16T15:21:46.000+0000"
                }
            ],
            "event": null
        }
    ]
    ```
#### Get Registrar By Id
- Request URL :- http://localhost:8080/api/coordinator/registrar/{id}
- Request Method :- GET
- Response 
    ```JSON
    {
        "id": ,
        "username": "",
        "password": "",
        "email": "",
        "firstName": "",
        "middleName": "",
        "lastName": "",
        "mobile": "",
        "createdAt": "2019-10-16T18:03:15.000+0000",
        "lastUpdated": "2019-10-16T18:03:15.000+0000",
        "role": [
            {
                "id": ,
                "name": "REGISTRAR",
                "description": "Registrar Role",
                "createdAt": "2019-10-16T15:21:46.000+0000",
                "lastUpdated": "2019-10-16T15:21:46.000+0000"
            }
        ],
        "event": null
    }
    ```

#### Delete Registrar By Id
- Request URL :- http://localhost:8080/api/coordinator/registrars/{id}
- Request Method :- DELETE
- Response 
    ```JSON
    {
        "status" : "",
        "message" : ""
    }
    ```

### For Registrars

#### Add Participant

- Request URL :- http://localhost:8080/api/registrar/participants/add
- Request Method :- POST
- Request Body
    ```JSON
    {
        "firstName" : "",
        "middleName" : "",
        "lastName" : "",
        "email" : "",
        "mobile" : ""
    }
    ```
- Response 
    ```JSON
    {
        "status" : "",
        "message" : ""
    }
    ```

#### Get All Participants
- Request URL :- http://localhost:8080/api/registrar/participants/all
- Request Method :- GET
- Response 
  ```JSON
    [
        {
            "id": ,
            "firstName": "",
            "middleName": "",
            "lastName": "",
            "email": "",
            "mobile": "",
            "createdAt": "2019-10-16T18:09:15.000+0000",
            "lastUpdated": "2019-10-16T18:09:15.000+0000",
            "createdBy": {},
            "updatedBy": {},
            "events": [
                {}, 
                {}
            ]
        },
        {}
    ]
    ```
#### Get All Participants By Event Id
- Request URL :- http://localhost:8080/api/registrar/participants/event/{eventid}
- Request Method :- GET
- Response 
  ```JSON
    [
        {
            "id": ,
            "firstName": "",
            "middleName": "",
            "lastName": "",
            "email": "",
            "mobile": "",
            "createdAt": "2019-10-16T18:09:15.000+0000",
            "lastUpdated": "2019-10-16T18:09:15.000+0000",
            "createdBy": {},
            "updatedBy": {},
            "events": [
                {}, 
                {}
            ]
        },
        {}
    ]
    ```

#### Attach or Event to Participant
- Request URL :- http://localhost:8080/api/registrar/participants/event
- Request Method :- POST
- Request Body
    ```JSON
    {
        "participantId" : "",
        "eventId" : ""
    }
    ```
- Response 
    ```JSON
    {
        "status" : "",
        "message" : ""
    }
    ```
    As soon as this request returns a success, an email is sent to the participant on their respective registered Email ID, regarding their successful registration of event participation.