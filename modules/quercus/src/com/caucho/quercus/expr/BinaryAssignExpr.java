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
 * @author Scott Ferguson
 */

package com.caucho.quercus.expr;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.caucho.quercus.Location;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.Value;

/**
 * Represents a PHP assignment expression.
 */
public class BinaryAssignExpr extends Expr {
  protected final AbstractVarExpr _var;
  protected final Expr _value;

  private static final Logger log = Logger.getLogger(BinaryAssignExpr.class.getName()); 
  
  public BinaryAssignExpr(Location location, AbstractVarExpr var, Expr value)
  {
    super(location);

    _var = var;
    _value = value;    
  }

  public BinaryAssignExpr(AbstractVarExpr var, Expr value)
  {
    _var = var;
    _value = value;       
  }

  /**
   * Creates a assignment
   * @param location
   */
  @Override
  public Expr createCopy(ExprFactory factory)
  {
    // quercus/3d9e
    return factory.createCopy(this);
  }

  /**
   * Returns true if a static false value.
   */
  @Override
  public boolean isAssign()
  {
    return true;
  }

  /**
   * Evaluates the expression.
   *
   * @param env the calling environment.
   *
   * ++ Taint Analysis
   *
   * @return the expression value.
   */
  @Override
  public Value eval(Env env)
  {
  	Value retVal = _var.evalAssignValue(env, _value);   	
    
  	if ( env.getQuercus().runTaintAnalysis() ) {
  		if ( _value instanceof CallExpr ) {
  			CallExpr c = (CallExpr) _value;
  			String funName = c.getName().toString();
  			
    		//PHP sanitizers generally take the string to sanitize as first argument.
    		//We could improve this by specifying in the configuration
    		//file ta-sanitizer.cfg which parameter gets sanitized.
  			if ( env.isTaintSanitizerFunction(funName) ) {		
  				String funArgName = "";
  				if ( c._args.length > 0 && null != c._args[0] )
  					funArgName = c._args[0].toString();  		
  			  			  				  	
					log.log(Level.INFO, "[TAINT ANALYSIS][BinaryAssignExpr.eval(Env)]: '" 
									+ funArgName + "' has been sanitized by " + funName + "." );   
					
					//Make the assigned value untainted
					_var.setTaint(null);
					
					//Write FirePHP log
					env.addInfoFirePHPLog(_var, funName);  				
  			}
  		}
  	}
  	
    return retVal;
  }

  /**
   * Evaluates the expression.
   *
   * @param env the calling environment.
   *
   * @return the expression value.
   */
  @Override
  public Value evalCopy(Env env)
  {
    // php/0d9e
    return eval(env).copy();
  }

  /**
   * Evaluates the expression.
   *
   * @param env the calling environment.
   *
   * @return the expression value.
   */
  @Override
  public Value evalRef(Env env)
  {
    Value value = _value.evalCopy(env);

    _var.evalAssignValue(env, value);

    // php/03d9, php/03mk
    return _var.eval(env);
  }

  public String toString()
  {
    return _var + "=" + _value;
  }
}

