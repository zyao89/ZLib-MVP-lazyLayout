package com.zyao.zutils.common;

import android.content.Context;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射工具类
 * Created by Zyao89 on 2015/10/21.
 */
public class ReflectUtils
{
    private static ReflectUtils mInstance = null;
    private final Context mContext;

    private ReflectUtils (Context context)
    {
        this.mContext = context;
    }

    static ReflectUtils with (Context context)
    {
        if (mInstance == null)
        {
            synchronized (ReflectUtils.class)
            {
                if (mInstance == null)
                {
                    mInstance = new ReflectUtils(context);
                }
            }
        }
        return mInstance;
    }

    /**
     * 反射方法
     *
     * @param className
     * @param methodName
     * @param args
     *
     * @return
     *
     * @throws Exception
     */
    public Object invokeMethod (String className, String methodName, Object[] args)
    {
        Object result = null;
        try
        {
            Class ownerClass = Class.forName(className);
            Object owner = ownerClass.newInstance();

            Class[] argsClass = new Class[args.length];

            for (int i = 0, j = args.length; i < j; i++)
            {
                argsClass[i] = args[i].getClass();
            }

            Method method = ownerClass.getMethod(methodName, argsClass);

            result = method.invoke(owner, args);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (InstantiationException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        }
        catch (InvocationTargetException e)
        {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 反射成员变量
     *
     * @param className
     * @param fieldName
     * @param args
     *
     * @return
     */
    public Field invokeField (String className, String fieldName, Object args)
    {
        Field field = null;
        try
        {
            Class cls = Class.forName(className);
            //			Object owner = cls.newInstance();
            field = cls.getDeclaredField(fieldName);
            field.setAccessible(true);//设置允许访问
            field.set(field, args);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        catch (NoSuchFieldException e)
        {
            e.printStackTrace();
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }
        return field;
    }

    /**
     * 反射静态方法
     *
     * @param className
     * @param methodName
     * @param args
     *
     * @return
     */
    public Object invokeStaticMethod (String className, String methodName, Object[] args)
    {
        Object result = null;
        try
        {
            Class ownerClass = Class.forName(className);

            Class[] argsClass = new Class[args.length];

            for (int i = 0, j = args.length; i < j; i++)
            {
                argsClass[i] = args[i].getClass();
            }

            Method staticMethod = ownerClass.getDeclaredMethod(methodName, argsClass);

            result = staticMethod.invoke(ownerClass, args);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        catch (InvocationTargetException e)
        {
            e.printStackTrace();
        }
        return result;
    }
}
