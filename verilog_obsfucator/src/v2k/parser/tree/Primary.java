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
package v2k.parser.tree;

/**
 *
 * @author karl
 */
public class Primary {
    private enum EType {eNumber, eMultConcat, eConcat, eString, 
                        eSystemFuncCall, eFuncCall, eMinTypmax, ePartSel};
    
    public Primary(Vnumber n) {
        m_primary = new ANumber(n);
    }
    public Primary(Vstring n) {
        m_primary = new AString(n);
    }
    public Primary(FunctionCall n) {
        m_primary = new AFcall(n);
    }
    public Primary(SystemFunctionCall n) {
        m_primary = new ASysFcall(n);
    }
    public Primary(MinTypMaxExpression mtm) {
        m_primary = new AMinTypMax(mtm);
    }
    public Primary(Concatenation cc) {
        m_primary = new AConcat(cc);
    }
    public Primary(MultConcatenation mc) {
        m_primary = new AMultConcat(mc);
    }
    public Primary(HierIdent hi, PartSelect ps) {
        m_primary = new APart(hi, ps);
    }
    
    private A   m_primary;
    
    private static abstract class A {
        protected A(EType e) {
            m_type = e;
        }
        final EType m_type;

        @Override
        public String toString() {
            return "A{" +
                    "m_type=" + m_type +
                    '}';
        }
    }
    
    private static class ANumber extends A {
        private ANumber(Vnumber n) {
            super(EType.eNumber);
            m_number = n;
        }
        private final Vnumber    m_number;

        @Override
        public String toString() {
            return m_number.toString();
        }
    }
    private static class AString extends A {
        private AString(Vstring n) {
            super(EType.eString);
            m_string = n;
        }
        private final Vstring    m_string;

        @Override
        public String toString() {
            return m_string.toString();
        }
    }
    private static class AFcall extends A {
        private AFcall(FunctionCall n) {
            super(EType.eFuncCall);
            m_fcall = n;
        }
        private final FunctionCall  m_fcall;

        @Override
        public String toString() {
            return "AFcall{" +
                    "m_fcall=" + m_fcall +
                    '}';
        }
    }
    private static class ASysFcall extends A {
        private ASysFcall(SystemFunctionCall n) {
            super(EType.eSystemFuncCall);
            m_fcall = n;
        }
        private final SystemFunctionCall  m_fcall;

        @Override
        public String toString() {
            return "ASysFcall{" +
                    "m_fcall=" + m_fcall +
                    '}';
        }
    }
    /**
     * In the context of a Primary, this expression was parsed
     * within "( MinTypMax )", so evaluate w/ appropriate/highest
     * precedence.
     */
    private static class AMinTypMax extends A {
        private AMinTypMax(MinTypMaxExpression n) {
            super(EType.eMinTypmax);
            m_mtm = n;
        }
        private final MinTypMaxExpression m_mtm;

        @Override
        public String toString() {
            return "AMinTypMax{" +
                    "m_mtm=" + m_mtm +
                    '}';
        }
    }
    private static class AConcat extends A {
        private AConcat(Concatenation n) {
            super(EType.eConcat);
            m_cc = n;
        }
        private final Concatenation m_cc;

        @Override
        public String toString() {
            return m_cc.toString();
        }
    }
    private static class AMultConcat extends A {
        private AMultConcat(MultConcatenation n) {
            super(EType.eMultConcat);
            m_mc = n;
        }
        private final MultConcatenation m_mc;

        @Override
        public String toString() {
            return "AMultConcat{" +
                    "m_mc=" + m_mc +
                    '}';
        }
    }
    private static class APart extends A {
        private APart(HierIdent hi, PartSelect ps) {
            super(EType.ePartSel);
            m_hi = hi;
            m_ps = ps;
        }
        private HierIdent   m_hi;
        private PartSelect  m_ps;

        @Override
        public String toString() {
            String out = m_hi.toString();
            if(null!=m_ps) out+= m_ps.toString();
            return out;
        }
    }

    @Override
    public String toString() {
        return m_primary.toString();
    }
}
