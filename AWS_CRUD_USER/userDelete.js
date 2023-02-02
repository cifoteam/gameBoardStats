const AWS = require('aws-sdk');
const ddb = new AWS.DynamoDB.DocumentClient();
const TABLE_NAME = "USERS_BGG";

exports.handler =  (event, context, callback) => {
    console.log(event);
    var bodyJSON = JSON.parse(event.body);
    
    var userId = bodyJSON.userId || null;
    //let timestampEpoch = new Date().getTime();
    //console.log("timestampEpoch", timestampEpoch);
    console.log("userId", userId);

    deleteTodo(userId).then(() => {
        callback(
            null,
            {
                statusCode: 200,
                body: JSON.stringify({
                    "deleted": true,
                    "userId": userId                    
                })
            }
        );
    });
    function deleteTodo(userid) {
        return ddb.delete({
            TableName: TABLE_NAME,
            Key: { userId : userid},
        }).promise();
    }
};