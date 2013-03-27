package com.cgcl.cloudesk.screens.act;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.cgcl.cloudesk.cloudeskmain.screens.act.R;
import com.cgcl.cloudesk.manage.app.AppData;
import com.cgcl.cloudesk.manage.config.UIConfig;
import com.cgcl.cloudesk.screens.act.aid.MyApplicationAid;

public class BoardActivity extends BaseActivity {
	private String grade[][];
	private String issue[];
	private String expert[];
	private TableRow tablerow[];
	private TextView text[][];
	private Resources r ;
	private int id[];
	private String name[];
	private int total;
	private String gradeList[];
	private int length;
    private TableLayout table;
    int a = 20;
	int b = 20;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		AppData appData = (AppData)getApplicationContext();
		setUIController(appData.getController());
		this.CurrentActivity = this;
		setContentView(R.layout.board);
		r = this.getBaseContext().getResources();
		findviews();
		getInit();		
				
	}
	public String getNameFrom(int k)
	{
		for (int i = 0 ; i < total; ++i)
		{
			if (id[i] == k)
				return name[i];
		}
		 return "No such Issue";
	}
	
	
	private void getInit()
	{
		total = this.getIntent().getIntExtra("total",0);
		length = this.getIntent().getIntExtra("length",0);
		
		gradeList = new String[length];
		gradeList = this.getIntent().getStringArrayExtra("list");
		id = new int[total];
		name = new String[total];
		
		tablerow = new TableRow[length/8];
		text = new TextView[length/8][20];
		for (int i = 0; i < total; ++i)
		{
			id[i] = Integer.parseInt(gradeList[i*2+1]);
			name[i] = gradeList[i*2+2];
		}
		int all = 0;
		tablerow[all] = new TableRow(this);
		tablerow[all].setGravity(Gravity.CENTER_VERTICAL);
		tablerow[all].setGravity(Gravity.LEFT);
		tablerow[all].setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,TableRow.LayoutParams.WRAP_CONTENT));
		
		text[all][0] = new TextView(this);
		text[all][0].setGravity(Gravity.CENTER_VERTICAL);
		text[all][0].setGravity(Gravity.CENTER_HORIZONTAL);					
		text[all][0].setText("用户名");
		text[all][0].setTextSize(20);
		text[all][0].setTextColor(this.getBaseContext().getResources().getColor(R.color.black));
		tablerow[all].addView(text[all][0]);
		
		for (int i = 0 ; i < total; ++i)
		{
				text[all][i+1] = new TextView(this);
				text[all][i+1].setGravity(Gravity.CENTER_VERTICAL);
				text[all][i+1].setGravity(Gravity.CENTER_HORIZONTAL);
				text[all][i+1].setText(name[i]);
				text[all][i+1].setTextSize(20);
				text[all][i+1].setTextColor(this.getBaseContext().getResources().getColor(R.color.black));
				tablerow[all].addView(text[all][i+1]);
		}
		table.addView(tablerow[all]);
		List list = new ArrayList() ; 
		for (int i = 2*total+1; i < length; i = i + 8)
		{
			if (list.contains(gradeList[i]))
				continue;
			list.add(gradeList[i]);
			++all;
			tablerow[all] = new TableRow(this);
			tablerow[all].setGravity(Gravity.CENTER_VERTICAL);
			tablerow[all].setGravity(Gravity.LEFT);
			tablerow[all].setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,TableRow.LayoutParams.WRAP_CONTENT));
			
			text[all][0] = new TextView(this);
			text[all][0].setGravity(Gravity.CENTER_VERTICAL);
			text[all][0].setGravity(Gravity.CENTER_HORIZONTAL);					
			text[all][0].setText(gradeList[i]);
			text[all][0].setTextSize(20);
			text[all][0].setTextColor(this.getBaseContext().getResources().getColor(R.color.black));
			tablerow[all].addView(text[all][0]);
			
			
			for (int j = 0 ; j < total ; j++)
			{
				String grade = "未评价";
				for (int k = i + 1 ; k < length; k += 8)
				{
					if (id[j] == Integer.parseInt(gradeList[k]))
					{
						grade = gradeList[k+6];
						break;
					}
				}
				text[all][j+1] = new TextView(this);
				text[all][j+1].setGravity(Gravity.CENTER_VERTICAL);
				text[all][j+1].setGravity(Gravity.CENTER_HORIZONTAL);					
				text[all][j+1].setText(grade);
				text[all][0].setTextSize(20);
				text[all][j+1].setTextColor(this.getBaseContext().getResources().getColor(R.color.black));
				tablerow[all].addView(text[all][j+1]);
			}
			table.addView(tablerow[all]);
		}
	
		
		
	
		
	/*	for (int i = total*2 ; i < total*2; ++i)
		{
			for (int j = 2*total+1;  j < length; j = j+8)
			{
				if (id[i] == (Integer.parseInt(gradeList[j+1])))
				{
					tablerow[all] = new TableRow(this);
					tablerow[all].setGravity(Gravity.CENTER_VERTICAL);
					tablerow[all].setGravity(Gravity.LEFT);
					tablerow[all].setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,TableRow.LayoutParams.WRAP_CONTENT));
					
					text[all][0] = new TextView(this);
					text[all][0].setGravity(Gravity.CENTER_VERTICAL);
					text[all][0].setGravity(Gravity.CENTER_HORIZONTAL);					
					text[all][0].setText(getNameFrom(Integer.parseInt(gradeList[j+1])));
					text[all][0].setTextColor(this.getBaseContext().getResources().getColor(R.color.black));
					tablerow[all].addView(text[all][0]);
					
					text[all][1] = new TextView(this);
					text[all][1].setGravity(Gravity.CENTER_VERTICAL);
					text[all][1].setGravity(Gravity.CENTER_HORIZONTAL);
					text[all][1].setText(gradeList[j]);
					text[all][1].setTextColor(this.getBaseContext().getResources().getColor(R.color.black));
					tablerow[all].addView(text[all][1]);
					
					for (int k = 2 ; k <= 7 ; ++k)
					{
						text[all][k] = new TextView(this);
						text[all][k].setGravity(Gravity.CENTER_VERTICAL);
						text[all][k].setGravity(Gravity.CENTER_HORIZONTAL);
						text[all][k].setText(gradeList[j+k]);
						text[all][k].setTextColor(this.getBaseContext().getResources().getColor(R.color.black));
						tablerow[all].addView(text[all][k]);
					}
					++all;
					table.addView(tablerow[all-1]);
				}
				
			}
		}*/
	
	}
	private void findviews()
	{
		table = (TableLayout)findViewById(R.id.tableLayout5);				
	}
	
	 @Override
	    public void showError()
		{
			controller.logout();
			AppData appData = (AppData)getApplicationContext();
		   	appData.setInstall(false);
		   	MainActivity.isInitSuceessed=false;  
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.runOnUiThread(new Runnable(){
				
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					AlertDialog.Builder builder = new AlertDialog.Builder(BoardActivity.this);
					builder.setTitle(R.string.error_load_title);
					builder.setMessage(R.string.error_network_content);
					builder.setPositiveButton(R.string.exit_ok, new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

							dialog.dismiss();
							AppData appData = (AppData)getApplicationContext();
			        	   	appData.setInstall(false);     	   	
			        	    MyApplicationAid.getInstance().exit();
			        	    System.exit(0);
						}
						
				});
					builder.show();
				}
		 });
		}
}
