# SwipeDeck
## A Tinder style Swipeable deck view for Android


## A Message To Developers
This is an almost complete re write of the first swipe deck, hopefully this one will be less bug prone and easier to update.
this is a very early release so i'm relying on your bug reports and feature suggestions to get this ready for prime time.

## Installation

In your repositories and dependencies section add these parameters:

```groovy
dependencies {
    compile 'com.github.pao11:SwipeDeckRelease:v1.0.0'
}
```
Sync Gradle and import Swipe-Deck into your project

```java
import com.pao11.xyi.swipedeck.SwipeDeck;
```

## Example

Start by defining a card view.
Note that you can use any view type of your choice, cardviews provide you with access to shadows
plus they look good. If you decide to use another view I recommend adding a drop shadow and perhaps a border.

Here we have a pretty ordinary card view defined in XML:

```xml
<android.support.v7.widget.CardView xmlns:card_view="http://schemas.android.com/apk/res-auto"
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/card_view"
    android:layout_gravity="center"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    card_view:cardCornerRadius="4dp"
    card_view:cardElevation="4dp"
    card_view:cardUseCompatPadding="true"
    android:layout_margin="8dp"
    android:gravity="center_horizontal"
    android:padding="25dp">

    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
        android:orientation="vertical" android:layout_width="match_parent"
        android:layout_height="match_parent">
        <RelativeLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content">
            <ImageView
                tools:src="@drawable/food"
                android:id="@+id/offer_image"
                android:layout_width="match_parent"
                android:scaleType="centerCrop"
                android:adjustViewBounds="true"
                android:layout_height="200dp" />
        </RelativeLayout>

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="horizontal"
            android:gravity="center">
            <ImageView
                android:layout_weight="1"
                android:id="@+id/left_image"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:src="@drawable/left_arrow"
                android:layout_below="@id/offer_image"
                android:layout_centerHorizontal="true" />
            <ImageView
                android:layout_weight="1"
                android:id="@+id/right_image"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:src="@drawable/right_arrow"
                android:layout_below="@+id/offer_image"
                android:layout_centerHorizontal="true" />
        </LinearLayout>
        <TextView
            android:id="@+id/sample_text"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Sample Text"
            android:gravity="center_horizontal"
            android:textSize="20dp" />

    </LinearLayout>
</android.support.v7.widget.CardView>
```

Next Swipe Deck takes an adapter in much the same way as other adapter views:

```java
    public class SwipeDeckAdapter extends BaseAdapter {

        private List<String> data;
        private Context context;

        public SwipeDeckAdapter(List<String> data, Context context) {
            this.data = data;
            this.context = context;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View v = convertView;
            if (v == null) {
                LayoutInflater inflater = getLayoutInflater();
                // normally use a viewholder
                v = inflater.inflate(R.layout.test_card2, parent, false);
            }

            ImageView imageView = (ImageView) v.findViewById(R.id.offer_image);
            Picasso.with(context).load(R.drawable.food).fit().centerCrop().into(imageView);
            TextView textView = (TextView) v.findViewById(R.id.sample_text);
            String item = (String)getItem(position);
            textView.setText(item);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("Layer type: ", Integer.toString(v.getLayerType()));
                    Log.i("Hardware Accel type:", Integer.toString(View.LAYER_TYPE_HARDWARE));
                }
            });
            return v;
        }
    }
```
Now we add a swipe deck to our layout:

```xml
<com.pao11.xyi.swipedeck.layouts.SwipeFrameLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:swipedeck="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:id="@+id/swipeLayout"
    android:orientation="vertical">

    <com.pao11.xyi.swipedeck.SwipeDeck
        android:id="@+id/swipe_deck"
        android:layout_width="match_parent"
        android:layout_height="480dp"
        android:padding="50dp"
        swipedeck:max_visible="3"
        swipedeck:card_spacing="15dp"
        swipedeck:swipe_enabled="true"/>

    <Button
        android:id="@+id/button"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="bottom"
        android:text="swipe left" />
    <Button
        android:id="@+id/button3"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="bottom|center"
        android:text="add card" />
    <Button
        android:id="@+id/button2"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="bottom|right"
        android:text="swipe right" />

</com.pao11.xyi.swipedeck.layouts.SwipeFrameLayout>

```
I've included some modified layouts (SwipeFrameLayout, SwipeRelativeLayout etc) for ease of use, but you can use any layout you desire. However you may not get the desired outcome unless you set android:clipChildren="false" on your containing layout. If you choose not to do this cards will be clipped as they move outside their view boundary.

Now we simply give our card deck an adapter and perhaps a callback from our Activity:

```java
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cardStack = (SwipeDeck) findViewById(R.id.swipe_deck);

        testData = new ArrayList<>();
        testData.add("0");
        testData.add("1");
        testData.add("2");
        testData.add("3");
        testData.add("4");

        adapter = new SwipeDeckAdapter(testData, this);
        if(cardStack != null){
            cardStack.setAdapter(adapter);
        }
        cardStack.setCallback(new SwipeDeck.SwipeDeckCallback() {
            @Override
            public void cardSwipedLeft(int positionInAdapter) {
                Log.i("MainActivity", "card was swiped left, position in adapter: " + positionInAdapter);
            }

            @Override
            public void cardSwipedRight(int positoinInAdapter) {
                Log.i("MainActivity", "card was swiped right, position in adapter: " + positoinInAdapter);

            }
        });

        cardStack.setLeftImage(R.id.left_image);
        cardStack.setRightImage(R.id.right_image);

        //example of buttons triggering events on the deck
        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardStack.swipeTopCardLeft(180);
            }
        });
        Button btn2 = (Button) findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardStack.swipeTopCardRight(180);
            }
        });

        Button btn3 = (Button) findViewById(R.id.button3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testData.add("a sample string.");
                adapter.notifyDataSetChanged();
            }
        });

    }
```
# ScreenShots


<img src="screenshots/scrn 1.png" width="200px" />
<img src="screenshots/scrn 2.png" width="200px" />
<img src="screenshots/scrn 3.png" width="200px" />
