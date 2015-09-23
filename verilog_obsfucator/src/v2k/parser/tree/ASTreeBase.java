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
import  antlr.Token;
import  v2k.message.ExceptionBase;
import  v2k.util.Pair;

public abstract class ASTreeBase {

    protected static class TreeException extends ExceptionBase {
        public TreeException(char severity, String code, Object ... args) {
            super(severity, code, args);
        }
    }
    /**
     * Add symbol.
     * @param id symbol to add.
     */
    public void addSymbol(Ident id) {
        //nothing;
    }
    public ModuleDeclaration moduleDeclaration(ModuleIdent mid, ListOf<ParameterDeclaration> lopd)
            throws TreeException {
        return null;
    }
    public void moduleDeclaration(ModuleDeclaration mdecl,
            boolean isAnsi, ListOf<PortDeclaration> lports) {
        //nothing
    }
    public void moduleDeclaration(ModuleDeclaration mdecl, NonPortModuleItem npmi) {
        //nothing
    }
    public void moduleDeclaration(ModuleDeclaration mdecl, ListOf<Port> lports) {
        //nothing
    }
    public void moduleDeclaration(ModuleDeclaration mdecl, ModuleItem mi) {
        //nothing
    }
    public void moduleDeclaration(ModuleDeclaration mdeclDone) {
        //nothing
    }
    public Vnumber number(Token tok) {
        return null;
    }
    public Vstring string(Token tok) {
        return null;
    }
    public Ident identifier(Token tok) {
        return new Ident(tok);
    }
	public BlockIdent blockIdentifier(Ident id) {
		return null;
	}
	public EventIdent eventIdentifier(Ident id) {
		return null;
	}
	public FuncIdent funcIdentifier(Ident id) {
		return null;
	}
	public GenerateBlockIdent generateBlockIdentifier(Ident id) {
		return null;
	}
	public GenvarIdent genvarIdentifier(Ident id) {
		return null;
	}
	public HierEventIdent hierarchicalEventIdentifier(HierIdent id) {
		return null;
	}
	public HierFunctionIdent hierarchicalFunctionIdentifier(HierIdent id) {
		return null;
	}
	public HierParameterIdent hierarchicalParameterIdentifier(HierIdent id) {
		return null;
	}
	public HierTaskIdent hierarchicalTaskIdentifier(HierIdent id) {
		return null;
	}
	public ModuleIdent moduleIdentifier(Ident id) {
		return null;
	}
	public ModuleInstanceIdent moduleInstanceIdentifier(Ident id) {
		return null;
	}
	public NetIdent netIdentifier(Ident id) {
		return null;
	}
	public ParameterIdent parameterIdentifier(Ident id) {
		return null;
	}
	public PortIdent portIdentifier(Ident id) {
		return null;
	}
	public RealIdent realIdentifier(Ident id) {
		return null;
	}
	public SpecparamIdent specparamIdentifier(Ident id) {
		return null;
	}
	public SystemFunctionIdent systemFunctionIdentifier(Token tok) {
		return null;
	}
	public SystemTaskIdent systemTaskIdentifier(Token tok) {
		return null;
	}
	public VariableIdent variableIdentifier(Ident id) {
		return null;
	}
	public TaskIdent taskIdentifier(Ident id) {
		return null;
	}
    public Primary primary(Vnumber n) {
        return null;
    }
    public Primary primary(Vstring s) {
        return null;
    }
    public Primary primary(FunctionCall s) {
        return null;
    }
    public Primary primary(SystemFunctionCall s) {
        return null;
    }
    public Primary primary(Concatenation s) {
        return null;
    }
    public Primary primary(MultConcatenation s) {
        return null;
    }
    public Primary primary(MinTypMaxExpression s) {
        return null;
    }
    public Primary primary(HierIdent hi, PartSelect ps) {
        return null;
    }
    public ConstPrimary constantPrimary(Primary p) {
        return null;
    }
    public UnaryOp unaryOp(int op) {
        return null;
    }
    public BinaryOp binaryOp(int op) {
        return null;
    }
    public Expression expression(Object e1, Object e2) {
        return null;
    }
    public Object expression_1(Object uop, Object prim) {
        return null;
    }
    public Object expression_2(boolean isTernary, Object e1, Object e2, Object e3) {
        return null;
    }
    public FunctionCall functionCall(HierFunctionIdent hfi, Expression ex) {
        return null;
    }
    public void functionCall(FunctionCall fc, Expression ex) {
    }
    public SystemFunctionCall systemFunctionCall(SystemFunctionIdent sfi) {
        return null;
    }
    public void systemFunctionCall(SystemFunctionCall sfc, Expression exp) {
    }
    public HierIdent hierarchicalIdentifier(Object obj) {
        return null;
    }
    public HierIdent hierarchicalIdentifier(Ident id) {
        return null;
    }
    public Object hierarchicalIdentifier2(Ident id, ConstExpression cex) {
        return null;
    }
    public void hierarchicalIdentifier2(Object hid, HierIdent hid2) {
    }
    public ConstExpression constantExpression(Expression ex) {
        return null;
    }
    public MinTypMaxExpression minTypMaxExpression(Expression mtm[]) {
        return null;
    }
    public Concatenation concatenation(Concatenation cc, Expression exp) {
        return null;
    }
    public MultConcatenation multipleConcatenation(ConstExpression cexp,
            Concatenation cc) {
        return null;
    }
    public RangeExpression rangeExpression(Expression lhs, int op, ConstExpression rhs) {
        return null;
    }
    public PartSelect partSelect(PartSelect ps, RangeExpression rexp) {
        return null;
    }
    public Object lvalue2(HierIdent hi, PartSelect ps) {
        return null;
    }
    public Lvalue lvalue(Object o1) {
        return null;
    }
    public ListOf<Lvalue> lvalue(ListOf<Lvalue> lof, Lvalue lval) {
        return null;
    }
    public Lvalue lvalue(ListOf<Lvalue> lof) {
        return null;
    }
    public ConstRangeExpression constantRangeExpression(ConstExpression ce1,
                int op, ConstExpression ce2) {
        return null;
    }
    public ConstMinTypMaxExpression constantMinTypeMaxExpression(ConstExpression c0,
            ConstExpression c1, ConstExpression c2) {
        return null;
    }
    public TaskEnable taskEnable(HierTaskIdent hti) {
        return null;
    }
    public void taskEnable(TaskEnable te, Expression ex) {       
    }
    public SystemTaskEnable systemTaskEnable(SystemTaskIdent hti) {
        return null;
    }
    public void systemTaskEnable(SystemTaskEnable te, Expression ex) {       
    }
    public LoopStatement loopStatement(Statement st) {
        return null;
    }
    public LoopStatement loopStatement(int tk, Expression ex, Statement st) {
        return null;
    }
    public LoopStatement loopStatement(VariableAssignment init, Expression test,
                        VariableAssignment iter, Statement stmt) {
        return null;
    }
    public CaseItem caseItem(Expression exp) {
        return null;
    }
    public CaseItem caseItem(Statement stmt) {
        return null;
    }
    public void caseItem(CaseItem ci, Expression ex) {     
    }
    public void caseItem(CaseItem ci, Statement stmt) {       
    }
    public CaseStatement caseStatement(int tk, Expression ex) {
        return null;
    }
    public void caseStatement(CaseStatement cs, CaseItem ci) {
    }
    public ConditionalStatement conditionalStatement(Expression ex, 
            Statement ifs, Statement elses) {
        return null;
    }
    public WaitStatement waitStatement(Expression ex, Statement st) {
        return null;
    }
    public ProceduralTimingControl proceduralTimingControl(DelayControl dc) {
        return null;
    }
    public ProceduralTimingControl proceduralTimingControl(EventControl ec) {
        return null;
    }
    public Object event_expression_2(boolean isOr, EventExpression ee, Object e2) {
        return null;
    }
    public Object event_expression_1(int tk, Expression exp) {
        return null;
    }
    public EventExpression eventExpression(Object e1, Object e2) {
        return null;
    }
    public EventTrigger eventTrigger(HierEventIdent hei) {
        return null;
    }
    public void eventTrigger(EventTrigger et, Expression ex) {       
    }
    public EventControl eventControl(HierEventIdent hei) {
        return null;
    }
    public EventControl eventControl(EventExpression ee) {
        return null;
    }
    public EventControl eventControl(boolean isStar) {
        return null;
    }
    public DisableStmt disableStatement(HierIdent hi) {
        return null;
    }
    public DelayOrEventControl delayOrEventControl(DelayControl dc) {
        return null;
    }
    public DelayOrEventControl delayOrEventControl(EventControl ec) {
        return null;
    }
    public DelayOrEventControl delayOrEventControl(Expression exp, EventControl ec) {
        return null;
    }
    public DelayValue delayValue(Vnumber vn) {
        return null;
    }
    public DelayValue delayValue(Ident id) {
        return null;
    }
    public DelayControl delayControl(DelayValue dv) {
        return null;
    }
    public DelayControl delayControl(MinTypMaxExpression mtm) {
        return null;
    }
    public FuncStatement functionStatement(Statement st) {
        return null;
    }
    public BlockingAssign blockingAssignment(Lvalue lv, DelayOrEventControl dec,
                Expression exp) {
        return null;
    }
    public NonBlockingAssign nonBlockingAssignment(Lvalue lv, DelayOrEventControl dec,
                Expression exp) {
        return null;
    }
	public Statement statement(BlockingAssign ba) {
		return null;
	}
	public Statement statement(NonBlockingAssign ba) {
		return null;
	}
	public Statement statement(CaseStatement cs) {
		return null;
	}
	public Statement statement(ConditionalStatement cs) {
		return null;
	}
	public Statement statement(DisableStmt ds) {
		return null;
	}
	public Statement statement(EventTrigger et) {
		return null;
	}
	public Statement statement(LoopStatement ls) {
		return null;
	}
	public Statement statement(SystemTaskEnable ste) {
		return null;
	}
	public Statement statement(TaskEnable te) {
		return null;
	}
	public Statement statement(WaitStatement ws) {
		return null;
	}
	public Statement statement(SeqBlock sb) {
		return null;
	}
	public Statement statement(ParBlock pb) {
		return null;
	}
    public Statement statement(ProceduralContinuousAssign pca) {
        return null;
    }
    public Statement statement(ProceduralTimingControlStatement ps) {
        return null;
    }
    public SeqBlock seqBlock(SeqBlock sb, BlockIdent id) {
        return null;
    }
    public SeqBlock seqBlock(SeqBlock sb, BlockIdent id, BlockItemDecl bid) {
        return null;
    }
    public SeqBlock seqBlock(SeqBlock sb, BlockIdent id, Statement st) {
        return null;
    }
    public ParBlock parBlock(ParBlock pb, BlockIdent id) {
        return null;
    }
    public ParBlock parBlock(ParBlock pb, BlockIdent id, BlockItemDecl bid) {
        return null;
    }
    public ParBlock parBlock(ParBlock pb, BlockIdent id, Statement st) {
        return null;
    }
    public ProceduralContinuousAssign procContAssign(VariableAssignment vas, boolean isAssign) {
        return null;
    }
    public ProceduralContinuousAssign procContAssign(Lvalue lv, boolean isDeassign) {
        return null;
    }
    public VariableAssignment variableAssignment(Lvalue lv, Expression exp) {
        return null;
    }
    public ProceduralTimingControlStatement procTimingControlStmt(
            ProceduralTimingControl pc, Statement st) {
        return null;
    }
    public AlwaysConstruct alwaysConstruct(Statement st) {
        return null;
    }
    public InitialConstruct initialConstruct(Statement st) {
        return null;
    }
    public NetAssign netAssignment(Lvalue lhs, Expression rhs) {
        return null;
    }
    public ContinuousAssign continuousAssign(Object d3, Object na) {
        return null;
    }
    public GenerateBlock generateBlock(GenerateBlockIdent gb,
            ModuleOrGenerateItem item) {
        return null;
    }
    public void generateBlock(GenerateBlock to, ModuleOrGenerateItem item) {
    }
    public CaseGenerateItem caseGenerateItem(CaseGenerateItem to, 
            ConstExpression ce, GenerateBlock gb) {
        return null;
    }
    public CaseGenerateConstruct caseGenerateConstruct(CaseGenerateConstruct to,
            ConstExpression ce, CaseGenerateItem cgi) {
        return null;
    }
    public IfGenerateConstruct ifGenerateConstruct(ConstExpression cexpr,
            GenerateBlock ifBlk, GenerateBlock elseBlk) {
        return null;
    }
    public ConditionalGenerateConstruct conditionalGenerateConstruct(
            IfGenerateConstruct igc, CaseGenerateConstruct cg) {
        return null;
    }
    public GenvarPrimary genvarPrimary(ConstPrimary cp, GenvarIdent gi) {
        return null;
    }
    public GenvarIteration genvarIteration(GenvarIdent lhs, GenvarExpression rhs) {
        return null;
    }
    public Object genvarExpression(UnaryOp uop, GenvarPrimary gp) {
        return null;
    }
    public Object genvarExpression(BinaryOp bop, GenvarExpression gv1, 
            GenvarExpression gv2, Object e2) {
        return null;
    }
    public GenvarExpression genvarExpression(Object e1, Object e2) {
        return null;
    }
    public GenvarInit genvarInit(GenvarIdent lhs, ConstExpression rhs) {
        return null;
    }
    public LoopGenerateConstruct loopGenerateConstruct(GenvarInit init,
            GenvarExpression test,  GenvarIteration iter, 
            GenerateBlock blk) {
        return null;
    }
    public NamedPortConnection namedPortConnection(PortIdent pi, Expression exp) {
        return null;
    }
    public ModuleInstance moduleInstance() {
        return null;
    }
    public void moduleInstance(ModuleInstance inst, ModuleInstanceIdent nm,
            Range rng) {
        //nothing
    }
    public void moduleInstance(ModuleInstance inst, ListOf<PortConnection> lopc) {
        //nothing
    }
    public ListOf<PortConnection> listOfPortConnections(ListOf<PortConnection> lpc,
            Expression exp, NamedPortConnection npc) {
        return null;
    }
    public ListOf<ModuleInstance> moduleInstantiation(ListOf<ModuleInstance> lom,
            Ident refNm, ParameterValueAssignment pva, ModuleInstance mi) {
        return null;
    }
    public ListOf<ParameterAssignment> listOfParamAssigns(ListOf<ParameterAssignment> lopa,
            Expression exp, NamedParamAssignment npa) {
        return null;
    }
    public NamedParamAssignment namedParameterAssignment(ParameterIdent id,
            MinTypMaxExpression exp) {
        return null;
    }
    public ParameterValueAssignment parameterValueAssignment(ListOf<ParameterAssignment> lopa) {
        return null;
    }
    public BlockRealType blockRealType(RealIdent id,  ListOf<Dimension> lod) {
        return null;
    }
    public BlockVariableType blockVariableType(VariableIdent id,  ListOf<Dimension> lod) {
        return null;
    }
    public BlockItemDecl blockItemDecl(boolean isSigned, Range rng,
            ListOf<BlockVariableType> lov) {
        return null;
    }
    public BlockItemDecl blockItemDecl(int la, ListOf<BlockVariableType> lov) {
        return null;
    }
    public BlockItemDecl blockItemDecl(int la, ListOf<BlockRealType> lov, boolean nil) {
        return null;
    }
	public BlockItemDecl blockItemDecl(EventDecl ele) {
		return null;
	}
	public BlockItemDecl blockItemDecl(LocalParameterDecl ele) {
		return null;
	}
	public BlockItemDecl blockItemDecl(ParameterDeclaration ele) {
		return null;
	}
    public TaskPortType taskPortType(int la) {
        return null;
    }
    public TaskPortType taskPortType(boolean isReg, boolean isSigned, Range rng) {
        return null;
    }
    public TfPortDeclaration tfPortDecl(int dir, TaskPortType tpt, ListOf<PortIdent> lop) {
        return null;
    }
    public TaskItemDecl taskItemDecl(BlockItemDecl tid, TfPortDeclaration tpd) {
        return null;
    }
    public TaskDeclaration taskDecl(boolean isAuto, TaskIdent ti) {
        return null;
    }
    public void taskDecl(TaskDeclaration td, TaskItemDecl tid) {
        //nothing
    }
    public void taskDecl(TaskDeclaration td,  ListOf<TfPortDeclaration> lop) {
        //nothing
    }
    public void taskDecl(TaskDeclaration td, BlockItemDecl bid) {
        //nothing
    }
    public void taskDecl(TaskDeclaration td, Statement st) {
        //nothing
    }
    public FuncType funcType(int tok, Range rng) {
        return null;
    }
    public FuncItemDecl funcItemDecl( TfPortDeclaration tpd, BlockItemDecl bid) {
        return null;
    }
    public FuncDecl funcDecl(boolean isAuto, FuncType type, FuncIdent id,
            ListOf<FuncItemDecl> items, ListOf<TfPortDeclaration> ports,
            ListOf<BlockItemDecl> blkItems, FuncStatement stmt) {
        return null;
    }
    public Range range(ConstExpression msb, ConstExpression lsb) {
        return null;
    }
    public Dimension dimension(ConstExpression msb, ConstExpression lsb) {
        return null;
    }
    public SpecparamAssign specparamAssign(SpecparamIdent id, ConstMinTypMaxExpression mtm) {
        return null;
    }
    public ParamAssign paramAssign(ParameterIdent id, ConstMinTypMaxExpression mtm) {
        return null;
    }
    public NetDeclAssign netDeclAssign(NetIdent id, Expression exp) {
        return null;
    }
    public DefparamAssign defparamAssign(HierParameterIdent hpi, ConstMinTypMaxExpression mtm) {
        return null;
    }
    public PortIdents portIdents(PortIdent id, ConstExpression expr) {
        return null;
    }
    public VariableType varType(VariableIdent id, ListOf<Dimension> ele, ConstExpression expr) {
        return null;
    }
    public RealType realType(RealIdent id, ListOf<Dimension> ele, ConstExpression expr) {
        return null;
    }
    public OutputVarType outputVarType(int la1) {
        return null;
    }
    public NetType netType(int la1) {
        return null;
    }
    public TimeDecl timeDecl(ListOf<VariableType> ids) {
        return null;
    }
    public RegDecl regDecl(boolean isSigned, Range rng, ListOf<VariableType> vid) {
        return null;
    }
    public RealtimeDecl realtimeDecl(ListOf<RealType> lort) {
        return null;
    }
    public RealDecl realDecl(ListOf<RealType> lort) {
        return null;
    }
    public NetIdentifiers netIdentifiers(NetIdentifiers ni, NetIdent id, Dimension dim) {
        return null;
    }
    public EventIdentifiers eventIdentifiers(EventIdentifiers ni, EventIdent id, Dimension dim) {
        return null;
    }
    public NetDeclaration netDeclaration(NetType type, boolean isSigned, Range rng,
            Delay3 del3, ListOf<NetIdentifiers> nets, ListOf<NetDeclAssign> decls) {
        return null;
    }
    public Delay3 delay3(ListOf<DelayValue> lod) {
        return null;
    }
    public Delay2 delay2(Delay3 d3) {
        return null;
    }
    public EventDecl eventDecl(ListOf<EventIdentifiers> loei) {
        return null;
    }
    public PortDeclaration portDecl(boolean isOnlyDefn,  int m_dir,
            NetType m_type, Range m_rng,
            OutputVarType m_ovt, boolean m_isReg, boolean m_isSigned,
            ListOf<? extends PortIdent> m_ids) {
        return null;
    }
	public ParameterType parameterType(int la1) {
		return null;
	}
    public SpecparamDecl specparamDecl(Range rng, ListOf<SpecparamAssign> spa) {
        return null;
    }
    public ParameterDeclaration parameterDecl(boolean m_isSigned, Range m_range,
            ParameterType m_type, ListOf<ParamAssign> m_assigns) {
        return null;
    }
    public LocalParameterDecl localParameterDecl(boolean m_isSigned, Range m_range,
            ParameterType m_type, ListOf<ParamAssign> m_assigns) {
        return null;
    }
	public NonPortModuleItem nonPortModuleItem(ModuleOrGenerateItem ele) {
		return null;
	}
	public NonPortModuleItem nonPortModuleItem(ListOf<ModuleOrGenerateItem> ele) {
		return null;
	}
	public NonPortModuleItem nonPortModuleItem(ParameterDeclaration ele) {
		return null;
	}
	public NonPortModuleItem nonPortModuleItem(SpecparamDecl ele) {
		return null;
	}
    public IntegerDecl integerDecl(ListOf<VariableType> lovt) {
        return null;
    }
	public ModuleOrGenerateItemDecl moduleOrGenItemDecl(NetDeclaration ele) {
		return null;
	}
	public ModuleOrGenerateItemDecl moduleOrGenItemDecl(RegDecl ele) {
		return null;
	}
	public ModuleOrGenerateItemDecl moduleOrGenItemDecl(IntegerDecl ele) {
		return null;
	}
	public ModuleOrGenerateItemDecl moduleOrGenItemDecl(RealDecl ele) {
		return null;
	}
	public ModuleOrGenerateItemDecl moduleOrGenItemDecl(TimeDecl ele) {
		return null;
	}
	public ModuleOrGenerateItemDecl moduleOrGenItemDecl(RealtimeDecl ele) {
		return null;
	}
	public ModuleOrGenerateItemDecl moduleOrGenItemDecl(EventDecl ele) {
		return null;
	}
	public ModuleOrGenerateItemDecl moduleOrGenItemDecl(ListOf<GenvarIdent> ele) {
		return null;
	}
	public ModuleOrGenerateItemDecl moduleOrGenItemDecl(TaskDeclaration ele) {
		return null;
	}
	public ModuleOrGenerateItemDecl moduleOrGenItemDecl(FuncDecl ele) {
		return null;
	}
	public ModuleOrGenerateItem moduleOrGenItem(ModuleOrGenerateItemDecl ele) {
		return null;
	}
	public ModuleOrGenerateItem moduleOrGenItem(LocalParameterDecl ele) {
		return null;
	}
	public ModuleOrGenerateItem moduleOrGenItem(ListOf<DefparamAssign> ele, boolean unsed) {
		return null;
	}
	public ModuleOrGenerateItem moduleOrGenItem(ContinuousAssign ele) {
		return null;
	}
	public ModuleOrGenerateItem moduleOrGenItem(ListOf<ModuleInstance> ele) {
		return null;
	}
	public ModuleOrGenerateItem moduleOrGenItem(InitialConstruct ele) {
		return null;
	}
	public ModuleOrGenerateItem moduleOrGenItem(AlwaysConstruct ele) {
		return null;
	}
	public ModuleOrGenerateItem moduleOrGenItem(LoopGenerateConstruct ele) {
		return null;
	}
	public ModuleOrGenerateItem moduleOrGenItem(ConditionalGenerateConstruct ele) {
		return null;
	}
	public ModuleItem moduleItem(PortDeclaration ele) {
		return null;
	}
	public ModuleItem moduleItem(NonPortModuleItem ele) {
		return null;
	}
    public PortReference portReference(PortIdent id, ConstRangeExpression range) {
        return null;
    }
    public PortExpression portExpression(PortExpression pexpr, PortReference pref) {
        return null;
    }
    public Port port(PortIdent pid, PortExpression expr) {
        return null;
    }
    public void popScope() {
        //nothing
    }
    ////
    public <E> ListOf<E> addToList(ListOf<E> lo, E ele) {
        return null;
    }
 }
