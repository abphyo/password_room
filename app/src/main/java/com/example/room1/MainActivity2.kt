package com.example.room1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.room1.adapters.RecyclerAdapter
import com.example.room1.room.AppDatabase
import com.example.room1.room.Dao
import com.example.room1.room.Data
import com.example.room1.room.RoomApplication
import com.example.room1.room.ViewModelActivity2
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity2 : AppCompatActivity() {
    private lateinit var add: FloatingActionButton
    private lateinit var editText1: EditText
    private lateinit var editText2: EditText
    private lateinit var editText3: EditText
    private lateinit var appDatabase: AppDatabase
    private lateinit var dao: Dao
    private lateinit var viewModel: ViewModelActivity2
    private var dataList = mutableListOf<Data?>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        add = findViewById(R.id.floatingActionButton)
        appDatabase = (application as RoomApplication).database
        dao = appDatabase.getDao()
        viewModel = ViewModelActivity2(dao)
        val key = intent.getStringExtra("key")
        Log.d("checkingKey2", "onCreate: $key")
        key?.let {
            viewModel.memorizeKey(it)
            viewModel.getDataList(it)
        }
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        Log.d("checkingDataList", "onCreate: $dataList")
        recyclerView.adapter = RecyclerAdapter(dataList)
        add.setOnClickListener {
            val contentView = LayoutInflater.from(this).inflate(R.layout.add_dialog, null, false)
            AlertDialog.Builder(this)
                .setTitle("New password")
                .setView(contentView)
                .setPositiveButton("ok") {
                    _, _ ->
                    editText1 = contentView.findViewById(R.id.name)
                    editText2 = contentView.findViewById(R.id.url)
                    editText3 = contentView.findViewById(R.id.password)
                    val name = editText1.editableText
                    val url = editText2.editableText
                    val password = editText3.editableText
                    dataList.add(Data(name = "$name", url = "$url", password = "$password", key = key))
                }
                .show()
        }
    }

    
}
