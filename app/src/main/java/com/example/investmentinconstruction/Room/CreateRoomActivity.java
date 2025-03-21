package com.example.investmentinconstruction.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.investmentinconstruction.ConnectRealtimeDatabase;
import com.example.investmentinconstruction.LoadingActivity;
import com.example.investmentinconstruction.LogicClasses.Advertisement;
import com.example.investmentinconstruction.LogicClasses.House;
import com.example.investmentinconstruction.LogicClasses.Room;
import com.example.investmentinconstruction.LogicClasses.Shop;
import com.example.investmentinconstruction.LogicClasses.User;
import com.example.investmentinconstruction.MainBottomNavigation;
import com.example.investmentinconstruction.databinding.ActivityCreateRoomBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CreateRoomActivity extends AppCompatActivity {

    private ActivityCreateRoomBinding binding_createRoom;
    private FirebaseAuth firebaseAuth;
    private Integer roomCode;
    private String[] masNumberPeople = {"2", "3", "4", "5", "6"};
    private String[] masDistrict = {"Arbat", "Gagarin", "Sokolniki"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding_createRoom = ActivityCreateRoomBinding.inflate(getLayoutInflater());
        setContentView(binding_createRoom.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();

        initSpinner();

        roomCode = 10000 + (int) (Math.random() * 90000);
        binding_createRoom.textViewRomeCode.setText(String.valueOf(roomCode));
    }

    public void createRoom(View view) {
        Integer numberPeople = Integer.valueOf(binding_createRoom.spinnerNumberPeople.getSelectedItem().toString());
        String nameDistrict = binding_createRoom.spinnerDistrict.getSelectedItem().toString();

        List<User> userList = new ArrayList<>();
        userList.add(new User(firebaseAuth.getCurrentUser().getUid(), nameDistrict, 0, null, null, null));
        Room room = new Room(roomCode, numberPeople, 1, 1, userList);

        ConnectRealtimeDatabase.getInstance(this).createRoom(room);

        Intent intent = new Intent(CreateRoomActivity.this, LoadingActivity.class);
        intent.putExtra("activity", "MainActivity");
        intent.putExtra("roomCode", roomCode);
        intent.putExtra(User.class.getSimpleName(), userList.get(0));
        startActivity(intent);
    }

    private void initSpinner() {
        ArrayAdapter<String> adapter_numberPeople = new ArrayAdapter(this, android.R.layout.simple_spinner_item, masNumberPeople);
        adapter_numberPeople.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding_createRoom.spinnerNumberPeople.setAdapter(adapter_numberPeople);

        ArrayAdapter<String> adapter_District = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, masDistrict);
        adapter_District.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding_createRoom.spinnerDistrict.setAdapter(adapter_District);
    }

    public void goToHome(View view) {
        Intent intent = new Intent(CreateRoomActivity.this, MainBottomNavigation.class);
        startActivity(intent);
    }

}