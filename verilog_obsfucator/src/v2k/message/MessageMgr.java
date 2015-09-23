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

package v2k.message;
import  java.util.*;
import  java.io.*;
import  v2k.util.Utils;

/**
 *
 * @author karl
 */
public class MessageMgr {
    
    public static void initialize() {
        if (null == getTheOne()) {
            stTheOne = new MessageMgr();
        }
    }
    /**Encapsulate message.*/
    public static class Message {
    	/**Create message.
    	 * 
    	 * @param severity	one of 'I', 'W', 'E' (for Info, Warning, Error, respectively).
    	 * @param code		a valid message code used to find message format.
    	 * @param args		arguments to pass to format.
    	 */
    	public Message(char severity, String code, Object ... args) {
    		initialize();
            m_type = getTheOne().getMessenger().factory(severity);
            m_message = format(m_type, code, args);
    	}
    	public void print() {
    		MessageMgr.print(this);
    	}
    	/**Get the message type.*/
    	IMessenger.EType getType() {
    		return m_type;
    	}
    	/**Get formatted message.*/
    	String getMessage() {
    		return m_message;
    	}
    	/**Message type*/
    	private IMessenger.EType	m_type;
    	/**Formatted message.*/
    	private String	m_message;
    }
    /**Format message.*/
    private static String format(IMessenger.EType type, String code, Object ... args) {
        String fmt = getTheOne().getFormat(code);
    	Utils.invariant(null != fmt);
        StringBuffer buf = new StringBuffer(type.getPfx());
        buf.append(": ");
        buf.append(String.format(fmt, args));
        buf.append(String.format("  (%s)", code));
        return buf.toString();
    }
    /**Conditionally display message.*/
    public static void message(boolean doMsg, char severity, String code, Object ... args) {
        if (doMsg) {
        	Message msg = new Message(severity, code, args);
        	print(msg);
        }
    }

    public static void message(char severity, String code, Object ... args) {
        Message msg = new Message(severity, code, args);
        print(msg);
    }
    public static void message(String severity, String code, Object ... args) {
        message(severity.charAt(0), code, args);
    }
    public static void print(Message msg) {
        getTheOne().getMessenger().message(msg);
        getTheOne().m_msgCnts[msg.getType().getIx()]++;
    }
    
    public static int getErrorCnt() {
        MessageMgr t = getTheOne();
		return (null == t) ? 0 : t.m_msgCnts[2];
    }
    
    private static MessageMgr getTheOne() {
        return stTheOne;
    }
    
    /** Creates a new instance of MessageMgr */
    private MessageMgr() {
        init();
    }
    
    private void init() {
        String root = Utils.getToolRoot();
        File f = new File(new File(root, stSubdir), stFname);
        try {
            LineNumberReader rdr = new LineNumberReader(new FileReader(f));
            String line;
            int mark;
            String msgCode;
            String msg;
            StringTokenizer toks;
            while (null != (line = rdr.readLine())) {
                if (0 <= (mark = line.indexOf("//"))) {
                    line = line.substring(0, mark);
                }
                if (1 > line.length()) {
                    continue;
                }
                line = line.trim();
                mark = line.indexOf(' ');
                msgCode = line.substring(0, mark);
                msg = line.substring(mark).trim();
                Utils.invariant(null == m_msgs.put(msgCode, msg));
            }
        }
        catch (Exception ex) {
            Utils.abnormalExit(ex);
        }
    }
    
    public static abstract class IMessenger {
        public static enum EType {
            eInfo (System.out, "Info ", 0), 
            eWarn (System.out, "Warn ", 1), 
            eError(System.err, "Error", 2);
            /**Create An Enum based on code.*/
            public static EType factory(char code) {
            	return EType.values()[m_charMapToEnum.indexOf(code)];
            }
            public PrintStream getOstrm() {
            	return m_os;
            }
            public String getPfx() {
            	return m_pfx;
            }
            public int getIx() {
            	return m_ix;
            }
            
            EType(PrintStream os, String pfx, int ix) {
            	m_os = os;
            	m_pfx = pfx;
            	m_ix = ix;
            }
            /**PrintStream for type/severity of message.
             * @see java.io.PrintStream
             */
            private final PrintStream	m_os;
            /**Message prefix based on severity of message.*/
            private final String 		m_pfx;
            /**Index of code.*/
            private final int 			m_ix;
            /**The following in order as EType ordinals.
             * @see java.lang.Enum
             */
            private final static String m_charMapToEnum = "IWE";
        }
        public EType factory(char code) {
        	return EType.factory(code);
        }
        public abstract void message(Message msg);
    }
    
    public static class DefaultMessenger extends IMessenger {
        public void message(Message msg) {
            PrintStream os = msg.getType().getOstrm();
            os.println(msg.getMessage());
            os.flush();
        }
     }

    public IMessenger getMessenger() {
        return m_messenger;
    }
    public String getFormat(String code) {
    	return m_msgs.get(code);
    }
    private static final String stSubdir = "props";
    private static final String stFname  = "parser.msg.txt";
    private static MessageMgr stTheOne = null;

    private Map<String, String> m_msgs = new HashMap<String, String>();
    private IMessenger m_messenger = new DefaultMessenger();
    private int m_msgCnts[] = new int[] {0, 0, 0};
}
