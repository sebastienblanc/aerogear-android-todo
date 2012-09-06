/*
 * JBoss, Home of Professional Open Source
 * Copyright 2012, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.aerogear.proto.todos.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import org.aerogear.android.AeroGearCollection;
import org.aerogear.proto.todos.Constants;
import org.aerogear.proto.todos.data.Task;
import org.aerogear.proto.todos.fragments.ToDoListFragment;

/**
 * This is an IntentService which performs networking tasks (via the AeroGearCollection)
 * in the background.  Right now this exists as part of the application, but in the future
 * we may genericize it into an AeroGear-supplied service that you can just declare in your
 * manifest.
 */
public class ToDoAPIService extends IntentService {
    public static final String TAG = ToDoAPIService.class.getName();

    private AeroGearCollection<Task> taskCollection =
            new AeroGearCollection<Task>("tasks", Task[].class);

    public ToDoAPIService() {
        super(TAG);
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();
        if (Constants.ACTION_REFRESH_TASKS.equals(action)) {
            Log.d(TAG, "Refreshing tasks from server...");
            try {
                refreshTasks();
            } catch (Exception e) {
                Log.d(TAG, "Coudldn't refresh tasks", e);
            }
        } else if (Constants.ACTION_POST_TASK.equals(action)) {
            Log.d(TAG, "Sending new task to server...");
            String title = intent.getStringExtra(Constants.EXTRA_TASK_TITLE);
            try {
                postTask(new Task(title));
            } catch (Exception e) {
                Log.d(TAG, "Couldn't post new task");
            }
        } else if (Constants.ACTION_DELETE_TASK.equals(action)) {
            Log.d(TAG, "Sending new task to server...");
            String id = intent.getStringExtra(Constants.EXTRA_TASK_ID);
            try {
                deleteTask(id);
            } catch (Exception e) {
                Log.d(TAG, "Couldn't delete task ID " + id);
            }
        }
    }

    private void refreshTasks() throws Exception {
        // Use the AeroGearCollection to grab all the tasks from the backend into a shared List
        taskCollection.getAll(ToDoListFragment.tasks);

        // All set, let everyone know...
        sendBroadcast(new Intent(Constants.ACTION_REFRESH_TASKS));
    }

    private void postTask(Task task) throws Exception {
        // Use the AeroGearCollection to post a new Task to the backend
        taskCollection.add(task);

        // And refresh our list to pick up the new one
        refreshTasks();
    }

    private void deleteTask(String id) throws Exception {
        taskCollection.delete(id);

        refreshTasks();
    }
}
