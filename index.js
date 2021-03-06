'use strict'
const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);
exports.sendNotification=functions.database.ref('/groupchat/{mm}/{chats}').onWrite(event=>{

	//const mm=event.params.chats;
	//const notification=event.params.notification;
	const group=event.params.mm;
	const data=event.data.val();
	const eventSnapshot=event.data;
	var messages=eventSnapshot.child("chat").val();
	console.log ('The new Message From:',group,messages);


	//Array Declaration
	var TIK=[];
	//Database Refernce
	var ref = admin.database().ref('/Device_Token/'+group);
		ref.on("child_added", function(snapshot, prevChildKey) {
		//Data
  	var newPost = snapshot.val();
		//Adding Data Into Array
		TIK.push(newPost.token);
	});
console.log("token",TIK);

//Notification
const payload={
	notification: {
		title : "New Message",
		 body : messages,
		 icon :"default",
		 click_action:"com.example.ibm.hermes_TARGET_NOTIFICATION"

	}
};

//return admin.messaging().sendToDevice("cUvHSeG4Ycc:APA91bFIDLmHCwOiVpey3i31ao2a0_cd9qEWsrukdze5rQbD2Y5GurrUQjd_EFgIu2n2_DS8iqtxIadi11hS9K6jEgZC_FFUAOoClGZEM2XvznEMHj7gdNpWoKxQ4WSNTcuti1a3PSjm",payload);

	//Message

return admin.messaging().sendToDevice(TIK,payload).then(response=>{

	console.log("This Was an Message");

});
//  console.log("List OF device Token",TIK);

	//Cheacking
	/*const Device_T=admin.database().ref(`/Device_Token/${group}/token`).once('value');
	return Device_T.then(fromDevice_tResult=>{
		const abs=fromDevice_tResult.val();
		console.console.log("Token Id", abs);
	});*/

	//Working


	/*const device_token=admin.database().ref(`/Device_Token/${group}/token`).once('value');

	return device_token.then(result =>{

		var token_id=result.val();
    var registrationTokens =["cUvHSeG4Ycc:APA91bFIDLmHCwOiVpey3i31ao2a0_cd9qEWsrukdze5rQbD2Y5GurrUQjd_EFgIu2n2_DS8iqtxIadi11hS9K6jEgZC_FFUAOoClGZEM2XvznEMHj7gdNpWoKxQ4WSNTcuti1a3PSjm",
														"fB814C9rhRg:APA91bF6QimUgstj_jWsTrxMamFqBCA_zdR8xYe0ciqbERmefcnFMqNbFriQWv_HrKe1m9x5lLzxSTMnpg_behT7795T6IK8ookdquBC021LIjVL9fk6veFATmCZpRVxNGgSxFvSUb3G"];
		const payload={
			notification: {
				title : "New Message",
				 body : messages,
				 icon :"default",
				 click_action:"com.example.ibm.hermes_TARGET_NOTIFICATION"

			}
		};

		//return admin.messaging().sendToDevice("cUvHSeG4Ycc:APA91bFIDLmHCwOiVpey3i31ao2a0_cd9qEWsrukdze5rQbD2Y5GurrUQjd_EFgIu2n2_DS8iqtxIadi11hS9K6jEgZC_FFUAOoClGZEM2XvznEMHj7gdNpWoKxQ4WSNTcuti1a3PSjm",payload);


		return admin.messaging().sendToDevice(registrationTokens,payload).then(response=>{

			console.log("This Was an Message",registrationTokens);

		});

	});*/
});
