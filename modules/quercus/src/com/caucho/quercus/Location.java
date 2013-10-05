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
 *   Free SoftwareFoundation, Inc.
 *   59 Temple Place, Suite 330
 *   Boston, MA 02111-1307  USA
 *
 * @author Sam
 */

package com.caucho.quercus;

/**
 * Records the source file location of a statement or expression.
 */
public class Location {
  public static final Location UNKNOWN = new Location();

  private final String _fileName;
  private final String _userPath;

  private final int _lineNumber;
  private final String _className;
  private final String _functionName;
  
  //++ Taint Analysis
  private boolean _taintInstrument = false;  

  public Location(String fileName,
                  int lineNumber, String className,
                  String functionName)
  {
    _fileName = fileName;
    _userPath = fileName;

    _lineNumber = lineNumber;
    _className = className;
    _functionName = functionName;
  }

  public Location(String fileName, String userPath,
                  int lineNumber, String className,
                  String functionName)
  {
    _fileName = fileName;
    _userPath = userPath;

    _lineNumber = lineNumber;
    _className = className;
    _functionName = functionName;
  }
  
  /*
   * ++ Taint Analysis
   */
  private Location(Location other, int lineNumber){
  	_fileName = other._fileName;
  	_userPath = other._userPath;
  	
  	_lineNumber = lineNumber;
  	_className = other._className;
  	_functionName = other._functionName;
  }
  
  public static final Location getInstTaintLocation(Location curLoc) {
  	Location instLoc = new Location(curLoc, curLoc.getLineNumber() + 1);
  	
  	instLoc._taintInstrument = true;
  	
  	return instLoc;
  }
  
  /*
   * ++ Taint Analysis
   */
  public final Location createNextLocation() {
  	Location retLoc = new Location( this, _lineNumber + 1 );
  	return retLoc;
  }
  
  public boolean getTaintInstrument(){
  	return _taintInstrument;
  }
  
  public void setTaintInstrument(boolean taintInstrument){
  	_taintInstrument = taintInstrument;
  }

  private Location()
  {
    _fileName = null;
    _userPath = null;

    _lineNumber = 0;
    _className = null;
    _functionName = null;
  }

  public String getFileName()
  {
    return _fileName;
  }

  public String getUserPath()
  {
    return _userPath;
  }

  public int getLineNumber()
  {
    return _lineNumber;
  }

  public String getClassName()
  {
    return _className;
  }

  public String getFunctionName()
  {
    return _functionName;
  }

  /**
   * Returns a prefix of the form "filename:linenumber: ", or the empty string
   * if the filename is not known.
   */
  public String getMessagePrefix()
  {
    if (_fileName == null)
      return "";
    else
      return _fileName + ":" + _lineNumber + ": ";
  }

  public boolean isUnknown()
  {
    return _fileName == null || _lineNumber <= 0;
  }

  public String toString()
  {
    return "Location[" + _fileName + ":" + _lineNumber + "]";
  }
}
