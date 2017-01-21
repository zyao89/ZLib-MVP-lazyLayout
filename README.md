# ZLib-MVP-lazyLayout
快速开发，新MVP框架

---

## zcore中包含两个框架：

> 1、com.zyao.zcore中包含的是典型mvp框架；

* 使用方法：
* P层：继承BaseActivity，BaseActivityPresenter，BaseFragment，BaseFragmentPresenter，BasePresenter
* V层：继承BaseViewHandler，BaseActivityViewHandler，BaseFragmentViewHandler
* M层：暂无基类


> 2、com.zyao.zcore2中采用rxjava + retrofit2 + dagger2

* P层：BaseComponentActivity，BaseComponentFragment，BaseComponentPresenter
* V层：BaseComponentActivityViewHandler，BaseComponentFragmentViewHandler，BaseComponentViewHandler
* M层：暂无基类

---

## Extra扩展实现类，可直接继承使用：

> 1、带有标题栏的ActivityViewHandler
```java
public class MainActivityViewHandler extends BaseTitleBarComponentActivityViewHandler<CoordinatorLayout> implements MainContract.IViewHandler
{}
```

> 2、带标题的侧滑菜单
```java
public class MainActivityViewHandler_2 extends BaseDrawerLayoutComponentActivityViewHandler implements MainContract.IViewHandler
{}
```

> 3、带标题的懒人模式
```java
public class MainActivityViewHandler extends BaseLazyCoordinatorComponentActivityViewHandler implements MainContract.IViewHandler
{}
```

> 4、带标题和ViewPager的懒人模式
```java
public class MainActivityViewHandler extends BaseLazyViewPagerComponentActivityViewHandler implements MainContract.IViewHandler
{}
```
