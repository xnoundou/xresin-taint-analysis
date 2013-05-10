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
 * @author Emil Ong
 */

package com.caucho.jaxb.property;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.caucho.jaxb.BinderImpl;
import com.caucho.jaxb.JAXBUtil;
import com.caucho.jaxb.NodeIterator;
import com.caucho.jaxb.mapping.Namer;
import com.caucho.util.L10N;

/**
 * a property for serializing/deserializing arrays
 */
public class BooleanArrayProperty extends ArrayProperty {
  private static final L10N L = new L10N(BooleanArrayProperty.class);

  public static final BooleanArrayProperty PROPERTY 
    = new BooleanArrayProperty();

  private BooleanArrayProperty()
  {
    super(BooleanProperty.PRIMITIVE_PROPERTY);
  }

  public Object read(Unmarshaller u, XMLStreamReader in, Object previous)
    throws IOException, XMLStreamException, JAXBException
  {
    boolean[] array = (boolean[]) previous;

    if (array == null)
      array = new boolean[1];
    else {
      boolean[] newArray = new boolean[array.length + 1];
      System.arraycopy(array, 0, newArray, 0, array.length);

      array = newArray;
    }

    array[array.length - 1] =
      ((Boolean) _componentProperty.read(u, in, null)).booleanValue();

    return array;
  }

  public Object bindFrom(BinderImpl binder, NodeIterator node, Object previous)
    throws IOException, JAXBException
  {
    boolean[] array = (boolean[]) previous;

    if (array == null)
      array = new boolean[1];
    else {
      boolean[] newArray = new boolean[array.length + 1];
      System.arraycopy(array, 0, newArray, 0, array.length);

      array = newArray;
    }

    Boolean b = (Boolean) _componentProperty.bindFrom(binder, node, null);

    array[array.length - 1] = b.booleanValue();

    return array;
  }

  public void write(Marshaller m, XMLStreamWriter out, 
                    Object value, Namer namer)
    throws IOException, XMLStreamException, JAXBException
  {
    if (value != null) {
      boolean[] array = (boolean[]) value;

      for (int i = 0; i < array.length; i++)
        BooleanProperty.PRIMITIVE_PROPERTY.write(m, out, array[i], namer);
    }
  }

  public Node bindTo(BinderImpl binder, Node node, 
                     Object value, Namer namer)
    throws IOException, JAXBException
  {
    QName qname = namer.getQName(value);
    QName name = JAXBUtil.qnameFromNode(node);
    Document doc = node.getOwnerDocument(); 

    if (! name.equals(qname))
      node = JAXBUtil.elementFromQName(qname, doc);

    binder.bind(value, node);

    if (value != null) {
      boolean[] array = (boolean[]) value;

      for (int i = 0; i < array.length; i++) {
        Node child = JAXBUtil.elementFromQName(qname, doc);
        node.appendChild(BooleanProperty.PRIMITIVE_PROPERTY.bindTo(binder, 
                                                                   child, 
                                                                   array[i], 
                                                                   namer));
      }
    }

    return node;
  }
}
