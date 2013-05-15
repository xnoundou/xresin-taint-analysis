/*
 * Copyright (c) 1998-2013 Caucho Technology -- all rights reserved
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
 */

package com.caucho.quercus.env;

import static com.caucho.quercus.env.TaintInfo.PropagationType.*;

import static com.caucho.quercus.env.TaintInfo.TaintSource.*;


/**
 * @author noundou
 * ++ Taint Analysis
 */
public class TaintInfo {

	public enum TaintSource {
		NOT_TAINTED, POST, GET, COOKIE, VAR, READ_STREAM
	}

	public enum PropagationType {
		UNDEF, COPY, OP_COPY
	}	
	
	Value _taintedVal;
	TaintSource _taintSrc = NOT_TAINTED;    
	PropagationType _propType = UNDEF;

	public TaintInfo(Value taintedVal, TaintSource taintSrc) {              
		_taintedVal = taintedVal;
		_taintSrc = taintSrc;
	}

	public String toString() {
		StringBuilder res = new StringBuilder("");

		switch(_taintSrc) {
		case GET: res.append( "GET" ); break;
		case POST: res.append( "POST" ); break;         
		case COOKIE: res.append( "COOKIE" ); break;
		case READ_STREAM: res.append( "READ_STREAM" ); break;
		case VAR: res.append( "VAR" ); break;
		default:
			res.append( "NOT_TAINTED" );
			break;
		}

		return res.toString();
	}

	public static TaintInfo getTaintedInfoPOST(Value _taintedVal) {
		TaintInfo t = new TaintInfo(_taintedVal, POST);         
		return t;
	}       

	public static TaintInfo getTaintedInfoGET(Value _taintedVal) {
		TaintInfo t = new TaintInfo(_taintedVal, GET);          
		return t;
	}       

	public static TaintInfo getTaintedInfoCOOKIE(Value _taintedVal) {
		TaintInfo t = new TaintInfo(_taintedVal, COOKIE);               
		return t;
	}       

	public static TaintInfo getTaintedInfoVAR(Value _taintedVal) {
		TaintInfo t = new TaintInfo(_taintedVal, VAR);          
		return t;
	}

	public static TaintInfo getTaintedInfoREADSTREAM(Value _taintedVal) {
		TaintInfo t = new TaintInfo(_taintedVal, READ_STREAM);          
		return t;
	}

	public Value getTaintedVal() {
		return _taintedVal;
	}

	public TaintSource getTaintSrc() {
		return _taintSrc;
	}

	public PropagationType getPropType() {
		return _propType;
	}

	public void setTaintedVal(Value taintedVal) {
		this._taintedVal = taintedVal;
	}

	public void setTaintSrc(TaintSource taintSrc) {
		this._taintSrc = taintSrc;
	}

	public void setPropagationType(PropagationType propType) {
		_propType = propType;
	}       

}
