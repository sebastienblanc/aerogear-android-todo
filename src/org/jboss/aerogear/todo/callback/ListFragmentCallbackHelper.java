package org.jboss.aerogear.todo.callback;

import java.util.List;

import android.widget.ArrayAdapter;

public interface ListFragmentCallbackHelper<T> {

	public List<T> getList();
	public ArrayAdapter<T> getAdapter();
	public void startRefresh();
}
