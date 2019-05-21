[Englis API](ATTRIBUTES.md)

## UltraRecyclerView
### setOrientation
```java
void setOrientation(int orientation)
```
设置滑动方向。

### setPagerSnap
```java
void setPagerSnap(int gravity)
```
设置分页模式，包括对齐方式。对齐方式包括顶部对齐(Gravity.START)、居中对齐(Gravity.CENTER)和尾部对齐(Gravity.END)。
```java
void setPagerSnap(int gravity, @Px int alignMargin)
```
设置分页模式，包括对齐方式和距离。对齐方式包括顶部对齐(Gravity.START)、居中对齐(Gravity.CENTER)和尾部对齐(Gravity.END)。
```java
boolean isPagerSnap()
```
是否开启分页模式。

### setOnSnapListener
```java
void setOnSnapListener(GravityPagerSnapHelper.OnSnapListener onSnapListener)
```
当开启分页模式并且位置发生改变时回调。

### setInfiniteLoop
```java
void setInfiniteLoop(boolean infiniteLoop)
```
设置无限循环。
```java
boolean isInfiniteLoop()
```
是否开启无限循环。

### AutoScroll
```java
void startAutoScroll()
```
开启自动滑动，间隔默认5s。
```java
void startAutoScroll(int duration)
```
开启自动滑动，指定间隔时间。
```java
void startAutoScrollDelayed(int delay)
```
延迟开启自动滑动，指定延迟时间，间隔默认5s。
```java
void startAutoScrollDelayed(int delay, int duration)
```
延迟开启自动滑动，指定延迟时间和间隔。
```java
void restartAutoScroll()
```
重启自动滑动。
```java
void stopAutoScroll()
```
停止自动滑动。
```java
void setAutoScrollSpeed(int duration)
```
设置自动滑动的速度。
```java
void setAutoScrollSpeed(int duration, Interpolator interpolator)
```
设置自动滑动的速度和加速器。
```java
boolean isAutoScroll()
```
是否开启自动滑动。

### getCurrentPosition
```java
int getCurrentPosition()
```
获得当前位置。

### getRealCount
```java
int getRealCount()
```
获得真实数据个数。

### refresh
```java
void refresh()
```
更新adapter。

## BannerView
BannerView包含以上所有属性设置，并且包含：
### setIndicator
```java
void setIndicatorVisibility(int visibility)
```
设置指示器的可见性。
```java
void setIndicatorBottomMargin(@Px int margin)
```
设置指示器到底部的距离。
```java
void setIndicatorSelectedWidth(@Px int selectedWidth)
```
设置指示器中选中的宽度。
```java
void setIndicatorDefaultWidth(@Px int defaultWidth)
```
设置指示器中默认的宽度。
```java
void setIndicatorHeight(@Px int height)
```
设置指示器高度。
```java
void setIndicatorMargin(@Px int margin)
```
设置指示器中的间距。
```java
void setIndicatorSelectedColor(@ColorInt int selectedColor)
```
设置指示器中选中的颜色。
```java
void setIndicatorDefaultColor(@ColorInt int defaultColor)
```
设置指示器中默认的颜色。
