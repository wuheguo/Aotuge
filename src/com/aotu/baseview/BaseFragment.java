package com.aotu.baseview;

import android.app.Fragment;

public class BaseFragment extends Fragment{
	
	
	private class AsyncTaskLoad extends AsyncTaskBase {
		public AsyncTaskLoad(LoadingView loadingView) {
			super(loadingView);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected Integer doInBackground(Integer... params) {
			int result = 1;
		
		
			return result;
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

	}

}
