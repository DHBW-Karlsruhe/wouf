/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bh.gui.chart;

/**
 *
 * @author Marco Hammel
 */
public interface IBHAddGroupValue extends IBHAddValue {

    /**
     * default keys for intervall as group min is index 0; max index 1
     */
    public static final String[] MIN_MAX_GROUP = {"min", "max"};
    public static final int MAX_POS = 1;
    public static final int MIN_POS = 1;

    /**
     * Have to be called bevor the stackedBarChart is used for grouped Values
     * @param categories
     */
    public void setDefaultGroupSettings(String[] categories);

    /**
     * Mapping each value by using catIdx to a specified group which have to be setted by
     * <code>setDefaultGroupSettings</code>
     * @param value
     * @param row
     * @param columnKey
     * @param catIdx
     */
    public void addValue(Number value, Comparable row, Comparable<String> columnKey, int catIdx);
}
