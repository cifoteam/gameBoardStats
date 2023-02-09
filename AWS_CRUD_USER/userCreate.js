const AWS = require('aws-sdk');
const ddb = new AWS.DynamoDB.DocumentClient();

exports.handler =  (event, context, callback) => {
    
    // create api ApiGatewayManagementApi to make a POST from lambda to engine by connectionid
    let apiGatewayManagementApi = new AWS.ApiGatewayManagementApi({ 
        apiVersion: '2018-11-29',    
        endpoint: 'https://xxxxxxx.execute-api.eu-central-1.amazonaws.com/test/',
        region: 'eu-central-1'
    });
    
    console.log(event.body);
    //declaration of const to the body of JSON
    //console.log(event);
    
    
    var bodyJSON = JSON.parse(event.body);
    

    /*
    my input
    "body": "{ \"userId\": \"usuario1\", \"name\": \"David\",  \"lastName\": \"Marchan\",
    \"userName\": \"usuario1\", \"password\": \"1234\", \"email\": \"1234@gmail.com\", \"avatar\": \"1234\",
    \"city\": \"Barcelona\", \"language\": \"ESP\",  \"gameCollection\": \"D&D-5e\"  }"
    
    
    */
    
    createUser(bodyJSON).then((response) => {
        callback(null,
        {
            statusCode: 200,
            body: JSON.stringify(response)
        }
        );
    });
}


function createUser(jsonRequest){
    
    var keys = Object.keys(jsonRequest);
    
    if(!(keys.includes("userId")) || !(keys.includes("userName")) || !(keys.includes("password")) || !(keys.includes("email")) ){
        
        return Promise.resolve("Can't create the user. Verify that you set the user ID, username, password & email");
    }
    
    var userToCreate = {};
    
    for ( var idx in keys){
        if(keys[idx] == "action")
            continue
        userToCreate[keys[idx]] = jsonRequest[keys[idx]]
    }
    
    var user = ddb.put({
        TableName : 'USERS_BGG',
        Item : userToCreate
    }).promise();
    
    return user;
    
}
