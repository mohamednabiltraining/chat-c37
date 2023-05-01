package com.route.chatc37.database

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*
import com.route.chatc37.database.models.Message
import com.route.chatc37.database.models.Room
import com.route.chatc37.database.models.User

class FireStoreUtils {
    val usersCollectionName = "users"
    val roomsCollectionName = "rooms"

    fun getUsersCollection():CollectionReference{
        val dataBase = FirebaseFirestore.getInstance();
        return dataBase.collection(usersCollectionName)

    }
    fun getRoomsCollection():CollectionReference{
        val dataBase = FirebaseFirestore.getInstance();
        return dataBase.collection(roomsCollectionName)

    }
    fun insertUserToDatabase(user:User):Task<Void>{
        val docRef =getUsersCollection()
            .document(user.id!!)
        return docRef.set(user)
    }
    fun getUserFromDatabase(uid:String):Task<DocumentSnapshot>{
       val docRef =  getUsersCollection()
            .document(uid)
        return docRef.get()
    }

    fun insertRoom(room: Room):Task<Void> {

        //getRoomsCollection().add(room)

        var docRef =  getRoomsCollection()
            .document()
        room.id =docRef.id
        return docRef.set(room)
    }
    fun getAllRooms():Task<QuerySnapshot>{
       return getRoomsCollection()
            .get()
    }
    fun sendMessage(message:Message):Task<Void>{
        val roomRef = getRoomsCollection()
            .document(message.roomId ?:"")
       val messages =
           roomRef.collection("messages")
        val messageDoc = messages.document()
        message.id = messageDoc.id
        return messageDoc.set(message)

    }
    fun getRoomMessages(roomId:String):Query{
        return getRoomsCollection()
            .document(roomId)
            .collection("messages")
            .orderBy("dateTime")

    }
}