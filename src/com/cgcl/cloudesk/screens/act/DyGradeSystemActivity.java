package com.cgcl.cloudesk.screens.act;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

import android.R.color;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Toast;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.cgcl.cloudesk.cloudeskmain.screens.act.R;
import com.cgcl.cloudesk.manage.app.AppData;
import com.cgcl.cloudesk.manage.config.UIConfig;
import com.cgcl.cloudesk.manage.core.Controller;
import com.cgcl.cloudesk.screens.act.aid.MyApplicationAid;

public class DyGradeSystemActivity extends BaseActivity {
	
	private int total = 5;
	private TableLayout table;
	private Button reset;
	private Button showDetails;
	private Button submit;
	private TableRow tablerow[];
	private String  gradeGrid[][];
	private Spinner spinner[][];
	private String gradeView[];
	private TextView issueView[];
	private int proid;

	private boolean isInstall = false;
	private ProgressBar loadingPBar;
	private TextView 	loadingText;
	private Vector<String> issue;
	private LinkedList<String> gradeList;
	private Vector<String> grade;
	private int id[];
	private LinearLayout loadingBar;
	private Resources r ;
	private String username ;
	private static final String[] level30={"未评价","0","1","2","3","4","5",
													"6","7","8","9","10",
													"11","12","13","14","15",
													"16","17","18","19","20",
													"21","22","23","24","25",
													"26","27","28","29","30"};  
	private static final String[] level20={"未评价","0","1","2","3","4","5",
												"6","7","8","9","10",
												"11","12","13","14","15",
												"16","17","18","19","20"};
	private static final String[] level10={"未评价","0","1","2","3","4","5",
													"6","7","8","9","10"};
	
	
	private ArrayAdapter<String> adapter[][];  
	//private Array<String>
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		  this.CurrentActivity = this;
		AppData appData = (AppData)getApplicationContext();
		setUIController(appData.getController());
		setType(UIConfig.gradeSystemFormType);
		setContentView(R.layout.dynamic_gradesystem);
		r = this.getBaseContext().getResources();
		username = Controller.username;
		getInit();
		findviews();
		listeners();
		//notifyGetGradeResult(new LinkedList<String>());
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.v("start", "ok");
		MoblieClientUI.currentActivity = this;		
		
	}

	private void getInit()
	{
		controller.getGrade("AllUsers");
	}

	private boolean checkUser()
	{
		return true;
	}

	private void findviews()
	{
		submit =(Button)findViewById(R.id.submit1);
		table = (TableLayout)findViewById(R.id.tableLayout3);
		showDetails = (Button)findViewById(R.id.showdetails);
		showDetails.setVisibility(View.INVISIBLE);
		reset  =(Button)findViewById(R.id.reset1);	
		loadingBar = (LinearLayout)findViewById(R.id.LinearLoadingBar);
		loadingPBar= (ProgressBar)findViewById(R.id.loadingPBar);
		loadingText= (TextView)findViewById(R.id.loadingText);
	}
	private int getRowNo(int proid)
	{
		for (int i = 0 ; i < total ; ++i)
		{
			if (proid == id[i])
				return  i;
		}
		return -1;
	}
	
	public void notifyGetGradeResult(LinkedList<String> gradeList)
	{
   	
		if (isInstall == false)
		{
		Log.i("gradeResult", "getResult");
		this.gradeList = gradeList;
		total = 0;
		issue = new Vector<String>();
		grade = new Vector<String>();
		Iterator<String> it = this.gradeList.iterator();
		if (it.hasNext())
		{
			total = Integer.parseInt(it.next());
			Log.i("total= ", Integer.toString(total));
			id = new int[total];
			gradeGrid = new String[total][7];
			for (int i = 0; i < total ; ++i)
				for (int j = 1;  j <= 5; ++j)
				{
					gradeGrid[i][j] = "未评价";
				}
			for (int i = 0 ; i <total; ++i)
			{
				id[i] = Integer.parseInt(it.next());
				Log.i("id[i]= ", Integer.toString(id[i]));
				issue.add(it.next());
				Log.i("issue[i]= ", issue.elementAt(i));
			}
			while (it.hasNext())
			{
				String userId = (it.next());
				if (userId.equals(username))
				{
					proid = Integer.parseInt(it.next());
					int j = getRowNo(proid);
					for(int k = 1; k <=6; ++k)
					{
						gradeGrid[j][k] = it.next();
					}
				}
				else
				{
					for (int i = 0 ; i <= 6; ++i)
					{
						it.next();
					}
				}
			}
		
			spinner = new Spinner[total][6];
			gradeView = new String[total];
			issueView = new TextView[total];
			tablerow  = new TableRow[total];
			adapter   = new ArrayAdapter[total][6];
			
			for (int i = 0 ; i < total; ++i)
			{
				tablerow[i] = new TableRow(this);
				issueView[i] = new TextView(this);
				gradeView[i] = new String("-10000");
				for (int j = 1;  j <= 5; ++j)
				{
					spinner[i][j] = new Spinner(this);
					switch (j)
					{
						case 1		:adapter[i][j] = new ArrayAdapter<String>(this, android.R.layout.preference_category, level30); break;
						case 2		:adapter[i][j] = new ArrayAdapter<String>(this, android.R.layout.preference_category, level10); break;
						case 3	    :;
						case 4		:;
						case 5 		:;adapter[i][j] = new ArrayAdapter<String>(this, android.R.layout.preference_category, level20); break;
						default 	: break;
					}
					
				}
			}
		}
		isInstall = true;
		refresh();
		}
		else
		{
			String msg[] = new String[gradeList.size()];
			for (int i = 0 ; i < gradeList.size(); ++i)
				msg[i] = gradeList.get(i);
			Intent intent = new Intent();
			intent.putExtra("list", msg);
			intent.putExtra("total", Integer.parseInt(gradeList.get(0)));
			intent.putExtra("length", gradeList.size());
			intent.setClass(DyGradeSystemActivity.this, BoardActivity.class);
			startActivity(intent);
		}
	}	
	public void refresh()
	{
		for (int i = 0 ; i < total; ++i)
		{
			for (int j = 1; j  <= 5; ++j)
			spinner[i][j].setOnItemSelectedListener(mylistener);
				
		};
		this.runOnUiThread(new Runnable() {
		
		@Override
		public void run() {
			
			if (checkUser())
			{
			
				showDetails.setVisibility(View.GONE);
			}
			else
			{
				showDetails.setVisibility(View.GONE);
			}
			// TODO Auto-generated method stub
			
			loadingBar.setVisibility(View.GONE);
			loadingPBar.setVisibility(View.GONE);
			loadingText.setVisibility(View.GONE);
			
			table.setVerticalScrollBarEnabled(true);
			for (int i = 0 ; i < total; ++i)
			{
				Log.i("now i = ", Integer.toString(i));
				
				//tablerow[i].setLayoutParams(new TableRow.LayoutParams(6));
				tablerow[i].setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,TableRow.LayoutParams.WRAP_CONTENT));
				tablerow[i].setGravity(Gravity.CENTER_HORIZONTAL);
				tablerow[i].setGravity(Gravity.CENTER_VERTICAL);
					
				issueView[i].setText(issue.get(i));
				issueView[i].setTextColor(r.getColor(R.color.black));
				issueView[i].setTextSize(18);
				issueView[i].setGravity(Gravity.CENTER_HORIZONTAL);
				issueView[i].setGravity(Gravity.CENTER_VERTICAL);
				tablerow[i].addView(issueView[i]);
				for (int j = 1;  j <= 5; ++j)
				{
					
					//ratingbar[i][j]= new MyRatingBar(this,null,android.R.attr.ratingBarStyleSmall);
					//ratingbar[i][j].setContentDescription();				
					//ratingbar[i][j].setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT));
					//ratingbar[i][j].setMax(5);
					
					//ratingbar[i][j].setNumStars(5);
					///ratingbar[i][j].setStepSize(1);
					///ratingbar[i][j].setIsIndicator(false);
					//ratingbar[i][j].setRating(3);			
				    
				   
				    adapter[i][j].setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
				    spinner[i][j].setAdapter(adapter[i][j]);		
				   //展开的标题
				    switch(j)
				    {
				    	case 1:spinner[i][j].setPrompt((String)r.getString(R.string.working_condition_1)); break;
				    	case 2:spinner[i][j].setPrompt((String)r.getString(R.string.working_condition_2));	break;
				    	case 3:spinner[i][j].setPrompt((String)r.getString(R.string.research_foreground_1)); break;
				    	case 4:spinner[i][j].setPrompt((String)r.getString(R.string.research_foreground_2));	break;
				    	case 5:spinner[i][j].setPrompt((String)r.getString(R.string.research_foreground_3));	break;
				    	default :break;
				    }
				    if (gradeGrid[i][j].equalsIgnoreCase("未评价"))
				    	spinner[i][j].setSelection(0);
				    else
				    	spinner[i][j].setSelection(Integer.parseInt(gradeGrid[i][j])+1);
				   /* if (gradeGrid[i][j].equals("优"))
				    	spinner[i][j].setSelection(2);
				    if (gradeGrid[i][j].equals("良"))
				    	spinner[i][j].setSelection(3);
				    if (gradeGrid[i][j].equals("中"))
				    	spinner[i][j].setSelection(4);
				    if (gradeGrid[i][j].equals("差"))
				    	spinner[i][j].setSelection(5);
				    */
				    spinner[i][j].setVisibility(View.VISIBLE);		
					tablerow[i].addView(spinner[i][j]);
					//ratingbar[i][j].setLayoutParams(new LinearLayout.LayoutParams(0,0));
				}
				
				//gradeView[i].setText(gradeGrid[i][5]);
				//gradeView[i].setTextColor(r.getColor(R.color.black));
				//gradeView[i].setGravity(Gravity.CENTER_HORIZONTAL);
			//	gradeView[i].setGravity(Gravity.CENTER_VERTICAL);
				//tablerow[i].addView(gradeView[i]);
				table.addView(tablerow[i]);
			}
		};
});
	
		
}
	
	
	private AdapterView.OnItemSelectedListener mylistener;
	private void listeners()
	{
		mylistener = new AdapterView.OnItemSelectedListener()
		{

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				for (int i = 0; i < total; ++i)
				{
					int temp = 0;
					for (int j = 1;  j <= 5; ++j)
					{
						String t_str =(String)spinner[i][j].getSelectedItem();
						if (t_str.equals("未评价"))
							temp = -10000;
						else 
							temp += spinner[i][j].getSelectedItemPosition()-1;
					}
			//修改到此处
				  gradeView[i] = Integer.toString(temp);
				}
			}
				// TODO Auto-generated method stub			

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		};
		
		
		reset.setOnClickListener(new Button.OnClickListener(){
			
			@Override
			public void onClick(View view)
			{
				for (int i = 0 ;  i < total; ++i)
				{
					for (int j = 1; j <=5 ; ++j)
					{
						spinner[i][j].setSelection(0);
					}
				}
			}
		});
		
		submit.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				LinkedList<String> Scoring = new LinkedList<String>();
				Scoring.add(username);
				boolean flag = false;
				for (int i = 0 ; i <total; ++i )
				{
				   //	if (Integer.parseInt(gradeView[i]) < 0)
				   //		continue;					
					Scoring.add(Integer.toString(id[i]));
					for (int j = 1; j <= 5; j++)
					{
						if (spinner[i][j].getSelectedItemPosition() == 0)
							Scoring.add("未评价");
						else
						{
							Scoring.add(Integer.toString(spinner[i][j].getSelectedItemPosition()-1));
							flag = true;
						}
					}
					if (Integer.parseInt(gradeView[i]) < 0)
						Scoring.add("未评价");
					else 
					{
						Scoring.add(gradeView[i]);
						flag = true;
					}
				}
				if (flag)
				{
					controller.scoring(Scoring);
				}
				else
				{
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							showToast("请先打分再提交");
						};
				 });
				}
			}		
			
		});
		
		showDetails.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				/*String msg[] = new String[gradeList.size()];
				for (int i = 0 ; i < gradeList.size(); ++i)
					msg[i] = gradeList.get(i);
				Intent intent = new Intent();
				intent.putExtra("list", msg);
				intent.putExtra("total", total);
				intent.putExtra("length", gradeList.size());
				intent.setClass(DyGradeSystemActivity.this, BoardActivity.class);
				startActivity(intent);
				*/
				controller.getGrade("AllUsers");
			}
			
		});
	}
	public void notifyScoringResult(String scoringResult)
	{
		if(scoringResult.equalsIgnoreCase("success"))
		{
			this.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					showToast("打分成功");
				};
		 });
		}
		else
		{

			 this.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					showToast("打分失败");
				}
			
		});
			
		}
	}
	private void showToast(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
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
					AlertDialog.Builder builder = new AlertDialog.Builder(DyGradeSystemActivity.this);
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
