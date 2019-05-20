[中文API](ATTRIBUTES-ch.md)

## UltraRecyclerView
### setOrientation
```java
void setOrientation(int orientation)
```
Sets the scroll orientation.

### setPagerSnap
```java
void setPagerSnap(int gravity)
```
Enables or disables pager snap and sets align gravity.
```java
void setPagerSnap(int gravity, @Px int alignMargin)
```
Enables or disables pager snap, and sets align gravity and margin.
```java
boolean isPagerSnap()
```
Returns whether this view enables pager snap.

### setOnSnapListener
```java
void setOnSnapListener(GravityPagerSnapHelper.OnSnapListener onSnapListener)
```
Register a callback to be invoked when this view enables pager snap and changes position.

### setInfiniteLoop
```java
void setInfiniteLoop(boolean infiniteLoop)
```
Enables or disables the items infinite loop.
```java
boolean isInfiniteLoop()
```
Returns whether the items are infinite loop.

### AutoScroll
```java
void startAutoScroll()
```
Starts auto scrolling, and the duration time is 5s.
```java
void startAutoScroll(int duration)
```
Starts auto scrolling, and the duration is specified.
```java
void startAutoScrollDelayed(int delay)
```
Starts auto scrolling, the start delay-time is specified, and the duration is 5s.
```java
void startAutoScrollDelayed(int delay, int duration)
```
Starts auto scrolling, the duration and start delay-time are specified.
```java
void restartAutoScroll()
```
Restarts auto scrolling.
```java
void stopAutoScroll()
```
Stops auto scrolling.
```java
void setAutoScrollSpeed(int duration)
```
Sets auto scrolling speed.
```java
void setAutoScrollSpeed(int duration, Interpolator interpolator)
```
Sets auto scrolling speed and interpolator.
```java
boolean isAutoScroll()
```
Returns whether this view is auto scrolled.

### getCurrentPosition
```java
int getCurrentPosition()
```
Returns current position.

### getRealCount
```java
int getRealCount()
```
Returns real count.

### refresh
```java
void refresh()
```
Refresh adapter.

## BannerView
BannerView includes all above attributes, and also includes:
### setIndicator
```java
void setIndicatorVisibility(int visibility)
```
Sets visibility of the indicator.
```java
void setIndicatorBottomMargin(@Px int margin)
```
Sets bottom margin of the indicator.
```java
void setIndicatorSelectedWidth(@Px int selectedWidth)
```
Sets selected width of the indicator.
```java
void setIndicatorDefaultWidth(@Px int defaultWidth)
```
Sets default width of the indicator.
```java
void setIndicatorHeight(@Px int height)
```
Sets height of the indicator.
```java
void setIndicatorMargin(@Px int margin)
```
Sets inner margin of the indicator.
```java
void setIndicatorSelectedColor(@ColorInt int selectedColor)
```
Sets selected color of the indicator.
```java
void setIndicatorDefaultColor(@ColorInt int defaultColor)
```
Sets default color of the indicator.
