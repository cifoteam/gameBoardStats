const AWS = require('aws-sdk');
const ddb = new AWS.DynamoDB.DocumentClient();

exports.handler = async (event) => {
    console.log(event);
    
        
    var bodyJSON = JSON.parse(event.body);
    
    var userId = bodyJSON.userId || null;
    var name = bodyJSON.name|| null;
    var lastName = bodyJSON.lastName || null;
    var userName = bodyJSON.userName || null;
    var password = bodyJSON.password || null;
    var email = bodyJSON.email || null ;
    var avatar = bodyJSON.avatar || null ;
    var city = bodyJSON.city || null ;
    var language = bodyJSON.language || null ;
    var gameCollection = bodyJSON.gameCollection || null ;
    /*
    console.log("userId", userId );
    console.log("creationTimestamp", creationTimestamp );
    console.log("taskStatus", taskStatus);
    console.log("assignee", assignee);
    console.log("taskDescription", taskDescription);
    */
    const response = {
        "userId" : userId,
        "name" : name,
        "lastName" : lastName,
        "userName" : userName,
        "password" : password,
        "email" : email,
        "avatar" : avatar,
        "city" : city,
        "language" : language,
        "gameCollection" : gameCollection

        };

    try {
    await updateTask(userId, name, lastName, userName, password, email, avatar, city, language, gameCollection);
        return {statusCode: 200, 
                body: JSON.stringify(response)};
    } catch (err) {
        return { error: err };
    }
};

async function updateTask(userId, name, lastName, userName, password, email, avatar, city, language, gameCollection){
  try {
    await ddb.update({
        TableName: 'USERS_BGG', 
        Key: { userId : userId},
        UpdateExpression: 'SET #name = :name, #lastName = :lastName, #userName = :userName, #password = :password, #email = :email, #avatar = :avatar, #city = :city, #language = :language, #gameCollection = :gameCollection',
        ExpressionAttributeNames: {'#name' : 'name', '#lastName' : 'lastName', '#userName' : 'userName', '#password' : 'password', '#email' : 'email', '#avatar' : 'avatar', '#city' : 'city', '#language' : 'language', '#gameCollection' : 'gameCollection'},
        ExpressionAttributeValues: {':name': name, ':lastName': lastName, ':userName' : userName, ':password':password, ':email': email, ':avatar' : avatar, ':city': city, ':language': language, ':gameCollection': gameCollection},
        ReturnValues:"UPDATED_NEW"  
    }).promise();
  } catch (err) {
    return err;
  }
}