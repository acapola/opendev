/*
 *************************************************************************
 *************************************************************************
 **                                                                     **
 **  V2KPARSE                                                           **
 **  Copyright (C) 2008-2009    Karl W. Pfalzer                         **
 **                                                                     **
 **  This program is free software; you can redistribute it and/or      **
 **  modify it under the terms of the GNU General Public License        **
 **  as published by the Free Software Foundation; either version 2     **
 **  of the License, or (at your option) any later version.             **
 **                                                                     **
 **  This program is distributed in the hope that it will be useful,    **
 **  but WITHOUT ANY WARRANTY; without even the implied warranty of     **
 **  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the      **
 **  GNU General Public License for more details.                       **
 **                                                                     **
 **  You should have received a copy of the GNU General Public License  **
 **  along with this program; if not, write to the                      **
 **  Free Software Foundation, Inc.                                     **
 **  51 Franklin Street, Fifth Floor                                    **
 **  Boston, MA  02110-1301, USA.                                       **
 **                                                                     **
 *************************************************************************
 *************************************************************************
 */
package v2k.util;

import java.util.*;
import java.io.*;

public class ProcArgs {
	public ProcArgs() {
	}
	
	public ProcArgs add(Spec spec) {
		m_specs.put(spec.getName(), spec);
		return this;
	}
	
	public static class ArgException extends Exception {
		ArgException(String msg) {
			super(msg);
		}
	}
	
	public Map<String, List<String>> parse(final String argv[]) throws ArgException {
		String ai;
		m_errors = new StringBuffer();
		Spec speci;
		for (int i = 0; i < argv.length; i++) {
			ai = argv[i];
			if (false == ai.startsWith("-")) {
				speci = m_specs.get(".");	//for arg w/ no option specifier
			}
			else {
				speci = m_specs.get(ai);
			}
			if (null == speci) {
				m_errors.append(String.format("Error: \"%s\": invalid option.\n", ai));
				if (++i < argv.length) {
					if (argv[i].startsWith("-")) {
						i--;
					}
					//else skip over arg to invalid option
				}
				continue;	//for
			}
			if (Spec.EType.eNone == speci.getType()) {
				update(speci);
			}
			else if (speci.getName().equals(".")) {
				update(speci, ai);
			}
			else if (++i >= argv.length) {
				m_errors.append(String.format("Error: \"%s\": missing argument.\n", ai));
			}
			else {
				update(speci, argv[i]);
			}
		}
		for (Spec opt : m_specs.values()) {
			switch (opt.getRepeat()) {
				case eOne: case eOneOrMore:
					if ( !opt.getName().equals(".") && (false == m_opts.containsKey(opt.getName()))) {
						m_errors.append(String.format("Error: \"%s\": option must be specified.\n", 
								opt.getName()));
					}
					break;
				default:
			}
		}
		if (0 < m_errors.length()) {
			throw new ArgException(m_errors.toString());
		}
		return m_opts;
	}
	
	private void update(Spec spec, String val) {
		List<String> vals = m_opts.get(spec.getName());
		if (null == vals) {
			vals = new LinkedList<String>();
			m_opts.put(spec.getName(), vals);
		}
		switch (spec.getRepeat()) {
			case eOne: case eZeroOrOne:
				if (0 < vals.size()) {
					m_errors.append(String.format("Error: \"%s\": option specified >1 time.\n", spec.getName()));
				}
				break;
		}
		vals.add(val);
		File file, dir;
		switch (spec.getType()) {
			case eNumber:
				try {
					Integer.parseInt(val);
				}
				catch (Exception ex) {
					m_errors.append(String.format("Error: \"%s\": argument of \"%s\" is not a number.\n", 
							val, spec.getName()));
				}
				break;
			case eReadableFile: case eReadableDir:
				file = new File(val);
				if (false == file.canRead()) {
					if (spec.getName().equals(".")) {
						m_errors.append(String.format("Error: \"%s\": is not readable.\n",
								val));
					}
					else {
						m_errors.append(String.format("Error: \"%s\": argument of \"%s\" is not readable.\n",
								val, spec.getName()));
					}
				}
				else {
					String badWhat = null;
					switch (spec.getType()) {
						case eReadableFile:
							if (false == file.isFile()) {
								badWhat = "file";
							}
							break;
						case eReadableDir:
							if (false == file.isDirectory()) {
								badWhat = "directory";
							}
							break;
					}
					if (null != badWhat) {
						final String fmt = "Error: \"%s\": argument of \"%s\" is not readable (%s).\n";
						m_errors.append(String.format(fmt, val, spec.getName(), badWhat));
					}
				}
				break;
			case eWriteableFile:
				file = new File(val);
				if (file.isFile() && !file.canWrite()) {
					m_errors.append(String.format("Error: \"%s\": argument of \"%s\" is not writeable.\n",
							val, spec.getName()));
				}
				else if (file.isDirectory()) {
					m_errors.append(String.format("Error: \"%s\": argument of \"%s\" is existing directory, expecting file.\n",
							val, spec.getName()));
				}
				else if (false == file.exists()) {
					boolean ok = true;
					if (null != file.getParent()) {
						dir = new File(file.getParent());
						if (false == dir.isDirectory()) {
							ok = dir.mkdirs();
						}
						ok = ok & dir.canWrite();
					}
					if (false == ok) {
						m_errors.append(String.format("Error: \"%s\": argument of \"%s\" is not writeable.\n",
								val, spec.getName()));
					}
				}
				break;
			default:
		}
	}
	
	private void update(Spec spec) {
		update(spec, null);
	}
	
	public static class Spec {
		
		public static enum EType {
			eString, eNumber, 
			eReadableFile, eReadableDir,
			eWriteableFile,
			eNone
		}
		
		public Spec(String nm, EType type, String usage) {
			int lix = nm.length() - 1;
			switch (nm.charAt(nm.length()-1)) {
				case '*':
					m_repeat = ERepeat.eZeroOrMore;
					break;
				case '+':
					m_repeat = ERepeat.eOneOrMore;
					break;
				case '?':
					m_repeat = ERepeat.eZeroOrOne;
					break;
				default:
					m_repeat = ERepeat.eOne;
					lix++;
			}
			m_optNm = nm.substring(0, lix);
			m_type = type;
			m_usage = usage;
		}
		
		public Spec(String nm, EType type) {
			this(nm, type, null);
		}

		public Spec(String nm) {
			this(nm, EType.eNone, null);
		}

		String getName() {
			return m_optNm;
		}
		
		ERepeat getRepeat() {
			return m_repeat;
		}
		
		EType getType() {
			return m_type;
		}
		
		static enum ERepeat {
			eOne, eZeroOrMore, eOneOrMore, eZeroOrOne
		}
		
		private String	m_optNm;
		private ERepeat	m_repeat;
		private EType	m_type;
		private String	m_usage;
	}
	
	private StringBuffer m_errors;
	private Map<String, List<String>> m_opts = new HashMap<String, List<String>>();
	private Map<String, Spec> m_specs = new HashMap<String, Spec>();
	
	public static void main(String argv[]) {
		ProcArgs pargs = new ProcArgs();
		pargs.add(new Spec("--sw1"))
			 .add(new Spec("--num1+", Spec.EType.eNumber))
			 .add(new Spec("-I*", Spec.EType.eReadableDir))
			 .add(new Spec("-o", Spec.EType.eWriteableFile))
			 .add(new Spec("--gcc", Spec.EType.eString))
			 ;
		try {
			pargs.parse(argv);
		}
		catch (ProcArgs.ArgException ex) {
			System.err.print(ex.getMessage());
		}
	}
}
