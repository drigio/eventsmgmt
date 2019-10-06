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
        "message" : "",
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
- Request URL :- http://localhost:8080/api/admin/admins/{username}
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
        "message" : "",
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

