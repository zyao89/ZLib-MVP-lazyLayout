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


