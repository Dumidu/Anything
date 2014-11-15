package com.example.anything;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends ActionBarActivity {

	private EditText inputLogin;
	private EditText inputPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		inputLogin = (EditText) findViewById(R.id.userName);
		inputPassword = (EditText) findViewById(R.id.passWord);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_login,
					container, false);
			return rootView;
		}
	}

	// go to the registration page
	 public void register(View view) {
	 Intent myIntent = new Intent(LoginActivity.this, SignupActivity.class);
	
	 LoginActivity.this.startActivity(myIntent);
	
	 }

	public void login(View view) {
		Log.d("inside onPreExe", "on click");
		Async(view);

	}

	private class ProcessLogin extends AsyncTask<String, String, JSONObject> {

		String login, password;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			inputLogin = (EditText) findViewById(R.id.userName);
			inputPassword = (EditText) findViewById(R.id.passWord);

			login = inputLogin.getText().toString();
			password = inputPassword.getText().toString();

			Log.d("login", login);
			Log.d("password", password);
			Log.d("inside onPreExe", "inside onPreExe");

		}

		@Override
		protected JSONObject doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			UserData userData = new UserData();
			JSONObject json = userData.loginUser(login, password);
			Log.d("inside", "doInBack");
			return json;
		}

		@Override
		protected void onPostExecute(JSONObject json) {
			// TODO Auto-generated method stub
			super.onPostExecute(json);
			Log.d("inside onPreExe", "inside onPostExe");

			try {
				if (json.getString("success") != null) {

					String resutlt = json.getString("success");
					int resultLen = resutlt.length();

					if (resultLen > 0) {

						Toast.makeText(getApplicationContext(),
								"Log in sucessfully!", Toast.LENGTH_SHORT)
								.show();
						Intent myIntent = new Intent(LoginActivity.this,
								MainPageActivity.class);
						Bundle b = new Bundle();
						b.putString("key", login);
						b.putString("msg", resutlt);
						myIntent.putExtras(b);
						startActivity(myIntent);
						finish();
					} else {
						Toast.makeText(getApplicationContext(),
								"Incorrect password or Username!",
								Toast.LENGTH_SHORT).show();

					}

				} else {
					Toast.makeText(getApplicationContext(),
							"Incorrect password or Username!",
							Toast.LENGTH_SHORT).show();

				}
			} catch (NumberFormatException e) {
				Toast.makeText(getApplicationContext(),
						"Incorrect password or Username!", Toast.LENGTH_SHORT)
						.show();
				e.printStackTrace();
			} catch (JSONException e) {
				Toast.makeText(getApplicationContext(),
						"Incorrect password or Username!", Toast.LENGTH_SHORT)
						.show();
				e.printStackTrace();
			}
		}

	}

	public void Async(View view) {
		Log.d("inside onPreExe", "inside Async");
		new ProcessLogin().execute();
	}

}
