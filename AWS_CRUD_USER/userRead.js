const AWS = require('aws-sdk');
const docClient = new AWS.DynamoDB.DocumentClient();

// Constants
const TABLE_NAME = "USERS_BGG";
const PARTITION_KEY = "userId";

// Scan function
async function listItems(){
  const params = {
    TableName : TABLE_NAME
  };
  try {
    const data = await docClient.scan(params).promise()
    console.log(data);
    return data
  } catch (err) {
    return err
  }
}

// Query function
async function listUserIDItems(userid){
  const queryParams = {
    TableName: TABLE_NAME,
    //IndexName: 'userId',
    KeyConditionExpression: "#pk = :pk",
    ExpressionAttributeNames: {
      "#pk": PARTITION_KEY
    },
    ExpressionAttributeValues: {
      ":pk": userid
    }
  };
  
  try {
    const data = await docClient.query(queryParams).promise();
    console.log(data);
    return data
  } catch (err) {
    return err
  }
}


// API call handler
exports.handler = async (event, context) => {
  const bodyJSON = JSON.parse(event.body);
  var userid = null;
  if (bodyJSON.userId) {
    userid = bodyJSON.userId;
  }
  console.log("userId: " + userid);
  try {
    var data = {};
    if (userid){
      console.log("Launching query...");
      data = await listUserIDItems(userid);
    }
    else{
      console.log("Launching scan...");
      data = await listItems();
    }
    // Final return must convert JSON "data" to string and assign it to the "body" key, then return a JSON object with it so can be sent back to the Frontend client
    return { body: JSON.stringify(data) }
  } catch (err) {
    return { error: err }
  }
}