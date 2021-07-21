package techmarket.uno.firebase01;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReadActivity extends AppCompatActivity {
    //нужен listview and arrayadapter and list(список)
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> listData;//1.
    //11.
    private List<User> listTemp;//данный лист будет хранить всю инфо о (because User)
    private DatabaseReference myDatabase; //3. создаем подключаемся к  базе данных
    private String USER_KEY = "user";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read_layout);
        init();
        getDataFromDB();
        //7.
        setOnClickItem();//14.
    }
    //2.
    private void init()
    {
        listView = findViewById(R.id.listView);
        listData = new ArrayList<>();
        //12.
        listTemp = new ArrayList<>();
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listData);
        listView.setAdapter(adapter);
        myDatabase = FirebaseDatabase.getInstance().getReference(USER_KEY); //4.
    }
    //5. создаем функцию считывания из базы данных
    private void getDataFromDB()
    {
        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {//7. очистка листДата перед добавлением актуальных данных
                if (listData.size() > 0) listData.clear();
                if (listTemp.size() > 0) listTemp.clear(); //13.

                //и помощи цикла получаем всех добавленных в базу
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {                                          //помещаем каждого из базы в listData
                    User user =ds.getValue(User.class);
                                                           //8. проверка юзер не пустой
                    assert user != null;
                    listData.add(user.name);
                    listTemp.add(user);
                }
                //9. извещаем адаптер, что листДата изменился
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        myDatabase.addValueEventListener(vListener);//6.
    }

    private void setOnClickItem()
    {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = listTemp.get(position);
                Intent i = new Intent(ReadActivity.this,ShowActivity.class);
                i.putExtra(Constants.USER_NAME,user.name);
                i.putExtra(Constants.USER_SECNAME,user.sec_name);
                i.putExtra(Constants.USER_EMAIL,user.email);
                startActivity(i);
            }
        });
    }
}
