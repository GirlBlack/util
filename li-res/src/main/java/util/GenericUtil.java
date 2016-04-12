package com.sprucetec.tms.utils.util;

import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.List;
import java.util.Queue;
import java.util.HashSet;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 集合工具类
 *
 * 交并差集
 * Title: GenericUtil.java<br>
 * Description: <br>
 * Copyright: Copyright (c) 2016<br>
 * Company: 北京云杉世界信息技术有限公司<br>
 *
 * @author lili
 * 2016年3月28日
 */
public class GenericUtil {

    /**
     * 合并两个有相同元素类型的 {@code java.util.List}。
     * <ul>
     * <li>{@code listA == null && listB == null} --> 返回 {@link #getHashSet()}。</li>
     * <li>{@code listA != null && listB == null} --> 返回 {@code listA}。</li>
     * <li>{@code listA == null && listB != null} --> 返回 {@code listB}。</li>
     * <li>{@code listA != null && listB != null} --> 返回 {@code listA} 和 {@code listB} 的并集。
     * </li>
     * </ul>
     *
     * @param <T> {@code List} 中保存的对象。
     * @param listA 第一个 {@code List}。
     * @param listB 第二个 {@code List}。
     * @return 返回 {@code ListA} 和 {@code listB} 的并集。
     */
    public static <T> List<T> unionHashList(List<T> listA, List<T> listB) {
        boolean isEmptyListA = isEmpty(listA);
        boolean isEmptyListB = isEmpty(listB);
        if (isEmptyListA && isEmptyListB)
            return getList();
        if (isEmptyListA && !isEmptyListB)
            return listB;
        if (!isEmptyListA && isEmptyListB)
            return listA;
        List<T> result = getList(listA);
        result.addAll(listB);
        return result;
    }

    /**
     * 取两个有相同元素类型的 {@code java.util.Set} 的交集，即公共部份的新的 {@code java.util.Set}。
     * <ul>
     * <li>{@code listA == null && listB == null} --> 返回 {@code null}。</li>
     * <li>{@code listA != null && listB == null} --> 返回 {@code null}。</li>
     * <li>{@code listA == null && listB != null} --> 返回 {@code null}。</li>
     * <li>{@code listA != null && listB != null} --> 返回 {@code listA} 和 {@code listB} 的交集。
     * </li>
     * </ul>
     *
     * @param <T> {@code List} 中保存的对象。
     * @param listA 第一个 {@code List}。
     * @param listB 第二个 {@code List}。
     * @return 返回 {@code listA} 和 {@code listB} 的交集。
     */
    public static <T> List<T> intersectHashList(List<T> listA, List<T> listB) {
        if (isEmpty(listA) || isEmpty(listB))
            return null;
        List<T> result = getList(listA);
        result.retainAll(listB);
        return result;
    }

    /**
     * 移除 {@code listA} 中那些包含在 {@code listB} 中的元素。<br />
     * 此方法不会修改 {@code listA}，只是复制一份作相应操作，返回的是全新的 {@code List} 对象。
     * <ul>
     * <li>{@code listA == null} --> 返回 {@code null}。</li>
     * <li>{@code listB == null} --> 返回 {@code listA}。</li>
     * <li>{@code listA != null && listB != null} --> 返回 {@code listA} 和 {@code listB}
     * 的不对称差集。</li>
     * </ul>
     *
     * @param <T> {@code List} 中保存的对象。
     * @param listA 第一个 {@code List}。
     * @param listB 第二个 {@code List}。
     * @return 返回 {@code listA} 和 {@code listB} 的不对称差集。
     */
    public static <T> List<T> differenceList(List<T> listA, List<T> listB) {
        if (isEmpty(listA))
            return null;
        if (isEmpty(listB))
            return listA;
        List<T> result = getList(listA);
        result.removeAll(listB);
        return result;
    }

    /**
     * 合并两个有相同元素类型的 {@code java.util.Set}。
     * <ul>
     * <li>{@code setA == null && setB == null} --> 返回 {@link #getHashSet()}。</li>
     * <li>{@code setA != null && setB == null} --> 返回 {@code setA}。</li>
     * <li>{@code setA == null && setB != null} --> 返回 {@code setB}。</li>
     * <li>{@code setA != null && setB != null} --> 返回 {@code setA} 和 {@code setB} 的并集。
     * </li>
     * </ul>
     *
     * @param <T> {@code Set} 中保存的对象。
     * @param setA 第一个 {@code Set}。
     * @param setB 第二个 {@code Set}。
     * @return 返回 {@code setA} 和 {@code setB} 的并集。
     */
    public static <T> Set<T> unionHashSet(Set<T> setA, Set<T> setB) {
        boolean isEmptySetA = isEmpty(setA);
        boolean isEmptySetB = isEmpty(setB);
        if (isEmptySetA && isEmptySetB)
           return getHashSet();
        if (isEmptySetA && !isEmptySetB)
            return setB;
        if (!isEmptySetA && isEmptySetB)
            return setA;
        Set<T> result = getHashSet(setA);
        result.addAll(setB);
        return result;
    }

    /**
     * 取两个有相同元素类型的 {@code java.util.Set} 的交集，即公共部份的新的 {@code java.util.Set}。
     * <ul>
     * <li>{@code setA == null && setB == null} --> 返回 {@code null}。</li>
     * <li>{@code setA != null && setB == null} --> 返回 {@code null}。</li>
     * <li>{@code setA == null && setB != null} --> 返回 {@code null}。</li>
     * <li>{@code setA != null && setB != null} --> 返回 {@code setA} 和 {@code setB} 的交集。
     * </li>
     * </ul>
     *
     * @param <T> {@code Set} 中保存的对象。
     * @param setA 第一个 {@code Set}。
     * @param setB 第二个 {@code Set}。
     * @return 返回 {@code setA} 和 {@code setB} 的交集。
     */
    public static <T> Set<T> intersectHashSet(Set<T> setA, Set<T> setB) {
        if (isEmpty(setA) || isEmpty(setB))
            return null;
        Set<T> result = getHashSet(setA);
        result.retainAll(setB);
        return result;
    }

    /**
     * 移除 {@code setA} 中那些包含在 {@code setB} 中的元素。<br />
     * 此方法不会修改 {@code setA}，只是复制一份作相应操作，返回的是全新的 {@code Set} 对象。
     * <ul>
     * <li>{@code setA == null} --> 返回 {@code null}。</li>
     * <li>{@code setB == null} --> 返回 {@code setA}。</li>
     * <li>{@code setA != null && setB != null} --> 返回 {@code setA} 和 {@code setB}
     * 的不对称差集。</li>
     * </ul>
     *
     * @param <T> {@code Set} 中保存的对象。
     * @param setA 第一个 {@code Set}。
     * @param setB 第二个 {@code Set}。
     * @return 返回 {@code setA} 和 {@code setB} 的不对称差集。
     */
    public static <T> Set<T> differenceHashSet(Set<T> setA, Set<T> setB) {
        if (isEmpty(setA))
            return null;
        if (isEmpty(setB))
            return setA;
        Set<T> result = getHashSet(setA);
        result.removeAll(setB);
        return result;
    }

    /**
     * 取两个有相同元素类型的 {@code java.util.Set} 的补集。
     *
     * @param <T> {@code Set} 中保存的对象。
     * @param setA 第一个 {@code Set}。
     * @param setB 第二个 {@code Set}。
     * @return 返回 {@code setA} 和 {@code setB} 的补集。
     */
    public static <T> Set<T> complementHashSet(Set<T> setA, Set<T> setB) {
        return differenceHashSet(unionHashSet(setA, setB), intersectHashSet(setA, setB));
    }

    /**
     * 用该方法来代替 {@code new HashSet<T>()} 方式获得新的 {@code java.util.Set} 的实例对象。
     *
     * @param <T> {@code Set<T>} 中保存的对象。
     * @return 返回 {@code java.util.Set<T>} 关于 {@code java.util.HashSet<T>} 实现的新实例。
     */
    public static <T> Set<T> getHashSet() {
        return new HashSet<T>();
    }

    /**
     * 用该方法来代替 <code>new HashSet<T>(Collection<? extends T> c)</code> 方式获得新的  
     * {@code java.util.Set} 的实例对象。  
     *
     * @param <T> {@code Set} 中保存的对象。  
     * @param c 其中的元素将存放在新的 {@code set} 中的 {@code collection}。  
     * @return 返回 {@code java.util.Set<T>} 关于 {@code java.util.HashSet<T>} 实现的新实例。  
     */
    public static <T> Set<T> getHashSet(Set<? extends T> c) {
        if (isEmpty(c))
            return new HashSet<T>();

        return new HashSet<T>(c);
    }

    public static <T> boolean isEmpty(Collection <? extends T> collection) {
        return (collection == null || collection.size() == 0);
    }


    /**
     * 用该方法来代替 {@code new ArrayList<T>()} 方式获得新的 {@code java.util.List} 的实例对象。
     *
     * @param <T> {@code List<T>} 中保存的对象。
     * @return 返回 {@code java.util.List<T>} 关于 {@code java.util.ArrayList<T>} 实现的新实例。
     */
    public static <T> List<T> getList() {
        return new ArrayList<T>();
    }

    /**
     * 用该方法来代替 {@code new ArrayList<T>()} 方式获得新的 {@code java.util.List} 的实例对象。
     *
     * @param <T> {@code List<T>} 中保存的对象。
     * @param c 其中的元素将存放在新的 {@code list} 中的 {@code collection}。
     * @return 返回 {@code java.util.List<T>} 关于 {@code java.util.ArrayList<T>} 实现的新实例。
     */
    public static <T> List<T> getList(List<? extends T> c) {
        if (isEmpty(c)) {
            return new ArrayList<T>();
        }
        return new ArrayList<T>(c);
    }
}
