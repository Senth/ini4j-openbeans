/**
 * Copyright 2005,2009 Ivan SZKIBA
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ini4j;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class MultiMapTest
{
    private static final String KEY1 = "key1";
    private static final String KEY2 = "key2";
    private static final String KEY3 = "key3";
    private static final String VALUE1 = "value1";
    private static final String VALUE2 = "value2";
    private static final String VALUE3 = "value3";
    private static final String[] VALUES = { VALUE1, VALUE2, VALUE3 };
    private MultiMap<String, String> map;

    @Before public void setUp()
    {
        map = new MultiMapImpl<String, String>();
    }

    @Test public void testAdd()
    {
        map.add(KEY1, VALUE1);
        map.add(KEY1, VALUE2);
        map.add(KEY1, VALUE3);
        assertEquals(3, map.length(KEY1));
        map.add(KEY1, VALUE3, 0);
        assertEquals(4, map.length(KEY1));
        assertEquals(VALUE3, map.get(KEY1, 0));
        assertEquals(VALUE3, map.get(KEY1, 3));
        map.clear();
        assertTrue(map.isEmpty());
    }

    @Test public void testAll()
    {
        map.putAll(KEY1, Arrays.asList(VALUES));
        assertEquals(VALUES.length, map.length(KEY1));
        String[] values = map.getAll(KEY1).toArray(new String[] {});

        assertArrayEquals(VALUES, values);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testContainsValue()
    {
        map.containsValue(VALUE1);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testEntrySetValue()
    {
        map.entrySet();
    }

    @Test public void testRemove()
    {
        map.add(KEY1, VALUE1);
        map.add(KEY2, VALUE1);
        map.add(KEY2, VALUE2);
        map.add(KEY3, VALUE1);
        map.add(KEY3, VALUE2);
        map.add(KEY3, VALUE3);
        map.remove(KEY3, 1);
        assertEquals(VALUE2, map.get(KEY3, 1));
        map.remove(KEY3, 1);
        assertEquals(0, map.length(KEY3));
        assertEquals(VALUE1, map.get(KEY3));
        map.remove(KEY3);
        assertFalse(map.containsKey(KEY3));
        map.remove(KEY2);
        assertFalse(map.containsKey(KEY2));
        map.remove(KEY1);
        assertFalse(map.containsKey(KEY1));
        assertEquals(0, map.size());
        assertTrue(map.isEmpty());
    }

    @Test public void testValues()
    {
        map.put(KEY1, VALUE1);
        map.put(KEY2, VALUE2);
        map.add(KEY2, VALUE3);
        String[] values = map.values().toArray(new String[] {});

        Arrays.sort(values);
        assertArrayEquals(values, VALUES);
    }
}