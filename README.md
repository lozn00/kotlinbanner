# Banner Kotlin

demo演示了把一个文本作为view,或者把一个图片作为view,欢迎集成使用
## 这是一个轮播图,使用viewpager实现页面的滑动，使用postDelay实现自调循环
可以显示标题,也可以显示总数点,效果图可以下载下来试试  就知道啦!
# 使用方法
Jcenter gradle 目标sdk25.0.3 
```
 compile 'cn.qssq666:kotlin.banner:0.1'
 
 
    <cn.qssq666.kotlin.banner.Banner
        android:id="@+id/asl"
        android:layout_width="match_parent"
        android:layout_height="150dp"></cn.qssq666.banner.Banner>
```
 图片演示

![演示图片地址](https://github.com/qssq/kotlinbanner/blob/master/Pictures/1.gif)

#本demo抽取自java android  
[java banner https://github.com/qssq/banner](https://github.com/qssq/banner)

# 常用方法

```
   bannerCurrent.setItem(listImgInfo)
         bannerCurrent.autoScroll = true;
         bannerCurrent.scrollTime = 1000;
         bannerCurrent.setNeedPoint(true)
         bannerCurrent.setNeedTitle(true)
   banner.getInternalViewPager().setPageTransformer(false, transformer);
   
```
   
# 本库的优点

优点就是布局diy基于布局文件的，这是别人的banner库最大的痛处，这个痛处导致稍微变通一下就没法解决了，这不是需求的变态，而是库写的不好,文档写的那么花哨，很多点赞，却坑了很多人,这是何必呢？
所以各位多多给我点赞吧!
只需要创建一个一模一样的view_auto_scroll_layout 稍微调整一下就可以了，或者继承该类,因为可以这样，什么需求还做不到？能滚动的各种view都通通可以。
# 后续更新
 2017年7月19日 18:03:30  不需要实现imageUrl，因为banner基于view, 所以 图片加载部分直接又用户构思， 不会用的朋友可以跑demo.MainActivity.this有介绍
 # 吐槽
 受够了别人的banner库，banner多扩展差劲，bugN多 ，也是醉了，
 ok ，本banner非常精简没有那么多垃圾代码，而且无bug,接口形式 非常容易扩展，你要的动画翻页都有。
 完全可以自定义任意指示器 标题栏布局
 支持自己拿Viewpager,方便用户进行更多操作。甚至包括修改轮播逻辑。
# 常见调整
   隐藏指示点 隐藏标题 ，
```
  banner.setNeedPoint(true);
 banner.setNeedTitle(true);
           
```



 # 变态需求与解决方案
 
 
### 场景一 给指示器搞特效
让指示器或 标题在外部非viewpager区域显示需求
so easy ，只需要保持id什么一样就行了,所以复制一份R.layout.view_auto_scroll_layout然后把指示器容器布局弄到外面就行，然后继承Banner类复写getLayout 方法，因为指示器是根据findById来的，所以这个需求不在话下。
或者直接直接复制一个到自己的项目中，也行，android studio默认识别宿主项目 的布局文件，切记别把id给删除了。

### 场景二 给标题搞特效
自由通过Xml调整布局，比如让标题发生一下旋转，倒影，或者描边， 总所周知,通过xml调节的方法更亲民,扩展性更好,因此解决方案就是场景1
 采用接口形式 非常容易扩展
 抽出轮播view里面的布局，也就是说你可以不是图片，那么这个代码自己写就好 
 
### 场景三 微调
        //你能拿到这个对象 你还不知道怎么调间距吗？xml 或者 代码编写 都可以.你喜欢哪个姿势呢?
   /*     TextView titleView = banner.getTitleView();
        ViewGroup titleGroup = banner.getTitleGroup();
        LinearLayout pointViewGroup = banner.getPointViewGroup();*/

### 场景四
让指示点的选中和被选中是图片
ok，我已经提供了继承方法，  *protected View onCreatePointView(IImgInfo info, int size, int margin)*
你只需要`集成复写就行
对于选中 和不选中 复写这个方法就行 。  
```
来，那种无限循环实际上网上的项目都有bug,为了提供一个完全无bug兼容对话框里面的布局等 设计，我就自己写了这个。