/*
 * Copyright (c) RedHat, 2012.
 */

package org.jboss.aerogear.todo.activities;


import org.jboss.aerogear.todo.R;
import org.jboss.aerogear.todo.activities.TodoActivity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class TodoActivityTest {

	@Test
	public void appNameTest() {
		String appName = new TodoActivity().getResources().getString(
				R.string.app_name);
		Assert.assertEquals("TODOs", appName);
	}
}
