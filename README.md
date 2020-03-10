# ONE-Unofficial

[Read in English](https://github.com/Uphie/ONE-Unofficial/wiki/ONE-Unofficial)

**此项目已停止维护。**

非官方版“**一个(ONE)**”，一个比官方版更优秀的版本。

"一个" 是一个韩寒主编的电子杂志 (**http://wufazhuce.com/**)。

刚开始接触到“一个”时，深深地被它简洁漂亮的UI设计所打动，当然最核心的是它的内容，每天一更新的金句、文章、问题、创意设计，很投合现在文艺青年的心理。后来作为读者时间长了以后发现广告越来越多，新版本的UI设计越来越不协调，渐渐有些失望。于是有了DIY的想法，在这个项目中改了小部分UI和功能，剃除了广告。

新版的官方ONE已经更新到了3.0以上，与旧版改变很大，“东西”板块已经停止更新，增加一些其他板块，此项目会根据可行性和大家的反馈跟进更新，谢谢！

关于Api问题，接口由非授权渠道获取，笔者在微博上告知过亭林镇工作室但未获回应，如有侵权请及时告知，笔者可此项目予以删除。

引用的库
----------
 * [butterknife](https://github.com/JakeWharton/butterknife)
 * [fastjson](https://github.com/alibaba/fastjson)
 * [android-async-http](https://github.com/loopj/android-async-http)
 * [fresco](https://github.com/facebook/fresco)
 * [paper](https://github.com/pilgr/Paper)
 * [android-PullToRefresh](https://github.com/chrisbanes/Android-PullToRefresh)

第三方服务
-----------
* 友盟统计Sdk
* 友盟用户反馈Sdk
* 友盟自动更新Sdk
* Facebook分享Sdk
  
截图
-----------

![Home](https://github.com/Uphie/ONE-Unofficial/blob/master/Screenshots/screenshot-1.png) 
![Article](https://github.com/Uphie/ONE-Unofficial/blob/master/Screenshots/screenshot-2.png)

![Question](https://github.com/Uphie/ONE-Unofficial/blob/master/Screenshots/screenshot-3.png) 
![Thing](https://github.com/Uphie/ONE-Unofficial/blob/master/Screenshots/screenshot-4.png)

版本
----------
注：建议使用最新代码编译后再预览，代码会不时小幅更新。
*  2016-2-19，修复了一些bug。目前的主框架逐渐显露出了一些不足，无法满足许多逻辑细节和体验细节。希望大家多多提[issue](https://github.com/Uphie/ONE-Unofficial/issues) :)

*  2016-2-3，增加了“首页”、“文章”、“问题”、“东西”四部分左滑刷新的功能，对PullToRefresh有轻微修改。下一版本会完善一些细节的地方。

*  2016-1-26，主要在个人中心增加了“关于”、“分享给好友”、“意见反馈”、“评分”和自动更新功能。下一个版本会增加左滑刷新功能。

*  2016-1-20，这两天修(zhe)改(teng)了下代码，增加了缓存机制，包括图片缓存和文件缓存，限制了最多只能查看往期7天的内容。
首次尝试了使用[paper](https://github.com/pilgr/Paper)，一个优秀的数据存储库，但一个缺点是paper不能设置过期时间，只好自己判断了。下一个版本会增加意见反馈新功能。

* 2016-1-8，由于工作的关系有一段时间没有维护了，现在拾了起来。从这版中开始使用日期作为版本名，更改了包名为studio.uphie.one。修复了程序长时间后台运行再打开界面重叠的bug，放弃了双击界面“喜欢”的功能。

