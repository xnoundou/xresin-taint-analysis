/*
 * Copyright (c) 1998-2012 Caucho Technology -- all rights reserved
 *
 * This file is part of Resin(R) Open Source
 *
 * Each copy or derived work must preserve the copyright notice and this
 * notice unmodified.
 *
 * Resin Open Source is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * Resin Open Source is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE, or any warranty
 * of NON-INFRINGEMENT.  See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Resin Open Source; if not, write to the
 *
 *   Free Software Foundation, Inc.
 *   59 Temple Place, Suite 330
 *   Boston, MA 02111-1307  USA
 *
 * @author Charles Reich
 */

package com.caucho.quercus.lib.simplexml;

import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.JsonEncodeContext;
import com.caucho.quercus.env.NullValue;
import com.caucho.quercus.env.QuercusClass;
import com.caucho.quercus.env.StringValue;
import com.caucho.quercus.env.Value;

import java.util.Iterator;

/**
 * SimpleXMLElement object oriented API facade.
 * Also acts as the DOM document.
 */
public class SimpleXMLAttribute extends SimpleXMLElement
{
  protected SimpleXMLAttribute(Env env,
                               QuercusClass cls,
                               SimpleXMLElement parent,
                               String name)
  {
    super(env, cls, parent, name);
  }

  protected SimpleXMLAttribute(Env env,
                               QuercusClass cls,
                               SimpleXMLElement parent,
                               String name,
                               String namespace,
                               StringValue text)
  {
    super(env, cls, parent, name, namespace);

    _text = text;
  }

  @Override
  protected void addNamespace(String prefix, String namespace)
  {
    if (_parent != null)
      _parent.addNamespace(prefix, namespace);
  }

  /**
   * Adds a namespace attribute to this node.
   */
  @Override
  protected void addNamespaceAttribute(Env env, String name,
                                       String namespace)
  {
    if (_parent != null)
      _parent.addNamespaceAttribute(env, name, namespace);
  }

  /**
   * Required for 'foreach'. When only values are specified in
   * the loop <code>foreach($a as $b)</code>, this method
   * should return an iterator that contains Java objects
   * that will be wrapped in a Value.
   *
   * When a 'foreach' loop with name/value pairs
   * i.e. <code>foreach($a as $b=>$c)</code>
   * invokes this method, it expects an iterator that
   * contains objects that implement Map.Entry.
   */
  public Iterator iterator()
  {
    if (_attributes != null)
      return _attributes.iterator();
    else
      return null;
  }

  @Override
  protected void toXMLImpl(StringValue sb)
  {
    sb.append(" ");

    if (_prefix != null && ! "".equals(_prefix)) {
      sb.append(_prefix);
      sb.append(":");
    }

    sb.append(_name);
    sb.append("=\"");
    if (_text != null)
      sb.append(_text);
    sb.append("\"");
  }

  /**
   * Implementation for getting the indices of this class.
   * i.e. <code>$a->foo[0]</code>
   */
  public Value __get(Env env, Value indexV)
  {
    if (indexV.isString()) {
      String name = indexV.toString();

      SimpleXMLElement attr = getAttribute(name);

      if (attr != null)
        return wrapJava(env, _cls, attr);
      else
        return NullValue.NULL;
    }
    else if (indexV.isLongConvertible()) {
      int i = indexV.toInt();

      if (i < _attributes.size())
        return wrapJava(env, _cls, _attributes.get(i));

      return NullValue.NULL;
    }
    else
      return NullValue.NULL;
  }

  @Override
  protected void jsonEncodeImpl(Env env,
                                JsonEncodeContext context,
                                StringValue sb,
                                boolean isTop,
                                boolean isPrintBraces)
  {
    sb.append('"');

    if (_prefix != null && ! "".equals(_prefix)) {
      sb.append(_prefix);
      sb.append(":");
    }

    sb.append(_name);
    sb.append('"');

    sb.append(':');

    sb.append('"');

    if (_text != null) {
      jsonEncodeText(env, context, sb, _text);
    }

    sb.append('"');
  }
}
