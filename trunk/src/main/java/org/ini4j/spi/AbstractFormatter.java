/*
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
package org.ini4j.spi;

import org.ini4j.CommentHandler;
import org.ini4j.Config;
import org.ini4j.OptionHandler;

import java.io.PrintWriter;

public abstract class AbstractFormatter implements OptionHandler, CommentHandler
{
    private static final char OPERATOR = '=';
    private static final char COMMENT = '#';
    private static final char SPACE = ' ';
    private static final String NEWLINE = "\n";
    private Config _config = Config.getGlobal();
    private PrintWriter _output;

    public Config getConfig()
    {
        return _config;
    }

    @Override public void handleComment(String comment)
    {
        for (String line : comment.split(NEWLINE))
        {
            getOutput().print(COMMENT);
            getOutput().println(line);
        }
    }

    @Override public void handleOption(String optionName, String optionValue)
    {
        if (getConfig().isStrictOperator())
        {
            if (getConfig().isEmptyOption() || (optionValue != null))
            {
                getOutput().print(escape(optionName));
                getOutput().print(OPERATOR);
            }

            if (optionValue != null)
            {
                getOutput().print(escape(optionValue));
            }

            if (getConfig().isEmptyOption() || (optionValue != null))
            {
                getOutput().println();
            }
        }
        else
        {
            String value = ((optionValue == null) && getConfig().isEmptyOption()) ? "" : optionValue;

            if (value != null)
            {
                getOutput().print(escape(optionName));
                getOutput().print(SPACE);
                getOutput().print(OPERATOR);
                getOutput().print(SPACE);
                getOutput().println(escape(value));
            }
        }
    }

    protected void setConfig(Config value)
    {
        _config = value;
    }

    protected PrintWriter getOutput()
    {
        return _output;
    }

    protected void setOutput(PrintWriter value)
    {
        _output = value;
    }

    protected String escape(String input)
    {
        return getConfig().isEscape() ? EscapeTool.getInstance().escape(input) : input;
    }
}
