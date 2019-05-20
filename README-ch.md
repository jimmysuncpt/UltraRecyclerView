### UltraRecyclerView

[English Document](README.md)

#### 简介
`UltraRecyclerView`是一个封装多种特性的RecyclerView。

#### 截图
<img src="images/horizontal.gif" width="40%" height="40%"/>
<img src="images/vertical.gif" width="40%" height="40%"/>

#### 主要功能

* 支持横向滑动／纵向滑动
* 支持分页滑动，并且支持对齐方式和距离
* 支持循环滚动
* 支持定时滚动，计时器使用Handler实现
* BannerView内置指示器，支持设置底部距离、已选/默认的颜色和宽度、高度和内部距离

以上特性支持同时使用。

#### 使用方法

版本请参考mvn repository上的最新版本（目前最新版本是1.0.0），最新的都会发布到 jcenter 上，确保配置了这个仓库源，然后引入依赖：

使用Gradle:

```
// gradle

```
或者maven：

```
// pom.xml in maven

```

在layout中使用UltraRecyclerView：

activity_recycler_view.xml

```xml
<com.jimmysun.ultrarecyclerview.UltraRecyclerView
    android:id="@+id/ultra_recycler_view"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:orientation="vertical"
    app:alignGravity="start"
    app:alignMargin="0dp"
    app:infiniteLoop="true"/>
```

或者使用BannerView:

```xml
<com.jimmysun.ultrarecyclerview.BannerView
    android:id="@+id/banner_view"
    android:layout_width="match_parent"
    android:layout_height="150dp"
    app:alignGravity="center"
    app:alignMargin="0dp"
    app:infiniteLoop="true"
    app:indicatorBottomMargin="8dp"
    app:indicatorSelectedColor="#FFFFFFFF"
    app:indicatorDefaultColor="#4D868E9E"
    app:indicatorSelectedWidth="10dp"
    app:indicatorDefaultWidth="10dp"
    app:indicatorHeight="2dp"
    app:indicatorMargin="5dp"
    app:indicatorVisibility="visible"/>
```

可以参考以下步骤使用UltraRecyclerView:

```java
mUltraRecyclerView = findViewById(R.id.ultra_recycler_view);
// 初始化MyAdapter，其继承于RecyclerView.Adapter
mUltraRecyclerView.setAdapter(new MyAdapter(RecyclerView.VERTICAL));
// 设置方向
mUltraRecyclerView.setOrientation(RecyclerView.VERTICAL);
// 设置分页模式，包括对齐方式和距离
mUltraRecyclerView.setPagerSnap(Gravity.START, 0);
// 设置无限循环
mUltraRecyclerView.setInfiniteLoop(true);
// 开启自动滚动
mUltraRecyclerView.startAutoScroll(2000);
mUltraRecyclerView.setAutoScrollSpeed(500);
```

或者在Java中使用BannerView：

```java
mBannerView = findViewById(R.id.banner_view);
// 初始化MyAdapter，其继承于RecyclerView.Adapter
mBannerView.setAdapter(new MyAdapter(RecyclerView.HORIZONTAL));
// 设置分页模式
mBannerView.setPagerSnap(Gravity.CENTER);
// 设置无限循环
mBannerView.setInfiniteLoop(true);
// 设置指示器的样式
mBannerView.setIndicatorBottomMargin(24);
mBannerView.setIndicatorSelectedColor(Color.GREEN);
mBannerView.setIndicatorDefaultColor(Color.WHITE);
```

Api接口详情请参考[文档](ATTRIBUTES-ch.md)

#### FAQ
* 如何刷新数据？可以使用如下方法：
    * ultraRecyclerView.refresh();

#### 示例

[Demo工程](https://github.com/jimmysuncpt/UltraRecyclerView/tree/master/demo)

#### 开源许可证
`UltraRecyclerView`遵循MIT开源许可证协议。
