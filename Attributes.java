/*
*Copyright (C) 2012 FAUX Thomas, HERICE Charlotte, PAYSAN-LAFOSSE Typhaine, SANSEN Joris
*This file is part of Pick_EM program
*Pick_EM is free software; you can redistribute it and/or modify
*it under the terms of the GNU General Public License as published by
*the Free Software Foundation; either version 2 of the License, or
*(at your option) any later version.

*This program is distributed in the hope that it will be useful,
*but WITHOUT ANY WARRANTY; without even the implied warranty of
*MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
*GNU General Public License for more details.

*You should have received a copy of the GNU General Public License along
*with this program; if not, write to the Free Software Foundation, Inc.,
*51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
*/ 

import java.util.Hashtable;

public final class Attributes {
	// This class is used to get all attributes entered by the user
	
	private static Attributes instance = null;
	
	private static Hashtable <String, String> attributes = new Hashtable<String, String>();
	
	private Attributes() {
		super();
	}

	public final static Attributes getInstance() {
		if (Attributes.instance == null) {
			synchronized(Attributes.class) {
				if (Attributes.instance == null) {
					Attributes.instance = new Attributes();
				}
			}
		}
		return Attributes.instance;
	}

	public static void setAttributes(String key, String value){
		attributes.put(key, value);
	}
	
	public static Hashtable <String, String> getAttributes() {
		return attributes;
	}
}