package womensafety.alertrisk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import crr.india.womensafety12.R;


@SuppressLint("NewApi")
public class FourthActivity extends Activity implements OnClickListener {

	TextView tv;
	ListView listview;
	Button b1;
	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.trial);
	    b1=(Button)findViewById(R.id.addContact);
	    tv=(TextView)findViewById(R.id.textView1);
	    b1.setOnClickListener(this);
	    listview = (ListView) findViewById(R.id.listView1);
	  }

	  private class StableArrayAdapter extends ArrayAdapter<String> {

	    HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

	    public StableArrayAdapter(Context context, int textViewResourceId,
	        List<String> objects) {
	      super(context, textViewResourceId, objects);
	      for (int i = 0; i < objects.size(); ++i) {
	        mIdMap.put(objects.get(i), i);
	      }
	    }

	    @Override
	    public long getItemId(int position) {
	      String item = getItem(position);
	      return mIdMap.get(item);
	    }

	    @Override
	    public boolean hasStableIds() {
	      return true;
	    }

	  }

	  @Override
	  public void onResume()
	  {
		  super.onResume();
		  DBMainClass1 db=new DBMainClass1("11","Contacts","Name","Phone","Email",this);
		    db.open();
		    ArrayList<String> list= db.getData();
		    db.close();

		    tv.setText("Contacts Found : " + new Integer(list.size()).toString());
		    final StableArrayAdapter adapter = new StableArrayAdapter(this, android.R.layout.simple_list_item_1, list);
		    listview.setAdapter(adapter);
		    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

		      @Override
		      public void onItemClick(AdapterView<?> parent, final View view,int position, long id) {
		        final String item = (String) parent.getItemAtPosition(position);
		     
		        
		        Intent i=new Intent(FourthActivity.this,FifthActivity.class);
		        i.putExtra("selectedString",item);
		        startActivity(i);
		      }

		    });
	  }
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent i=new Intent(FourthActivity.this,FifthActivity.class);
		startActivity(i);
	}

	}
