# Android-Studio-mid-Note
Android Studio 期中NotePad作业

# 项目简介
这是一个基于 Android Studio 开发的 NotePad 应用，作为期中作业，项目在原有基础上进行了功能扩展，旨在提供更丰富的笔记管理体验。支持时间戳、搜索、UI 美化、响应变色、笔记排序、背景颜色修改以及文件输出等功能。

# 功能特性
时间戳：每个笔记自动生成创建时间和修改时间。
搜索功能：用户可以通过关键词搜索笔记。
UI 美化：应用的基础颜色为 HoloLight，界面设计简洁且美观。
响应变色：点击笔记时，背景颜色会发生变化，增强交互反馈。
输出功能（不完善）：支持将笔记内容导出为系统文件（该功能尚未完全实现）。
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

# 基础功能
时间戳：每条笔记自动记录创建时间和最后修改时间。用户可以在笔记详情页面查看这些时间。

搜索功能：用户可以通过输入关键词进行笔记搜索，快速定位到相关内容。

# 扩展功能
UI 美化：基础颜色为 HoloLight，整体 UI 简洁明了，用户体验友好。

响应变色：用户点击笔记时，笔记的背景颜色会发生变化，增强交互感。

输出功能：用户可以将笔记内容导出为系统文件，功能尚在开发中，当前支持文本格式输出。

背景颜色选择：用户可以为每条笔记选择一个自定义背景颜色，以便于管理和区分不同笔记。

笔记排序：用户可以根据笔记的创建时间、最后修改时间或者背景颜色来排序笔记，方便整理。
# 主界面
显示所有笔记，点击任意笔记可以查看和编辑。


截图提示：展示笔记列表和 UI 美化效果。

# 搜索功能
用户可以通过关键字搜索笔记。


截图提示：展示搜索框和搜索结果。

# 笔记详情
用户可以点击笔记进入详情页面，编辑笔记内容，修改背景颜色。


截图提示：展示笔记的编辑界面，突出显示响应变色和背景颜色选择功能。

# 输出功能
展示笔记导出的界面（如果功能已实现）。


截图提示：展示导出笔记为文件的界面或按钮（功能未完全实现时标注）。

# 未来的计划
输出功能完善：支持将笔记导出为 .txt 或 .pdf 格式文件。
增加笔记分类：允许用户将笔记分类，便于管理。
UI 优化：提供更多主题选项，让用户可以自定义应用外观。
云同步功能：支持跨设备同步笔记，提升数据的可访问性。

