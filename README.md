# Android-Studio-mid-Note
Android Studio 期中NotePad作业

# 项目简介
这是一个基于 Android Studio 开发的 NotePad 应用，作为期中作业，项目在原有基础上进行了功能扩展，旨在提供更丰富的笔记管理体验。支持时间戳、搜索、UI 美化、响应变色、笔记排序、背景颜色修改以及文件输出等功能。

# 功能特性
时间戳：每个笔记自动生成创建时间和修改时间。
搜索功能：用户可以通过关键词搜索笔记。
UI 美化：应用的基础颜色为 HoloLight，界面设计简洁且美观。
响应变色：点击笔记时，背景颜色会发生变化，增强交互反馈。
背景颜色选择：每条笔记可以自定义背景颜色。
笔记排序：可以根据创建时间、修改时间或颜色对笔记进行排序。
# 技术栈
开发语言：Java 17

IDE：Android Studio

构建工具：Gradle

JDK：Amazon Corretto 17

数据库：SQLite（存储笔记数据）

依赖库：RecyclerView、Room Database、Glide（图片处理）、File API（导出功能）

其中：JDK版本为17  Gradle的版本为8.9.0  AGP的版本为8.7.2  API版本为35.0

![image](https://github.com/user-attachments/assets/b51b4b26-0a9e-40b0-90e3-228840bf382c)
![image](https://github.com/user-attachments/assets/de4a1225-3727-47ee-a8e6-326788ffb20b)

# 安装和配置
克隆项目
https://github.com/llfjfz/NotePad
打开 Android Studio
在 Android Studio 中打开项目目录。
同步 Gradle 
点击 Sync Now 以同步 Gradle 配置。

# 运行应用
连接 Android 设备或启动模拟器，点击运行按钮即可。

# 项目结构

主要的类:
NotesList类 应用程序的入口，笔记本的首页面会显示笔记的列表

NoteEditor类 编辑笔记内容的Activity

TitleEditor类 编辑笔记标题的Activity

NotePadProvider类 这是笔记本应用的ContentProvider

NoteColor类 用来选择颜色

NoteSearch类 用于实现笔记查询

MyCursorAdapter类 继承SimpleCursorAdapter

主要的布局文件：

note_editor.xml 笔记主页面布局

noteslist_item.xml 笔记主页面每个列表项布局

title_editor.xml 修改笔记主题布局

note_search.xml 笔记内容查询布局

note_color.xml 对选择颜色界面进行布局

主要的菜单文件：

editor_options_menu.xml 编辑笔记内容的菜单布局

list_context_menu.xml 笔记内容编辑上下文菜单布局

list_options_menu.xml 笔记主页面可选菜单布局

![image](https://github.com/user-attachments/assets/08951e1d-d7bc-49ec-b3c5-ca74afcbe7f1)

![image](https://github.com/user-attachments/assets/2cdc02cb-d43e-42d3-b323-b57128660e7c)


![image](https://github.com/user-attachments/assets/b3e7c813-b4b7-4f4a-a735-0d80fbf9d58a)

# 代码说明

## 基础功能
时间戳：每条笔记自动记录创建时间和最后修改时间。用户可以在笔记详情页面查看这些时间。
![image](https://github.com/user-attachments/assets/ca6c46f4-014b-4aa1-80ad-ebdd2e225537)

在NotePad原应用中，笔记列表只显示了笔记的标题。要对它做时间扩展，可以把时间放在标题的下方。
首先，找到列表中item的布局：noteslist_item.xml。
在这个布局文件中，能看到一个TextView，这个便是笔记列表的标题item了。

```
<TextView xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@android:id/text1"
    android:layout_width="match_parent"
    android:layout_height="?android:attr/listPreferredItemHeight"
    android:textAppearance="?android:attr/textAppearanceLarge"
    android:gravity="center_vertical"
    android:paddingLeft="5dip"
    android:singleLine="true"
/>
```

要在标题下方加时间显示，就要在标题的TextView下再加一个时间的TextView。但是由于原应用列表item只需要一个标题，所以不需要用上别的布局，而我要多加一个时间TextView，就要把标题TextView和时间TextView放入垂直的线性布局。
由于要美化UI，所以将TextView原字体颜色改为黑色。新加的时间TextView字体大小小于标题TextView。

```
<?xml version="1.0" encoding="utf-8"?>
<!--添加一个垂直的线性布局-->
<LinearLayout  xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/layout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">
    <!--原标题TextView-->
    <TextView xmlns:android="http://schemas.android.com/apk/res/android"
        android:id="@android:id/text1"
        android:layout_width="match_parent"
        android:layout_height="?android:attr/listPreferredItemHeight"
        android:textAppearance="?android:attr/textAppearanceLarge"
        android:gravity="center_vertical"
        android:paddingLeft="5dip"
        android:textColor="@color/colorBlack"
        android:singleLine="true"
        />
    <!--添加显示时间的TextView-->
    <TextView
        android:id="@+id/text1_time"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:textAppearance="?android:attr/textAppearanceSmall"
        android:paddingLeft="5dip"
        android:textColor="@color/colorBlack"/>
</LinearLayout>
```

先查看程序如何定义数据库结构的，NotePadProvider.java中:

```
@Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + NotePad.Notes.TABLE_NAME + " ("
            + NotePad.Notes._ID + " INTEGER PRIMARY KEY,"
            + NotePad.Notes.COLUMN_NAME_TITLE + " TEXT,"
            + NotePad.Notes.COLUMN_NAME_NOTE + " TEXT,"
            + NotePad.Notes.COLUMN_NAME_CREATE_DATE + " INTEGER,"
            + NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE + " INTEGER"
            + ");");
    }
```

可以看出，NotePad数据库已经存在时间信息。
再到NoteList.java文件中查看，是如何将数据装填到列表中。
可以发现，当前Activity所用到的数据被定义在PROJECTION中：

```
private static final String[] PROJECTION = new String[] {
            NotePad.Notes._ID, // 0
            NotePad.Notes.COLUMN_NAME_TITLE, // 1
    };
```

通过Cursor从数据库中读取出：

```
Cursor cursor = managedQuery(
            getIntent().getData(),            // Use the default content URI for the provider.
            PROJECTION,                       // Return the note ID and title for each note.
            null,                             // No where clause, return all records.
            null,                             // No where clause, therefore no where column values.
            NotePad.Notes.DEFAULT_SORT_ORDER  // Use the default sort order.
        );
```

通过SimpleCursorAdapter装填：
``` java
String[] dataColumns = { NotePad.Notes.COLUMN_NAME_TITLE } ;
int[] viewIDs = { android.R.id.text1 };
SimpleCursorAdapter adapter
    = new SimpleCursorAdapter(
            this, // The Context for the ListView
            R.layout.noteslist_item, // Points to the XML for a list item
            cursor,   // The cursor to get items from
            dataColumns,
            viewIDs
    );
// Sets the ListView's adapter to be the cursor adapter that was just created.
setListAdapter(adapter);
```

要将时间显示，首先要在PROJECTION中定义显示的时间，原应用有两种时间，我选择修改时间作为显示的时间。颜色部分先忽略，下文会涉及。

``` java
 private static final String[] PROJECTION = new String[] {
            NotePad.Notes._ID, // 0
            NotePad.Notes.COLUMN_NAME_TITLE, // 1
            //扩展 显示时间 颜色
            NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE,
            NotePad.Notes.COLUMN_NAME_BACK_COLOR, 
    };
```

Cursor不变，在dataColumns，viewIDs中补充时间部分：
 
``` java
String[] dataColumns = { NotePad.Notes.COLUMN_NAME_TITLE ,  NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE } ;
int[] viewIDs = { android.R.id.text1 , R.id.text1_time };

SimpleCursorAdapter不变（在改笔记列表颜色时需要做出改变，下文会涉及）。做完这些，标题下确实会多显示一行数据，但是，这并不是我们平常所见到的时间格式，而是时间戳，需要对这些数据进行转化，使人能看的懂。
我选则的方法时把时间戳改为以时间格式存入，改动地方分别为NotePadProvider中的insert方法和NoteEditor中的updateNote方法。前者为创建笔记时产生的时间，后者为修改笔记时产生的时间。下面代码中的dateTime即为转化后的时间格式，将其用ContentValues的put方法存入数据库。

Long now = Long.valueOf(System.currentTimeMillis());
Date date = new Date(now);
SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
String dateTime = format.format(date);
``` 

搜索功能：用户可以通过输入关键词进行笔记搜索，快速定位到相关内容。
![image](https://github.com/user-attachments/assets/a7b81761-413b-49b1-b189-68a017ad6cd6)

笔记查询
要添加笔记查询功能，就要在应用中增加一个搜索的入口。找到菜单的xml文件，list_options_menu.xml，添加一个搜索的item，搜索图标用安卓自带的图标，设为总是显示：

```
<item
    android:id="@+id/menu_search"
    android:title="@string/menu_search"
    android:icon="@android:drawable/ic_search_category_default"
    android:showAsAction="always">
</item>
```
在NoteList中找到onOptionsItemSelected方法，在switch中添加搜索的case语句:
```
 //添加搜素
    case R.id.menu_search:
    Intent intent = new Intent();
    intent.setClass(NotesList.this,NoteSearch.class);
    NotesList.this.startActivity(intent);
    return true;
```
在case语句中写跳转activity代码之前要先写好搜索的activity，新建一个名为NoteSearch的activity。由于搜索出来的也是笔记列表，所以可以模仿NoteList的activity继承ListActivity。在安卓中有个用于搜索控件：SearchView，可以把SearchView跟ListView相结合，动态地显示搜索结果。先布局搜索页面，在layout中新建布局文件note_search_list.xml：
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical" android:layout_width="match_parent"
    android:layout_height="match_parent">
    <SearchView
        android:id="@+id/search_view"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:iconifiedByDefault="false"
        android:queryHint="输入搜索内容..."
        android:layout_alignParentTop="true">
    </SearchView>
    <ListView
        android:id="@android:id/list"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">
    </ListView>
</LinearLayout>
```

在上面的ListView的id命名方式与往常“@+id/”的方式有些不同，之前用“@+id/”方式尝试过，但是运行会出错，可能是由于是继承ListAcitivity的缘故。
要动态地显示搜索结果，就要对SearchView文本变化设置监听，NoteSearch除了要继承ListView外还要实现SearchView.OnQueryTextListener接口：

```
public class NoteSearch extends ListActivity  implements SearchView.OnQueryTextListener {
    private static final String[] PROJECTION = new String[] {
            NotePad.Notes._ID, // 0
            NotePad.Notes.COLUMN_NAME_TITLE, // 1
            //扩展 显示时间 颜色
            NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE, // 2
            NotePad.Notes.COLUMN_NAME_BACK_COLOR
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_search_list);
        Intent intent = getIntent();
        if (intent.getData() == null) {
            intent.setData(NotePad.Notes.CONTENT_URI);
        }
        SearchView searchview = (SearchView)findViewById(R.id.search_view);
        //为查询文本框注册监听器
        searchview.setOnQueryTextListener(NoteSearch.this);  
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }
    @Override
    public boolean onQueryTextChange(String newText) {
        String selection = NotePad.Notes.COLUMN_NAME_TITLE + " Like ? ";
        String[] selectionArgs = { "%"+newText+"%" };
        Cursor cursor = managedQuery(
                getIntent().getData(),            // Use the default content URI for the provider.
                PROJECTION,                       // Return the note ID and title for each note. and modifcation date
                selection,                        // 条件左边
                selectionArgs,                    // 条件右边
                NotePad.Notes.DEFAULT_SORT_ORDER  // Use the default sort order.
        );
        String[] dataColumns = { NotePad.Notes.COLUMN_NAME_TITLE ,  NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE };
        int[] viewIDs = { android.R.id.text1 , R.id.text1_time };
        MyCursorAdapter adapter = new MyCursorAdapter(
                this,
                R.layout.noteslist_item,
                cursor,
                dataColumns,
                viewIDs
        );
        setListAdapter(adapter);
        return true;
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // Constructs a new URI from the incoming URI and the row ID
        Uri uri = ContentUris.withAppendedId(getIntent().getData(), id);
        // Gets the action from the incoming Intent
        String action = getIntent().getAction();
        // Handles requests for note data
        if (Intent.ACTION_PICK.equals(action) || Intent.ACTION_GET_CONTENT.equals(action)) {
            // Sets the result to return to the component that called this Activity. The
            // result contains the new URI
            setResult(RESULT_OK, new Intent().setData(uri));
        } else {
            // Sends out an Intent to start an Activity that can handle ACTION_EDIT. The
            // Intent's data is the note ID URI. The effect is to call NoteEdit.
            startActivity(new Intent(Intent.ACTION_EDIT, uri));
        }
    }
}
```

即使我不需要使用onQueryTextSubmit方法，onQueryTextSubmit和onQueryTextChange两个方法也是实现接口必须写的方法。onListItemClick方法是点击NoteList的item跳转到对应笔记编辑界面的方法，NoteList中有这个方法，搜索出来的笔记跳转原理与NoteList中笔记一样，所以可以直接从NoteList中复制过来直接使用。

使用PROJECTION，Cursor，adapter方法与时间显示的原理一样，这里不多描述，但是可以注意到adapter是用MyCursorAdapter声明的，MyCursorAdapter是继承SimpleCursorAdapter，对其中个别方法进行重写，下文UI部分会提到。
动态搜索的实现最主要的部分在onQueryTextChange方法中，在使用这个方法，要先为SearchView注册监听：

```
SearchView searchview = (SearchView)findViewById(R.id.search_view);
searchview.setOnQueryTextListener(NoteSearch.this);  
```

而onQueryTextChange方法作用是，当SearchView中文本发生变化时，执行其中代码，搜索还有一个重要的部分就是要做到模糊匹配而不是严格匹配，可以使用数据库查询语句中的LIKE和%结合来实现，newText为输入搜索的内容：

```
String[] selectionArgs = { "%"+newText+"%" };
```

最后要在AndroidManifest.xml注册NoteSearch：

```
<!--添加搜索activity-->
    <activity
        android:name="NoteSearch"
        android:label="@string/title_notes_search">
    </activity>
```

## 扩展功能
UI 美化：基础颜色为 HoloLight，整体 UI 简洁明了，用户体验友好。
![image](https://github.com/user-attachments/assets/c324a02c-9f62-4922-89eb-dd2872c2b7e3)

UI美化
先给NoteList换个主题，把黑色换成白色，在AndroidManifest.xml中NotesList的Activity中添加：
```
android:theme="@android:style/Theme.Holo.Light"
```
UI美化主要是让NoteList和NoteSearch每条笔记都有背景色，并且能保存。要做到保存颜色的数据，最直接的办法就是在数据库中添加一个颜色的字段，在这之前在NotePad契约类中添加：
```
public static final String COLUMN_NAME_BACK_COLOR = "color";
```
创建数据库表地方添加颜色的字段：
```
 @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + NotePad.Notes.TABLE_NAME + "   ("
        + NotePad.Notes._ID + " INTEGER PRIMARY KEY,"
        + NotePad.Notes.COLUMN_NAME_TITLE + " TEXT,"
        + NotePad.Notes.COLUMN_NAME_NOTE + " TEXT,"
        + NotePad.Notes.COLUMN_NAME_CREATE_DATE + " INTEGER,"
        + NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE + " INTEGER,"
        + NotePad.Notes.COLUMN_NAME_BACK_COLOR + " INTEGER" //颜色
        + ");");
       }
```
把颜色定义为INTEGER的主要原因是，在系统中预定于好五种颜色，根据颜色对应不int值选择要显示的颜色，契约类中的定义：
```
public static final int DEFAULT_COLOR = 0; //白
public static final int YELLOW_COLOR = 1; //黄
public static final int BLUE_COLOR = 2; //蓝
public static final int GREEN_COLOR = 3; //绿
public static final int RED_COLOR = 4; //红
```
由于数据库中多了一个字段，所以要在NotePadProvider中添加对其相应的处理，static{}中：
```
sNotesProjectionMap.put(
        NotePad.Notes.COLUMN_NAME_BACK_COLOR,
        NotePad.Notes.COLUMN_NAME_BACK_COLOR);
```
insert中：
```
 // 新建笔记，背景默认为白色
if (values.containsKey(NotePad.Notes.COLUMN_NAME_BACK_COLOR) == false) {
        values.put(NotePad.Notes.COLUMN_NAME_BACK_COLOR, NotePad.Notes.DEFAULT_COLOR);
        }
```
将颜色填充到ListView，可以用SimpleCursorAdapter中的getView，bindView，newView方法来实现，我选择了bindView。自定义一个CursorAdapter继承SimpleCursorAdapter，既能完成cursor读取的数据库内容填充到item，又能将颜色填充：
```
public class MyCursorAdapter extends SimpleCursorAdapter {
    public MyCursorAdapter(Context context, int layout, Cursor c,
                           String[] from, int[] to) {
        super(context, layout, c, from, to);
    }
    @Override
    public void bindView(View view, Context context, Cursor cursor){
        super.bindView(view, context, cursor);
        //从数据库中读取的cursor中获取笔记列表对应的颜色数据，并设置笔记颜色
        int x = cursor.getInt(cursor.getColumnIndex(NotePad.Notes.COLUMN_NAME_BACK_COLOR));
        /**
         * 白 255 255 255
         * 黄 247 216 133
         * 蓝 165 202 237
         * 绿 161 214 174
         * 红 244 149 133
         */
        switch (x){
            case NotePad.Notes.DEFAULT_COLOR:
                view.setBackgroundColor(Color.rgb(255, 255, 255));
                break;
            case NotePad.Notes.YELLOW_COLOR:
                view.setBackgroundColor(Color.rgb(247, 216, 133));
                break;
            case NotePad.Notes.BLUE_COLOR:
                view.setBackgroundColor(Color.rgb(165, 202, 237));
                break;
            case NotePad.Notes.GREEN_COLOR:
                view.setBackgroundColor(Color.rgb(161, 214, 174));
                break;
            case NotePad.Notes.RED_COLOR:
                view.setBackgroundColor(Color.rgb(244, 149, 133));
                break;
            default:
                view.setBackgroundColor(Color.rgb(255, 255, 255));
                break;
        }
    }
}
```
NoteList中的PROJECTION添加颜色项：
```
private static final String[] PROJECTION = new String[] {
            NotePad.Notes._ID, // 0
            NotePad.Notes.COLUMN_NAME_TITLE, // 1
            //扩展 显示时间 颜色
            NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE, // 2
            NotePad.Notes.COLUMN_NAME_BACK_COLOR,
    };
```
并且将NoteList中用的SimpleCursorAdapter改使用MyCursorAdapter：
```
 //修改为可以填充颜色的自定义的adapter，自定义的代码在MyCursorAdapter.java中
adapter = new MyCursorAdapter(
        this,
        R.layout.noteslist_item,
        cursor,
        dataColumns,
        viewIDs
    );
```
由于目前为止并没有设置颜色的选项，所以创建的笔记都是白色背景的，但是结合下一个功能，背景更换，让编辑笔记时的背景色跟笔记列表的该笔记背景色同为一种颜色。



响应变色：用户点击笔记时，笔记的背景颜色会发生变化，增强交互感。
![image](https://github.com/user-attachments/assets/37036ab0-ec59-4881-ae01-1970acf7af64)





背景颜色选择：用户可以为每条笔记选择一个自定义背景颜色，以便于管理和区分不同笔记。
![image](https://github.com/user-attachments/assets/2c6b8c9b-ef7d-4f4f-ba4a-500590fdc709)





笔记排序：用户可以根据笔记的创建时间、最后修改时间或者背景颜色来排序笔记，方便整理。
![image](https://github.com/user-attachments/assets/b2c85a2d-804b-4eb6-bedb-7e80d4a8822d)
  按颜色排序后
![image](https://github.com/user-attachments/assets/2fdd448d-9c97-48f5-8f25-bc5626e21450)








# 未来的计划
完成输出功能：支持将笔记导出为 .txt 或 .pdf 格式文件。
增加笔记分类：允许用户将笔记分类，便于管理。
UI 优化：提供更多主题选项，让用户可以自定义应用外观。
云同步功能：支持跨设备同步笔记，提升数据的可访问性。
