package com.example.user.todo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class ToDoListActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new TodoListFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_to_do_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class TodoListFragment extends Fragment {

        public static final int REQUEST_CODE = 0;
        TodoListAdapter mAdapter;
        List<Task> todoList = new ArrayList<>();
        EditText edtTask;
        TextView txvEmpty;

        public TodoListFragment() {
        }



        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setHasOptionsMenu(true);
        }



        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_to_do_list, container, false);
            prepareListView(rootView);
           // edtTask =  (EditText)rootView.findViewById(R.id.txtItem);
            txvEmpty = (TextView)rootView.findViewById(R.id.empty);
            return rootView;
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            super.onCreateOptionsMenu(menu, inflater);
            inflater.inflate(R.menu.menu_fragment_todo_list,menu);
        }

        private void prepareListView(View rootView) {
            final ListView listView = (ListView)rootView.findViewById(R.id.list);

            mAdapter = new TodoListAdapter(getActivity(),todoList);
            listView.setAdapter(mAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //String Title = (Task) parent.getItemAtPosition(position).toString();
                    Log.d("test", "hice click");
                    //Toast.makeText(getActivity(), task, Toast.LENGTH_LONG);
                }
            });



        }


        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int menuId = item.getItemId();
            Boolean handled=false;
            switch (menuId){
                case R.id.add_todo:
                    Intent createTaskActivity = new Intent(getActivity(), CreateTaskActivity.class);
                    startActivityForResult(createTaskActivity, REQUEST_CODE);
                    handled = true;
                break;
            }
            if(!handled)
            {
             handled = super.onOptionsItemSelected(item);
            }

            return handled;
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            switch (resultCode){
                case Activity.RESULT_OK:
                    Task task = new Task();
                    task.setTitle(data.getStringExtra(CreateTaskActivity.NEW_TASK_DESCRIPTION));
                    todoList.add(task);
                    mAdapter.notifyDataSetChanged();
                    break;
                case Activity.RESULT_CANCELED:
                    Toast.makeText(getActivity(), R.string.canceled_message, Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }
}
