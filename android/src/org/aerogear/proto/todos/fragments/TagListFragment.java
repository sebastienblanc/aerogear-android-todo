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

package org.aerogear.proto.todos.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import org.aerogear.proto.todos.Constants;
import org.aerogear.proto.todos.R;
import org.aerogear.proto.todos.activities.MainActivity;
import org.aerogear.proto.todos.data.Tag;

import java.util.ArrayList;
import java.util.List;

public class TagListFragment extends ListBaseFragment {
    public static List<Tag> tags = new ArrayList<Tag>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.list, null);

        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(getResources().getString(R.string.tags));

        ImageView add = (ImageView) view.findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ((MainActivity) getActivity()).showTagForm();
            }
        });

        adapter = new ArrayAdapter<Tag>(getActivity(), android.R.layout.simple_list_item_1,
                                        tags);

        ListView tagListView = (ListView) view.findViewById(R.id.list);
        tagListView.setAdapter(adapter);

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        startRefresh(Constants.TAGS);
    }

}
