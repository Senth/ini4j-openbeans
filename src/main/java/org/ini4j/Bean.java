/*
 * Copyright 2005 [ini4j] Development Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ini4j;

import java.io.File;

import java.lang.reflect.Method;

import java.net.URI;
import java.net.URL;

import java.util.TimeZone;

class Bean
{
    private static final String PARSE_METHOD = "valueOf";

    protected static Object parseSpecialValue(String value, Class clazz) throws IllegalArgumentException
    {
        Object o;

        try
        {
            if (clazz == File.class)
            {
                o = new File(value);
            }
            else if (clazz == URL.class)
            {
                o = new URL(value);
            }
            else if (clazz == URI.class)
            {
                o = new URI(value);
            }
            else if (clazz == Class.class)
            {
                o = Class.forName(value);
            }
            else if (clazz == TimeZone.class)
            {
                o = TimeZone.getTimeZone(value);
            }
            else
            {

                // look for "valueOf" converter method
                Method parser = clazz.getMethod(PARSE_METHOD, new Class[] { String.class });

                o = parser.invoke(null, new Object[] { value });
            }
        }
        catch (Exception x)
        {
            throw (IllegalArgumentException) new IllegalArgumentException().initCause(x);
        }

        return o;
    }

    protected static Object parseValue(String value, Class clazz) throws IllegalArgumentException
    {
        if (clazz == null)
        {
            throw new IllegalArgumentException("null argument");
        }

        Object o = null;

        if (value == null)
        {
            o = zero(clazz);
        }
        else if (clazz.isPrimitive())
        {
            try
            {
                if (clazz == Boolean.TYPE)
                {
                    o = Boolean.valueOf(value);
                }
                else if (clazz == Byte.TYPE)
                {
                    o = Byte.valueOf(value);
                }
                else if (clazz == Character.TYPE)
                {
                    o = new Character(value.charAt(0));
                }
                else if (clazz == Double.TYPE)
                {
                    o = Double.valueOf(value);
                }
                else if (clazz == Float.TYPE)
                {
                    o = Float.valueOf(value);
                }
                else if (clazz == Integer.TYPE)
                {
                    o = Integer.valueOf(value);
                }
                else if (clazz == Long.TYPE)
                {
                    o = Long.valueOf(value);
                }
                else if (clazz == Short.TYPE)
                {
                    o = Short.valueOf(value);
                }
            }
            catch (Exception x)
            {
                throw (IllegalArgumentException) new IllegalArgumentException().initCause(x);
            }
        }
        else
        {
            if (clazz == String.class)
            {
                o = value;
            }
            else if (clazz == Character.class)
            {
                o = new Character(value.charAt(0));
            }
            else
            {
                o = parseSpecialValue(value, clazz);
            }
        }

        return o;
    }

    protected static Object zero(Class clazz)
    {
        Object o = null;

        if (clazz.isPrimitive())
        {
            if (clazz == Boolean.TYPE)
            {
                o = Boolean.FALSE;
            }
            else if (clazz == Byte.TYPE)
            {
                o = new Byte((byte) 0);
            }
            else if (clazz == Character.TYPE)
            {
                o = new Character('\0');
            }
            else if (clazz == Double.TYPE)
            {
                o = new Double(0.0);
            }
            else if (clazz == Float.TYPE)
            {
                o = new Float(0.0f);
            }
            else if (clazz == Integer.TYPE)
            {
                o = new Integer(0);
            }
            else if (clazz == Long.TYPE)
            {
                o = new Long(0L);
            }
            else if (clazz == Short.TYPE)
            {
                o = new Short((short) 0);
            }
        }

        return o;
    }
}
