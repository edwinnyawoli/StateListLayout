# StateListLayout
StateListLayout is a custom layout that allows you to show different
user interfaces in the same activity or fragment for the various
states that the application may be in. 


## An Example
An application may want to initially show a loading screen when the 
activity starts, then show a list of items after they have been
completely loaded from disk or network.

To accomplish this, the activity layout can be specified like so

````xml
    <com.edwinnyawoli.statelistlayout.StateListLayout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        xmlns:tools="http://schemas.android.com/tools"
        android:id="@+id/state_list_layout"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:active_state="progress">
     
        <android.support.v7.widget.RecyclerView
            android:id="@+id/history_recycler_view"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            app:state="content" />
    
        <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:gravity="center"
                app:state="progress">
        
                <ProgressBar
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:indeterminate="true" />
        </LinearLayout>
    </com.edwinnyawoli.statelistlayout.StateListLayout>
````

and in the activity source file, the `setState()` method of the StateListLayout
can be called to change its state.
````java
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final StateListLayout stateListLayout = findViewById(R.id.state_list_layout);
        
        // ... Fetch list of items
        stateListLayout.setState(State.CONTENT);
    }
}

````


## Active state and state
In the example above, two xml attributes are used: `app:active_state` and `app:state`.
The first attribute is used to specify which state should be considered
as active and shown when the activity or fragment is initially displayed.

The second attribute, `app:state` is used to associate a state with a layout.
The value specified for the attribute is the name of the state object that would
be created for the layout.

## Download
 To use StateListLayout, add the JitPack repository to your root build file
 ````groovy
    allprojects {
        repositories {
            maven { url 'https://jitpack.io' }
        }
    }
 ````
and add the dependency
````groovy
    dependencies {
        compile 'com.github.edwinnyawoli:StateListLayout:1.0.0'
    }
````


# License
    Copyright 2017 Edwin Nyawoli
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
       http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.