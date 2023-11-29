package edu.northeastern.g15finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FriendsActivity extends AppCompatActivity {

    RecyclerView allFriendsRecyclerView;

    EditText friend_un;

    User currentUser;

    String currentUserName;

    List<User> friends;

    FriendsRVAdapter friendsRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        allFriendsRecyclerView = findViewById(R.id.friends_recycler_view);
        friend_un = findViewById(R.id.friend_un);

        SharedPreferences sharedPref = getSharedPreferences("userdata", Context.MODE_PRIVATE);
        currentUserName = sharedPref.getString("currentUserName", null);

        friends = new ArrayList<>();
        getUser();

        friendsRVAdapter = new FriendsRVAdapter(friends, this);

        allFriendsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        allFriendsRecyclerView.setAdapter(friendsRVAdapter);

    }


    private void getUser(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(currentUserName);


        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    User user = document.toObject(User.class);
                    currentUser = user;
                    getFriends();
                } else {
                    Log.i("FUUUCK", "USER DOESNT EXIST");
                }
            } else {
                Log.i("FUUUCK", "get failed with ", task.getException());
            }
        });
    }

    private void getFriends() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference friendsRef = db.collection("users");
        Query query = friendsRef.whereIn("userName", currentUser.getFriendsIds());

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot document = task.getResult();
                for (QueryDocumentSnapshot snap : document) {
                    User userTemp = snap.toObject(User.class);
                    Log.i("FUUUCK FRENS", userTemp.getUserName());
                    friends.add(userTemp);
                }
                Log.i("FUUUCK FRENS", ""+friends.size());
                allFriendsRecyclerView.getAdapter().notifyDataSetChanged();
            } else {
                Log.i("FUUUCK", "get failed with ", task.getException());
            }
        });

    }


    public void addFriendClick(View view) {
        Log.i("FUUUCK", "HERE1");
        String friend_username = friend_un.getText().toString().trim();
        if(friend_username == ""){
            return;
        }
        Log.i("FUUUCK", "HERE2 " + friend_username);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(currentUserName);

        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    currentUser.getFriendsIds().add(friend_username);
                    FirebaseFirestore.getInstance().collection("users")
                            .document(currentUser.getUserName())
                            .set(currentUser);
                    getFriends();
                } else {
                    Log.i("FUUUCK", "USER DOESNT EXIST");
                }
            } else {
                Log.i("FUUUCK", "get failed with ", task.getException());
            }
        });
    }
}