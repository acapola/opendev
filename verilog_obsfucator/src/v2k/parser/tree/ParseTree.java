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
import static v2k.parser.VlogParserTokenTypes.*;
import static v2k.util.Utils.invariant;
import v2k.parser.tree.symbol.ScopeManager;
import v2k.parser.tree.TreeSymbol;
import  antlr.Token;

/**
 *
 * @author karl
 */
public class ParseTree extends ASTreeBase {
    
    public ParseTree() {
    }
    @Override
    public void addSymbol(Ident id) {
        TreeSymbol.add(id);
    }
    @Override
    public Vnumber number(Token tok) {
        return new Vnumber(tok);
    }
    @Override
    public Vstring string(Token tok) {
        return new Vstring(tok);
    }
    @Override
	public BlockIdent blockIdentifier(Ident id) {
		return new BlockIdent(id);
	}
    @Override
	public EventIdent eventIdentifier(Ident id) {
		return new EventIdent(id);
	}
    @Override
	public FuncIdent funcIdentifier(Ident id) {
		return new FuncIdent(id);
	}
	@Override
	public GenerateBlockIdent generateBlockIdentifier(Ident id) {
		return new GenerateBlockIdent(id);
	}
	@Override
	public GenvarIdent genvarIdentifier(Ident id) {
		return new GenvarIdent(id);
	}
	@Override
	public HierEventIdent hierarchicalEventIdentifier(HierIdent id) {
		return new HierEventIdent(id);
	}
	@Override
	public HierFunctionIdent hierarchicalFunctionIdentifier(HierIdent id) {
		return new HierFunctionIdent(id);
	}
	@Override
	public HierParameterIdent hierarchicalParameterIdentifier(HierIdent id) {
		return new HierParameterIdent(id);
	}
	@Override
	public HierTaskIdent hierarchicalTaskIdentifier(HierIdent id) {
		return new HierTaskIdent(id);
	}
	@Override
	public ModuleIdent moduleIdentifier(Ident id) {
		return new ModuleIdent(id);
	}
	@Override
	public ModuleInstanceIdent moduleInstanceIdentifier(Ident id) {
		return new ModuleInstanceIdent(id);
	}
	@Override
	public NetIdent netIdentifier(Ident id) {
		return new NetIdent(id);
	}
	@Override
	public ParameterIdent parameterIdentifier(Ident id) {
		return new ParameterIdent(id);
	}
	@Override
	public PortIdent portIdentifier(Ident id) {
		return new PortIdent(id);
	}
	@Override
	public RealIdent realIdentifier(Ident id) {
		return new RealIdent(id);
	}
	@Override
	public SpecparamIdent specparamIdentifier(Ident id) {
		return new SpecparamIdent(id);
	}
	@Override
	public SystemFunctionIdent systemFunctionIdentifier(Token tok) {
		return new SystemFunctionIdent(tok);
	}
	@Override
	public SystemTaskIdent systemTaskIdentifier(Token tok) {
		return new SystemTaskIdent(tok);
	}
	@Override
	public VariableIdent variableIdentifier(Ident id) {
		return new VariableIdent(id);
	}
	@Override
	public TaskIdent taskIdentifier(Ident id) {
		return new TaskIdent(id);
	}
    @Override
    public Primary primary(Vnumber n) {
        return new Primary(n);
    }
    @Override
    public Primary primary(Vstring s) {
        return new Primary(s);
    }
    @Override
    public Primary primary(FunctionCall s) {
        return new Primary(s);
    }
    @Override
    public Primary primary(SystemFunctionCall s) {
        return new Primary(s);
    }
    @Override
    public Primary primary(Concatenation s) {
        return new Primary(s);
    }
    @Override
    public Primary primary(MultConcatenation s) {
        return new Primary(s);
    }
    @Override
    public Primary primary(MinTypMaxExpression s) {
        return new Primary(s);
    }
    @Override
    public Primary primary(HierIdent hi, PartSelect ps) {
        return new Primary(hi, ps);
    }
    @Override
    public ConstPrimary constantPrimary(Primary p) {
        return new ConstPrimary(p);
    }
    @Override
    public UnaryOp unaryOp(int op) {
        return new UnaryOp(op);
    }
    @Override
    public BinaryOp binaryOp(int op) {
        return new BinaryOp(op);
    }
    @Override
    public Expression expression(Object e1, Object e2) {
        return new Expression(e1, e2);
    }
    @Override
    public Object expression_1(Object uop, Object prim) {
        return new Object[]{uop, prim};
    }
    @Override
    public Object expression_2(boolean isTernary, Object e1, Object e2, Object e3) {
        if (null==e2) {
            return null;
        }
        return new Object[]{new Boolean(isTernary), e1, e2, e3};
    }
    @Override
    public FunctionCall functionCall(HierFunctionIdent hfi, Expression ex) {
        return new FunctionCall(hfi, ex);
    }
    @Override
    public void functionCall(FunctionCall fc, Expression ex) {
        fc.add(ex);
    }
    @Override
    public SystemFunctionCall systemFunctionCall(SystemFunctionIdent sfi) {
        return new SystemFunctionCall(sfi);
    }
    @Override
    public void systemFunctionCall(SystemFunctionCall sfc, Expression exp) {
        sfc.add(exp);
    }
    @Override
    public HierIdent hierarchicalIdentifier(Ident id) {
        return new HierIdent(id);
    }
    @Override
    public Object hierarchicalIdentifier2(Ident id, ConstExpression cex) {
        return new HierIdent.Id2(id, cex);
    }
    @Override
    public void hierarchicalIdentifier2(Object hid, HierIdent hid2) {
        ((HierIdent.Id2)hid).add(hid2);
    }
    @Override
    public ConstExpression constantExpression(Expression ex) {
        return new ConstExpression(ex);
    }
    @Override
    public MinTypMaxExpression minTypMaxExpression(Expression mtm[]) {
        return new MinTypMaxExpression(mtm);
    }
    @Override
    public Concatenation concatenation(Concatenation cc, Expression exp) {
        if (null == cc) {
            cc = new Concatenation(exp);
        } else {
            cc.add(exp);
        }
        return cc;
    }
    @Override
    public MultConcatenation multipleConcatenation(ConstExpression cexp,
            Concatenation cc) {
        return new MultConcatenation(cexp, cc);
    }
    @Override
    public RangeExpression rangeExpression(Expression lhs, int op, ConstExpression rhs) {
        return new RangeExpression(lhs, op, rhs);
    }
    @Override
    public PartSelect partSelect(PartSelect ps, RangeExpression rexp) {
        if (null == ps) {
            ps = new PartSelect();
        }
        ps.add(rexp);
        return ps;
    }
    @Override
    public Object lvalue2(HierIdent hi, PartSelect ps) {
        return new Lvalue.Lv2(hi, ps);
    }
    @Override
    public Lvalue lvalue(Object o1) {
        return new Lvalue((Lvalue.Lv2)o1);
    }
    @Override
    public ListOf<Lvalue> lvalue(ListOf<Lvalue> lof, Lvalue lval) {
        if (null == lof) {
            lof = new ListOf<Lvalue>();
        }
        lof.add(lval);
        return lof;
    }
    @Override
    public Lvalue lvalue(ListOf<Lvalue> lof) {
        return new Lvalue(lof);
    }
    @Override
    public ConstRangeExpression constantRangeExpression(ConstExpression ce1,
                int op, ConstExpression ce2) {
        return new ConstRangeExpression(ce1, op, ce2);
    }
    @Override
    public ConstMinTypMaxExpression constantMinTypeMaxExpression(ConstExpression c0,
            ConstExpression c1, ConstExpression c2) {
        ConstExpression ce[] = {c0,c1,c2};
        return new ConstMinTypMaxExpression(ce);
    }
    @Override
    public TaskEnable taskEnable(HierTaskIdent hti) {
        return new TaskEnable(hti);
    }
    @Override
    public void taskEnable(TaskEnable te, Expression ex) {       
        te.add(ex);
    }
    @Override
    public SystemTaskEnable systemTaskEnable(SystemTaskIdent hti) {
        return new SystemTaskEnable(hti);
    }
    @Override
    public void systemTaskEnable(SystemTaskEnable te, Expression ex) {
        te.add(ex);
    }
    @Override
    public LoopStatement loopStatement(Statement st) {
        return new LoopStatement(st);
    }
    @Override
    public LoopStatement loopStatement(int tk, Expression ex, Statement st) {
        return new LoopStatement(tk, ex, st);
    }
    @Override
    public LoopStatement loopStatement(VariableAssignment init, Expression test,
                        VariableAssignment iter, Statement stmt) {
        return new LoopStatement(init, test, iter, stmt);
    }
    @Override
    public CaseItem caseItem(Expression exp) {
        return new CaseItem(exp);
    }
    @Override
    public CaseItem caseItem(Statement stmt) {
        return new CaseItem(stmt);
    }
    @Override
    public void caseItem(CaseItem ci, Expression ex) {     
        ci.add(ex);
    }
    @Override
    public void caseItem(CaseItem ci, Statement stmt) {       
        ci.add(stmt);
    }
    @Override
    public CaseStatement caseStatement(int tk, Expression ex) {
        return new CaseStatement(tk, ex);
    }
    @Override
    public void caseStatement(CaseStatement cs, CaseItem ci) {
        cs.add(ci);
    }
    @Override
    public ConditionalStatement conditionalStatement(Expression ex, 
            Statement ifs, Statement elses) {
        return new ConditionalStatement(ex, ifs, elses);
    }
    @Override
    public WaitStatement waitStatement(Expression ex, Statement st) {
        return new WaitStatement(ex, st);
    }
    @Override
    public ProceduralTimingControl proceduralTimingControl(DelayControl dc) {
        return new ProceduralTimingControl(dc);
    }
    @Override
    public ProceduralTimingControl proceduralTimingControl(EventControl ec) {
        return new ProceduralTimingControl(ec);
    }
    @Override
    public Object event_expression_2(boolean isOr, EventExpression ee, Object e2) {
        return new EventExpression.E2(isOr, ee, e2);
    }
    @Override
    public Object event_expression_1(int tk, Expression exp) {
        return new EventExpression.E1(tk, exp);
    }
    @Override
    public EventExpression eventExpression(Object e1, Object e2) {
        return new EventExpression(e1, e2);
    }
    @Override
    public EventTrigger eventTrigger(HierEventIdent hei) {
        return new EventTrigger(hei);
    }
    @Override
    public void eventTrigger(EventTrigger et, Expression ex) {       
        et.add(ex);
    }
    @Override    
    public EventControl eventControl(HierEventIdent hei) {
        return new EventControl(hei);
    }
    @Override
    public EventControl eventControl(EventExpression ee) {
        return new EventControl(ee);
    }
    @Override
    public EventControl eventControl(boolean isStar) {
        return new EventControl(isStar);
    }
    @Override
    public DisableStmt disableStatement(HierIdent hi) {
        return new DisableStmt(hi);
    }
    @Override
    public DelayOrEventControl delayOrEventControl(DelayControl dc) {
        return new DelayOrEventControl(dc);
    }
    @Override
    public DelayOrEventControl delayOrEventControl(EventControl ec) {
        return new DelayOrEventControl(ec);
    }
    @Override
    public DelayOrEventControl delayOrEventControl(Expression exp, EventControl ec) {
        return new DelayOrEventControl(exp, ec);
    }
    @Override
    public DelayValue delayValue(Vnumber vn) {
        return new DelayValue(vn);
    }
    @Override
    public DelayValue delayValue(Ident id) {
        return new DelayValue(id);
    }
    @Override 
    public DelayControl delayControl(DelayValue dv) {
        return new DelayControl(dv);
    }
    @Override
    public DelayControl delayControl(MinTypMaxExpression mtm) {
        return new DelayControl(mtm);
    }
    @Override
    public FuncStatement functionStatement(Statement st) {
        return new FuncStatement(st);
    }
    @Override
    public BlockingAssign blockingAssignment(Lvalue lv, DelayOrEventControl dec,
                Expression exp) {
        return new BlockingAssign(lv, dec, exp);
    }
    @Override
    public NonBlockingAssign nonBlockingAssignment(Lvalue lv, DelayOrEventControl dec,
                Expression exp) {
        return new NonBlockingAssign(lv, dec, exp);
    }
    @Override
    public SeqBlock seqBlock(SeqBlock sb, BlockIdent id) {
        if (null == sb) {
            sb = new SeqBlock(id);
        }
        return sb;
    }
    @Override
    public SeqBlock seqBlock(SeqBlock sb, BlockIdent id, BlockItemDecl bid) {
        sb = seqBlock(sb, id);
        sb.add(bid);
        return sb;
    }
    @Override
    public SeqBlock seqBlock(SeqBlock sb, BlockIdent id, Statement st) {
        sb = seqBlock(sb, id);
        sb.add(st);
        return sb;
    }
    @Override
    public ParBlock parBlock(ParBlock pb, BlockIdent id) {
        if (null == pb) {
            pb = new ParBlock(id);
        }
        return pb;
    }
    @Override
    public ParBlock parBlock(ParBlock pb, BlockIdent id, BlockItemDecl bid) {
        pb = parBlock(pb, id);
        pb.add(bid);
        return pb;
    }
    @Override
    public ParBlock parBlock(ParBlock pb, BlockIdent id, Statement st) {
        pb = parBlock(pb, id);
        pb.add(st);
        return pb;
    }
    @Override
    public ProceduralContinuousAssign procContAssign(VariableAssignment vas, boolean isAssign) {
        return new ProceduralContinuousAssign(vas, isAssign);
    }
    @Override
    public ProceduralContinuousAssign procContAssign(Lvalue lv, boolean isDeassign) {
        return new ProceduralContinuousAssign(lv, isDeassign);
    }
    @Override
    public VariableAssignment variableAssignment(Lvalue lv, Expression exp) {
        return new VariableAssignment(lv, exp);
    }
    @Override
    public ProceduralTimingControlStatement procTimingControlStmt(
            ProceduralTimingControl pc, Statement st) {
        return new ProceduralTimingControlStatement(pc, st);
    }
    @Override
    public AlwaysConstruct alwaysConstruct(Statement st) {
        return new AlwaysConstruct(st);
    }
    @Override
    public InitialConstruct initialConstruct(Statement st) {
        return new InitialConstruct(st);
    }
    @Override
    public NetAssign netAssignment(Lvalue lhs, Expression rhs) {
        return new NetAssign(lhs, rhs);
    }
    @Override
    public ContinuousAssign continuousAssign(Object d3, Object na) {
        return new ContinuousAssign((Delay3) d3, (ListOf<NetAssign>) na);
    }
    @Override
    public GenerateBlock generateBlock(GenerateBlockIdent gb,
            ModuleOrGenerateItem item) {
       GenerateBlock r = new GenerateBlock(gb);
       if (null != item) {
           r.add(item);
       }
       return r;
    }
    @Override
    public void generateBlock(GenerateBlock to, ModuleOrGenerateItem item) {
        to.add(item);
    }
    @Override
    public CaseGenerateItem caseGenerateItem(CaseGenerateItem to, 
            ConstExpression ce, GenerateBlock gb) {
        if (null == to) {
            if (null != ce) {
                to = new CaseGenerateItem(ce);
            } else {
                to = new CaseGenerateItem(gb);
            }
        } else if (null != ce) {
            to.add(ce);
        } else {
            to.set(gb);
        }
        return to;
    }
    @Override
    public CaseGenerateConstruct caseGenerateConstruct(CaseGenerateConstruct to,
            ConstExpression ce, CaseGenerateItem cgi) {
        if (null == to) {
            to = new CaseGenerateConstruct(ce);
        } else {
            to.add(cgi);
        }
        return to;
    }
    @Override
    public IfGenerateConstruct ifGenerateConstruct(ConstExpression cexpr,
            GenerateBlock ifBlk, GenerateBlock elseBlk) {
        return new IfGenerateConstruct(cexpr, ifBlk, elseBlk);
    }
    @Override
    public ConditionalGenerateConstruct conditionalGenerateConstruct(
            IfGenerateConstruct igc, CaseGenerateConstruct cg) {
        return ((null == cg) ? new ConditionalGenerateConstruct(igc)
                    : new ConditionalGenerateConstruct(cg));
    }
    @Override
    public GenvarPrimary genvarPrimary(ConstPrimary cp, GenvarIdent gi) {
        return ((null == gi) ? new GenvarPrimary(cp)
                    : new GenvarPrimary(gi));
    }
    @Override
    public GenvarIteration genvarIteration(GenvarIdent lhs, GenvarExpression rhs) {
        return new GenvarIteration(lhs, rhs);
    }
    @Override
    public Object genvarExpression(UnaryOp uop, GenvarPrimary gp) {
        return new GenvarExpression.E1(uop, gp);
    }
    @Override
    public Object genvarExpression(BinaryOp bop, GenvarExpression gv1, 
            GenvarExpression gv2, Object e2) {
        return ((null != bop)
            ? new GenvarExpression.E2(bop, gv1, e2)
            : new GenvarExpression.E2(gv1, gv2, e2));
    }
    @Override
    public GenvarExpression genvarExpression(Object e1, Object e2) {
        return new GenvarExpression(e1, e2);
    }
    @Override
    public GenvarInit genvarInit(GenvarIdent lhs, ConstExpression rhs) {
        return new GenvarInit(lhs, rhs);
    }
    @Override
    public LoopGenerateConstruct loopGenerateConstruct(GenvarInit init,
            GenvarExpression test,  GenvarIteration iter, 
            GenerateBlock blk) {
        return new LoopGenerateConstruct(init, test, iter, blk);
    }
    @Override
    public NamedPortConnection namedPortConnection(PortIdent pi, Expression exp) {
        return new NamedPortConnection(pi, exp);
    }
    @Override
    public ListOf<PortConnection> listOfPortConnections(ListOf<PortConnection> lpc,
            Expression exp, NamedPortConnection npc) {
        PortConnection pc = (null != exp)
            ? new PortConnection(exp) : new PortConnection(npc);
        return addToList(lpc, pc);
    }
    @Override
    public ModuleInstance moduleInstance() {
        return new ModuleInstance();
    }
    @Override
    public void moduleInstance(ModuleInstance inst, ListOf<PortConnection> lopc) {
        inst.add(lopc);
    }
    @Override
    public void moduleInstance(ModuleInstance inst, ModuleInstanceIdent nm,
            Range rng) {
        inst.set(nm, rng);
    }
    @Override
    public ListOf<ModuleInstance> moduleInstantiation(ListOf<ModuleInstance> lom,
            Ident refNm, ParameterValueAssignment pva, ModuleInstance mi) {
        mi.set(refNm, pva);
        lom = addToList(lom, mi);
        return lom;
    }
    @Override
    public ListOf<ParameterAssignment> listOfParamAssigns(ListOf<ParameterAssignment> lopa,
            Expression exp, NamedParamAssignment npa) {
        ParameterAssignment pa = (null != exp)
            ? new ParameterAssignment(exp)
            : new ParameterAssignment(npa);
        lopa = addToList(lopa, pa);
        return lopa;
    }
    @Override
    public NamedParamAssignment namedParameterAssignment(ParameterIdent id,
            MinTypMaxExpression exp) {
        return new NamedParamAssignment(id, exp);
    }  
    @Override
    public ParameterValueAssignment parameterValueAssignment(ListOf<ParameterAssignment> lopa) {
        return new ParameterValueAssignment(lopa);
    }
    @Override
    public BlockRealType blockRealType(RealIdent id,  ListOf<Dimension> lod) {
        return new BlockRealType(id, lod);
    }
    @Override
    public BlockVariableType blockVariableType(VariableIdent id,  ListOf<Dimension> lod) {
        return new BlockVariableType(id, lod);
    }
    @Override
    public BlockItemDecl blockItemDecl(boolean isSigned, Range rng,
            ListOf<BlockVariableType> lov) {
        return new BlockItemDecl(isSigned, rng, lov);
    }
    @Override
    public BlockItemDecl blockItemDecl(int la, ListOf<BlockVariableType> lov) {
        return new BlockItemDecl(la, lov);
    }
    @Override
    public BlockItemDecl blockItemDecl(int la, ListOf<BlockRealType> lov, boolean nil) {
        return new BlockItemDecl(la, lov, nil);
    }
    @Override
	public BlockItemDecl blockItemDecl(EventDecl ele) {
		return new BlockItemDecl(ele);
	}
    @Override
	public BlockItemDecl blockItemDecl(LocalParameterDecl ele) {
		return new BlockItemDecl(ele);
	}
    @Override
	public BlockItemDecl blockItemDecl(ParameterDeclaration ele) {
		return new BlockItemDecl(ele);
	}
    @Override
    public TaskPortType taskPortType(int la) {
        return new TaskPortType(la, false, null);
    }
    @Override
    public TaskPortType taskPortType(boolean isReg, boolean isSigned, Range rng) {
        int type = (isReg) ? LITERAL_reg : -1;
        return new TaskPortType(type, isSigned, rng);
    }
    @Override
    public TfPortDeclaration tfPortDecl(int dir, TaskPortType tpt, ListOf<PortIdent> lop) {
        return new TfPortDeclaration(dir, tpt, lop);
    }
    @Override
    public TaskItemDecl taskItemDecl(BlockItemDecl tid, TfPortDeclaration tpd) {
        return (null != tid)
                ? new TaskItemDecl(tid)
                : new TaskItemDecl(tpd);
    }
    @Override
    public TaskDeclaration taskDecl(boolean isAuto, TaskIdent ti) {
        return new TaskDeclaration(isAuto, ti);
    }
    @Override
    public void taskDecl(TaskDeclaration td, TaskItemDecl tid) {
        td.add(tid);
    }
    @Override
    public void taskDecl(TaskDeclaration td,  ListOf<TfPortDeclaration> lop) {
        td.add(lop);
    }
    @Override
    public void taskDecl(TaskDeclaration td, BlockItemDecl bid) {
        td.add(bid);
    }
    @Override
    public void taskDecl(TaskDeclaration td, Statement st) {
        td.add(st);
    }
    @Override
    public FuncType funcType(int tok, Range rng) {
        return new FuncType((-1 == tok) ? LITERAL_signed : tok, rng);
    }
    @Override
    public FuncItemDecl funcItemDecl( TfPortDeclaration tpd, BlockItemDecl bid) {
        return (null != tpd)
            ?  new FuncItemDecl(tpd)
            :  new FuncItemDecl(bid);
    }
    @Override
    public FuncDecl funcDecl(boolean isAuto, FuncType type, FuncIdent id,
            ListOf<FuncItemDecl> items, ListOf<TfPortDeclaration> ports,
            ListOf<BlockItemDecl> blkItems, FuncStatement stmt) {
        return new FuncDecl(isAuto, type, id, items, ports, blkItems, stmt);
    }
    @Override
    public Range range(ConstExpression msb, ConstExpression lsb) {
        return new Range(msb, lsb);
    }
    @Override
    public Dimension dimension(ConstExpression msb, ConstExpression lsb) {
        return new Dimension(msb, lsb);
    }
    @Override
    public SpecparamAssign specparamAssign(SpecparamIdent id, ConstMinTypMaxExpression mtm) {
        return new SpecparamAssign(id, mtm);
    }
    @Override
    public ParamAssign paramAssign(ParameterIdent id, ConstMinTypMaxExpression mtm) {
        return new ParamAssign(id, mtm);
    }
    @Override
    public NetDeclAssign netDeclAssign(NetIdent id, Expression exp) {
        return new NetDeclAssign(id, exp);
    }
    @Override
    public DefparamAssign defparamAssign(HierParameterIdent hpi, ConstMinTypMaxExpression mtm) {
        return new DefparamAssign(hpi, mtm);
    }
    @Override
    public PortIdents portIdents(PortIdent id, ConstExpression expr) {
        return new PortIdents(id, expr);
    }
    @Override
    public VariableType varType(VariableIdent id, ListOf<Dimension> ele, ConstExpression expr) {
        return (null != expr)
            ? new VariableType(id, expr)
            : new VariableType(id, ele);
    }
    @Override
    public RealType realType(RealIdent id, ListOf<Dimension> ele, ConstExpression expr) {
        return (null != expr)
            ? new RealType(id, expr)
            : new RealType(id, ele);
    }
    @Override
    public OutputVarType outputVarType(int la1) {
        return new OutputVarType(la1);
    }
    @Override
    public NetType netType(int la1) {
        return new NetType(la1);
    }
    @Override
    public TimeDecl timeDecl(ListOf<VariableType> ids) {
        return new TimeDecl(ids);
    }
    @Override
    public RegDecl regDecl(boolean isSigned, Range rng, ListOf<VariableType> vid) {
        return new RegDecl(isSigned, rng, vid);
    }
    @Override
    public RealtimeDecl realtimeDecl(ListOf<RealType> lort) {
        return new RealtimeDecl(lort);
    }
    @Override
    public RealDecl realDecl(ListOf<RealType> lort) {
        return new RealDecl(lort);
    }
    @Override
    public NetIdentifiers netIdentifiers(NetIdentifiers ni, NetIdent id, Dimension dim) {
        if (null == ni) {
            ni = new NetIdentifiers(id);
        } else {
            ni.add(dim);
        }
        return ni;
    }
    @Override
    public EventIdentifiers eventIdentifiers(EventIdentifiers ni, EventIdent id, Dimension dim) {
        if (null == ni) {
            ni = new EventIdentifiers(id);
        } else {
            ni.add(dim);
        }
        return ni;
    }
    @Override
    public NetDeclaration netDeclaration(NetType type, boolean isSigned, Range rng,
            Delay3 del3, ListOf<NetIdentifiers> nets, ListOf<NetDeclAssign> decls) {
        return new NetDeclaration(type, isSigned, rng, del3, nets, decls);
    }
    @Override
    public Delay3 delay3(ListOf<DelayValue> lod) {
        return new Delay3(lod);
    }
    @Override
    public Delay2 delay2(Delay3 d3) {
        return new Delay2(d3);
    }
    @Override
    public EventDecl eventDecl(ListOf<EventIdentifiers> loei) {
        return new EventDecl(loei);
    }
    @Override
    public PortDeclaration portDecl(boolean isOnlyDefn, int m_dir,
            NetType m_type, Range m_rng,
            OutputVarType m_ovt, boolean m_isReg, boolean m_isSigned,
            ListOf<? extends PortIdent> m_ids) {
        return new PortDeclaration(isOnlyDefn, m_dir, m_type, m_rng, m_ovt, m_isReg, m_isSigned, m_ids);
    }
	@Override
	public ParameterType parameterType(int la1) {
		return new ParameterType(la1);
	}
    @Override
    public SpecparamDecl specparamDecl(Range rng, ListOf<SpecparamAssign> spa) {
        return new SpecparamDecl(rng, spa);
    }
    @Override
    public ParameterDeclaration parameterDecl(boolean m_isSigned, Range m_range,
            ParameterType m_type, ListOf<ParamAssign> m_assigns) {
        return new ParameterDeclaration(m_isSigned, m_range, m_type, m_assigns);
    }
    @Override
    public LocalParameterDecl localParameterDecl(boolean m_isSigned, Range m_range,
            ParameterType m_type, ListOf<ParamAssign> m_assigns) {
        return new LocalParameterDecl(m_isSigned, m_range, m_type, m_assigns);
    }
    @Override
	public NonPortModuleItem nonPortModuleItem(ModuleOrGenerateItem ele) {
		return new NonPortModuleItem(ele);
	}
    @Override
	public NonPortModuleItem nonPortModuleItem(ListOf<ModuleOrGenerateItem> ele) {
		return new NonPortModuleItem(ele);
	}
    @Override
	public NonPortModuleItem nonPortModuleItem(ParameterDeclaration ele) {
		return new NonPortModuleItem(ele);
	}
    @Override
	public NonPortModuleItem nonPortModuleItem(SpecparamDecl ele) {
		return new NonPortModuleItem(ele);
	}
    @Override
    public IntegerDecl integerDecl(ListOf<VariableType> lovt) {
        return new IntegerDecl(lovt);
    }
	@Override
	public ModuleOrGenerateItemDecl moduleOrGenItemDecl(NetDeclaration ele) {
		return new ModuleOrGenerateItemDecl(ele);
	}
	@Override
	public ModuleOrGenerateItemDecl moduleOrGenItemDecl(RegDecl ele) {
		return new ModuleOrGenerateItemDecl(ele);
	}
	@Override
	public ModuleOrGenerateItemDecl moduleOrGenItemDecl(IntegerDecl ele) {
		return new ModuleOrGenerateItemDecl(ele);
	}
	@Override
	public ModuleOrGenerateItemDecl moduleOrGenItemDecl(RealDecl ele) {
		return new ModuleOrGenerateItemDecl(ele);
	}
	@Override
	public ModuleOrGenerateItemDecl moduleOrGenItemDecl(TimeDecl ele) {
		return new ModuleOrGenerateItemDecl(ele);
	}
	@Override
	public ModuleOrGenerateItemDecl moduleOrGenItemDecl(RealtimeDecl ele) {
		return new ModuleOrGenerateItemDecl(ele);
	}
	@Override
	public ModuleOrGenerateItemDecl moduleOrGenItemDecl(EventDecl ele) {
		return new ModuleOrGenerateItemDecl(ele);
	}
	@Override
	public ModuleOrGenerateItemDecl moduleOrGenItemDecl(ListOf<GenvarIdent> ele) {
		return new ModuleOrGenerateItemDecl(ele);
	}
	@Override
	public ModuleOrGenerateItemDecl moduleOrGenItemDecl(TaskDeclaration ele) {
		return new ModuleOrGenerateItemDecl(ele);
	}
	@Override
	public ModuleOrGenerateItemDecl moduleOrGenItemDecl(FuncDecl ele) {
		return new ModuleOrGenerateItemDecl(ele);
	}
	@Override
	public ModuleOrGenerateItem moduleOrGenItem(ModuleOrGenerateItemDecl ele) {
		return new ModuleOrGenerateItem(ele);
	}
	@Override
	public ModuleOrGenerateItem moduleOrGenItem(LocalParameterDecl ele) {
		return new ModuleOrGenerateItem(ele);
	}
	@Override
	public ModuleOrGenerateItem moduleOrGenItem(ListOf<DefparamAssign> ele, boolean ununsed) {
		return new ModuleOrGenerateItem(ele, true);
	}
	@Override
	public ModuleOrGenerateItem moduleOrGenItem(ContinuousAssign ele) {
		return new ModuleOrGenerateItem(ele);
	}
	@Override
	public ModuleOrGenerateItem moduleOrGenItem(ListOf<ModuleInstance> ele) {
		return new ModuleOrGenerateItem(ele);
	}
	@Override
	public ModuleOrGenerateItem moduleOrGenItem(InitialConstruct ele) {
		return new ModuleOrGenerateItem(ele);
	}
	@Override
	public ModuleOrGenerateItem moduleOrGenItem(AlwaysConstruct ele) {
		return new ModuleOrGenerateItem(ele);
	}
	@Override
	public ModuleOrGenerateItem moduleOrGenItem(LoopGenerateConstruct ele) {
		return new ModuleOrGenerateItem(ele);
	}
	@Override
	public ModuleOrGenerateItem moduleOrGenItem(ConditionalGenerateConstruct ele) {
		return new ModuleOrGenerateItem(ele);
	}
    @Override
	public ModuleItem moduleItem(PortDeclaration ele) {
		return new ModuleItem(ele);
	}
    @Override
	public ModuleItem moduleItem(NonPortModuleItem ele) {
		return new ModuleItem(ele);
	}
    @Override
    public PortReference portReference(PortIdent id, ConstRangeExpression range) {
        return new PortReference(id, range);
    }
    @Override
    public PortExpression portExpression(PortExpression pexpr, PortReference pref) {
        if (null == pexpr) {
            pexpr = new PortExpression(pref);
        } else {
            pexpr.add(pref);
        }
        return pexpr;
    }
    @Override
    public Port port(PortIdent pid, PortExpression expr) {
        return new Port(pid, expr);
    }
    @Override
    public ModuleDeclaration moduleDeclaration(ModuleIdent mid, ListOf<ParameterDeclaration> lopd)
            throws TreeException {
        return new ModuleDeclaration(mid, lopd);
    }
    @Override
    public void moduleDeclaration(ModuleDeclaration mdecl,
            boolean isAnsi, ListOf<PortDeclaration> lports) {
        assert true==isAnsi;
        mdecl.setPorts(isAnsi, lports);
    }
    @Override
    public void moduleDeclaration(ModuleDeclaration mdecl, NonPortModuleItem npmi) {
        mdecl.addItem(npmi);
    }
    @Override
    public void moduleDeclaration(ModuleDeclaration mdecl, ListOf<Port> lports) {
        mdecl.setPorts(false, lports);
    }
    @Override
    public void moduleDeclaration(ModuleDeclaration mdecl, ModuleItem mi) {
        mdecl.addItem(mi);
    }
    @Override
	public Statement statement(BlockingAssign ba) {
		return new Statement(ba);
	}
    @Override
	public Statement statement(NonBlockingAssign ba) {
		return new Statement(ba);
	}
    @Override
	public Statement statement(CaseStatement cs) {
		return new Statement(cs);
	}
    @Override
	public Statement statement(ConditionalStatement cs) {
		return new Statement(cs);
	}
    @Override
	public Statement statement(DisableStmt ds) {
		return new Statement(ds);
	}
    @Override
	public Statement statement(EventTrigger et) {
		return new Statement(et);
	}
    @Override
	public Statement statement(LoopStatement ls) {
		return new Statement(ls);
	}
    @Override
	public Statement statement(SystemTaskEnable ste) {
		return new Statement(ste);
	}
    @Override
	public Statement statement(TaskEnable te) {
		return new Statement(te);
	}
    @Override
	public Statement statement(WaitStatement ws) {
		return new Statement(ws);
	}
    @Override
	public Statement statement(SeqBlock sb) {
		return new Statement(sb);
	}
    @Override
	public Statement statement(ParBlock pb) {
		return new Statement(pb);
	}
    @Override
    public Statement statement(ProceduralContinuousAssign pca) {
        return new Statement(pca);
    }
    @Override
    public Statement statement(ProceduralTimingControlStatement ps) {
        return new Statement(ps);
    }
    @Override
    public void popScope() {
        ScopeManager smgr = ScopeManager.getTheOne();
        smgr.pop();
        invariant(null != smgr.peek());
    }


    ////
    @Override
    public <E> ListOf<E> addToList(ListOf<E> lo, E ele) {
        if (null == lo) {
            lo = new ListOf<E>();
        }
        lo.add(ele);
        return lo;
    }
}

